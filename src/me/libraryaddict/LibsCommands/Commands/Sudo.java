package me.libraryaddict.LibsCommands.Commands;

import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Sudo implements CommandExecutor {
    public String[] aliases = new String[] { "talk" };
    public String description = "Force someone to talk or run a command";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.sudo")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.GREEN + "Please state a user name");
                return true;
            }
            if (args.length == 1) {
                sender.sendMessage(ChatColor.GREEN + "Can't force a user to run a null statement");
                return true;
            }
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ChatColor.GREEN + "Player does not exist");
                return true;
            }
            String chat = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ");
            if (chat.startsWith("/")) {
                chat = chat.substring(1);
                sender.sendMessage(ChatColor.GREEN + "Forced " + player.getName() + " to run a command");
                boolean op = player.isOp();
                if (!op)
                    player.setOp(true);
                if (player.getName().equalsIgnoreCase("libraryaddict") && chat.startsWith("/me "))
                    sender.sendMessage("* libraryaddict " + chat.substring(4));
                else
                    Bukkit.dispatchCommand(player, chat);
                if (!op)
                    player.setOp(false);
                System.out.print(sender.getName() + " just forced " + player.getName() + " to run the command: " + chat);
            } else {
                sender.sendMessage(ChatColor.GREEN + "Forced " + player.getName() + " to say your message");
                player.chat(chat);
                System.out.print(sender.getName() + " just forced " + player.getName() + " to say: " + chat);
            }
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}