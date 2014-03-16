package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Cape implements CommandExecutor {
    public String[] aliases = new String[] { "minecon", "capes" };
    public String capeMessage = ChatColor.GREEN
            + "Seen a few players with a cape on? You get the cape from various sources. "
            + "The only official way is to go to minecon. "
            + "The players with a cape of a red creeper and the cape of a pickaxe on a blue background as well as the piston on a green background got theirs there. "
            + "Client mods can give you them but they require you to have it installed, Anyone that doesn't have the mod installed will not see it";
    public String description = "Tells the player where to get that fancy cape they saw";

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage(capeMessage);
        return true;
    }
}