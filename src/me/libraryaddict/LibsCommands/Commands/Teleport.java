package me.libraryaddict.LibsCommands.Commands;

import me.libraryaddict.LibsCommands.LibsCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport implements CommandExecutor {
    public String[] aliases = new String[] { "tp" };
    public String description = "Teleport to a player";
    private LibsCommands lib;

    public Teleport(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.teleport")) {
            Player p = Bukkit.getPlayerExact(sender.getName());
            if (args.length > 0) {
                Player player = Bukkit.getPlayer(args[0]);
                Location loc = null;
                if (player == null) {
                    sender.sendMessage(ChatColor.BLUE + "Unknown player \"" + args[0] + "\"");
                    return true;
                }
                if (args.length > 1) {
                    p = player;
                    player = Bukkit.getPlayer(args[1]);
                    if (player == null) {
                        if (args.length > 3) {
                            if (lib.isNumeric(args[1]) && lib.isNumeric(args[2]) && lib.isNumeric(args[3]))
                                loc = new Location(p.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]),
                                        Double.parseDouble(args[3]));
                            else {
                                sender.sendMessage(ChatColor.BLUE + "Location " + args[1] + ", " + args[2] + ", " + args[3]
                                        + " has a non-number in");
                                return true;
                            }
                        } else {
                            sender.sendMessage(ChatColor.BLUE + "Unknown player \"" + args[1] + "\"");
                            return true;
                        }
                    } else
                        loc = player.getLocation();
                } else
                    loc = player.getLocation();
                p.teleport(loc);
            } else
                sender.sendMessage(ChatColor.BLUE + "/tp <player> <player>");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}