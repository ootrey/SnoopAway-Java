# SnoopAway

### Introduction

SnoopAway is a utility that provides multiple methods for privacy protection so that your private content won't be visible to others.

### Features

* Adding a leading dot to each files. In Unix-like operating systems, any file or folder that starts with a dot character is to be treated as hidden. This method can hide files in Android, Linux and macOS but **doesn't work in Windows**.

* Adding a ".snoopaway" extension to each files. Windows determines the type of a file via its file extension. By adding a ".snoopaway" extension, Windows will consider the file type as unknown so that the thumbnails of your private content will not show on file manager. **This method doesn't work on most desktop Linux environments** since they don't determine file type by extension.

* Scrambling filenames. Scramble filenames using Base64 encoder so that their contents cannot be easily guessed. **This method doesn't work on most desktop Linux environments either.**

### Quick Start

The latest version for Windows and Linux can be [downloaded here](https://github.com/dev-kitty/SnoopAway-Java/releases).

#### Windows

1. Download *snoopaway-&lt;version&gt;-windows-x64.zip*
2. Unpack the ZIP
3. Double-click *SnoopAway.cmd* to launch the program

#### Linux
1. Download *snoopaway-&lt;version&gt;-linux-x64.zip*
2. Unpack the ZIP
3. Run *SnoopAway.sh* from terminal

### Advanced Usage

If you are using the latest executable jar file from [releases page](https://github.com/dev-kitty/SnoopAway-Java/releases), be sure you have Java 13 installed. If you compile on your own, **a minimum version of Java 8 is required**.

#### Launch Graphical User Interface

```
java -jar snoopaway-executable.jar gui
```

**Optionally**, you can choose your preferred language via command-line arguments:

```
java -jar snoopaway-executable.jar gui --locale <preferred-locale>
```
Here, the *preferred-locale* is a language tag that conforms to the IETF BCP 47 standard.

#### Start Command Line Assistant

For command-line interface users, SnoopAway provides an intuitive assistant:

```
java -jar snoopaway-executable.jar assistant
```

Follow the assistant to perform the task you want.

### Notes

Thank *rain_parkour* for proofreading this English version of README.md.
