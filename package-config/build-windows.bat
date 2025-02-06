@echo off
REM Build Windows native installer for DroneDeck

REM Set variables
set APP_VERSION=1.0.0
set MAIN_JAR=DroneDeck.jar
set ICON_PATH=src/main/resources/DroneDeck_Logo.png
set INPUT_DIR=lib
set OUTPUT_DIR=dist

REM Create output directory if it doesn't exist
if not exist "%OUTPUT_DIR%" mkdir "%OUTPUT_DIR%"

REM Copy the main JAR to the input directory
copy "%MAIN_JAR%" "%INPUT_DIR%\"
set MAIN_CLASS=main.java.DroneDeck

REM Create output directory
if not exist "%OUTPUT_DIR%" mkdir "%OUTPUT_DIR%"

REM Create MSI installer
jpackage ^
  --type msi ^
  --name DroneDeck ^
  --app-version %APP_VERSION% ^
  --input %INPUT_DIR% ^
  --main-jar %MAIN_JAR% ^
  --main-class %MAIN_CLASS% ^
  --icon %ICON_PATH% ^
  --win-dir-chooser ^
  --win-menu ^
  --win-shortcut ^
  --win-menu-group DroneDeck ^
  --description "DroneDeck - Drone Fleet Management System" ^
  --vendor "DroneDeck" ^
  --copyright "Copyright 2024" ^
  --win-per-user-install ^
  --dest "%OUTPUT_DIR%"

REM Create EXE installer
jpackage ^
  --type exe ^
  --name DroneDeck ^
  --app-version %APP_VERSION% ^
  --input %INPUT_DIR% ^
  --main-jar %MAIN_JAR% ^
  --main-class %MAIN_CLASS% ^
  --icon %ICON_PATH% ^
  --win-dir-chooser ^
  --win-menu ^
  --win-shortcut ^
  --win-menu-group DroneDeck ^
  --description "DroneDeck - Drone Fleet Management System" ^
  --vendor "DroneDeck" ^
  --copyright "Copyright 2024" ^
  --win-per-user-install ^
  --dest "%OUTPUT_DIR%"

echo Build complete! Check the output directory for installers.
