package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Night implements CommandExecutor {
    public String description = "Turns the current world to nighttime";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.night")) {
            World world = null;
            if (args.length > 0) {
                for (World w : Bukkit.getWorlds()) {
                    if (w.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                        world = w;
                        break;
                    }
                }
                if (world == null) {
                    sender.sendMessage(ChatColor.RED + "World " + args[0] + " cannot be found");
                    return true;
                }
            }
            if (world == null) {
                if (sender instanceof Player)
                    world = ((Player) sender).getWorld();
                else {
                    sender.sendMessage(ChatColor.RED + "/night <World>");
                    return true;
                }
            }
            world.setTime(13000);
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}