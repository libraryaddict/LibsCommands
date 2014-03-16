package me.libraryaddict.LibsCommands.Commands;

import me.libraryaddict.LibsCommands.LibsCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Motd implements CommandExecutor {
    public String[] aliases = new String[0];
    public String description = "Displays the motd greeting";
    private LibsCommands lib;

    public Motd(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage(String.format(
                ChatColor.translateAlternateColorCodes('&', lib.getConfig().getString("Motd").replace("\\n", "\n")),
                sender.getName()));
        return true;
    }
}