package me.libraryaddict.LibsCommands.Commands;

import me.libraryaddict.LibsCommands.LibsCommands;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class Tag implements CommandExecutor {
    public String description = "Give a mob a custom name by right clicking them";
    private LibsCommands lib;

    public Tag(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.tag")) {
            Player p = Bukkit.getPlayerExact(sender.getName());
            p.removeMetadata("LibraryaddictTagName", lib);
            p.removeMetadata("LibraryaddictTagTimer", lib);
            if (args.length == 0) {
                p.setMetadata("LibraryaddictTagTimer", new FixedMetadataValue(lib, System.currentTimeMillis() + 10000));
                sender.sendMessage(ChatColor.GREEN
                        + "Now removing the custom name of the next living entity you right click. 10 seconds!");
            } else {
                String name = ChatColor.translateAlternateColorCodes('&', StringUtils.join(args, " "));
                p.setMetadata("LibraryaddictTagTimer", new FixedMetadataValue(lib, System.currentTimeMillis() + 10000));
                p.setMetadata("LibraryaddictTagName", new FixedMetadataValue(lib, name));
                sender.sendMessage(ChatColor.GREEN + "Now adding the custom name '" + ChatColor.RESET + name + ChatColor.RESET
                        + ChatColor.GREEN + "'. 10 seconds to right click them!");
            }
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}