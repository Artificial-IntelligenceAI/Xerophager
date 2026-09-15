package io.github.artificialintelligenceai.xerophager.client;

import io.github.artificialintelligenceai.xerophager.client.consent.ConsentState;
import io.github.artificialintelligenceai.xerophager.client.gui.ConsentWarningScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class XerophagerClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ConsentState consentState = new ConsentState();

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (consentState.isAccepted()
					|| client.getOverlay() != null
					|| client.screen == null
					|| client.screen instanceof ConsentWarningScreen) {
				return;
			}

			client.setScreen(new ConsentWarningScreen(client.screen, consentState));
		});
	}
}
