package me.libraryaddict.LibsCommands.Commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Broadcast implements CommandExecutor {
    public String[] aliases = new String[] { "bc" };
    public String description = "Broadcasts to the server a special message";
    public String broadcastMessage = ChatColor.DARK_GREEN + "[" + ChatColor.GREEN + "Broadcast" + ChatColor.DARK_GREEN + "] "
            + ChatColor.RESET + "%s";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.broadcast")) {
            if (args.length > 0)
                Bukkit.broadcastMessage(String.format(broadcastMessage,
                        ChatColor.translateAlternateColorCodes('&', StringUtils.join(args, " "))));
            else
                sender.sendMessage(ChatColor.YELLOW + "Need a message to broadcast");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}