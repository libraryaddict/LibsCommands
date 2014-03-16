package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode implements CommandExecutor {
    public String[] aliases = new String[] { "gm" };
    public String description = "Change gamemode";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.gamemode")) {
            Player p = Bukkit.getPlayerExact(sender.getName());
            if (args.length > 0) {
                if (args.length == 2) {
                    if (args[0].equals("0"))
                        p.setGameMode(GameMode.SURVIVAL);
                    else if (args[0].equals("1"))
                        p.setGameMode(GameMode.CREATIVE);
                    else if (args[0].equals("2"))
                        p.setGameMode(GameMode.ADVENTURE);
                } else {
                    Player player = Bukkit.getPlayer(args[0]);
                    if (player == null) {
                        sender.sendMessage(ChatColor.BLUE + "Player not found!");
                        return true;
                    }
                    if (args.length > 2) {
                        if (args[0].equals("0")) {
                            player.setGameMode(GameMode.SURVIVAL);
                        } else if (args[0].equals("1")) {
                            player.setGameMode(GameMode.CREATIVE);
                        } else if (args[0].equals("2")) {
                            player.setGameMode(GameMode.ADVENTURE);
                        } else {
                            sender.sendMessage(ChatColor.BLUE + "Unknown gamemode");
                            return true;
                        }
                    } else if (player.getGameMode() == GameMode.SURVIVAL)
                        player.setGameMode(GameMode.CREATIVE);
                    else
                        player.setGameMode(GameMode.SURVIVAL);
                    sender.sendMessage(ChatColor.BLUE + player.getDisplayName() + ChatColor.RESET + ChatColor.BLUE
                            + " is now in " + player.getGameMode().name());
                }
            } else {
                if (p.getGameMode() == GameMode.SURVIVAL)
                    p.setGameMode(GameMode.CREATIVE);
                else
                    p.setGameMode(GameMode.SURVIVAL);
            }
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}