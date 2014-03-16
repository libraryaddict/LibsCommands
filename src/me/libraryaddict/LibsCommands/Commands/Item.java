package me.libraryaddict.LibsCommands.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;

public class Item implements CommandExecutor {
    public String[] aliases = new String[] { "i" };
    public String description = "Spawn in items with /i <ItemName/ID>:<Data> <Amount>";

    private Material getMaterial(String string) {
        Material m = Material.getMaterial(string.toUpperCase());
        if (m == null) {
            try {
                m = Material.getMaterial(Integer.parseInt(string));
            } catch (Exception ex) {

            }
        }
        if (m == null) {
            for (Material mat : Material.values())
                if (mat.name().replace("_", "").equalsIgnoreCase(string.replace("_", ""))) {
                    m = mat;
                    break;
                }
        }
        return m;
    }

    private int[] parseString(String[] args) throws Exception {
        int[] returns = new int[3];
        returns[2] = 0;
        returns[1] = 64;
        String[] first = args[0].split(":");
        Material m = getMaterial(first[0]);
        if (m == null)
            throw new Exception("Unrecognized item name " + first[0]);
        returns[0] = m.getId();
        if (first.length > 1) {
            try {
                returns[2] = Integer.parseInt(first[1]);
            } catch (Exception ex) {
                throw new Exception("Cannot parse " + args[0]);
            }
        }
        if (args.length > 1)
            try {
                returns[1] = Integer.parseInt(args[1]);
            } catch (Exception ex) {
                throw new Exception("Cannot parse " + args[1]);
            }
        return returns;
    }

    private int getMaxAllowed(CommandSender sender, int itemId) {
        for (PermissionAttachmentInfo permissions : sender.getEffectivePermissions()) {
            if (permissions.getPermission().startsWith("bukkit.command.item")) {
                // Its a give permission
                String[] strings = permissions.getPermission().replace("bukkit.command.item", "").split(".");
                if (strings.length > 0) {
                    if (isNumeric(strings[0]) && Integer.parseInt(strings[0]) == itemId) {
                        // Def gonna return at this point
                        if (!permissions.getValue())
                            return -1;
                        if (strings.length > 1)
                            if (isNumeric(strings[1])) {
                                return Integer.parseInt(strings[1]);
                            } else
                                return 1;
                        return 9999;
                    } else
                        continue;
                } 
            }
        }
        return sender.hasPermission("bukkit.command.item") ? 9999 : -1;
    }

    private boolean isNumeric(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.give")) {
            if (args.length > 0) {
                ItemStack[] items = null;
                int[] returns;
                try {
                    returns = parseString(args);
                    double amount = returns[1];
                    int max = getMaxAllowed(sender, returns[0]);
                    if (max == -1) {
                        sender.sendMessage(ChatColor.RED + "You do not have permission to spawn in this item");
                        return true;
                    } else if (amount > max)
                        amount = max;
                    items = new ItemStack[(int) Math.ceil(amount / 64)];
                    for (int i = 0; i < items.length; i++) {
                        ItemStack item = new ItemStack(returns[0], (int) amount, (short) returns[2]);
                        items[i] = item;
                        amount -= 64;
                    }
                } catch (Exception ex) {
                    sender.sendMessage(ChatColor.RED + "Error: " + ex.getMessage());
                    return true;
                }
                Player p = Bukkit.getPlayerExact(sender.getName());
                for (ItemStack item : items)
                    p.getInventory().addItem(item);
                sender.sendMessage(ChatColor.RED + "Added " + returns[1] + " of "
                        + Material.getMaterial(returns[0]).name().toLowerCase() + " to " + p.getName() + "'s inventory");
            } else
                sender.sendMessage(ChatColor.RED + "/i <Item name or ID>:<Data Value> <Amount>");
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }
}