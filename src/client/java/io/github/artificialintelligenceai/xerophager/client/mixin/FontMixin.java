package io.github.artificialintelligenceai.xerophager.client.mixin;

import io.github.artificialintelligenceai.xerophager.client.font.FontLayout;
import io.github.artificialintelligenceai.xerophager.client.font.PublicSansFontPolicy;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Font.class)
public abstract class FontMixin {
	@ModifyConstant(method = "<init>", constant = @Constant(intValue = 9))
	private int xerophager$useComfortableLineHeight(int original) {
		return FontLayout.LINE_HEIGHT;
	}

	@ModifyConstant(
			method = {
					"wordWrapHeight(Ljava/lang/String;I)I",
					"wordWrapHeight(Lnet/minecraft/network/chat/FormattedText;I)I"
			},
			constant = @Constant(intValue = 9)
	)
	private int xerophager$measureComfortableWrappedText(int original) {
		return FontLayout.LINE_HEIGHT;
	}

	@Redirect(
			method = {"method_27516", "method_37297"},
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/network/chat/Style;getFont()Lnet/minecraft/resources/ResourceLocation;"
			)
	)
	private ResourceLocation xerophager$measureWithRealBoldFont(Style style) {
		return PublicSansFontPolicy.selectFont(style);
	}

	@Redirect(
			method = {"method_27516", "method_37297"},
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/network/chat/Style;isBold()Z"
			)
	)
	private boolean xerophager$measureWithoutSyntheticBold(Style style) {
		return PublicSansFontPolicy.useSyntheticBold(style);
	}

	@ModifyVariable(
			method = {
					"drawInternal(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;IIZ)I",
					"drawInternal(Lnet/minecraft/util/FormattedCharSequence;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I"
			},
			at = @At("HEAD"),
			argsOnly = true,
			ordinal = 0
	)
	private boolean xerophager$disablePixelOffsetTextShadow(boolean dropShadow) {
		return false;
	}
}
