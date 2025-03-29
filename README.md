# DroneDeck v1.2.0

[![Java](https://img.shields.io/badge/Java-JDK%2023-ED8B00?logo=java)](https://www.oracle.com/java/)
[![Desktop Development](https://img.shields.io/badge/Desktop-GUI%20Application-3178C6)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![REST API](https://img.shields.io/badge/Backend-REST%20API%20Integration-brightgreen)](https://restfulapi.net/)
[![Multithreading](https://img.shields.io/badge/Concurrency-Multithreaded-blueviolet)](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
[![MVC Architecture](https://img.shields.io/badge/Architecture-MVC%20Pattern-8A2BE2)](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)
[![JUnit](https://img.shields.io/badge/Testing-JUnit5-success?logo=junit5)](https://junit.org/junit5/)
[![Data Visualization](https://img.shields.io/badge/UI-Data%20Visualization-00BFFF)](https://docs.oracle.com/javase/tutorial/uiswing/components/index.html)
[![License](https://img.shields.io/badge/License-MIT-lightgrey)](LICENSE)

This repository contains the source code and supporting documentation for the **DroneDeck** project, developed as part of the **Object-Oriented Programming in Java** course at Frankfurt UAS (Winter 2024). The goal is to create a Java application with a graphical user interface (GUI) to interact with a drone simulation system using a [RESTful API](http://dronesim.facets-labs.com).

## What's New in v1.2.0

- **Demo Mode**: Added ability to run with simulated data without API access (`java -jar DroneDeck.jar --demo`)

## Demo

![DroneDeck Demo](demo/DroneDeck_Demo.gif)

## Documentation
For comprehensive project documentation, including user handbook and technical details, see our [Full Documentation](Docs.md).

## Architecture

DroneDeck follows a clean, modern architecture focused on scalability and maintainability:

```mermaid
graph TD
    subgraph "Frontend"
        UI[UI Components]
        Dashboard[Dashboard View]
        Catalog[Catalog View]
    end

    subgraph "Core"
        Controller[Controller Layer]
        Services[Service Layer]
        DataAccess[Data Access Layer]
    end

    subgraph "External"
        API[Drone API]
    end

    UI --> Dashboard
    UI --> Catalog
    Dashboard --> Controller
    Catalog --> Controller
    Controller --> Services
    Services --> DataAccess
    Services --> API
```

**Key Technologies:**
- Java Swing with modern FlatLaf UI components
- REST API integration with token authentication
- Multithreaded data processing for performance
- Local data caching for offline capabilities

---

## Getting Started

### Tools You Need to Download

1. **IntelliJ IDEA** (Recommended IDE):  
   Download and install [IntelliJ](https://www.jetbrains.com/de-de/idea/)
   **Recommended Installation Tips**:
   - Check **Add "bin" folder to the PATH**
   - Check **Add "Open Folder as Project"**
   - Check **.java** under "Create Associations"

2. **Git** (Version Control):  
   Download [Git](https://git-scm.com/downloads)

3. **GitHub Desktop** (Recommended):  
   Download [GitHub Desktop](https://github.com/apps/desktop)

4. **Postman** (Optional, for API Testing):  
   Download [Postman](https://www.postman.com/downloads/)

5. **JDK 23** (Java Development Kit):  
   Download the latest [JDK 23](https://www.oracle.com/java/technologies/javase/jdk23-archive-downloads.html)

### Environment Setup

To successfully run the application and interact with the DroneSim API, you will need to set up an environment variable:

1. **Set the `DRONE_API_KEY` environment variable**:
   - The value of `DRONE_API_KEY` should be the API token, which you can find after logging in to [DroneSim](http://dronesim.facets-labs.com).
   - **Benefit**: By setting this environment variable, you won't be prompted to enter your API key manually each time you start the application.

2. **Accessing the API from outside Frankfurt UAS**:
   If you're working from a location other than **Frankfurt UAS**, and you want to connect to the API, you will need to download and set up [FortiClient VPN](https://www.fortinet.com/de/support/product-downloads) to connect to the Frankfurt UAS network. The API can only be accessed from within this network.

### Running the Application

#### Standard Mode

1. **Download** the `DroneDeck.jar` file from the [latest release](https://github.com/Code-Hauptwache/DroneDeck/releases).
2. **Run the application** using one of these methods:
   - Double-click the JAR file
   - Run via command line: `java -jar DroneDeck.jar`

**Requirements:**
- Java 23 or newer (Download from [Oracle](https://www.oracle.com/java/))
- Screen resolution: 1024x768 or higher
- Frankfurt UAS network access (either on campus or via VPN)
- DroneSim account with API token (obtained through university credentials)

#### Demo Mode

If you don't have API access or want to quickly test the application, you can use the demo mode with mock data.

**Option 1: Using the pre-built JAR (Recommended)**
- Download the `DroneDeck.jar` file from the [latest release](https://github.com/Code-Hauptwache/DroneDeck/releases)
- Run with demo mode flag: `java -jar DroneDeck.jar --demo`

**Option 2: Running from source code**
This option requires building the project first:
1. Open the project in IntelliJ IDEA
2. Go to Build menu
3. Select "Build Project"
4. Then use one of the commands below

**Windows:**
- Run the included `RunDemoDroneDeck.bat` file (recommended)
- Or run via Command Prompt:
  ```
  java -cp "out/production/DroneDeck;lib/*" main.java.DroneDeck --demo
  ```

**macOS/Linux:**
- Run via terminal: `java -cp out/production/DroneDeck:lib/* main.java.DroneDeck --demo`

**Note:** On some Linux distributions, if you encounter classpath issues, you may need to use:
```
java -cp out/production/DroneDeck:$(echo lib/*.jar | tr ' ' ':') main.java.DroneDeck --demo
```

**Note:** Demo mode uses mock data and doesn't require API access or an API token. This is perfect for exploring the application features without needing to set up a connection to the drone API server.

---

## Additional Resources
- **[Notion](https://www.notion.so/zakabouj/DroneDeck-OOP-Java-Projcet-152408e5d09b8033b5aed50a06d138d8) Documentation**:  
  - [Project Documentation](https://www.notion.so/zakabouj/DroneDeck-Documentation-152408e5d09b800bb222fb4f9c63cec0)  
- **[Figma](https://www.figma.com/design/lKYP3mLiFFFGDFb1HdLXus/DroneDeck-Mockup?node-id=80-33&t=G7z15kf5VyYzPZE0-0) for Wireframes and Mokups**

---

## Project Requirements and Files

### Linked PDF Files:
- [**Project Requirements**](https://github.com/user-attachments/files/18007575/2024_winter_project_description.pdf)
- [**Milestones Description**](https://github.com/user-attachments/files/18007586/milestones.pdf)

![image](https://github.com/user-attachments/assets/805d8c2c-a472-428c-8842-9c0fc9db3453)

---

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.
