package com.mysticalsurvival;

import com.mysticalsurvival.commands.lobby.LobbyCommands;
import com.mysticalsurvival.commands.MainCommand;
import com.mysticalsurvival.commands.parkour.ParkourAdminCommand;
import com.mysticalsurvival.commands.parkour.ParkourCommands;
import com.mysticalsurvival.core.GamePlayer;
import com.mysticalsurvival.core.parkour.Parkour;
import com.mysticalsurvival.listeners.lobby.GUIListener;
import com.mysticalsurvival.listeners.lobby.GamePlayerListener;
import com.mysticalsurvival.listeners.parkour.*;
import com.mysticalsurvival.util.MessagesConfig;
import com.mysticalsurvival.util.PlayerStatistics;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Games extends JavaPlugin {

    private static Games instance;

    @Override
    public void onEnable() {
        // Plugin startup logic

        //instance thingy
        Games.instance = this;

        //initialization
        Parkour.init();
        PlayerStatistics.init();
        registerCommands();
        registerEvents();
        MessagesConfig.init();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        //saves everyone's data
        for (GamePlayer gp : PlayerStatistics.getAllGamePlayers()) {
            gp.save();
        }
    }

    public static File getPluginDataFolder() {
        //gets datafolder

        return Games.getPlugin(Games.class).getDataFolder();
    }

    public static Games getInstance() {
        //instance getter

        return Games.instance;
    }

    void registerCommands() {
        //register the cmds

        getCommand("parkouradmin").setExecutor(new ParkourAdminCommand());
        getCommand("leaveparkour").setExecutor(new ParkourCommands());
        getCommand("parkours").setExecutor(new LobbyCommands());
        getCommand("mysticalgames").setExecutor(new MainCommand());
    }

    void registerEvents() {
        //register events

        //gets pluginManager to clean up code
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PressurePlateListener(), this);
        pm.registerEvents(new ParkourBlocksListener(), this);
        pm.registerEvents(new MoveEventPlayer(), this);
        pm.registerEvents(new PlayerLeave(), this);
        pm.registerEvents(new GUIListener(), this);
        pm.registerEvents(new ExtraListeners(), this);
        pm.registerEvents(new GamePlayerListener(), this);
    }
}
