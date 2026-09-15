package io.github.artificialintelligenceai.xerophager.client.mixin;

import net.minecraft.client.gui.font.FontTexture;
import net.minecraft.client.gui.font.GlyphRenderTypes;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FontTexture.class)
public abstract class FontTextureMixin extends AbstractTexture {
	@Inject(method = "<init>", at = @At("TAIL"))
	private void xerophager$enableSmoothFontSampling(
			GlyphRenderTypes renderTypes,
			boolean colored,
			CallbackInfo callbackInfo
	) {
		setFilter(true, false);
	}
}
