#!/bin/bash
# Build macOS native installers for DroneDeck

# Set variables
APP_VERSION="1.0.0"
MAIN_JAR="DroneDeck.jar"
ICON_PATH="src/main/resources/DroneDeck_Logo.png"
INPUT_DIR="lib"
OUTPUT_DIR="dist"

# Create output directory if it doesn't exist
mkdir -p "$OUTPUT_DIR"

# Copy the main JAR to the input directory
cp "$MAIN_JAR" "$INPUT_DIR/"
MAIN_CLASS="main.java.DroneDeck"

# Create DMG installer
jpackage \
  --type dmg \
  --name DroneDeck \
  --app-version "$APP_VERSION" \
  --input "$INPUT_DIR" \
  --main-jar "$MAIN_JAR" \
  --main-class "$MAIN_CLASS" \
  --icon "$ICON_PATH" \
  --description "DroneDeck - Drone Fleet Management System" \
  --vendor "DroneDeck" \
  --copyright "Copyright 2024" \
  --mac-package-name "DroneDeck" \
  --mac-package-identifier "com.dronedeck.app" \
  --dest "$OUTPUT_DIR"

# Create PKG installer
jpackage \
  --type pkg \
  --name DroneDeck \
  --app-version "$APP_VERSION" \
  --input "$INPUT_DIR" \
  --main-jar "$MAIN_JAR" \
  --main-class "$MAIN_CLASS" \
  --icon "$ICON_PATH" \
  --description "DroneDeck - Drone Fleet Management System" \
  --vendor "DroneDeck" \
  --copyright "Copyright 2024" \
  --mac-package-name "DroneDeck" \
  --mac-package-identifier "com.dronedeck.app" \
  --dest "$OUTPUT_DIR"

echo "Build complete! Check the output directory for installers."
