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

Gameplay, story, art, audio, and other creative decisions will be documented after they are chosen by the designer.

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

Minecraft is a trademark of Microsoft. This project is not affiliated with or endorsed by Microsoft or Mojang Studios.

## License

Copyright 2026 Tankun Sriket

Licensed under the [Apache License 2.0](LICENSE).
