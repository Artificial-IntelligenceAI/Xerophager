package io.github.artificialintelligenceai.xerophager.client.mixin;

import io.github.artificialintelligenceai.xerophager.client.font.FontLayout;
import net.minecraft.client.gui.components.Checkbox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Checkbox.class)
public abstract class CheckboxMixin {
	@ModifyConstant(method = "getBoxSize", constant = @Constant(intValue = 9))
	private static int xerophager$matchCheckboxToTextHeight(int original) {
		return FontLayout.LINE_HEIGHT;
	}

	@ModifyArg(
			method = "renderWidget",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/components/MultiLineTextWidget;setPosition(II)V"
			),
			index = 1
	)
	private int xerophager$opticallyCenterCheckboxLabel(int y) {
		return y + 3;
	}
}
