package io.github.artificialintelligenceai.xerophager.client.gui;

import io.github.artificialintelligenceai.xerophager.client.consent.ConsentState;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public final class ConsentWarningScreen extends Screen {
	private static final int CONTENT_WIDTH = 320;
	private static final int TEXT_COLOR = 0xFFE6E6E6;
	private static final int MUTED_TEXT_COLOR = 0xFFB8B8B8;
	private static final int TITLE_COLOR = 0xFFFF5555;
	private static final ResourceLocation PUBLIC_SANS_BOLD =
			ResourceLocation.fromNamespaceAndPath("xerophager", "public_sans_bold");
	private static final List<Component> WARNING_LINES = List.of(
			Component.translatable("screen.xerophager.warning.introduction"),
			Component.translatable("screen.xerophager.warning.time"),
			Component.translatable("screen.xerophager.warning.position"),
			Component.translatable("screen.xerophager.warning.interface_audio"),
			Component.translatable("screen.xerophager.warning.gamemode"),
			Component.translatable("screen.xerophager.warning.world")
	);

	private final Screen parent;
	private final ConsentState consentState;
	private Component saveError;

	public ConsentWarningScreen(Screen parent, ConsentState consentState) {
		super(Component.translatable("screen.xerophager.warning.title"));
		this.parent = parent;
		this.consentState = consentState;
	}

	@Override
	protected void init() {
		int buttonWidth = 160;
		int buttonX = (width - buttonWidth) / 2;
		int buttonY = Math.min(height - 36, 196);

		addRenderableWidget(Button.builder(
				Component.translatable("screen.xerophager.warning.accept"),
				button -> acceptWarning()
		).bounds(buttonX, buttonY, buttonWidth, 20).build());
	}

	@Override
	public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		graphics.fillGradient(0, 0, width, height, 0xFF120000, 0xFF000000);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		// Screen.render draws the background first and the widgets second. Drawing
		// the warning text afterward keeps it out of Minecraft's background blur.
		super.render(graphics, mouseX, mouseY, delta);

		int centerX = width / 2;
		int contentLeft = Math.max(16, centerX - CONTENT_WIDTH / 2);
		int lineWidth = Math.min(CONTENT_WIDTH, width - 32);
		int y = 28;

		graphics.drawCenteredString(
				font,
				title.copy().withStyle(style -> style.withFont(PUBLIC_SANS_BOLD)),
				centerX,
				y,
				TITLE_COLOR
		);
		y += 28;

		for (Component line : WARNING_LINES) {
			graphics.drawWordWrap(font, line, contentLeft, y, lineWidth, TEXT_COLOR);
			y += font.wordWrapHeight(line, lineWidth) + 6;
		}

		Component intendedBehavior = Component.translatable("screen.xerophager.warning.intended")
				.copy()
				.withStyle(style -> style.withFont(PUBLIC_SANS_BOLD));
		graphics.drawWordWrap(font, intendedBehavior, contentLeft, y + 4, lineWidth, TITLE_COLOR);
		graphics.drawCenteredString(
				font,
				Component.translatable("screen.xerophager.warning.instruction"),
				centerX,
				Math.min(height - 52, 178),
				MUTED_TEXT_COLOR
		);

		if (saveError != null) {
			graphics.drawCenteredString(font, saveError, centerX, Math.min(height - 64, 164), TITLE_COLOR);
		}
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return false;
	}

	@Override
	public void onClose() {
		// Acceptance is required before this screen can close.
	}

	@Override
	public boolean isPauseScreen() {
		return true;
	}

	private void acceptWarning() {
		if (consentState.accept()) {
			minecraft.setScreen(parent);
		} else {
			saveError = Component.translatable("screen.xerophager.warning.save_error");
		}
	}
}
