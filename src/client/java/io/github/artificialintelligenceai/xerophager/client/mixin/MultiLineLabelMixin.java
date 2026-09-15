package io.github.artificialintelligenceai.xerophager.client.mixin;

import io.github.artificialintelligenceai.xerophager.client.font.FontLayout;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(targets = "net.minecraft.client.gui.components.MultiLineLabel$2")
public abstract class MultiLineLabelMixin {
	@ModifyConstant(method = "renderCentered(Lnet/minecraft/client/gui/GuiGraphics;II)V", constant = @Constant(intValue = 9))
	private int xerophager$spaceDefaultLabelLines(int original) {
		return FontLayout.LINE_HEIGHT;
	}
}
