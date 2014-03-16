package me.libraryaddict.LibsCommands.Commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Kick implements CommandExecutor {
    public String description = "Kick someone from the server";
    public String kickMessage = "Kicked by admin\n%s";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.kick")) {
            if (args.length > 0) {
                Player player = Bukkit.getPlayer(args[0]);
                if (player != null) {
                    player.kickPlayer(String.format(kickMessage, StringUtils.join(args, " ").substring(args[0].length())));
                    sender.sendMessage(ChatColor.RED + player.getName() + " kicked.");
                } else
                    sender.sendMessage(ChatColor.RED + "Player not found!");
            } else
                sender.sendMessage(ChatColor.RED + "/kick <player> <message>");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}