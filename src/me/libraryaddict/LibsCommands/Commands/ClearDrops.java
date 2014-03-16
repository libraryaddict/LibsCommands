package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

public class ClearDrops implements CommandExecutor {
    public String description = "Removes all drops in the world";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.cleardrops")) {
            int cur = 0;
            for (Entity entity : Bukkit.getPlayerExact(sender.getName()).getWorld().getEntities()) {
                if (entity instanceof Item) {
                    entity.remove();
                    cur += 1;
                }
            }
            sender.sendMessage(ChatColor.GRAY + "" + cur + " removed");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}