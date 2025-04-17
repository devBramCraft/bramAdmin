package bramcraft.net.bramAdmin.commands;

import bramcraft.net.bramAdmin.BramAdmin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    private final BramAdmin plugin;
    public StaffChatCommand(BramAdmin plugin) {
        this.plugin = plugin;

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }

        if (!(player.hasPermission("bramadmin.staffchat"))) {
            player.sendMessage(ChatColor.RED + "do not have permission to use this command.");
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /staffchat <message>");
            return true;
        }

        String message = String.join(" ", args);
        FileConfiguration config = plugin.getConfig();

        String prefix = ChatColor.translateAlternateColorCodes('&', config.getString("staffchat.prefix", "&8[&bStaff&8] &r"));

        String format = config.getString("staffchat.format", "%prefix%%player%: %message%%");

        String finalMessage = format
                .replace("%prefix%", prefix)
                .replace("%message%", message)
                .replace("%player%", player.getName());

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("bramadmin.staffchat")) {
                p.sendMessage(finalMessage);
            }
        }

        return true;
    }

}
