# bramAdmin

## Description

`bramAdmin` is a Minecraft server plugin designed to help server administrators manage player punishments and staff utilities. It provides features such as muting, banning, staff chat, and more.

## Features

- **Mute Players**: Temporarily mute players for specific durations.
- **Ban Players**: Temporarily or permanently ban players.
- **Staff Chat**: Communicate privately with other staff members.
- **Fly and Vanish Modes**: Enable flight and invisibility for staff.
- **Inventory Viewing**: View other players' inventories.
- **Persistent Data**: Stores mute and ban data for server restarts.

## Installation

1. Download the plugin JAR file.
2. Place the JAR file in the `plugins` folder of your Minecraft server.
3. Start or restart the server to load the plugin.

## Commands

| Command           | Description                          | Usage                     | Permission            |
|--------------------|--------------------------------------|---------------------------|-----------------------|
| `/fly`            | Toggle fly mode for staff           | `/fly`                    | `bramadmin.fly`       |
| `/vanish`         | Toggle vanish mode                  | `/vanish` or `/v`         | `bramadmin.vanish`    |
| `/staffchat`      | Send a message to staff chat         | `/staffchat <message>`    | `bramadmin.staffchat` |
| `/sc`             | Toggle staff chat                   | `/sc`                     | `bramadmin.staffchat` |
| `/staff`          | Toggle full staff mode              | `/staff`                  | `bramadmin.staff`     |
| `/invsee`         | View another player's inventory      | `/invsee <player>`        | `bramadmin.invsee`    |
| `/flyspeed`       | Set your fly speed                  | `/flyspeed <1-10>`        | `bramadmin.flyspeed`  |
| `/bramadmin`      | Core command for the plugin          | `/bramadmin [reload]`     | `bramadmin.use`       |
| `/punish`         | Opens the punishment GUI            | `/punish <player>`        | `bramadmin.punish`    |
| `/unban`          | Unbans a player                     | `/unban <player>`         | `bramadmin.unban`     |
| `/unmute`         | Unmutes a player                    | `/unmute <player>`        | `bramadmin.unmute`    |

## Permissions

| Permission          | Description                                | Default |
|----------------------|--------------------------------------------|---------|
| `bramadmin.use`      | Allows use of `/bramadmin`                | `true`  |
| `bramadmin.reload`   | Allows reloading the plugin configuration | `op`    |
| `bramadmin.fly`      | Allows toggling flight                    | `op`    |
| `bramadmin.flyspeed` | Allows setting fly speed                  | `op`    |
| `bramadmin.vanish`   | Allows toggling vanish mode               | `op`    |
| `bramadmin.staffchat`| Allows using and viewing staff chat       | `op`    |
| `bramadmin.staff`    | Allows toggling staff mode                | `op`    |
| `bramadmin.invsee`   | Allows viewing other players' inventories | `op`    |
| `bramadmin.punish`   | Allows using the punishment GUI           | `op`    |
| `bramadmin.unban`    | Allows unbanning players                  | `op`    |
| `bramadmin.unmute`   | Allows unmuting players                   | `op`    |

## Requirements
- **Spigot** or **Paper** server (1.17 or higher)

## Configuration

The plugin automatically creates configuration files in the `plugins/BramAdmin` directory. These files store data such as muted players, banned players, and plugin settings.

## Development

### Prerequisites

- Java 17 or higher
- Maven

## Release Notes

## Support
For support, please open an issue on the GitHub repository or contact the plugin author directly.
https://dsc.gg/smpstore

### Version 1.0.0

- Initial release with mute, ban, and staff utility functionality.
- Persistent data storage using YAML.
- GUI-based punishment system.