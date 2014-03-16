package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WhereAmI implements CommandExecutor {
    public String[] aliases = new String[] { "ip" };
    public String description = "Check out which server you are in";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage(ChatColor.DARK_GRAY + "Currently playing in server: " + ChatColor.GRAY + Bukkit.getMotd());
        return true;
    }
}