package com.coolv1994.nameitem;

import com.coolv1994.nameitem.events.ItemSetLoreEvent;
import com.coolv1994.nameitem.events.ItemSetNameEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vinnie
 */
public class Utils {

    public static boolean useEvents;

    public static boolean hasPermission(Player player, String action, String item) {
        if (player.isOp())
            return true;
        if (player.hasPermission("nameitem.deny." + action + "." + item) ||
                player.hasPermission("nameitem.deny.*." + item))
            return false;
        if (player.hasPermission("nameitem.*." + item) ||
                player.hasPermission("nameitem.*.any"))
            return true;
        return player.hasPermission("nameitem." + action + "." + item) ||
                player.hasPermission("nameitem." + action + ".any");
    }

    public static boolean canChangeName(Player player, ItemStack item, boolean recolor) {
        String action = "rename";
        if (recolor) {
            action = "recolor";
        }

        if (!hasPermission(player, action, item.getType().name())) {
            player.sendMessage("[NameItem] You do not have permission to " + action + " this item.");
            return false;
        }

        if (!useEvents) {
            return true;
        }

        ItemSetNameEvent event = new ItemSetNameEvent(player, item, recolor);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public static boolean canChangeLore(Player player, ItemStack item) {
        if (!hasPermission(player, "lore", item.getType().name())) {
            player.sendMessage("[NameItem] You do not have permission to set lore on this item.");
            return false;
        }

        if (!useEvents) {
            return true;
        }

        ItemSetLoreEvent event = new ItemSetLoreEvent(player, item);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return !event.isCancelled();
    }

    public static String getNameFromArgs(String[] args) {
        int end = args.length - 1;
        StringBuilder name = new StringBuilder();
        for (int i = 0; ; i++) {
            name.append(args[i]);
            if (i == end)
                return name.toString();
            name.append(" ");
        }
    }

    public static void renameItem(Player player, ItemStack item, String name) {
        int max = Plugin.getInstance().getConfig().getInt("maxNameChars");
        if (name.length() > max) {
            name = name.substring(0, max);
        }
        name = ChatColor.translateAlternateColorCodes('&', name);
        ItemMeta metaData = item.getItemMeta();
        metaData.setDisplayName(name);
        item.setItemMeta(metaData);
    }

    public static void nameColorItem(Player player, ItemStack item, String name) {
        name = ChatColor.translateAlternateColorCodes('&', name);
        if (!ChatColor.stripColor(name).equals(
                ChatColor.stripColor(item.getItemMeta().getDisplayName()))) {
            player.sendMessage("[NameItem] Name must match the current items name.");
            return;
        }
        renameItem(player, item, name);
    }

    public static void setItemLore(Player player, ItemStack item, String lore) {
        int maxChars = Plugin.getInstance().getConfig().getInt("maxLoreChars");
        int maxLines = Plugin.getInstance().getConfig().getInt("maxLoreLines");
        lore = ChatColor.translateAlternateColorCodes('&', lore);
        String[] loreA = lore.split("\\{N\\}");
        List<String> loreL = new ArrayList<String>();
        int line = 0;
        for (String loreS : loreA) {
            line++;
            if (line > maxLines) {
                break;
            }
            if (loreS.length() > maxChars) {
                loreS = loreS.substring(0, maxChars);
            }
            loreL.add(loreS);
        }
        ItemMeta metaData = item.getItemMeta();
        metaData.setLore(loreL);
        item.setItemMeta(metaData);
    }

}
