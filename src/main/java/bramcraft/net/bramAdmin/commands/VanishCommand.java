package bramcraft.net.bramAdmin.commands;

import bramcraft.net.bramAdmin.BramAdmin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class VanishCommand implements CommandExecutor {

    public static Set<Player> getVanishedPlayers() {
        return vanishedPlayers;
    }

    private final BramAdmin plugin;
    private static final Set<Player> vanishedPlayers = new HashSet<>();

    public VanishCommand(BramAdmin plugin) {
        this.plugin = plugin;
    }

    public static boolean isVanished(Player player) {
        return vanishedPlayers.contains(player);
    }

    public static void setVanished(Player player, boolean vanished) {
        if (vanished) {
            vanishedPlayers.add(player);
        } else {
            vanishedPlayers.remove(player);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        boolean vanish = !isVanished(player);
        setVanished(player, vanish);

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p != player) {
                if (vanish) {
                    p.hidePlayer(plugin, player);
                } else {
                    p.showPlayer(plugin, player);
                }
            }
        }

        // Tablist en feedback
        player.sendMessage(ChatColor.GRAY + "You are now " + (vanish ? "vanished." : "visible."));
        player.setPlayerListName(vanish ? "" : player.getName());

        return true;
    }
}
