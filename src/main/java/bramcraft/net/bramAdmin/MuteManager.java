package bramcraft.net.bramAdmin;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;
import java.io.IOException;

public class MuteManager implements Listener {

    private final File file;
    private final FileConfiguration config;

    public MuteManager() {
        File directory = new File("plugins/BramAdmin");
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it does not exist
        }

        file = new File(directory, "muted.yml");
        if (!file.exists()) {
            try {
                file.createNewFile(); // Create the file if it does not exist
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void mutePlayer(Player player, int durationInSeconds) {
        long muteUntil = System.currentTimeMillis() + (durationInSeconds * 1000L);
        config.set("muted." + player.getUniqueId(), muteUntil);
        saveConfig();
        player.sendMessage(ChatColor.YELLOW + "You have been muted for " + (durationInSeconds / 3600) + " hours.");
    }

    public boolean isPlayerMuted(Player player) {
        String path = "muted." + player.getUniqueId();
        if (!config.contains(path)) return false;

        long muteUntil = config.getLong(path);
        if (System.currentTimeMillis() > muteUntil) {
            config.set(path, null); // Remove expired mute
            saveConfig();
            return false;
        }
        return true;
    }

    private void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (this.isPlayerMuted(player)) {
            player.sendMessage(ChatColor.RED + "You are muted and cannot chat.");
            event.setCancelled(true);
        }
    }
}