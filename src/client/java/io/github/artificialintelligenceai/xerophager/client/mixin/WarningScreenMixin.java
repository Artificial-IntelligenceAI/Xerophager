package io.github.artificialintelligenceai.xerophager.client.mixin;

import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.multiplayer.WarningScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(WarningScreen.class)
public abstract class WarningScreenMixin {
	private static final int MAX_READABLE_WARNING_WIDTH = 520;

	@ModifyArg(
			method = "init",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/components/FocusableTextWidget;<init>(ILnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/Font;I)V"
			),
			index = 0
	)
	private int xerophager$limitInitialSafetyMessageWidth(int width) {
		return isSafetyScreen() ? Math.min(width, MAX_READABLE_WARNING_WIDTH) : width;
	}

	@ModifyArg(
			method = "init",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/components/FocusableTextWidget;<init>(ILnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/Font;I)V"
			),
			index = 1
	)
	private Component xerophager$separateSafetyMessageParagraphs(Component message) {
		if (!isSafetyScreen()) {
			return message;
		}

		String text = message.getString();
		int sentenceBreak = text.indexOf(". ");
		if (sentenceBreak < 0) {
			return message;
		}

		return Component.literal(
				text.substring(0, sentenceBreak + 1)
						+ "\n\n"
						+ text.substring(sentenceBreak + 2)
		).setStyle(message.getStyle());
	}

	@ModifyArg(
			method = "repositionElements",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/components/FocusableTextWidget;setMaxWidth(I)Lnet/minecraft/client/gui/components/MultiLineTextWidget;"
			),
			index = 0
	)
	private int xerophager$keepSafetyMessageReadableAfterResize(int width) {
		return isSafetyScreen() ? Math.min(width, MAX_READABLE_WARNING_WIDTH) : width;
	}

	private boolean isSafetyScreen() {
		return (Object) this instanceof SafetyScreen;
	}
}
