package bramcraft.net.bramAdmin;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class StaffChatToggleCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (!player.hasPermission("bramadmin.staffchat")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use staff chat.");
            return true;
        }

        StaffChatToggle.toggle(player);
        boolean toggled = StaffChatToggle.isToggled(player);

        player.sendMessage(ChatColor.GRAY + "Staff chat is now " +
                (toggled ? ChatColor.GREEN + "ENABLED" : ChatColor.RED + "DISABLED") +
                ChatColor.GRAY + ".");

        return true;
    }
}
