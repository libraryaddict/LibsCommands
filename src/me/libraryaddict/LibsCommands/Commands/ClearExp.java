package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;

public class ClearExp implements CommandExecutor {
    public String description = "Removes all the exp in the world";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.clearexp")) {
            int cur = 0;
            int value = 0;
            for (Entity entity : Bukkit.getPlayerExact(sender.getName()).getWorld().getEntities()) {
                if (entity instanceof ExperienceOrb) {
                    value += ((ExperienceOrb) entity).getExperience();
                    entity.remove();
                    cur += 1;
                }
            }
            sender.sendMessage(ChatColor.GRAY + "" + cur + " removed with a total value of " + value);
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}