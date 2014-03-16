package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Top implements CommandExecutor {
    public String description = "Teleport to the top of the blocks";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.top")) {
            ((Player) sender).teleport(((Player) sender).getLocation().getWorld()
                    .getHighestBlockAt(((Player) sender).getLocation()).getLocation().add(0.5, 1, 0.5));
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        }
        return true;
    }
}