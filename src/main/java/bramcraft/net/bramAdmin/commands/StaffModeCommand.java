package bramcraft.net.bramAdmin;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class StaffModeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (!player.hasPermission("bramadmin.staff")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use /staff.");
            return true;
        }

        if (StaffModeManager.isInStaffMode(player)) {
            StaffModeManager.disableStaffMode(player);
            player.sendMessage(ChatColor.RED + "Staff mode disabled.");
        } else {
            StaffModeManager.enableStaffMode(player);
            player.sendMessage(ChatColor.GREEN + "Staff mode enabled.");
        }

        return true;
    }
}
