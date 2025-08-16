# Contributing to TimedActions

We welcome contributions to the TimedActions plugin! This document provides guidelines for contributing to the project.

## 🤝 How to Contribute

### 🐛 Reporting Bugs

If you find a bug, please create an issue with:

- **Clear Description**: What happened vs. what you expected
- **Steps to Reproduce**: Detailed steps to reproduce the issue
- **Environment**: Server version, plugin version, Java version
- **Configuration**: Relevant parts of your config.yml
- **Logs**: Any error messages or relevant console output

### 💡 Suggesting Features

For feature requests:

- **Use Case**: Describe the problem you're trying to solve
- **Proposed Solution**: How you think it should work
- **Alternatives**: Other ways you've considered solving this
- **Examples**: Provide example configurations if applicable

### 🔧 Code Contributions

#### Prerequisites

- Java 21+
- Gradle 8.5+
- Git
- IDE (IntelliJ IDEA recommended)

#### Setup Development Environment

1. **Clone the repository**

   ```bash
   git clone https://github.com/TejasLamba2006/TimedActions.git
   cd TimedActions
   ```

2. **Import into IDE**
   - Open the project in your IDE
   - Import as a Gradle project
   - Wait for dependencies to download

3. **Build the project**

   ```bash
   ./gradlew build
   ```

#### Code Standards

- **Java 21**: Use modern Java features appropriately
- **Code Style**: Follow existing code formatting
- **Javadoc**: Document public methods and classes
- **Null Safety**: Handle null values appropriately
- **Resource Management**: Always close resources properly

#### Architecture Guidelines

- **Singleton Pattern**: Main plugin class uses singleton
- **Manager Classes**: Use manager classes for distinct functionality
- **Configuration**: YAML-based configuration with validation
- **Logging**: Use plugin logger for all output
- **Thread Safety**: Ensure thread-safe operations for scheduled tasks

#### Testing

Before submitting:

1. **Build Successfully**

   ```bash
   ./gradlew build
   ```

2. **Test Basic Functionality**
   - Plugin loads without errors
   - Commands work correctly
   - Schedules execute properly
   - Configuration reloads work

3. **Test Edge Cases**
   - Invalid configurations
   - Missing dependencies
   - Server restart scenarios

#### Pull Request Process

1. **Fork** the repository
2. **Create** a feature branch: `git checkout -b feature/your-feature-name`
3. **Commit** your changes with clear messages
4. **Test** thoroughly
5. **Push** to your fork
6. **Create** a pull request with:
   - Clear description of changes
   - Why the changes are needed
   - How to test the changes
   - Screenshots if applicable

## 📋 Development Guidelines

### 🏗️ Project Structure

```
src/main/java/org/tejaslamba2006/timedactions/
├── TImedActionsPlugin.java          # Main plugin class
├── manager/
│   ├── ScheduleManager.java         # Schedule management
│   └── PlaceholderManager.java      # PlaceholderAPI integration
├── model/
│   ├── Schedule.java                # Schedule data model
│   └── ScheduleMode.java            # Schedule mode enum
├── command/
│   └── timedactions/                # Command implementations
├── util/
│   └── GameUtil.java                # Utility classes
└── listener/                        # Event listeners (future)
```

### 🔌 Adding New Features

#### Adding a New Command

1. Create command class in `command/timedactions/`
2. Extend appropriate base class
3. Register in `TimedActionsCommand`
4. Add permission to plugin.yml
5. Update documentation

#### Adding a New Manager

1. Create manager class in `manager/` package
2. Initialize in main plugin class
3. Add cleanup in `onDisable()`
4. Document public API

#### Adding Configuration Options

1. Add to default config.yml
2. Add validation in config loading
3. Update documentation
4. Provide examples

### 🧪 Testing Guidelines

#### Unit Testing

- Test individual components in isolation
- Mock external dependencies
- Test edge cases and error conditions

#### Integration Testing

- Test plugin integration with server
- Test configuration loading
- Test command execution

#### Performance Testing

- Test with multiple schedules
- Test with high player counts
- Monitor memory usage

## 📚 Documentation

### Code Documentation

- **Javadoc**: All public methods and classes
- **Inline Comments**: Complex logic and workarounds
- **TODOs**: Mark future improvements clearly

### User Documentation

- **README.md**: Keep examples up to date
- **Configuration**: Document all options
- **Commands**: Document syntax and permissions

## 🐛 Debugging

### Common Issues

#### Schedule Not Running

- Check console for errors
- Verify configuration syntax
- Check if plugin is enabled
- Verify time format is correct

#### Commands Not Executing

- Check player is online for player-specific commands
- Verify command syntax
- Check console permissions
- Test commands manually

#### PlaceholderAPI Issues

- Verify PlaceholderAPI is installed
- Check placeholder expansion in console
- Test with and without PlaceholderAPI

### Debugging Tools

- **Test Configuration**: Use fast intervals for testing
- **Console Logging**: Enable detailed logging
- **Manual Triggers**: Use `/timedactions trigger` for testing
- **Debug Mode**: Add temporary debug output

## 🔄 Release Process

### Version Numbering

- **Major (X.0.0)**: Breaking changes, major features
- **Minor (1.X.0)**: New features, backwards compatible  
- **Patch (1.0.X)**: Bug fixes, small improvements

### Release Checklist

- [ ] Update version in build.gradle
- [ ] Update CHANGELOG.md
- [ ] Update README.md if needed
- [ ] Test thoroughly
- [ ] Create release notes
- [ ] Build and test JAR
- [ ] Tag release
- [ ] Upload to distribution platforms

## 📞 Getting Help

- **Issues**: GitHub Issues for bugs and features
- **Discussions**: GitHub Discussions for questions
- **Discord**: Join our community Discord (if available)
- **Documentation**: Check README.md and code comments

## 📄 License

By contributing to TimedActions, you agree that your contributions will be licensed under the same license as the project.

## 🙏 Recognition

Contributors will be:

- Listed in the CONTRIBUTORS.md file
- Credited in release notes
- Mentioned in the plugin description (for major contributions)

Thank you for contributing to TimedActions! 🎉
