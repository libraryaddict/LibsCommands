package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Thunder implements CommandExecutor {
    public String[] aliases = new String[] { "lightning" };
    public String description = "Turns thunder on in the current world";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.thunder")) {
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
                    sender.sendMessage(ChatColor.RED + "/thunder <World>");
                    return true;
                }
            }
            world.setThundering(true);
            world.setStorm(true);
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}