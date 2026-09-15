package io.github.artificialintelligenceai.xerophager.client.mixin;

import io.github.artificialintelligenceai.xerophager.client.font.FontLayout;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MultiLineTextWidget.class)
public abstract class MultiLineTextWidgetMixin {
	@ModifyConstant(
			method = {"getHeight", "renderWidget"},
			constant = @Constant(intValue = 9)
	)
	private int xerophager$spaceParagraphLines(int original) {
		return FontLayout.LINE_HEIGHT;
	}
}
