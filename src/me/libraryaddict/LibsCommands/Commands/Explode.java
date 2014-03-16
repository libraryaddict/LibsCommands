package me.libraryaddict.LibsCommands.Commands;

import me.libraryaddict.LibsCommands.LibsCommands;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Explode implements CommandExecutor {
    public String[] aliases = new String[] { "kaboom" };
    public String description = "Explodes with the majestic might of a creeper";
    private LibsCommands lib;

    public Explode(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.explode")) {
            Player p = (Player) sender;
            if (args.length == 0)
                p.getWorld().createExplosion(p.getLocation(), 3F);
            else if (lib.isNumeric(args[0])) {
                float size = Float.parseFloat(args[0]);
                if (size > 50F) {
                    size = 50F;
                    sender.sendMessage(ChatColor.RED + "Are you insane? Limiting explosion size to 50");
                }
                p.getWorld().createExplosion(p.getLocation(), size);
            } else
                sender.sendMessage(ChatColor.RED + args[0] + " is not a number");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}