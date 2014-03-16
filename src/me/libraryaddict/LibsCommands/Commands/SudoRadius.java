package me.libraryaddict.LibsCommands.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import me.libraryaddict.LibsCommands.LibsCommands;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoRadius implements CommandExecutor {
    public String description = "Force players in a radius to talk or run a command";
    private LibsCommands lib;

    public SudoRadius(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.sudoradius")) {
            if (args.length > 1) {
                if (lib.isNumeric(args[0])) {
                    double radius = Double.parseDouble(args[0]);
                    ArrayList<String> sudod = new ArrayList<String>();
                    Player p = Bukkit.getPlayerExact(sender.getName());
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getLocation().distance(p.getLocation()) > radius || player == p)
                            continue;
                        sudod.add(player.getName());
                        String chat = StringUtils.join(Arrays.copyOfRange(args, 1, args.length), " ").replace("%me%",
                                player.getName());
                        if (chat.startsWith("/")) {
                            chat = chat.substring(1);
                            boolean op = player.isOp();
                            if (!op)
                                player.setOp(true);
                            Bukkit.dispatchCommand(player, chat);
                            if (!op)
                                player.setOp(false);
                        } else
                            player.chat(chat);
                    }
                    sender.sendMessage(ChatColor.YELLOW + "Sudo'd " + StringUtils.join(sudod, ", ") + ".");
                    System.out.print(sender.getName() + " sudo'd " + StringUtils.join(sudod, ", ") + ".");
                } else
                    sender.sendMessage(ChatColor.YELLOW + "Thats not a number");
            } else
                sender.sendMessage(ChatColor.YELLOW + "/sudoradius <radius> <command/chat>");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}