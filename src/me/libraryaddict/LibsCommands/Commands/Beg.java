package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Beg implements CommandExecutor {
    public String[] aliases = new String[] { "begging", "beggers" };
    public String description = "Displays a warning against begging";
    public String messageAgainstBegging = ChatColor.GREEN + "The staff do not like to see people begging, The staff members "
            + ChatColor.RED + "never" + ChatColor.GREEN + " give out free stuff! They will however mute, kick and ban you.";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage(messageAgainstBegging);
        return true;
    }
}