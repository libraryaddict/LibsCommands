package me.libraryaddict.LibsCommands.Commands;

import java.util.ArrayList;
import java.util.List;
import me.libraryaddict.LibsCommands.LibsCommands;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Enchant implements CommandExecutor {
    private final int[] BVAL = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
    public String description = "Enchants the currently held item";
    private LibsCommands lib;
    private final String[] RCODE = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

    public Enchant(LibsCommands commands) {
        lib = commands;
    }

    private boolean isNatural(Enchantment ench) {
        return (ench.getName().equals(ench.getName().toUpperCase()) && !ench.getName().contains(" "));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender.hasPermission("bukkit.command.enchant")) {
            Player p = Bukkit.getPlayerExact(sender.getName());
            if (args.length == 0) {
                List<String> names = new ArrayList<String>();
                for (Enchantment enchant : Enchantment.values())
                    names.add(enchant.getName().toUpperCase());
                sender.sendMessage(ChatColor.GREEN + "Enchants: |" + ChatColor.RED
                        + StringUtils.join(names, ChatColor.GREEN + "|  |" + ChatColor.RED) + ChatColor.GREEN + "|");
            } else {
                Enchantment enchant = Enchantment.getByName(args[0].toUpperCase());
                if (enchant == null) {
                    for (Enchantment enchantment : Enchantment.values())
                        if (enchantment.getName().equalsIgnoreCase(args[0]))
                            enchant = enchantment;
                    if (enchant == null) {
                        sender.sendMessage(ChatColor.GREEN + "Enchant: " + args[0] + " - Not found");
                        return true;
                    }
                }
                int level = enchant.getMaxLevel();
                if (args.length > 1) {
                    if (lib.isNumeric(args[1]))
                        level = Integer.parseInt(args[1]);
                }
                if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
                    p.getItemInHand().addUnsafeEnchantment(enchant, level);
                    p.sendMessage(ChatColor.GREEN + "Item enchanted with " + enchant.getName() + " with level " + level);
                    updateEnchants(p.getItemInHand());
                } else
                    p.sendMessage(ChatColor.GREEN + "Unable to enchant item");
            }
        } else
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        return true;
    }

    private String toRoman(int binary) {
        if (binary <= 0 || binary >= 4000) {
            throw new NumberFormatException("Value outside roman numeral range.");
        }
        String roman = "";
        for (int i = 0; i < RCODE.length; i++) {
            while (binary >= BVAL[i]) {
                binary -= BVAL[i];
                roman += RCODE[i];
            }
        }
        return roman;
    }

    ItemStack updateEnchants(ItemStack item) {
        ArrayList<String> enchants = new ArrayList<String>();
        for (Enchantment ench : item.getEnchantments().keySet()) {
            if (!isNatural(ench)) {
                enchants.add(ChatColor.GRAY + ench.getName() + " " + toRoman(item.getEnchantments().get(ench)));
            }
        }
        ItemMeta meta = item.getItemMeta();
        meta.setLore(enchants);
        item.setItemMeta(meta);
        return item;
    }
}