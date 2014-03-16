package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportHere implements CommandExecutor {
    public String[] aliases = new String[] { "tphere" };
    public String description = "Teleport someone to you";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.teleporthere")) {
            Player p = Bukkit.getPlayerExact(sender.getName());
            if (p == null) {
                sender.sendMessage(ChatColor.BLUE + "No!!!");
                return true;
            }
            if (args.length > 0) {
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null) {
                    sender.sendMessage(ChatColor.BLUE + "Player not found");
                    return true;
                }
                player.teleport(p);
            } else
                sender.sendMessage(ChatColor.BLUE + "No player name dictated");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}