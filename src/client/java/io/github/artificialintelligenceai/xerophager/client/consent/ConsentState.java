package io.github.artificialintelligenceai.xerophager.client.consent;

import io.github.artificialintelligenceai.xerophager.Xerophager;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class ConsentState {
	private static final String WARNING_VERSION = "1";
	private static final String ACCEPTED_KEY = "accepted";
	private static final String VERSION_KEY = "warning_version";

	private final Path configPath;
	private boolean accepted;

	public ConsentState() {
		this(FabricLoader.getInstance().getConfigDir().resolve("xerophager-client.properties"));
	}

	ConsentState(Path configPath) {
		this.configPath = configPath;
		this.accepted = loadAcceptance();
	}

	public boolean isAccepted() {
		return accepted;
	}

	public boolean accept() {
		Properties properties = new Properties();
		properties.setProperty(ACCEPTED_KEY, Boolean.TRUE.toString());
		properties.setProperty(VERSION_KEY, WARNING_VERSION);

		try {
			Files.createDirectories(configPath.getParent());
			try (Writer writer = Files.newBufferedWriter(configPath, StandardCharsets.UTF_8)) {
				properties.store(writer, "Xerophager client consent");
			}
			accepted = true;
			return true;
		} catch (IOException exception) {
			Xerophager.LOGGER.error("Could not save acceptance of the Xerophager warning", exception);
			return false;
		}
	}

	private boolean loadAcceptance() {
		if (!Files.isRegularFile(configPath)) {
			return false;
		}

		Properties properties = new Properties();
		try (Reader reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {
			properties.load(reader);
			return Boolean.parseBoolean(properties.getProperty(ACCEPTED_KEY))
					&& WARNING_VERSION.equals(properties.getProperty(VERSION_KEY));
		} catch (IOException exception) {
			Xerophager.LOGGER.warn("Could not read the Xerophager warning acceptance; the warning will be shown", exception);
			return false;
		}
	}
}
