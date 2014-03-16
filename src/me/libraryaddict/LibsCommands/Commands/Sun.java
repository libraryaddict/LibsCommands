package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sun implements CommandExecutor {
    public String[] aliases = new String[] { "stoprain" };
    public String description = "Stops the rain";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.sun")) {
            World world = null;
            if (args.length > 0) {
                for (World w : Bukkit.getWorlds()) {
                    if (w.getName().toLowerCase().startsWith(args[0].toLowerCase())) {
                        world = w;
                        break;
                    }
                }
                if (world == null)
                    sender.sendMessage(ChatColor.RED + "World " + args[0] + " cannot be found");
            }
            if (world == null) {
                if (sender instanceof Player)
                    world = ((Player) sender).getWorld();
                else {
                    sender.sendMessage(ChatColor.RED + "/sun <World>");
                    return true;
                }
            }
            world.setThundering(false);
            world.setStorm(false);
            world.setThunderDuration(0);
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}