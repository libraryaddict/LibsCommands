package me.libraryaddict.LibsCommands.Commands;

import java.util.ArrayList;
import java.util.List;
import me.libraryaddict.LibsCommands.LibsCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ignore implements CommandExecutor {
    public String description = "No more shall you see this annoying person talk";
    private LibsCommands lib;

    public Ignore(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length > 0) {
            Player ignore = Bukkit.getPlayer(args[0]);
            if (ignore == null) {
                sender.sendMessage(ChatColor.BLUE + "Player doesn't exist");
                return true;
            }
            if (ignore.hasPermission(lib.staffPermission) || sender.getName().equals(ignore.getName())) {
                sender.sendMessage(ChatColor.BLUE + "You may not ignore that person");
                return true;
            }
            List<String> ignores = new ArrayList<String>();
            if (lib.ignoring.containsKey(sender.getName()))
                ignores = lib.ignoring.get(sender.getName());
            if (ignores.contains(ignore.getName())) {
                ignores.remove(ignore.getName());
                sender.sendMessage(ChatColor.BLUE + "No longer ignoring " + ignore.getName());
            } else {
                ignores.add(ignore.getName());
                sender.sendMessage(ChatColor.BLUE + "Now ignoring " + ignore.getName());
            }
            if (ignores.size() == 0)
                lib.ignoring.remove(sender.getName());
            else
                lib.ignoring.put(sender.getName(), ignores);
        } else
            sender.sendMessage(ChatColor.BLUE + "/ignore <Player>");
        return true;
    }
}