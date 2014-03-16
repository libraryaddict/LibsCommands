package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VotingLinks implements CommandExecutor {
    public String[] aliases = new String[] { "vote", "v" };
    public String description = "View the links to vote at";
    public String[] voteLinks = new String[] { "&5 - Vote at these links!", "&4 - http://bit.ly/1cKaBpU",
            "&1 - http://bit.ly/1cohl8b", "&2 - http://bit.ly/1hi965F", "&d - http://bit.ly/JQkrew",
            "&7 - http://bit.ly/19KTjEw", "&3 - http://bit.ly/1eXCE8p", "&6 - http://bit.ly/1bBHQai",
            "&c - http://bit.ly/1d0QNjE" };

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        for (String link : voteLinks) {
            sender.sendMessage(link);
        }
        return true;
    }
}