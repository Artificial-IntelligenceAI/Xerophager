package io.github.artificialintelligenceai.xerophager.client.mixin;

import io.github.artificialintelligenceai.xerophager.client.font.PublicSansFontPolicy;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.gui.Font$StringRenderOutput")
public abstract class FontStringRenderOutputMixin {
	@Redirect(
			method = "accept",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/network/chat/Style;getFont()Lnet/minecraft/resources/ResourceLocation;"
			)
	)
	private ResourceLocation xerophager$renderWithRealBoldFont(Style style) {
		return PublicSansFontPolicy.selectFont(style);
	}

	@Redirect(
			method = "accept",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/network/chat/Style;isBold()Z"
			)
	)
	private boolean xerophager$renderWithoutSyntheticBold(Style style) {
		return PublicSansFontPolicy.useSyntheticBold(style);
	}
}
