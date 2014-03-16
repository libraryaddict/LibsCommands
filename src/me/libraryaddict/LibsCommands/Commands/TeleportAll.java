package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportAll implements CommandExecutor {
    public String[] aliases = new String[] { "tpall" };
    public String description = "Teleport all to a single player";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.teleportall")) {
            Player p = Bukkit.getPlayerExact(sender.getName());
            if (args.length > 0) {
                p = Bukkit.getPlayer(args[0]);
            }
            if (p == null) {
                sender.sendMessage(ChatColor.BLUE + "Player not found");
                return true;
            }
            for (Player player : Bukkit.getOnlinePlayers())
                player.teleport(p);
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}