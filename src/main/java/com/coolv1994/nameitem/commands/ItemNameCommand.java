package com.coolv1994.nameitem.commands;

import com.coolv1994.nameitem.Plugin;
import com.coolv1994.nameitem.Utils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Vinnie on 7/31/2015.
 */
public class ItemNameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("[NameItem] This command can not be used from console.");
            return true;
        }

        if (args.length == 0) {
            return false;
        }

        final Player player = (Player) sender;
        final ItemStack hand = player.getItemInHand();
        if (hand == null) {
            player.sendMessage("[NameItem] You must have an item selected.");
            return true;
        }

        Bukkit.getScheduler().runTaskAsynchronously(Plugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (Utils.canChangeName(player, hand, false)) {
                    Utils.renameItem(player, hand, Utils.getNameFromArgs(args));
                }
            }
        });
        return true;
    }

}
