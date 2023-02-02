package com.mysticalsurvival.games.util;

import com.mysticalsurvival.games.Games;
import org.bukkit.configuration.file.YamlConfiguration;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class MessagesConfig {
    public static final File MSGFILE = new File(Games.getPluginDataFolder(), "messages.yml");
    static Games main = Games.getInstance();
    static DataWriter dw = new DataWriter();

    private static HashMap<String, String> messages = new HashMap<>();
    private static HashMap<String, List<String>> commandUsage = new HashMap<>();

    private MessagesConfig() {

    }

    public static void init() {
        if (!(MSGFILE.exists())) {
            try {
                main.saveResource("messages.yml", false);
                YamlConfiguration config = YamlConfiguration.loadConfiguration(MSGFILE);
                config.options().copyDefaults();
                config.save(MSGFILE);
            } catch (IOException ignored) {
                main.getLogger().log(Level.SEVERE, "Failed to save messages config file.");
            }
        }

        //games.
        dw.readConfigSection(MSGFILE, "games").getKeys(false).forEach(key -> {

            //games.*
            dw.readConfigSection(MSGFILE, "games."+key).getKeys(false).forEach(key1 -> {

                //games.*.*
                dw.readConfigSection(MSGFILE, "games."+key+"."+key1).getKeys(false).forEach(key2 -> {

                    //games.parkour.admin.command-usage
                    if (key2.equalsIgnoreCase("command-usage")) {

                        commandUsage.put("games."+key+"."+key1+"."+key2, dw.readListData(MSGFILE, "games."+key+"."+key1+"."+key2));

                    } else if (key2.equalsIgnoreCase("command-player")) {

                        messages.put("games." + key + "." + key1 + "." + key2, dw.readStringData(MSGFILE, "games." + key + "." + key1 + "." + key2));

                    } else {
                        //games.parkour.*.*.*
                        dw.readConfigSection(MSGFILE, "games."+key+"."+key1+"."+key2).getKeys(false).forEach(key3 -> {

                            messages.put("games." + key + "." + key1 + "." + key2 + "." + key3, dw.readStringData(MSGFILE, "games." + key + "." + key1 + "." + key2 + "." + key3));

                        });

                    }

                });

            });

        });
        main.getLogger().log(Level.WARNING, "There may be an error if keys are missing from the messages.yml file. Check if you have a corrupt file.");
        main.getLogger().log(Level.INFO, "Successfully loaded the messages config of MysticalGames.");

    }

    @Nullable
    public static String getMessage(String id) {
        return messages.get(id);
    }

    public static List<String> getList(String id) {
        return commandUsage.get(id);
    }
}