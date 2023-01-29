package com.mysticalsurvival.core.lobby;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

public class Placeholders extends PlaceholderExpansion {

    @Override
    public String getAuthor() {
        return "Excu_";
    }

    @Override
    public String getIdentifier() {
        return "MysticalGames";
    }

    @Override
    public String getVersion() {
        return "1.1";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        switch (params) {
            case "game" -> {
                if (player.getPlayer().getLocation().getWorld().getName().equalsIgnoreCase("Parkour")) {
                    return "Parkour";
                } else {
                    return "Lobby";
                }
            }
            case "level" -> {
                return "Coming Soon!";
            }
            default -> {
                return null;
            }
        }

    }

}
