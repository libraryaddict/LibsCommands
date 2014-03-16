package me.libraryaddict.LibsCommands.Commands;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import me.libraryaddict.LibsCommands.LibsCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Server implements CommandExecutor {
    public String description = "A command for the server to make you switch bungeecord servers";
    private LibsCommands lib;

    public Server(LibsCommands commands) {
        lib = commands;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (args.length > 0) {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF("Connect");
                out.writeUTF(args[0]); // Target Server
            } catch (IOException e) {
            }
            Bukkit.getPlayerExact(sender.getName()).sendPluginMessage(lib, "BungeeCord", b.toByteArray());
        } else {
            sender.sendMessage(ChatColor.RED + "/server <Server>");
        }
        return true;
    }
}