package bramcraft.net.bramAdmin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.plugin.java.JavaPlugin;

public class BramAdminCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;

    public BramAdminCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("goat")) {
                sender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Confirmed:");
                sender.sendMessage(ChatColor.DARK_PURPLE + "🐐 Bram is the actual GOAT. 🐐");
                sender.sendMessage(ChatColor.GRAY + "Certified by bramAdmin and ChatGPT ♥");
                return true;
            }

            if (args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("bramadmin.reload")) {
                    sender.sendMessage(ChatColor.RED + "You don’t have permission to reload bramAdmin.");
                    return true;
                }

                plugin.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "✅ bramAdmin config reloaded!");
                return true;
            }
        }

        // Helpmenu
        sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "bramAdmin v1.0 by Bram Wijnen");
        sender.sendMessage(ChatColor.DARK_GRAY + "----------------------------------");
        sender.sendMessage(ChatColor.GRAY + "Commands:");
        sender.sendMessage(ChatColor.YELLOW + "/fly, /vanish (/v), /staff");
        sender.sendMessage(ChatColor.YELLOW + "/invsee <player>, /staffchat, /sc");
        sender.sendMessage(ChatColor.YELLOW + "/bramadmin reload - reload config");
        sender.sendMessage(ChatColor.DARK_GRAY + "----------------------------------");
        sender.sendMessage(ChatColor.BLUE + "Support: https://discord.gg/aJt6QaWAbn");

        return true;
    }
    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            java.util.List<String> suggestions = new java.util.ArrayList<>();

            if ("reload".startsWith(args[0].toLowerCase()) && sender.hasPermission("bramadmin.reload")) {
                suggestions.add("reload");
            }


            return suggestions;
        }
        return java.util.Collections.emptyList();
    }

}
