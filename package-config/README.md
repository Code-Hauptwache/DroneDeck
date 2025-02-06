# DroneDeck Native Packaging

This directory contains scripts to create native installers for DroneDeck v1.0.0 for Windows, macOS, and Linux.

## Prerequisites

- JDK 16 or later (for jpackage tool)
- Platform-specific requirements:
  - Windows:
    - WiX Toolset (for MSI creation)
    - Windows SDK (for EXE creation)
  - macOS:
    - Xcode Command Line Tools
    - Apple Developer ID (for signed packages)
  - Linux:
    - For DEB: dpkg-deb
    - For RPM: rpmbuild

## Building Installers

### Windows (.msi and .exe)
```batch
.\build-windows.bat
```

### macOS (.dmg and .pkg)
```bash
chmod +x build-macos.sh
./build-macos.sh
```

### Linux (.deb and .rpm)
```bash
chmod +x build-linux.sh
./build-linux.sh
```

## Cross-Platform Building

To create installers for all platforms, you need to:

1. Build on Windows for Windows installers
2. Build on macOS for macOS installers
3. Build on Linux for Linux installers

Each platform requires its own build environment and tools.

## Build Process

1. First, build the main JAR file:
```bash
mkdir -p build
javac -d build src/main/java/**/*.java
jar cfe DroneDeck.jar main.java.DroneDeck -C build .
```

2. Then run the appropriate build script for your platform.

## Output Files

The scripts will create installers in the `dist` directory:

- Windows:
  - `DroneDeck-1.0.0.msi`
  - `DroneDeck-1.0.0.exe`
- macOS:
  - `DroneDeck-1.0.0.dmg`
  - `DroneDeck-1.0.0.pkg`
- Linux:
  - `dronedeck_1.0.0-1_amd64.deb`
  - `dronedeck-1.0.0-1.x86_64.rpm`

## Directory Structure

```
DroneDeck/
├── build/              # Compiled .class files
├── dist/              # Output directory for installers
├── lib/               # Dependencies
└── package-config/    # Build scripts and configuration
```

## Notes

- The installers include all necessary dependencies and a bundled JRE
- Build scripts automatically copy the main JAR to the lib directory
- Users don't need to install Java separately
- Each installer will create appropriate shortcuts and menu entries
- Uninstallation is handled through the platform's standard package management
