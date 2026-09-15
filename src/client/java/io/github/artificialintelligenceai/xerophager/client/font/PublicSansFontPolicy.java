package io.github.artificialintelligenceai.xerophager.client.font;

import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

public final class PublicSansFontPolicy {
	private static final ResourceLocation PUBLIC_SANS_BOLD =
			ResourceLocation.fromNamespaceAndPath("xerophager", "public_sans_bold");

	private PublicSansFontPolicy() {
	}

	public static ResourceLocation selectFont(Style style) {
		ResourceLocation requested = style.getFont();
		return style.isBold() && isPublicSansFont(requested) ? PUBLIC_SANS_BOLD : requested;
	}

	public static boolean useSyntheticBold(Style style) {
		return style.isBold() && !isPublicSansFont(style.getFont());
	}

	private static boolean isPublicSansFont(ResourceLocation font) {
		if (font.equals(PUBLIC_SANS_BOLD)) {
			return true;
		}

		if (!font.getNamespace().equals("minecraft")) {
			return false;
		}

		return switch (font.getPath()) {
			case "default", "uniform", "alt", "illageralt" -> true;
			default -> false;
		};
	}
}
