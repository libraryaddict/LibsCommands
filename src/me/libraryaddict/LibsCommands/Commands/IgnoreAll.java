package me.libraryaddict.LibsCommands.Commands;

import me.libraryaddict.LibsCommands.LibsCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class IgnoreAll implements CommandExecutor {
    public String description = "Ignores everyone but staff";
    private LibsCommands lib;

    public IgnoreAll(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (lib.ignoringAll.contains(sender.getName())) {
            sender.sendMessage(ChatColor.BLUE + "No longer ignoring everyone");
            lib.ignoringAll.remove(sender.getName());
        } else {
            sender.sendMessage(ChatColor.BLUE + "Now ignoring everyone");
            lib.ignoringAll.add(sender.getName());
        }
        return true;
    }
}