package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {
    public String description = "Toggle fly";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.fly")) {
            if (args.length > 0) {
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null) {
                    sender.sendMessage(ChatColor.BLUE + "Player not found");
                } else {
                    player.setAllowFlight(!player.getAllowFlight());
                    sender.sendMessage(ChatColor.BLUE + "Turned flying to " + player.getAllowFlight() + " for "
                            + player.getDisplayName());
                }
            } else {
                Player player = Bukkit.getPlayer(sender.getName());
                player.setAllowFlight(!player.getAllowFlight());
                sender.sendMessage(ChatColor.BLUE + "Turned flying to " + player.getAllowFlight() + " for "
                        + player.getDisplayName());
            }
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}