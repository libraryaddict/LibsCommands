package me.libraryaddict.LibsCommands.Commands;

import me.libraryaddict.LibsCommands.LibsCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Speed implements CommandExecutor {
    public String description = "Change your walk or fly speed";
    private LibsCommands lib;

    public Speed(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.speed")) {
            if (args.length == 0 || !lib.isNumeric(args[0])) {
                sender.sendMessage(ChatColor.RED
                        + "This command changes your fly or walk speed depending on which you are doing, use /speed number");
                return true;
            }
            Player p = Bukkit.getPlayerExact(sender.getName());
            float move = Float.parseFloat(args[0]);
            if (move >= -10F && move <= 10F) {
                if (p.isFlying()) {
                    p.setFlySpeed(move / 10F);
                    sender.sendMessage(ChatColor.BLUE + "Fly speed set to " + move);
                } else {
                    p.setWalkSpeed(move / 10F);
                    sender.sendMessage(ChatColor.BLUE + "Walk speed set to " + move);
                }
            } else
                sender.sendMessage(ChatColor.BLUE + "You must set the number from -10.0 to 10.0");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}