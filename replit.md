# Overview

This is a Java port of an audio composition application called "Naad" that was originally written in C++. The project aims to recreate a digital audio workstation (DAW) or music composition tool that handles audio playback, mixing, tempo control, and provides a user interface for creating musical compositions using "Bols" (musical elements) arranged in loops and compositions.

The application appears to focus on Indian classical music or tabla-based compositions, given the terminology used. It includes features for audio engine management, visual composition grids, waveform visualization, and various audio effects and controls.

## Current Implementation Status

**✅ COMPLETED - Core Backend Implementation (C1 & C2 Intern Tasks)**

The Java backend has been fully implemented with all core functionality working:

### Core Data Structures
- **Bol.java**: Tabla syllables with proper IDs, names, and variations (Na01, Ta01, Dha01, etc.)
- **Loop.java**: Rhythmic patterns with BPM, matra management, and bol collections
- **Composition.java**: Complete tabla compositions with multiple loops and auto-composition features
- **Sequence.java**: Playback ordering and bol sequencing for audio rendering

### File I/O System
- **CompositionFileHandler.java**: JSON-based saving/loading of compositions (.naad files)
- **LoopFileHandler.java**: Individual loop file operations (.loop files)
- Backup creation, directory management, and file validation

### Application Management
- **NaadApplication.java**: Main application lifecycle with integration points for UI/Audio teams
- **SettingsManager.java**: Configuration management with persistent JSON storage

### Integration Points for UI/Audio Teams
- **File Extensions**: `.naad` for compositions, `.loop` for individual loops
- **Settings Keys**: 
  - `audio.samplePath` - Path to audio sample files
  - `audio.defaultBpm` - Default tempo (180)
  - `file.defaultSaveLocation` - Default save directory
  - `composition.autoBackup` - Auto-backup enabled
- **JSON Format**: Clean serialization with Jackson, proper timestamp handling

# User Preferences

Preferred communication style: Simple, everyday language.

# System Architecture

## Backend Architecture (IMPLEMENTED)
- **Core Data Model**: Object-oriented design using Bol, Loop, Composition, and Sequence entities
- **File I/O System**: JSON-based serialization with backup and validation
- **State Management**: Centralized application state through NaadApplication and SettingsManager
- **Integration Layer**: Clear separation of concerns with core/io/util packages

## Audio Processing Pipeline (TO BE IMPLEMENTED BY AUDIO TEAM)
- **Format Conversion**: Audio format handling and optimization for different file types
- **Tempo Control**: Sophisticated timing mechanisms for maintaining musical tempo and synchronization
- **Effects Engine**: Audio effects and variations system for enhancing musical elements
- **Real-time Mixing**: Live audio mixing capabilities through the AudioMixer component

## Frontend Architecture (TO BE IMPLEMENTED BY UI TEAM)
- **Main Window Architecture**: Centralized window management with custom composition grid components
- **Custom UI Components**: Specialized components including composition grids, waveform visualization, control panels
- **Event Management**: UI event handling system for user interactions with audio controls and composition elements
- **Theming System**: Configurable UI styling and theme support for customizable user experience

## Application Workflow
- **Team-based Architecture**: Modular design separating concerns between audio engine, UI, and core logic components
- **Integration Layer**: Well-defined interface system between modules through stable JSON formats and clear APIs
- **Configuration Management**: Centralized settings handling with persistent storage
- **Testing Framework**: Comprehensive demo system validates all core functionality

# External Dependencies

## Development Environment
- **Java 17**: Modern Java runtime for application execution with GraalVM
- **Maven**: Build system with proper dependency management
- **Jackson**: JSON processing for file I/O and configuration

## Audio Libraries (TO BE ADDED BY AUDIO TEAM)
- **Java Audio APIs**: Java Sound API or similar for audio processing and playback
- **Audio Format Support**: Support for various audio file formats for importing and exporting compositions

## Legacy Integration
- **C++ Codebase Reference**: Original C++ implementation serves as specification and reference for the Java port
- **Cross-platform Compatibility**: Java implementation ensures platform independence compared to original C++ version

## Build and Testing Tools
- **Maven**: Complete project structure with proper dependencies
- **Jackson Libraries**: JSON serialization with time module support
- **JUnit**: Testing framework available for comprehensive test coverage

# Project Status

## Week 10-12 Tasks (COMPLETED)
✅ **Intern C1 (Backend Developer)**:
- Core data structures (Composition, Loop, Bol, Sequence) with full functionality
- File I/O system with JSON-based format and validation
- Data validation and error handling throughout
- Application state management

✅ **Intern C2 (Integration Specialist)**:
- Main application class with lifecycle management
- Settings and configuration management with persistence
- Integration points ready for UI and Audio teams
- Comprehensive demonstration of all functionality

## Ready for Next Phase
The backend is now ready for integration with:
- **Audio Team (A1 & A2)**: Can implement BolPlayer, AudioMixer, and tempo control using the core data structures
- **UI Team (U1 & U2)**: Can build MainWindow, CompositionGrid, and controls using the file I/O and state management systems

## Demo Verification
The application successfully demonstrates:
- Creating and managing tabla syllables (Bols)
- Building rhythmic patterns (Loops) with proper BPM and matra handling
- Composing complete pieces with multiple loops
- Auto-composition with traditional tabla patterns
- Saving/loading compositions to/from JSON files
- Settings and configuration management
- All integration points for other teams