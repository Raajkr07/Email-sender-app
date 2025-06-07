# 📧 Android Email Sender
A modern Android application that enables users to compose and send emails with multiple attachments through Gmail's SMTP server, built with Kotlin and JavaMail API.

## ✨ Features

- 📝 **Rich Email Composition** - Create emails with HTML formatting support
- 📎 **Multiple Attachments** - Attach various file types from device storage
- 🔐 **Secure Authentication** - Gmail SMTP integration with App Password support
- 💾 **Draft Management** - Save and restore email drafts locally
- ⚡ **Async Operations** - Non-blocking email sending with Kotlin Coroutines
- 🎨 **Material Design** - Clean, intuitive user interface

## 🚀 Quick Start

### Prerequisites

- Android Studio Arctic Fox or newer
- Android SDK 24+ (Android 7.0)
- Google Account with 2-Step Verification enabled

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/android-email-sender.git
   cd android-email-sender
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

3. **Add dependencies** (if not already present in `app/build.gradle`)
   ```gradle
   implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.1'
   implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3'
   implementation 'com.sun.mail:android-mail:1.6.7'
   implementation 'com.sun.mail:android-activation:1.6.7'
   ```

4. **Sync and Build**
   - Click "Sync Now" when prompted
   - Build the project

## 🔧 Configuration

### Gmail SMTP Setup

To send emails through Gmail, you need to configure authentication:

#### Step 1: Enable 2-Step Verification
1. Go to [Google Account Security](https://myaccount.google.com/security)
2. Enable **2-Step Verification** if not already enabled

#### Step 2: Generate App Password
1. Navigate to [App Passwords](https://myaccount.google.com/apppasswords)
2. Select **Mail** and your device type
3. Copy the generated 16-character password (no spaces)

#### Step 3: Configure Credentials
In `MainActivity.kt`, update these variables:
```kotlin
private val fromEmail = "your-email@gmail.com"
private val appPassword = "your-generated-app-password"
```

> ⚠️ **Security Note**: Never commit real credentials to version control. Consider using BuildConfig or secure storage for production apps.

## 📖 Usage

1. **Launch the app** on your Android device or emulator
2. **Fill in email details**:
   - Recipient email address
   - Subject line
   - Email body (HTML supported)
3. **Add attachments** (optional):
   - Tap the attachment button
   - Select files from device storage
4. **Send email**:
   - Tap "Send" button
   - Wait for confirmation

### Draft Management
- Drafts are automatically saved locally
- Load previous drafts from the app menu

## 🏗️ Architecture

```
app/
├── MainActivity.kt      # UI handling and email composition
├── MailSender.kt       # Email sending logic with SMTP
├── FileUtils.kt        # File handling utilities
└── AndroidManifest.xml # App permissions and configuration
```

### Key Components

- **MainActivity**: Handles UI interactions, file picking, and draft management
- **MailSender**: Manages SMTP authentication and email transmission
- **FileUtils**: Resolves file paths from URIs for attachments

## 🛡️ Permissions

Required permissions in `AndroidManifest.xml`:
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

No runtime permissions required for core functionality.

## 🔒 Security Best Practices

- ✅ Uses Gmail App Passwords instead of account passwords
- ✅ Implements TLS/STARTTLS for secure transmission
- ✅ No sensitive data logging
- ⚠️ For production: Use `EncryptedSharedPreferences` or Android Keystore
- ⚠️ Consider implementing OAuth 2.0 for enhanced security

## 🐛 Troubleshooting

<details>
<summary><strong>Authentication Issues</strong></summary>

**Problem**: Email sending fails with authentication error

**Solutions**:
- Verify 2-Step Verification is enabled
- Ensure App Password is correctly copied (no spaces)
- Check device date/time settings
- Confirm Gmail account is active
</details>

<details>
<summary><strong>File Attachment Issues</strong></summary>

**Problem**: Cannot attach files or null file paths

**Solutions**:
- Verify file picker permissions
- Check if `FileUtils.getPath()` handles all URI types
- Test with different file providers
- Ensure files are accessible to the app
</details>

<details>
<summary><strong>App Crashes</strong></summary>

**Problem**: App crashes during email sending

**Solutions**:
- Check Logcat for specific exceptions
- Verify JavaMail dependencies are properly included
- Ensure no version conflicts in build.gradle
- Test on different Android versions
</details>

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📋 TODO

- [ ] OAuth 2.0 authentication implementation
- [ ] Rich text editor for email composition
- [ ] Email templates
- [ ] Batch email sending
- [ ] Email scheduling
- [ ] Dark theme support

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [JavaMail for Android](https://javaee.github.io/javamail/) - SMTP email functionality
- [Material Design](https://material.io/design) - UI/UX guidelines
- Android developer community for inspiration and support

## 📬 Support

If you find this project helpful, please ⭐ star the repository!

For questions or support, please open an issue on GitHub.

---

**Happy Emailing!** 📧✨
