@echo off
TITLE DroneDeck Demo Mode Launcher
COLOR 0A

ECHO =========================================================
ECHO                DroneDeck DEMO MODE LAUNCHER
ECHO =========================================================
ECHO This launcher starts DroneDeck in DEMO MODE with mock data
ECHO without requiring a connection to the drone API server.
ECHO.
ECHO NOTE: When in demo mode, no API token is required!
ECHO.

REM Check if output directory exists
IF NOT EXIST "out\production\DroneDeck" (
    COLOR 0C
    ECHO ERROR: Compiled files not found!
    ECHO.
    ECHO Please build the project in IntelliJ IDEA first:
    ECHO 1. Open the project in IntelliJ
    ECHO 2. Go to Build menu
    ECHO 3. Select "Build Project"
    ECHO.
    ECHO After building, try running this script again.
    ECHO.
    PAUSE
    EXIT /B 1
)

ECHO Starting DroneDeck in DEMO MODE...
ECHO.
ECHO Demo instructions:
ECHO - The application will run with mock drone data
ECHO - You can switch views using the top navigation
ECHO - No API connection will be attempted
ECHO.

java -cp "out/production/DroneDeck;lib/*" main.java.DroneDeck --demo

if errorlevel 1 (
    COLOR 0C
    ECHO.
    ECHO Failed to start DroneDeck.
    ECHO Please make sure you've built the project in IntelliJ first.
    ECHO.
    ECHO If you still encounter issues, check:
    ECHO 1. Java is properly installed and available
    ECHO 2. All required libraries are present in the lib directory
    ECHO.
    PAUSE
) else (
    ECHO.
    ECHO DroneDeck has exited successfully.
    ECHO.
    PAUSE
)