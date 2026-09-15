# Xerophager

Xerophager is an early-stage horror mod for Minecraft: Java Edition. Its creative premise and first gameplay feature are still being designed.

## Project status

The neutral technical foundation is ready:

- Minecraft 1.21.1
- Fabric Loader
- Java 21
- Fabric API
- Official Mojang mappings
- Gradle with Fabric Loom
- A blocking first-launch consent warning
- Public Sans used throughout the game's text rendering
- Google Noto Color Emoji used for supported emoji glyphs throughout the game

Gameplay, story, art, audio, and other creative decisions will be documented after they are chosen by the designer.

## Intended behavior

Xerophager is intended to deliberately manipulate parts of the game and player experience as horror mechanics. Its planned capabilities include:

- Changing the time of day and the current in-game day
- Repositioning one or more players
- Altering player-facing GUIs
- Changing what one or more players can hear
- Changing one or more players' game modes
- Modifying world settings
- Making other related changes to player or world state when required by the designed horror experience

These effects are intended behavior, not bugs or malicious activity. Players and server operators should expect them when the corresponding features are implemented and enabled. The capabilities will be introduced progressively as their exact designs are approved.

On the first client launch, Xerophager displays a warning describing these capabilities. The player must click **I accept** before continuing. Acceptance is stored locally in `config/xerophager-client.properties`; deleting that file causes the warning to appear again.

Xerophager places Google Noto Color Emoji ahead of Public Sans in every built-in Minecraft font family. Colored emoji therefore appear anywhere those fonts are used, while ordinary text remains Public Sans. Minecraft 1.21.1 resolves bitmap fonts one Unicode code point at a time, so single-codepoint emoji are supported; joined sequences such as many flags, families, and skin-tone combinations may render as separate component glyphs.

## Development

### Requirements

- A Java 21 JDK
- An internet connection for the first dependency download

### Build

On macOS or Linux:

```sh
./gradlew build
```

On Windows:

```powershell
.\gradlew.bat build
```

The distributable mod is produced in `build/libs/`.

### Run in a development client

On macOS or Linux:

```sh
./gradlew runClient
```

On Windows:

```powershell
.\gradlew.bat runClient
```

## Credits

- **Designer and project owner:** Tankun Sriket
- **Code writers:** Tankun Sriket and ChatGPT (GPT-5)
- **Programming language:** Java 21
- **Mod loader:** Fabric Loader
- **Modding API:** Fabric API
- **Mappings:** Official Mojang mappings
- **Build tooling:** Gradle and Fabric Loom
- **Target game:** Minecraft: Java Edition 1.21.1
- **Typeface:** [Public Sans v2.001](https://github.com/uswds/public-sans), created and maintained by the Public Sans Project Authors and the U.S. Web Design System
- **Emoji artwork:** [Google Noto Color Emoji v2.051](https://github.com/googlefonts/noto-emoji), converted from its official PNG image resources into Minecraft bitmap-font atlases

Minecraft is a trademark of Microsoft. This project is not affiliated with or endorsed by Microsoft or Mojang Studios.

## License

Copyright 2026 Tankun Sriket

Licensed under the [Apache License 2.0](LICENSE).

The bundled Public Sans font is separately licensed under the [SIL Open Font License 1.1](src/main/resources/META-INF/licenses/xerophager/Public-Sans-OFL-1.1.md).

The bundled Noto Color Emoji image resources and derived bitmap atlases are separately licensed under the [Apache License 2.0](src/main/resources/META-INF/licenses/xerophager/Noto-Color-Emoji-Apache-2.0.txt).
