package bramcraft.net.bramAdmin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChatListener implements Listener {

    private final BramAdmin plugin;

    public StaffChatListener(BramAdmin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!StaffChatToggle.isToggled(player)) return;
        if (!player.hasPermission("bramadmin.staffchat")) return;

        event.setCancelled(true);

        FileConfiguration config = plugin.getConfig();
        String prefix = ChatColor.translateAlternateColorCodes('&',
                config.getString("staffchat.prefix", "&8[&bStaff&8] &r"));
        String format = config.getString("staffchat.format", "%prefix%%player%: %message%");

        String finalMessage = format
                .replace("%prefix%", prefix)
                .replace("%player%", player.getName())
                .replace("%message%", event.getMessage());

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("bramadmin.staffchat")) {
                p.sendMessage(finalMessage);
            }
        }
    }
}
