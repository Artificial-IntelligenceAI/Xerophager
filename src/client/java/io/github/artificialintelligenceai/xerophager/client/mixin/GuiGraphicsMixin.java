package io.github.artificialintelligenceai.xerophager.client.mixin;

import io.github.artificialintelligenceai.xerophager.client.font.FontLayout;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
	@ModifyConstant(method = "drawWordWrap", constant = @Constant(intValue = 9))
	private int xerophager$spaceWrappedLines(int original) {
		return FontLayout.LINE_HEIGHT;
	}
}
