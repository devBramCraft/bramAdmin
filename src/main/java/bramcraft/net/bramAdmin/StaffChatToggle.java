package bramcraft.net.bramAdmin;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.entity.Player;

public class StaffChatToggle {
    private static final Set<Player> toggled = new HashSet<>();

    public static boolean isToggled(Player player) {
        return toggled.contains(player);
    }

    public static void toggle(Player player) {
        if (toggled.contains(player)) {
            toggled.remove(player);
        } else {
            toggled.add(player);
        }
    }

    public static void disable(Player player) {
        toggled.remove(player);
    }
}
