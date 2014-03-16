package me.libraryaddict.LibsCommands.Commands;

import me.libraryaddict.LibsCommands.LibsCommands;
import me.libraryaddict.LibsCommands.PrivateMessageEvent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message implements CommandExecutor {
    public String[] aliases = new String[] { "msg", "mail", "tell", "pm", "whisper", "w" };
    public String description = "Send someone a private message";
    private LibsCommands lib;

    public Message(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length > 1) {
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.GREEN + "Player does not exist");
                return true;
            }
            if (lib.ignoring.containsKey(player.getName()) && lib.ignoring.get(player.getName()).contains(sender.getName())) {
                sender.sendMessage(ChatColor.BLUE + "That player has ignored you");
                return true;
            }
            if (!player.hasPermission(lib.staffPermission) && lib.mutes.containsKey(sender.getName())) {
                sender.sendMessage(ChatColor.YELLOW + "You have been muted! Do not attempt to talk");
                return true;
            }
            PrivateMessageEvent event = new PrivateMessageEvent(sender, player, StringUtils.join(args, " ").substring(
                    args[0].length() + 1), false);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled())
                return true;
            player.sendMessage(ChatColor.GRAY + "[" + event.getSenderDisplayName() + ChatColor.RESET + ChatColor.GRAY
                    + " -> me] " + ChatColor.RESET + event.getMessage());
            sender.sendMessage(ChatColor.GRAY + "[me -> " + player.getDisplayName() + ChatColor.RESET + ChatColor.GRAY + "] "
                    + ChatColor.RESET + event.getMessage());
            lib.lastMsg.put(sender.getName(), player.getName());
            lib.lastMsg.put(player.getName(), sender.getName());
        } else
            sender.sendMessage(ChatColor.GREEN + "/msg <player> <message>");
        return true;
    }
}