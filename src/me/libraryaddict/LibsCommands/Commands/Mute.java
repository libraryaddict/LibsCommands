package me.libraryaddict.LibsCommands.Commands;

import me.libraryaddict.LibsCommands.LibsCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Mute implements CommandExecutor {
    public String description = "Prevent someone from talking";
    private LibsCommands lib;

    public Mute(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.mute")) {
            if (args.length > 0) {
                Player p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    sender.sendMessage(ChatColor.YELLOW + "Can't find player");
                    return true;
                }
                long expires = 0;
                if (args.length > 1) {
                    try {
                        expires = (long) (System.currentTimeMillis() + (1000 * 60 * Double.parseDouble(args[1])));
                    } catch (Exception ex) {
                        sender.sendMessage(ChatColor.YELLOW + args[1] + " is not a valid time!");
                        return true;
                    }
                }
                lib.mutes.put(p.getName(), expires);
                String expire = "until the server restarts";
                if (expires != 0) {
                    expire = "for " + lib.toReadableString((expires - System.currentTimeMillis()) / 1000);
                }
                sender.sendMessage(ChatColor.YELLOW + p.getName() + " is now muted " + expire);
                return true;
            }
            sender.sendMessage(ChatColor.YELLOW + "/mute <Name> <Minutes>");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}