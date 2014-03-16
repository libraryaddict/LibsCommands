package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Crash implements CommandExecutor {
    public String description = "Screws over the victim";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.crash")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "Please state player name");
                return true;
            }
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.GREEN + "Player does not exist");
                return true;
            }
            player.sendBlockChange(player.getLocation(), Integer.MAX_VALUE, Byte.MAX_VALUE);
            sender.sendMessage(ChatColor.GREEN + player.getName() + " has been crashed. FUCK YEAH!");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}