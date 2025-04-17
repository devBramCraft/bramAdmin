package bramcraft.net.bramAdmin;

import bramcraft.net.bramAdmin.commands.VanishCommand;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class StaffModeManager {

    private static final HashMap<UUID, ItemStack[]> savedInventories = new HashMap<>();
    private static final HashMap<UUID, GameMode> savedGamemodes = new HashMap<>();

    public static boolean isInStaffMode(Player player) {
        return savedInventories.containsKey(player.getUniqueId());
    }

    public static void enableStaffMode(Player player) {
        // Save state
        savedInventories.put(player.getUniqueId(), player.getInventory().getContents());
        savedGamemodes.put(player.getUniqueId(), player.getGameMode());

        // Staff settings
        player.getInventory().clear();
        player.setAllowFlight(true);
        player.setFlying(true);
        player.setGameMode(GameMode.CREATIVE);

        VanishCommand.setVanished(player, true);
        for (Player p : player.getServer().getOnlinePlayers()) {
            if (!p.equals(player)) {
                p.hidePlayer(player.getServer().getPluginManager().getPlugin("bramAdmin"), player);
            }
        }

        // Give staff items (bijv. compass of stick)
        // player.getInventory().setItem(0, new ItemStack(Material.COMPASS));
    }

    public static void disableStaffMode(Player player) {
        UUID uuid = player.getUniqueId();
        if (!savedInventories.containsKey(uuid)) return;

        // Restore state
        player.getInventory().clear();
        player.getInventory().setContents(savedInventories.remove(uuid));
        player.setGameMode(savedGamemodes.remove(uuid));

        player.setAllowFlight(false);
        player.setFlying(false);

        VanishCommand.setVanished(player, false);
        for (Player p : player.getServer().getOnlinePlayers()) {
            p.showPlayer(player.getServer().getPluginManager().getPlugin("bramAdmin"), player);
        }
    }
}
