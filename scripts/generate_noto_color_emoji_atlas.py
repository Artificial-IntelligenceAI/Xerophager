#!/usr/bin/env python3
"""Build Minecraft bitmap-font atlases from Noto Color Emoji PNG artwork."""

from __future__ import annotations

import argparse
import json
import re
from pathlib import Path

from PIL import Image


SOURCE_SIZE = 72
CELL_SIZE = 44
COLUMNS = 16
GLYPHS_PER_ATLAS = 256
FONT_HEIGHT = 11
FONT_ASCENT = 9
SOURCE_PATTERN = re.compile(r"emoji_u([0-9a-f]+)\.png")


def is_supported_single_codepoint(codepoint: int) -> bool:
	# Keep ordinary keyboard characters in Public Sans. Keycaps, regional-indicator
	# flags, and private-use placeholders require sequence shaping that Minecraft's
	# bitmap font provider does not perform.
	if codepoint in {0x23, 0x2A, *range(0x30, 0x3A), 0x20E3}:
		return False
	if 0x1F1E6 <= codepoint <= 0x1F1FF:
		return False
	if 0xE000 <= codepoint <= 0xF8FF:
		return False
	if 0xF0000 <= codepoint <= 0xFFFFD or 0x100000 <= codepoint <= 0x10FFFD:
		return False
	return True


def collect_glyphs(source_directory: Path) -> list[tuple[int, Path]]:
	glyphs: list[tuple[int, Path]] = []
	for path in source_directory.glob("emoji_u*.png"):
		match = SOURCE_PATTERN.fullmatch(path.name)
		if match is None:
			continue
		codepoint = int(match.group(1), 16)
		if is_supported_single_codepoint(codepoint):
			glyphs.append((codepoint, path))
	return sorted(glyphs)


def write_atlases(glyphs: list[tuple[int, Path]], output_root: Path) -> None:
	font_directory = output_root / "font"
	texture_directory = output_root / "textures" / "font"
	font_directory.mkdir(parents=True, exist_ok=True)
	texture_directory.mkdir(parents=True, exist_ok=True)

	for old_atlas in texture_directory.glob("noto_color_emoji_*.png"):
		old_atlas.unlink()

	providers: list[dict[str, object]] = []
	for atlas_index, start in enumerate(range(0, len(glyphs), GLYPHS_PER_ATLAS)):
		page = glyphs[start : start + GLYPHS_PER_ATLAS]
		rows = (len(page) + COLUMNS - 1) // COLUMNS
		atlas = Image.new("RGBA", (COLUMNS * CELL_SIZE, rows * CELL_SIZE), (0, 0, 0, 0))
		characters: list[str] = []

		for row in range(rows):
			row_characters: list[str] = []
			for column in range(COLUMNS):
				glyph_index = row * COLUMNS + column
				if glyph_index >= len(page):
					row_characters.append("\0")
					continue

				codepoint, source_path = page[glyph_index]
				with Image.open(source_path) as source:
					if source.size != (SOURCE_SIZE, SOURCE_SIZE):
						raise ValueError(f"Unexpected source size for {source_path}: {source.size}")
					glyph = source.convert("RGBA").resize(
						(CELL_SIZE, CELL_SIZE), Image.Resampling.LANCZOS
					)
				atlas.alpha_composite(glyph, (column * CELL_SIZE, row * CELL_SIZE))
				row_characters.append(chr(codepoint))
			characters.append("".join(row_characters))

		atlas_name = f"noto_color_emoji_{atlas_index:02d}.png"
		atlas.save(texture_directory / atlas_name, optimize=True)
		providers.append(
			{
				"type": "bitmap",
				"file": f"xerophager:font/{atlas_name}",
				"height": FONT_HEIGHT,
				"ascent": FONT_ASCENT,
				"chars": characters,
			}
		)

	font_definition = {"providers": providers}
	(font_directory / "noto_color_emoji.json").write_text(
		json.dumps(font_definition, ensure_ascii=True, indent="\t") + "\n",
		encoding="utf-8",
	)


def main() -> None:
	parser = argparse.ArgumentParser()
	parser.add_argument(
		"source_directory",
		type=Path,
		help="Path to Noto Emoji's png/72 directory",
	)
	parser.add_argument(
		"output_root",
		type=Path,
		help="Path to assets/xerophager in the mod resources",
	)
	arguments = parser.parse_args()

	glyphs = collect_glyphs(arguments.source_directory)
	if not glyphs:
		raise SystemExit("No single-codepoint emoji PNG files found")
	write_atlases(glyphs, arguments.output_root)
	print(f"Generated {len(glyphs)} glyphs across {(len(glyphs) + 255) // 256} atlases")


if __name__ == "__main__":
	main()
