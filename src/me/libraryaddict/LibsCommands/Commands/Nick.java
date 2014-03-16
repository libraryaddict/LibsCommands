package me.libraryaddict.LibsCommands.Commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Nick implements CommandExecutor {
    public String description = "Change your nickname";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.nick")) {
            Player p = Bukkit.getPlayerExact(sender.getName());
            if (args.length == 0) {
                sender.sendMessage(ChatColor.BLUE + "Need a name");
                return true;
            }
            String name = null;
            if (args.length > 1) {
                p = Bukkit.getPlayer(args[0]);
                name = ChatColor.translateAlternateColorCodes('&', StringUtils.join(args, " ").substring(args[0].length() + 1));
                if (p == null) {
                    sender.sendMessage(ChatColor.BLUE + "Can't find player");
                    return true;
                }
            } else {
                p = Bukkit.getPlayerExact(sender.getName());
                name = ChatColor.translateAlternateColorCodes('&', args[0]);
            }
            p.setDisplayName(name + ChatColor.RESET);
            sender.sendMessage(ChatColor.BLUE + "Nick changed to " + p.getDisplayName());
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}