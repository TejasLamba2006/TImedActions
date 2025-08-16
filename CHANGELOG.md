# Changelog

All notable changes to the TimedActions plugin will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2025-08-16

### 🎉 Initial Release

This is the first stable release of TimedActions - a comprehensive Minecraft plugin for scheduling automated actions.

### ✨ Features Added

#### Core Scheduling System

- **Dual Time Modes**: Support for both Minecraft days and real-world time intervals
- **Flexible Intervals**: Real-time scheduling with `s` (seconds), `m` (minutes), `h` (hours), `d` (days) suffixes
- **Minecraft Time**: Schedule actions based on in-game day cycles
- **Multiple Schedules**: Configure unlimited independent schedules with unique timings

#### Command System

- **Command Execution**: Execute any server commands when schedules trigger
- **Per-Player Commands**: Commands execute for each online player automatically
- **Admin Commands**: Full command interface for management
  - `/timedactions reload` - Hot-reload configuration
  - `/timedactions list` - View all schedules and their status
  - `/timedactions trigger <name>` - Manually execute schedules

#### Integration & Compatibility

- **PlaceholderAPI Integration**: Full placeholder support in commands and messages
- **Soft Dependencies**: Seamless integration with popular plugins
  - Vault (Economy integration)
  - LuckPerms (Permission management)
  - WorldBorder (Border manipulation)
- **Plugin Safety**: Graceful handling when optional plugins are missing

#### Broadcasting & Logging

- **Broadcast Messages**: Optional server-wide announcements for schedule triggers
- **Color Code Support**: Full Minecraft color code support in messages
- **Console Logging**: Configurable logging for debugging and monitoring
- **Custom Prefix**: Configurable broadcast message prefix

#### Configuration

- **YAML Configuration**: User-friendly YAML-based configuration system
- **Hot Reload**: Apply configuration changes without server restart
- **Validation**: Built-in configuration validation with helpful error messages
- **Examples**: Comprehensive example configurations included

### 🔧 Technical Details

#### Requirements

- **Minecraft Version**: 1.13+ (Spigot/Paper)
- **Java Version**: 21+
- **Required Dependency**: BlueSlimeCore 2.9+

#### Optional Dependencies

- PlaceholderAPI 2.11.6+ (for placeholder support)
- Vault (for economy integration)
- LuckPerms (for permission management)

#### Performance

- **Efficient Scheduling**: Optimized task scheduling with proper cleanup
- **Memory Management**: Automatic task cancellation on plugin disable
- **Thread Safety**: Safe concurrent schedule execution
- **Resource Friendly**: Minimal impact on server performance

### 📋 Default Configuration

The plugin ships with example configurations for common use cases:

- Border shrinking every 5 Minecraft days
- Hourly diamond rewards with economy integration
- Weekly rank promotions
- Custom broadcast messages with placeholders

### 🛠️ Developer Features

#### Architecture

- **Modular Design**: Separate managers for scheduling and placeholder handling
- **Singleton Pattern**: Clean plugin lifecycle management
- **Reflection Safety**: Safe PlaceholderAPI integration with fallback
- **Extensible**: Easy to extend with additional features

#### Code Quality

- **Comprehensive Logging**: Detailed logging for troubleshooting
- **Error Handling**: Graceful error handling with user-friendly messages
- **Documentation**: Extensive code documentation and examples
- **Testing**: Built-in testing configurations for rapid development

### 📚 Documentation

- Complete README with setup instructions
- Example configurations for popular plugins
- API documentation for developers
- Quick testing guide with helper scripts
- Command reference and permission guide

### 🔐 Permissions

- `timedactions.admin` - Full access to all plugin commands (default: op)

### 📦 Distribution

- **Plugin JAR**: Ready-to-use plugin file
- **Source Code**: Complete source code with build scripts
- **Documentation**: Comprehensive documentation and examples
- **License**: Open source license included

### 🏗️ Build System

- **Gradle**: Modern Gradle build system
- **Dependencies**: Automatic dependency management
- **Testing**: Built-in test configurations
- **Packaging**: One-click JAR generation

### 🧪 Testing

- **Test Configurations**: Pre-built testing configurations with fast intervals
- **Helper Scripts**: Batch scripts for quick config switching
- **Validation**: Comprehensive testing checklist
- **Debug Mode**: Enhanced logging for development

---

## [Unreleased]

### Planned Features

- Random delay support for schedules
- Per-world schedule configurations  
- Condition-based scheduling
- Web interface for schedule management
- Advanced permission-based triggers

---

## Release Notes Format

### Categories

- **✨ Features Added** - New functionality
- **🔧 Changed** - Changes in existing functionality  
- **🐛 Fixed** - Bug fixes
- **🗑️ Removed** - Removed functionality
- **⚠️ Deprecated** - Soon-to-be removed features
- **🔒 Security** - Security improvements

### Versioning

- **Major (X.0.0)** - Breaking changes, major new features
- **Minor (1.X.0)** - New features, backwards compatible
- **Patch (1.0.X)** - Bug fixes, small improvements
