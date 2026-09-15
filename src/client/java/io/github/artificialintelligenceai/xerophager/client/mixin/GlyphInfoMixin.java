package io.github.artificialintelligenceai.xerophager.client.mixin;

import com.mojang.blaze3d.font.GlyphInfo;
import io.github.artificialintelligenceai.xerophager.client.font.FontLayout;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlyphInfo.class)
public interface GlyphInfoMixin {
	@Inject(method = "getAdvance(Z)F", at = @At("RETURN"), cancellable = true)
	private void xerophager$addReadableLetterSpacing(
			boolean bold,
			CallbackInfoReturnable<Float> callbackInfo
	) {
		callbackInfo.setReturnValue(callbackInfo.getReturnValue() + FontLayout.LETTER_SPACING);
	}
}
