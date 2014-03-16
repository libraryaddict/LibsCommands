package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeSurvival implements CommandExecutor {
    public String[] aliases = new String[] { "gms" };
    public String description = "Change gamemode to survival";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.gamemodesurvival")) {
            Player p;
            if (args.length > 0) {
                p = Bukkit.getPlayer(args[0]);
                if (p == null) {
                    sender.sendMessage(ChatColor.RED + "Cannot find player '" + args[0] + "'");
                    return true;
                }
                sender.sendMessage(ChatColor.RED + "Changed " + p.getName() + "'s gamemode to survival");
            } else {
                p = (Player) sender;
            }
            p.setGameMode(GameMode.SURVIVAL);
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}