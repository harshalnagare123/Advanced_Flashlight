# Advanced Flashlight 🔦

An Android application that provides advanced flashlight functionality with multiple features including color screen modes, user authentication, and profile management.

## 📱 Overview And Screenshots

Advanced Flashlight is a feature-rich Android application developed in Java that goes beyond basic flashlight functionality. It includes color-changing screen modes, user login system, and personalized user profiles. The app provides an intuitive interface with smooth animations and modern UI design.

<img width="150" height="300" alt="screen_1" src="https://github.com/user-attachments/assets/4f432b61-884f-47a5-b072-49b0d3ee2228" />
<img width="150" height="300" alt="screen_2" src="https://github.com/user-attachments/assets/eca5c596-c533-47c5-b5f6-e9204d6695e2" />
<img width="150" height="300" alt="screen_3" src="https://github.com/user-attachments/assets/1d99797d-4e65-447e-bc4d-65f75cc6c0bc" />
<img width="150" height="300" alt="screen_4" src="https://github.com/user-attachments/assets/890bde7d-8e5d-4557-9b6c-4826a191afe8" />


## ✨ Main Features

### Core Functionality
- **Flashlight Control**: Toggle device LED flashlight on/off
- **Bright Screen Mode**: Use device screen as a bright light source
- **SOS Alert Lighting**: Use SOS in Emergency
- **Strobe Lighting Mode**: Control Blinking of the Flashlight
- **Color Screen Options**: Multiple color options for screen-based lighting
  - White
  - Red
  - Green
  - Blue
  - Yellow
  - Custom colors

### User Management
- **User Authentication**: Login system for personalized experience
- **User Profiles**: Create and manage user profiles
- **Profile Information**: Store and display user details
- **Splash Screen**: Professional app startup experience

## 🏗️ Project Structure

```
Advanced_Flashlight/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/rainbowflashlight/
│   │   │   │   ├── MainActivity.java              # Main flashlight screen
│   │   │   │   ├── Brightscreenactivity.java      # Bright screen with color options
│   │   │   │   ├── LoginActivity.java             # User login screen
│   │   │   │   ├── UserProfileActivity.java       # User profile management
│   │   │   │   └── SplashActivity.java            # Splash screen
│   │   │   ├── res/
│   │   │   │   ├── layout/                        # XML layout files
│   │   │   │   ├── drawable/                      # App icons and images
│   │   │   │   ├── values/                        # Strings, colors, themes
│   │   │   │   ├── font/                          # Custom fonts
│   │   │   │   └── xml/                           # Backup rules
│   │   │   └── AndroidManifest.xml                # App configuration
│   │   ├── androidTest/                           # Instrumented tests
│   │   └── test/                                  # Unit tests
│   ├── build.gradle                               # App-level build configuration
│   └── proguard-rules.pro                         # ProGuard configuration
├── gradle/                                         # Gradle wrapper
├── build.gradle                                    # Project-level build configuration
├── gradle.properties                               # Gradle properties
├── settings.gradle                                 # Project settings
└── README.md                                       # This file
```

### Permissions Required
- `CAMERA`: Access to device camera flash LED
- `FLASHLIGHT`: Direct flashlight control (Android 6.0+)

## 📥 Installation & Build

### Prerequisites
- Android Studio Arctic Fox (2020.3.1) or later
- JDK 8 or higher
- Android SDK with API level 34
- Gradle 7.0+

### Build Instructions

1. **Clone the repository**:
   ```bash
   git clone https://github.com/harshalnagare123/Advanced_Flashlight.git
   cd Advanced_Flashlight
   ```

2. **Open in Android Studio**:
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned repository folder
   - Wait for Gradle sync to complete

3. **Build the project**:
   ```bash
   ./gradlew build
   ```
   Or use Android Studio's Build menu: `Build > Make Project`

4. **Run on device/emulator**:
   - Connect an Android device via USB (with USB debugging enabled) or start an emulator
   - Click the "Run" button in Android Studio or use:
   ```bash
   ./gradlew installDebug
   ```

## 📖 Usage Notes

### Battery Considerations
- Extended use of flashlight may drain battery quickly
- Bright screen mode consumes more power than LED flashlight
- Consider using screen brightness controls for optimal battery life

### Device Compatibility
- Requires device with LED flash (most modern Android devices)
- Screen brightness features work on all Android devices

### Best Practices
- Close the app when not in use to save battery
- Adjust screen brightness in bright screen mode as needed
- Ensure camera permissions are granted for flashlight functionality

## 🐛 Known Issues & Future Enhancements

### Potential Improvements
- [ ] Add widget for quick access
- [ ] Include brightness adjustment slider
- [ ] Add more color presets
- [ ] Implement timer functionality
- [ ] Add battery percentage indicator
- [ ] Include multiple themes


## 📞 Support

For issues, questions, or suggestions:
- Open an issue in the GitHub repository
- Check existing issues for solutions
- Contribute to discussions

---

**Note**: This application is designed for Android devices and requires appropriate permissions to access hardware features like the camera flash LED.

*Last Updated: October 2024*
