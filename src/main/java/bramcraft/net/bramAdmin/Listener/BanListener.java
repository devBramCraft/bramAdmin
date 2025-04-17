package bramcraft.net.bramAdmin;

import org.bukkit.BanEntry;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.Date;

public class BanListener implements Listener {

    private final BramAdmin plugin;

    public BanListener(BramAdmin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        String name = event.getPlayer().getName();
        BanList banList = Bukkit.getBanList(BanList.Type.NAME);
        BanEntry entry = banList.getBanEntry(name);

        if (entry != null) {
            String reason = entry.getReason() != null ? entry.getReason() : "No reason provided";
            Date expires = entry.getExpiration();

            String time = (expires == null) ? "Permanent" : expires.toString();

            String message = ChatColor.translateAlternateColorCodes('&',
                    "&cYou are banned for " + time + "\n" +
                            "&cReason: " + reason);
            event.disallow(PlayerLoginEvent.Result.KICK_BANNED, message);
        }
    }
}
