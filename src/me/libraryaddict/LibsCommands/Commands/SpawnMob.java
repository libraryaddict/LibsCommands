package me.libraryaddict.LibsCommands.Commands;

import java.util.List;
import java.util.Random;

import me.libraryaddict.LibsCommands.LibsCommands;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.inventory.ItemStack;

public class SpawnMob implements CommandExecutor {
    public String description = "Spawn a mob in";
    private LibsCommands lib;
    public int maxMobsToSpawn = 50;

    public SpawnMob(LibsCommands commands) {
        lib = commands;
    }

    EntityType getType(String name) {
        for (EntityType type : EntityType.values())
            if (type.name().replace("_", "").equalsIgnoreCase(name.replace("_", "")))
                return type;
        return null;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.spawnmob")) {
            if (args.length > 0) {
                Player p = (Player) sender;
                List<Block> blocks = p.getLastTwoTargetBlocks(null, 200);
                Location l = blocks.get(1).getLocation().clone().add(0.5, 1, 0.5);
                int amount = 1;
                EntityType type = getType(args[0]);
                if (type == null) {
                    sender.sendMessage(ChatColor.RED + "Unknown entity type " + args[0]);
                    return true;
                }
                if (args.length > 1)
                    if (lib.isNumeric(args[1])) {
                        amount = Integer.parseInt(args[1]);
                        if (amount > maxMobsToSpawn) {
                            amount = maxMobsToSpawn;
                            sender.sendMessage(ChatColor.RED + "Max mobs to spawn limit has been reached. Limiting to " + amount);
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Unknown amount");
                        return true;
                    }
                for (int i = 0; i < amount; i++) {
                    Entity e = l.getWorld().spawnEntity(l, type);
                    if (e instanceof Skeleton && ((Skeleton) e).getSkeletonType() == SkeletonType.NORMAL)
                        ((Skeleton) e).getEquipment().setItemInHand(
                                new ItemStack(Material.BOW, 1, (short) new Random().nextInt(384)));
                }
                p.sendMessage(ChatColor.RED + "Spawned " + amount + " of " + type.name().toLowerCase());
            } else
                sender.sendMessage(ChatColor.RED + "/spawnmob <EntityType> <Amount>");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}