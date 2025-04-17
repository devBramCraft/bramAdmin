package bramcraft.net.bramAdmin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlySpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("bramadmin.flyspeed")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(ChatColor.YELLOW + "Usage: /flyspeed <1-10>");
            return true;
        }

        try {
            int speed = Integer.parseInt(args[0]);

            if (speed < 1 || speed > 10) {
                player.sendMessage(ChatColor.RED + "Please enter a value between 1 and 10.");
                return true;
            }

            float flySpeed = speed / 10f;
            player.setFlySpeed(flySpeed);
            player.sendMessage(ChatColor.GREEN + "Fly speed set to: " + flySpeed);
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "That is not a valid number.");
        }

        return true;
    }
}
