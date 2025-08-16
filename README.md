# TimedActions Plugin

A comprehensive Spigot/Paper Minecraft plugin that allows server administrators to schedule and execute commands at specific intervals using both Minecraft time and real-world time.

## Features

### Core Functionality

- **Dual Time Modes**: Schedule actions based on either:
  - Minecraft days (in-game time)
  - Real-world time (seconds, minutes, hours, days)
- **Command Execution**: Run any server commands when schedules trigger
- **PlaceholderAPI Integration**: Full support for placeholders in commands and messages
- **Multiple Schedules**: Configure unlimited separate schedules with different timings
- **Broadcast Messages**: Optional broadcast messages when schedules trigger
- **Console Logging**: Configurable logging for schedule triggers

### Admin Commands

- `/timedactions reload` - Reload configuration and restart all schedules
- `/timedactions list` - Display all configured schedules and their status
- `/timedactions trigger <scheduleName>` - Manually trigger a specific schedule

### Permission

- `timedactions.admin` - Access to all admin commands (default: op)

## Configuration

### Example config.yml

```yaml
# TimedActions Configuration
schedules:
  borderShrink:
    mode: minecraft_days  # minecraft_days or real_time
    interval: 5           # 5 minecraft days
    commands:
      - "worldborder set 5000"
      - "say The world border is now %worldborder_size%!"
    broadcast: "&cThe world border shrinks every 5 days! Current size: %worldborder_size%"
    console-log: true

  giveDiamonds:
    mode: real_time       # real_time mode
    interval: 1h          # 1 hour (supports: s, m, h, d for seconds, minutes, hours, days)
    commands:
      - "give %player_name% diamond 1"
      - "eco give %player_name% 100"  # Vault integration
    broadcast: "&b%player_name% received a diamond and $100!"
    console-log: true

  rankUp:
    mode: real_time
    interval: 7d          # 7 days
    commands:
      - "lp user %player_name% parent add veteran"
    broadcast: "&6%player_name% has ranked up to Veteran after 7 days!"
    console-log: true

# Global settings
settings:
  # Whether to enable PlaceholderAPI support
  placeholder-api: true
  
  # Prefix for all broadcast messages
  broadcast-prefix: "&8[&6TimedActions&8]&r "
  
  # Whether to log all schedule triggers to console
  global-console-log: true
```

### Schedule Configuration

Each schedule supports the following options:

- **name**: Unique identifier for the schedule
- **mode**: `minecraft_days` or `real_time`
- **interval**:
  - For `minecraft_days`: Number of Minecraft days
  - For `real_time`: Time with suffix (s=seconds, m=minutes, h=hours, d=days)
- **commands**: List of commands to execute (supports placeholders)
- **broadcast**: Optional message to broadcast to all players (supports placeholders)
- **console-log**: Boolean, whether to log this schedule's execution to console

### Time Intervals

Real-time intervals support:

- `30s` - 30 seconds
- `5m` - 5 minutes
- `2h` - 2 hours
- `1d` - 1 day
- `7d` - 7 days

## Plugin Integration

### PlaceholderAPI

Full support for all PlaceholderAPI placeholders in:

- Commands
- Broadcast messages
- Built-in placeholders like `%player_name%`

### Common Plugin Examples

#### WorldBorder Integration

```yaml
schedules:
  borderShrink:
    mode: minecraft_days
    interval: 5
    commands:
      - "worldborder set 5000"
    broadcast: "&cWorld border has been reduced!"
```

#### Vault Economy Integration

```yaml
schedules:
  dailyReward:
    mode: real_time
    interval: 1d
    commands:
      - "eco give %player_name% 100"
    broadcast: "&a%player_name% received daily $100 reward!"
```

#### LuckPerms Integration

```yaml
schedules:
  weeklyPromotion:
    mode: real_time
    interval: 7d
    commands:
      - "lp user %player_name% parent add veteran"
    broadcast: "&6%player_name% has been promoted to Veteran!"
```

## Dependencies

### Required

- **BlueSlimeCore** - Core API framework - <https://www.spigotmc.org/resources/blueslimecore.83189/>

### Optional (Soft Dependencies)

- **PlaceholderAPI** - For placeholder support in commands and messages
- **Vault** - For economy command integration
- **LuckPerms** - For permission management commands
- **WorldBorder** - For world border manipulation commands

## Installation

1. Download the plugin JAR file
2. Place it in your server's `plugins` folder
3. Ensure BlueSlimeCore is installed
4. Restart your server
5. Configure schedules in `plugins/TimedActions/config.yml`
6. Use `/timedactions reload` to apply changes

## API Information

- **Minecraft Version**: 1.13+
- **Server Software**: Spigot, Paper, or compatible forks
- **Java Version**: 8+

## Commands Summary

| Command | Permission | Description |
|---------|------------|-------------|
| `/timedactions reload` | `timedactions.admin` | Reload configuration and restart schedules |
| `/timedactions list` | `timedactions.admin` | List all configured schedules |
| `/timedactions trigger <name>` | `timedactions.admin` | Manually trigger a schedule |

## Support

This plugin is designed to be extensible and maintainable. Future features could include:

- Random delays for schedules
- Per-world schedule configurations
- Permission-based schedule triggers
- Advanced condition-based scheduling
- Web interface for schedule management

## License

See LICENSE file for details.
