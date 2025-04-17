package bramcraft.net.bramAdmin;

import bramcraft.net.bramAdmin.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import bramcraft.net.bramAdmin.Listener.InvseeListener;
import bramcraft.net.bramAdmin.Listener.StaffChatListener;
import bramcraft.net.bramAdmin.Listener.*;

import java.util.HashMap;
import java.util.Map;

public final class BramAdmin extends JavaPlugin implements Listener {

    private final Map<Player, String> targetMap = new HashMap<>();
    private final MuteManager muteManager = new MuteManager(); // Instance of MuteManager

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        boolean isFlyEnabled = config.getBoolean("fly.enabled", false);
        double defaultFlySpeed = config.getDouble("fly.defaultSpeed", 0.1);

        getLogger().info("Fly enabled: " + isFlyEnabled);
        getLogger().info("Default fly speed: " + defaultFlySpeed);

        getCommand("staffchat").setExecutor(new StaffChatCommand(this));
        getCommand("sc").setExecutor(new StaffChatToggleCommand());
        BramAdminCommand adminCommand = new BramAdminCommand(this);
        getCommand("bramadmin").setExecutor(adminCommand);
        getCommand("bramadmin").setTabCompleter(adminCommand);

        getServer().getPluginManager().registerEvents(new StaffChatListener(this), this);

        getCommand("flyspeed").setExecutor(new FlySpeedCommand());
        getCommand("staff").setExecutor(new StaffModeCommand());
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("invsee").setExecutor(new InvseeCommand(this));
        getServer().getPluginManager().registerEvents(new InvseeListener(this), this);
        getCommand("fly").setExecutor(new FlyCommand(this));

        // New punish system
        this.getCommand("punish").setExecutor(new PunishCommand());
        this.getCommand("unban").setExecutor(new UnbanCommand());
        this.getCommand("unmute").setExecutor(new UnmuteCommand());
        Bukkit.getPluginManager().registerEvents(this, this);

        getLogger().info("Bram Admin Plugin Enabled");
        getLogger().info("Java Version: " + System.getProperty("java.version") + " " + System.getProperty("java.vendor"));
        getLogger().info("For Support join https://discord.gg/aJt6QaWAbn");
    }

    @Override
    public void onDisable() {
        getLogger().info("Bram Admin Plugin Disabled");
        getLogger().info("Java Version: " + System.getProperty("java.version") + " " + System.getProperty("java.vendor"));
        getLogger().info("For Support join https://discord.gg/aJt6QaWAbn");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getView().getTitle().startsWith("Punishing ")) {
            e.setCancelled(true);
            ItemStack clicked = e.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;
            String targetName = targetMap.get(p);
            if (targetName == null) return;

            switch (clicked.getType()) {
                case ANVIL:
                    openBanMenu(p, targetName);
                    break;
                case BOOK:
                    openMuteMenu(p, targetName);
                    break;
                case PAPER:
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("punish.warn").replace("{reason}", "Inappropriate behavior")));
                    break;
                case BARRIER:
                    p.closeInventory();
                    break;
                case RED_DYE:
                    if (p.hasPermission("bramadmin.ban.1d")) {
                        Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(targetName, "Banned for 1 day", null, p.getName());
                        Player targetPlayer1d = Bukkit.getPlayer(targetName);
                        if (targetPlayer1d != null) {
                            targetPlayer1d.kickPlayer(ChatColor.RED + "You have been banned for 1 day. Reason: Breaking server rules");
                        }
                        p.sendMessage(ChatColor.GREEN + "Banned " + targetName + " for 1 day.");
                        p.closeInventory();
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have permission to ban for 1 day.");
                    }
                    break;

                case ORANGE_DYE:
                    if (p.hasPermission("bramadmin.ban.7d")) {
                        Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(targetName, "Banned for 7 days", null, p.getName());
                        Player targetPlayer7d = Bukkit.getPlayer(targetName);
                        if (targetPlayer7d != null) {
                            targetPlayer7d.kickPlayer(ChatColor.RED + "You have been banned for 7 days. Reason: Serious misconduct");
                        }
                        p.sendMessage(ChatColor.GREEN + "Banned " + targetName + " for 7 days.");
                        p.closeInventory();
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have permission to ban for 7 days.");
                    }
                    break;

                case NETHER_STAR:
                    if (p.hasPermission("bramadmin.ban.perma")) {
                        Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(targetName, "Permanently banned", null, p.getName());
                        Player targetPlayerPerma = Bukkit.getPlayer(targetName);
                        if (targetPlayerPerma != null) {
                            targetPlayerPerma.kickPlayer(ChatColor.RED + "You have been permanently banned. Reason: Extreme violation of the rules");
                        }
                        p.sendMessage(ChatColor.GREEN + "Permanently banned " + targetName + ".");
                        p.closeInventory();
                    } else {
                        p.sendMessage(ChatColor.RED + "You don't have permission to permanently ban.");
                    }
                    break;
            }
        } else if (e.getView().getTitle().startsWith("Ban Options for ")) {
            e.setCancelled(true);
            ItemStack clicked = e.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;
            String targetName = targetMap.get(p);
            if (targetName == null) return;

            switch (clicked.getType()) {
                case RED_DYE:
                    Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(targetName, "Banned for 1 day", null, p.getName());
                    p.sendMessage(ChatColor.GREEN + "Banned " + targetName + " for 1 day.");
                    p.closeInventory();
                    break;
                case ORANGE_DYE:
                    Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(targetName, "Banned for 7 days", null, p.getName());
                    p.sendMessage(ChatColor.GREEN + "Banned " + targetName + " for 7 days.");
                    p.closeInventory();
                    break;
                case NETHER_STAR:
                    Bukkit.getBanList(org.bukkit.BanList.Type.NAME).addBan(targetName, "Permanently banned", null, p.getName());
                    p.sendMessage(ChatColor.GREEN + "Permanently banned " + targetName + ".");
                    p.closeInventory();
                    break;
                case BARRIER:
                    openPunishMenu(p, targetName);
                    break;
            }
        } else if (e.getView().getTitle().startsWith("Mute Options for ")) {
            e.setCancelled(true);
            ItemStack clicked = e.getCurrentItem();
            if (clicked == null || !clicked.hasItemMeta()) return;
            String targetName = targetMap.get(p);
            if (targetName == null) return;

            Player targetPlayer = Bukkit.getPlayer(targetName);
            if (targetPlayer == null) {
                p.sendMessage(ChatColor.RED + "Player is not online.");
                return;
            }

            switch (clicked.getType()) {
                case RED_DYE:
                    muteManager.mutePlayer(targetPlayer, 3600); // Mute for 1 hour
                    p.sendMessage(ChatColor.GREEN + "Muted " + targetName + " for 1 hour.");
                    break;
                case ORANGE_DYE:
                    muteManager.mutePlayer(targetPlayer, 86400); // Mute for 1 day
                    p.sendMessage(ChatColor.GREEN + "Muted " + targetName + " for 1 day.");
                    break;
                case NETHER_STAR:
                    muteManager.mutePlayer(targetPlayer, 604800); // Mute for 7 days
                    p.sendMessage(ChatColor.GREEN + "Muted " + targetName + " for 7 days.");
                    break;
                case BARRIER:
                    openPunishMenu(p, targetName);
                    break;
            }
        }
    }

    private void openPunishMenu(Player staff, String target) {
        Inventory gui = Bukkit.createInventory(null, 27, "Punishing " + target);
        gui.setItem(11, createItem(Material.ANVIL, ChatColor.RED + "Ban"));
        gui.setItem(13, createItem(Material.BOOK, ChatColor.BLUE + "Mute"));
        gui.setItem(15, createItem(Material.PAPER, ChatColor.GOLD + "Warning"));
        gui.setItem(22, createItem(Material.BARRIER, ChatColor.GRAY + "Cancel"));
        staff.openInventory(gui);
    }

    private void openBanMenu(Player staff, String target) {
        Inventory gui = Bukkit.createInventory(null, 27, "Ban Options for " + target);
        gui.setItem(11, createItem(Material.RED_DYE, ChatColor.RED + "Ban 1 Day"));
        gui.setItem(13, createItem(Material.ORANGE_DYE, ChatColor.GOLD + "Ban 7 Days"));
        gui.setItem(15, createItem(Material.NETHER_STAR, ChatColor.DARK_RED + "Permanent Ban"));
        gui.setItem(22, createItem(Material.BARRIER, ChatColor.GRAY + "Back"));
        staff.openInventory(gui);
    }

    private void openMuteMenu(Player staff, String target) {
        Inventory gui = Bukkit.createInventory(null, 27, "Mute Options for " + target);
        gui.setItem(11, createItem(Material.RED_DYE, ChatColor.RED + "Mute 1 Hour"));
        gui.setItem(13, createItem(Material.ORANGE_DYE, ChatColor.GOLD + "Mute 1 Day"));
        gui.setItem(15, createItem(Material.NETHER_STAR, ChatColor.DARK_RED + "Mute 7 Days"));
        gui.setItem(22, createItem(Material.BARRIER, ChatColor.GRAY + "Back"));
        staff.openInventory(gui);
    }

    private ItemStack createItem(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

    class PunishCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (!(sender instanceof Player)) return true;
            Player p = (Player) sender;
            if (args.length != 1) {
                p.sendMessage(ChatColor.RED + "Usage: /punish <player>");
                return true;
            }
            String targetName = args[0];
            targetMap.put(p, targetName);
            openPunishMenu(p, targetName);
            return true;
        }
    }

    class UnbanCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /unban <player>");
                return true;
            }
            String target = args[0];
            Bukkit.getBanList(org.bukkit.BanList.Type.NAME).pardon(target);
            sender.sendMessage(ChatColor.GREEN + "Player " + target + " has been unbanned.");
            return true;
        }
    }

    class UnmuteCommand implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.RED + "Usage: /unmute <player>");
                return true;
            }
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer != null) {
                muteManager.mutePlayer(targetPlayer, 0); // Unmute immediately
                sender.sendMessage(ChatColor.GREEN + "Player " + args[0] + " has been unmuted.");
            } else {
                sender.sendMessage(ChatColor.RED + "Player not found.");
            }
            return true;
        }
    }
}