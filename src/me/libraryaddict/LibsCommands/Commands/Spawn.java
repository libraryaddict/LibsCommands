package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {
    public String description = "Teleport to spawn in the current world";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.spawn")) {
            Player p = (Player) sender;
            p.teleport(p.getWorld().getSpawnLocation());
            sender.sendMessage(ChatColor.YELLOW + "Teleported to spawn");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}