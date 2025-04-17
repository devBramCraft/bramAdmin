package bramcraft.net.bramAdmin;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class InvseeListener implements Listener {

    private final JavaPlugin plugin;

    public InvseeListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player admin = (Player) event.getWhoClicked();

        if (admin.hasMetadata("invseeing")) {
            ClickType click = event.getClick();

            if (click.isShiftClick() || click == ClickType.NUMBER_KEY) {
                event.setCancelled(true);
                admin.sendMessage(ChatColor.RED + "Shift-click and number keys are disabled during invsee.");
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player admin = (Player) event.getPlayer();
        if (admin.hasMetadata("invseeing")) {
            admin.removeMetadata("invseeing", plugin);
        }
    }

    public void markInvsee(Player admin) {
        admin.setMetadata("invseeing", new FixedMetadataValue(plugin, true));
    }
}
