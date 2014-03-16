package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearInventory implements CommandExecutor {
    public String[] aliases = new String[] { "ci" };
    public String description = "Clear targets inventory";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.clearinventory")) {
            Player p = Bukkit.getPlayerExact(sender.getName());
            if (args.length > 0) {
                p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    sender.sendMessage(ChatColor.RED + "Cannot find player: " + args[0]);
                    return true;
                }
            }
            p.getInventory().clear();
            sender.sendMessage(ChatColor.RED + "Inventory cleared");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}