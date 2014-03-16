package me.libraryaddict.LibsCommands.Commands;

import me.libraryaddict.LibsCommands.LibsCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportPosition implements CommandExecutor {
    public String[] aliases = new String[] { "tppos", "teleportpos" };
    public String description = "Teleport to a position";
    private LibsCommands lib;

    public TeleportPosition(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.teleportposition")) {
            Player p = Bukkit.getPlayerExact(sender.getName());
            if (args.length > 2) {
                if (lib.isNumeric(args[0]) && lib.isNumeric(args[0]) && lib.isNumeric(args[1])) {
                    Location loc = new Location(p.getWorld(), Double.parseDouble(args[0]), Double.parseDouble(args[1]),
                            Double.parseDouble(args[2]));
                    p.teleport(loc);
                } else
                    sender.sendMessage(ChatColor.BLUE + "Location " + args[0] + ", " + args[1] + ", " + args[2]
                            + " has a non-number in");
            } else
                sender.sendMessage(ChatColor.BLUE + "Not enough arguements!");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}