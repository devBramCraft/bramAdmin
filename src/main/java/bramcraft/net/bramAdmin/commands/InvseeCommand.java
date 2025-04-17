package bramcraft.net.bramAdmin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

public class InvseeCommand implements CommandExecutor {

    private final Plugin plugin;

    public InvseeCommand(Plugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player viewer)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (!viewer.hasPermission("bramadmin.invsee")) {
            viewer.sendMessage(ChatColor.RED + "You don't have permission to use /invsee.");
            return true;
        }

        if (args.length != 1) {
            viewer.sendMessage(ChatColor.RED + "Usage: /invsee <player>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null || !target.isOnline()) {
            viewer.sendMessage(ChatColor.RED + "Player not found.");
            return true;
        }

        viewer.setMetadata("invseeing", new FixedMetadataValue(plugin, true));
        viewer.openInventory(target.getInventory());
        viewer.sendMessage(ChatColor.GRAY + "Opening " + target.getName() + "'s inventory...");
        return true;
    }
}
