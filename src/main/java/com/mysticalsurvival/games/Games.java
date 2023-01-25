package com.mysticalsurvival.games;

import com.mysticalsurvival.games.commands.MainCommand;
import com.mysticalsurvival.games.commands.lobby.LobbyCommands;
import com.mysticalsurvival.games.commands.parkour.ParkourAdminCommand;
import com.mysticalsurvival.games.commands.parkour.ParkourCommands;
import com.mysticalsurvival.games.core.GamePlayer;
import com.mysticalsurvival.games.core.parkour.Parkour;
import com.mysticalsurvival.games.listeners.lobby.GUIListener;
import com.mysticalsurvival.games.listeners.lobby.GamePlayerListener;
import com.mysticalsurvival.games.listeners.parkour.*;
import com.mysticalsurvival.games.util.MessagesConfig;
import com.mysticalsurvival.games.util.PlayerStatistics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Games extends JavaPlugin {

    private static Games instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Games.instance = this;
        Parkour.init();
        PlayerStatistics.init();
        registerCommands();
        registerEvents();
        MessagesConfig.init();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        System.out.println("sus");
        for (GamePlayer gp : PlayerStatistics.getGameAllGamePlayers()) {
            gp.save();
        }
    }

    public static File getPluginDataFolder() {
        return Games.getPlugin(Games.class).getDataFolder();
    }

    public static Games getInstance() {
        return Games.instance;
    }

    void registerCommands() {
        getCommand("parkouradmin").setExecutor(new ParkourAdminCommand());
        getCommand("leaveparkour").setExecutor(new ParkourCommands());
        getCommand("parkours").setExecutor(new LobbyCommands());
        getCommand("mysticalgames").setExecutor(new MainCommand());
    }

    void registerEvents() {
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
