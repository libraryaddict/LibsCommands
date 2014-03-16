package me.libraryaddict.LibsCommands.Commands;

import java.lang.reflect.Method;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Snow implements CommandExecutor {
    public String[] aliases = new String[] { "snowday", "snowman" };
    private Method canSnow = null;
    public String description = "Snow over a area";
    private Object snowBlock;

    private boolean canPlace(Block b) {
        try {
            Object world = b.getWorld().getClass().getMethod("getHandle").invoke(b.getWorld());
            if (canSnow == null) {
                Class blockClass = Class.forName(world.getClass().getPackage().getName() + ".Block");
                snowBlock = blockClass.getDeclaredField("SNOW").get(null);
                canSnow = blockClass.getMethod("canPlace", Class.forName(world.getClass().getPackage().getName() + ".World"),
                        int.class, int.class, int.class);
            }
            return (Boolean) canSnow.invoke(snowBlock, world, b.getX(), b.getY(), b.getZ());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.snow")) {
            if (args.length > 0) {
                Integer radius = Integer.parseInt(args[0]);
                Location loc = ((Player) sender).getLocation().clone();
                for (int x = -radius; x <= radius; x++) {
                    for (int z = -radius; z <= radius; z++) {
                        if ((x * x) + (z * z) <= radius * radius) {
                            Block b = loc.getWorld().getHighestBlockAt(loc.getBlockX() + x, loc.getBlockZ() + z);
                            if (b.getType() == Material.AIR && canPlace(b))
                                b.setType(Material.SNOW);
                        }
                    }
                }
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Snowed in the area with a radius of " + args[0]);
            } else
                sender.sendMessage(ChatColor.RED + "Please state a radius");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}