# 🥁 Naad Tabla Composer

A Java-based tabla sound player and composer that plays authentic tabla sounds and creates rhythmic patterns.

## ✅ Fixed and Working!

This project has been completely debugged and now works perfectly with tabla sounds.

## 🚀 Quick Start

### Option 1: Simple Main Class
```bash
# Compile and run the main class
javac Main.java
java Main
```

### Option 2: Advanced Tabla Player
```bash
# Compile and run the advanced player
javac NaadTablaPlayer.java
java NaadTablaPlayer
```

## 🎵 Features

- **Individual Sound Playback**: Play each tabla sound (Dhun04.wav to Dhun10.wav)
- **Rhythm Patterns**: Create and play tabla rhythm patterns like "Dha-Din-Din-Dha"
- **Compositions**: Play traditional tabla compositions like Teental (16-beat cycle)
- **Loops**: Repeat patterns multiple times
- **Sequences**: Play sounds with custom timing intervals
- **Smart File Detection**: Automatically finds sound files in multiple locations

## 🎼 What You'll Hear

When you run the program, you'll experience:

1. **Individual Tabla Sounds**: Each Dhun sound played separately
2. **Simple Rhythm**: Basic "Dha-Din-Din-Dha" pattern
3. **Traditional Composition**: 16-beat Teental pattern with proper tabla bols
4. **Looped Patterns**: Repeated rhythmic cycles
5. **Custom Sequences**: Sounds with varied timing

## 🔧 Technical Fixes Applied

- ✅ **Path Issues Fixed**: Smart file finder checks multiple locations
- ✅ **Package Conflicts Resolved**: Simplified standalone classes
- ✅ **Missing Dependencies**: Uses only standard Java libraries
- ✅ **Error Handling**: Proper exception handling for missing files
- ✅ **Sound System**: Complete audio playback implementation

## 📁 Project Structure

```
Naad/
├── Main.java                 # Fixed main class with all features
├── NaadTablaPlayer.java     # Advanced tabla player
├── Dhun04.wav to Dhun10.wav # Tabla sound files
├── Ga/, Ge/, Ke/, Na/, Ti/  # Additional sound directories
└── README.md                # This file
```

## 🎯 Expected Output

```
🥁 === NAAD TABLA COMPOSER === 🥁
Starting tabla sound demonstration...

🎵 Testing individual sounds:
Playing: Dhun04.wav
Playing: Dhun05.wav
...

🔄 Playing loop pattern:
Loop 1/3
...

🎼 Playing composition:
Playing 1/3: Dhun04.wav
...

✅ All sounds finished!
```

## 🐛 Troubleshooting

If you don't hear sounds:
1. Ensure `.wav` files are in the same directory as your `.java` files
2. Check your system audio is working
3. Verify Java has audio permissions
4. Try running from the repository root directory

## 🎵 Tabla Bols Included

- **Dha**: Deep bass sound (Dhun04.wav)
- **Din**: Sharp treble sound (Dhun05.wav)
- **Na**: Open bass sound (Dhun07.wav)
- **Ta**: Closed treble sound (Dhun08.wav)
- **Tin**: Metallic sound (Dhun09.wav)

Enjoy your tabla compositions! 🎶