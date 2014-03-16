package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {
    public String description = "Heal up completely";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.heal")) {
            Player p;
            if (args.length > 0)
                p = Bukkit.getPlayer(args[0]);
            else
                p = (Player) sender;
            if (p == null) {
                sender.sendMessage(ChatColor.RED + "Can't find player " + ChatColor.GREEN + args[0]);
                return true;
            }
            p.setHealth(20);
            p.setFoodLevel(20);
            p.setSaturation(5.0F);
            p.setExhaustion(0F);
            p.setFireTicks(0);
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}