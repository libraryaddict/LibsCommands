package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventorySee implements CommandExecutor {
    public String[] aliases = new String[] { "openinv", "seeinv", "invopen", "seeinventory", "invsee" };
    public String description = "Take a peek into someones inventory";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.inventorysee")) {
            Player p = Bukkit.getPlayerExact(sender.getName());
            if (args.length > 0) {
                Player player = Bukkit.getPlayer(args[0]);
                if (player == null) {
                    sender.sendMessage(ChatColor.GREEN + "Player not found");
                    return true;
                }
                p.openInventory(player.getInventory());
            }
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}