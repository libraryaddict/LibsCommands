package me.libraryaddict.LibsCommands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

public class LibsCommands extends JavaPlugin implements Listener {
    private String chatColorPerm;
    public List<String> god = new ArrayList<String>();
    public Map<String, List<String>> ignoring = new HashMap<String, List<String>>();
    public List<String> ignoringAll = new ArrayList<String>();
    public Map<String, String> lastMsg = new HashMap<String, String>();
    public HashMap<String, Long> mutes = new HashMap<String, Long>();

    public String staffPermission = "bukkit.command.ban";

    @EventHandler(priority = EventPriority.LOWEST)
    public void commandPreprocess(PlayerCommandPreprocessEvent event) {
        if (event.getMessage().toLowerCase().startsWith("/me ")) {
            if (!event.getPlayer().hasPermission("bukkit.command.me") && getConfig().getBoolean("RemoveMe", true)) {
                event.setCancelled(true);
                event.getPlayer().chat(event.getPlayer().getName() + event.getMessage().substring(3));
                return;
            }
            if (event.getPlayer().hasPermission("bukkit.command.me"))
                event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        }
    }

    public CommandSender getSender(String name) {
        Set<Permissible> permissibles = Bukkit.getPluginManager().getPermissionSubscriptions("ThisIsUsedForMessaging");
        for (Permissible permissible : permissibles) {
            if (permissible instanceof CommandSender) {
                CommandSender user = (CommandSender) permissible;
                if (user.getName().equals(name))
                    return user;
            }
        }
        return null;
    }

    public boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Iterator<Player> itel = event.getRecipients().iterator();
        Player p = event.getPlayer();
        if (!p.hasPermission(staffPermission)) {
            while (itel.hasNext()) {
                Player player = itel.next();
                if (ignoringAll.contains(player.getName())
                        || (ignoring.containsKey(player.getName()) && ignoring.get(player.getName()).contains(p.getName()))) {
                    itel.remove();
                }
            }
        }
        if (p.hasPermission(chatColorPerm)) {
            event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        }
        if (mutes.containsKey(p.getName())) {
            long expires = mutes.get(p.getName());
            String expire = "";
            if (expires > 0) {
                if (expires < System.currentTimeMillis()) {
                    mutes.remove(p.getName());
                    return;
                } else {
                    expire = " Your mute expires in " + toReadableString((expires - System.currentTimeMillis()) / 1000);
                }
            }
            event.setCancelled(true);
            p.sendMessage(ChatColor.YELLOW + "You have been muted!" + expire);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (god.contains(p.getName()))
                event.setCancelled(true);
        }
    }

    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().addPermission(new Permission("PrivateMessage", PermissionDefault.TRUE));
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        staffPermission = getConfig().getString("StaffPermission");
        chatColorPerm = getConfig().getString("ChatColorPermission");
        final JavaPlugin plugin = this;
        new CommandManager().load(plugin);
        if (Bukkit.getPluginManager().getPermission("ThisIsUsedForMessaging") == null) {
            Permission perm = new Permission("ThisIsUsedForMessaging", PermissionDefault.TRUE);
            perm.setDescription("Used for messages in LibsCommands");
            Bukkit.getPluginManager().addPermission(perm);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (getConfig().getBoolean("DisplayMotd"))
            event.getPlayer().sendMessage(
                    String.format(
                            ChatColor.translateAlternateColorCodes('&', getConfig().getString("Motd").replace("\\n", "\n")),
                            event.getPlayer().getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ignoring.remove(event.getPlayer().getName());
        lastMsg.remove(event.getPlayer().getName());
    }

    @EventHandler
    public void onRightClick(PlayerInteractEntityEvent event) {
        if (event.getPlayer().hasMetadata("LibraryaddictTagTimer") && event.getRightClicked() instanceof LivingEntity) {
            long timer = event.getPlayer().getMetadata("LibraryaddictTagTimer").get(0).asLong();
            event.getPlayer().removeMetadata("LibraryaddictTagTimer", this);
            String name = null;
            if (event.getPlayer().hasMetadata("LibraryaddictTagName")) {
                name = event.getPlayer().getMetadata("LibraryaddictTagName").get(0).asString();
                event.getPlayer().removeMetadata("LibraryaddictTagName", this);
            }
            if (timer > System.currentTimeMillis()) {
                event.setCancelled(true);
                if (name == null)
                    ((LivingEntity) event.getRightClicked()).setCustomNameVisible(false);
                else {
                    ((LivingEntity) event.getRightClicked()).setCustomName(name);
                    ((LivingEntity) event.getRightClicked()).setCustomNameVisible(true);
                }
                event.getPlayer().sendMessage(
                        ChatColor.BLUE
                                + event.getRightClicked().getType().name().toLowerCase()
                                + " no"
                                + (name == null ? " longer has a custom tag" : "w has a custom tag: '" + ChatColor.RESET + name
                                        + ChatColor.BLUE + "'"));
            }
        }
    }

    @EventHandler
    public void signPlace(SignChangeEvent event) {
        if (event.getPlayer().hasPermission("bukkit.command.sign")) {
            for (int i = 0; i < 4; i++)
                event.setLine(i, ChatColor.translateAlternateColorCodes('&', event.getLines()[i]));
        }
    }

    public String toReadableString(long time) {
        String string = "%s days, %s hours, %s minutes.";
        time -= time % 60;
        time /= 60; // Is now in minutes
        long minutes = time % 60;
        time /= 60; // Now in hours
        long hours = time % 24;
        time /= 24; // Now in days
        long days = time;
        string = String.format(string, days, hours, minutes);
        return string;
    }
}
