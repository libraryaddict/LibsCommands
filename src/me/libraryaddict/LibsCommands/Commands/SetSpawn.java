package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawn implements CommandExecutor {
    public String description = "Set the spawn in the current world";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.setspawn")) {
            Player p = (Player) sender;
            Location loc = p.getLocation();
            p.getWorld().setSpawnLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            p.getWorld().save();
            sender.sendMessage(ChatColor.YELLOW + "Spawn saved for world '" + ChatColor.DARK_GREEN + loc.getWorld().getName()
                    + ChatColor.YELLOW + "' to X: " + ChatColor.DARK_GREEN + loc.getBlockX() + ChatColor.YELLOW + ", Y: "
                    + ChatColor.DARK_GREEN + loc.getBlockY() + ChatColor.YELLOW + ", Z: " + ChatColor.DARK_GREEN
                    + loc.getBlockZ());
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}