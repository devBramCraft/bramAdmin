package bramcraft.net.bramAdmin.Listener;

import bramcraft.net.bramAdmin.BramAdmin;
import bramcraft.net.bramAdmin.commands.VanishCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.entity.Player;

public class VanishListener implements Listener {

    private final BramAdmin plugin;

    public VanishListener(BramAdmin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        for (Player vanished : VanishCommand.getVanishedPlayers()) {
            event.getPlayer().hidePlayer(plugin, vanished);


        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        VanishCommand.setVanished(event.getPlayer(), false); // Clean up
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (VanishCommand.isVanished(event.getPlayer())) {
            event.setCancelled(true); // Block opening chests, doors, etc.
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (VanishCommand.isVanished(event.getPlayer())) {
            event.setCancelled(true); // Block chat
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player vanished && VanishCommand.isVanished(vanished)) {
            event.setCancelled(true); // Can't hit while vanished
        }
    }
}
