package bramcraft.net.bramAdmin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    private final BramAdmin plugin;

    public FlyCommand(BramAdmin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("bramadmin.fly")) {
            player.sendMessage("You don't have permission to use this command");
            return true;
        }

        // Controleer of vliegen is ingeschakeld in de configuratie
        if (!plugin.getConfig().getBoolean("fly.enabled", false)) {
            player.sendMessage(ChatColor.RED + "Flying is currently disabled.");
            return true;
        }

        boolean isFlying = player.getAllowFlight();
        player.setAllowFlight(!isFlying);
        player.setFlying(!isFlying);

        player.sendMessage(ChatColor.GREEN + "Flying is now " + (isFlying ? "disabled" : "enabled"));

        return true;

    }
}
