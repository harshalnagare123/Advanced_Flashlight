# Advanced Flashlight 🔦

An Android application that provides advanced flashlight functionality with multiple features including color screen modes, user authentication, and profile management.

## 📱 Overview

Advanced Flashlight is a feature-rich Android application developed in Java that goes beyond basic flashlight functionality. It includes color-changing screen modes, user login system, and personalized user profiles. The app provides an intuitive interface with smooth animations and modern UI design.

## ✨ Main Features

### Core Functionality
- **Flashlight Control**: Toggle device LED flashlight on/off
- **Bright Screen Mode**: Use device screen as a bright light source
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

### Additional Features
- Material Design UI components
- Smooth transitions and animations
- Responsive button controls
- Night mode support
- Custom fonts and styling

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

## 🔧 Technical Details

### Technologies Used
- **Language**: Java
- **Minimum SDK**: API 21 (Android 5.0 Lollipop)
- **Target SDK**: API 34 (Android 14)
- **Build System**: Gradle
- **UI Framework**: Android XML Layouts with Material Design

### Key Components
1. **MainActivity**: Primary interface for flashlight control
2. **Brightscreenactivity**: Color screen functionality with multiple color options
3. **LoginActivity**: User authentication interface
4. **UserProfileActivity**: Profile management screen
5. **SplashActivity**: Initial loading screen with app branding

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

### Generate APK

**Debug APK**:
```bash
./gradlew assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

**Release APK** (requires signing configuration):
```bash
./gradlew assembleRelease
```
Output: `app/build/outputs/apk/release/app-release.apk`

## 🚀 Running the Application

### First Launch
1. The app opens with a **Splash Screen** displaying the app logo
2. After 2-3 seconds, you'll be directed to the **Login Screen**
3. Enter your credentials or create a new profile
4. Access the main flashlight interface

### Using the Flashlight
1. **Toggle Flashlight**: Tap the main flashlight button to turn on/off the LED
2. **Bright Screen**: Access the bright screen mode for screen-based lighting
3. **Color Selection**: Choose from multiple color options for different lighting needs
4. **Profile**: Access your user profile from the menu

## 📖 Usage Notes

### Battery Considerations
- Extended use of flashlight may drain battery quickly
- Bright screen mode consumes more power than LED flashlight
- Consider using screen brightness controls for optimal battery life

### Device Compatibility
- Requires device with LED flash (most modern Android devices)
- Screen brightness features work on all Android devices
- Some features may vary based on Android version

### Best Practices
- Close the app when not in use to save battery
- Adjust screen brightness in bright screen mode as needed
- Ensure camera permissions are granted for flashlight functionality

## 🤝 Contributing

Contributions are welcome! If you'd like to contribute to Advanced Flashlight:

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/YourFeature`
3. Commit your changes: `git commit -m 'Add YourFeature'`
4. Push to the branch: `git push origin feature/YourFeature`
5. Open a Pull Request

### Contribution Guidelines
- Follow existing code style and conventions
- Add comments for complex logic
- Test thoroughly on multiple devices/Android versions
- Update documentation for new features
- Ensure backward compatibility when possible

## 🐛 Known Issues & Future Enhancements

### Potential Improvements
- [ ] Add SOS signal mode
- [ ] Implement strobe/flashing patterns
- [ ] Add widget for quick access
- [ ] Include brightness adjustment slider
- [ ] Add more color presets
- [ ] Implement timer functionality
- [ ] Add battery percentage indicator
- [ ] Include dark theme toggle

## 📄 License

This project is available for educational and personal use. Please check with the repository owner for specific licensing terms.

## 👤 Author

**Harshal Nagare**
- GitHub: [@harshalnagare123](https://github.com/harshalnagare123)

## 🙏 Acknowledgments

- Android Open Source Project for framework and libraries
- Material Design guidelines for UI/UX inspiration
- Android developer community for resources and support

## 📞 Support

For issues, questions, or suggestions:
- Open an issue in the GitHub repository
- Check existing issues for solutions
- Contribute to discussions

---

**Note**: This application is designed for Android devices and requires appropriate permissions to access hardware features like the camera flash LED.

*Last Updated: October 2024*
