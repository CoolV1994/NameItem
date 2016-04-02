package com.coolv1994.nameitem;

import com.coolv1994.nameitem.commands.ItemLoreCommand;
import com.coolv1994.nameitem.commands.ItemNameCommand;
import com.coolv1994.nameitem.commands.ItemNameColorCommand;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Vinnie on 7/31/2015.
 */
public class Plugin extends JavaPlugin {

	private static Plugin instance;

	public static Plugin getInstance() {
            return instance;
	}

	private void enableCommands() {
            getCommand("itemname").setExecutor(new ItemNameCommand());
            getCommand("itemnamecolor").setExecutor(new ItemNameColorCommand());
            getCommand("itemlore").setExecutor(new ItemLoreCommand());
	}

	@Override
	public void onEnable() {
            instance = this;
            saveDefaultConfig();
            reloadConfig();
            Utils.useEvents = getConfig().getBoolean("useEvents");
            enableCommands();
	}

	@Override
	public void onDisable() {
	}

}
