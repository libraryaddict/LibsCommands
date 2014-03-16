package me.libraryaddict.LibsCommands.Commands;

import me.libraryaddict.LibsCommands.LibsCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Unmute implements CommandExecutor {
    public String description = "Unmute a player";
    private LibsCommands lib;

    public Unmute(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.unmute")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.BLUE + "Need a name");
                return true;
            }
            Player p = Bukkit.getPlayer(args[0]);
            if (p == null) {
                sender.sendMessage(ChatColor.YELLOW + "Can't find player");
                return true;
            }
            if (lib.mutes.containsKey(p.getName())) {
                long expires = lib.mutes.get(p.getName());
                String expire = "until the server restarts";
                if (expires != 0) {
                    expire = "for " + lib.toReadableString((expires - System.currentTimeMillis()) / 1000);
                }
                lib.mutes.remove(p.getName());
                sender.sendMessage(ChatColor.YELLOW + p.getName() + " is no longer muted " + expire);
            } else {
                sender.sendMessage(ChatColor.YELLOW + "This player isn't muted");
            }
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}