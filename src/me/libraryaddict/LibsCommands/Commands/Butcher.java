package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class Butcher implements CommandExecutor {
    public String[] aliases = new String[] { "killall" };
    public String description = "Kills all the mobs selectively or indiscriminately";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.butcher")) {
            int cur = 0;
            for (Entity entity : Bukkit.getPlayerExact(sender.getName()).getWorld().getEntities()) {
                if (entity instanceof LivingEntity && !(entity instanceof Player)) {
                    LivingEntity creature = (LivingEntity) entity;
                    if (args.length == 0 || args[0].equalsIgnoreCase("all")
                            || creature.getType().name().replace("_", "").equalsIgnoreCase(args[0].replace("_", ""))) {
                        entity.remove();
                        cur += 1;
                    }
                }
            }
            sender.sendMessage(ChatColor.GRAY + "" + cur + " removed");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}