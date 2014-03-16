package me.libraryaddict.LibsCommands.Commands;

import me.libraryaddict.LibsCommands.LibsCommands;
import me.libraryaddict.LibsCommands.PrivateMessageEvent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Reply implements CommandExecutor {
    public String[] aliases = new String[] { "r" };
    public String description = "Send a reply to the person you are talking to";
    private LibsCommands lib;

    public Reply(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "You didn't even bother typing a reply?");
            return true;
        }
        if (lib.lastMsg.containsKey(sender.getName())) {
            CommandSender player = lib.getSender(lib.lastMsg.get(sender.getName()));
            if (player == null) {
                sender.sendMessage(ChatColor.BLUE + lib.lastMsg.get(sender.getName()) + " isn't in the game!");
                return true;
            }
            if (lib.ignoring.containsKey(player.getName()) && lib.ignoring.get(player.getName()).contains(sender.getName())) {
                sender.sendMessage(ChatColor.BLUE + "That player has ignored you");
                return true;
            }
            PrivateMessageEvent event = new PrivateMessageEvent(sender, player, StringUtils.join(args, " "), true);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return true;
            player.sendMessage(ChatColor.GRAY + "[" + event.getSenderDisplayName() + ChatColor.RESET + ChatColor.GRAY
                    + " -> me] " + ChatColor.RESET + event.getMessage());
            sender.sendMessage(ChatColor.GRAY + "[me -> " + event.getReceiverDisplayName() + ChatColor.RESET + ChatColor.GRAY
                    + "] " + ChatColor.RESET + event.getMessage());
            lib.lastMsg.put(sender.getName(), player.getName());
            lib.lastMsg.put(player.getName(), sender.getName());
        } else
            sender.sendMessage(ChatColor.GREEN + "No one to reply to.. Forever alone.");
        return true;
    }
}