package com.mysticalsurvival.games.effects;

import com.mysticalsurvival.games.Games;
import com.mysticalsurvival.games.core.parkour.Checkpoint;
import com.mysticalsurvival.games.core.parkour.Parkour;
import com.mysticalsurvival.games.core.parkour.ParkourMap;
import com.mysticalsurvival.games.core.parkour.ParkourRun;
import com.mysticalsurvival.games.util.MessagesConfig;
import com.mysticalsurvival.games.util.ParkourInventory;
import com.mysticalsurvival.games.util.PlayerStatistics;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.math.BigDecimal;
import java.util.Objects;

public class ParkourPlayerActionManager {

    Games main = Games.getInstance();

    public void playerStepOnStartPressurePlate(Player player, ParkourMap parkourMap, Location loc) {
        if (Parkour.getParkourRunInstance(player) == null) {
            new ParkourRun(player, parkourMap);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.start.success")))
                    .replaceAll("<player>", player.getName())
                    .replaceAll("<playerdisplay>", player.getDisplayName())
                    .replaceAll("<map>", parkourMap.getName()));
            player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);

            player.getInventory().setContents(ParkourInventory.getInventory().getContents());
            if (PlayerStatistics.getGamePlayer(player).getOtherPlayerVisibility()) {
                player.getInventory().setItem(4, ParkourInventory.getVisItem1());
            } else {
                player.getInventory().setItem(4, ParkourInventory.getVisItem2());
            }
            player.updateInventory();
        } else if (Parkour.getParkourRunInstance(player).getParkourMap().getStartLocation().equals(loc)) {
            Parkour.getParkourRunInstance(player).resetTimer();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.start.time-reset")))
                    .replaceAll("<player>", player.getName())
                    .replaceAll("<playerdisplay>", player.getDisplayName())
                    .replaceAll("<map>", parkourMap.getName()));
            player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 10, 1);
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.start.other-parkour")))
                    .replaceAll("<player>", player.getName())
                    .replaceAll("<playerdisplay>", player.getDisplayName())
                    .replaceAll("<map>", parkourMap.getName()));
            player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
        }
    }

    public void playerStepOnEndPressurePlate(Player player, ParkourMap parkourMap, Location loc) {
        if (Parkour.getParkourRunInstance(player) != null) {
            if (Parkour.getParkourRunInstance(player).getParkourMap().getEndLocation().equals(loc)) {
                ParkourRun pr = Parkour.getParkourRunInstance(player);
                if (pr.finishParkour()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.end.success")))
                            .replaceAll("<player>", player.getName())
                            .replaceAll("<playerdisplay>", player.getDisplayName())
                            .replaceAll("<map>", parkourMap.getName())
                            .replaceAll("<time>", pr.getTime()));
                    player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_CELEBRATE, 10, 1);
                    player.sendTitle(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.end.title")))
                            .replaceAll("<player>", player.getName())
                            .replaceAll("<playerdisplay>", player.getDisplayName())
                            .replaceAll("<map>", parkourMap.getName())
                            .replaceAll("<time>", pr.getTime()),
                            ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.end.subtitle")))
                            .replaceAll("<player>", player.getName())
                            .replaceAll("<playerdisplay>", player.getDisplayName())
                            .replaceAll("<map>", parkourMap.getName())
                            .replaceAll("<time>", pr.getTime()),
                            10, 40, 10);
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.end.no-checkpoints")))
                            .replaceAll("<player>", player.getName())
                            .replaceAll("<playerdisplay>", player.getDisplayName())
                            .replaceAll("<map>", parkourMap.getName())
                            .replaceAll("<time>", pr.getTime()));
                    player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.end.other-parkour")))
                        .replaceAll("<player>", player.getName())
                        .replaceAll("<playerdisplay>", player.getDisplayName())
                        .replaceAll("<map>", parkourMap.getName()));
                player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
            }
        } else {
            player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.end.skip")))
                    .replaceAll("<player>", player.getName())
                    .replaceAll("<playerdisplay>", player.getDisplayName())
                    .replaceAll("<map>", parkourMap.getName()));
        }
    }

    public void playerCancelParkourUsingCommand(Player player) {
        if (Parkour.getParkourRunInstance(player) != null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.command-leave.success")))
                    .replaceAll("<player>", player.getName())
                    .replaceAll("<playerdisplay>", player.getDisplayName()));
            player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_DEATH, 10, 1);
            Parkour.getParkourRunInstance(player).cancelParkour();
        } else {
            player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.command-leave.no-parkour")))
                    .replaceAll("<player>", player.getName())
                    .replaceAll("<playerdisplay>", player.getDisplayName()));
        }
    }

    public void playerResetParkourUsingCommand(Player player) {
        if (Parkour.getParkourRunInstance(player) != null) {
            Parkour.getParkourRunInstance(player).resetTimer();
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.command-reset.success")))
                    .replaceAll("<player>", player.getName())
                    .replaceAll("<playerdisplay>", player.getDisplayName()));
            player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 10, 1);
            player.teleport(Parkour.getParkourRunInstance(player).getParkourMap().getSpawnLocation());
        } else {
            player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.command-reset.success")))
                    .replaceAll("<player>", player.getName())
                    .replaceAll("<playerdisplay>", player.getDisplayName()));
        }
    }

    public void sendTimeOnActionBar(Player player, BigDecimal time) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(
                Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.time.action-bar").replaceAll("<time>", ParkourRun.getParkourRunInstance(player).getTime()))));
    }

    public void playerDeath(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.death.success")))
                .replaceAll("<player>", player.getName())
                .replaceAll("<playerdisplay>", player.getDisplayName()));
        player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_DEATH, 10, 1);
        Parkour.getParkourRunInstance(player).parkourDeath();
    }

    public void playerStepCheckpoint(Checkpoint ck, Player player) {
        if (Parkour.getParkourRunInstance(player) != null) {
            if (Parkour.getParkourRunInstance(player).getParkourMap().equals(ck.getParkourMap())) {
                if (Parkour.getParkourRunInstance(player).reachCheckpoint(ck)) {
                    player.playSound(player.getEyeLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10, 1);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.checkpoint.success")))
                            .replaceAll("<player>", player.getName())
                            .replaceAll("<playerdisplay>", player.getDisplayName())
                            .replaceAll("<map>", Parkour.getParkourRunInstance(player).getParkourMap().getName())
                            .replaceAll("<ckid>", String.valueOf(ck.getId()))
                            .replaceAll("<time>", Parkour.getParkourRunInstance(player).getTime()));
                    player.sendTitle(
                            ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.checkpoint.title")))
                            .replaceAll("<player>", player.getName())
                            .replaceAll("<playerdisplay>", player.getDisplayName())
                            .replaceAll("<map>", Parkour.getParkourRunInstance(player).getParkourMap().getName())
                            .replaceAll("<ckid>", String.valueOf(ck.getId()))
                            .replaceAll("<time>", Parkour.getParkourRunInstance(player).getTime()),
                            ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.checkpoint.subtitle")))
                                    .replaceAll("<player>", player.getName())
                                    .replaceAll("<playerdisplay>", player.getDisplayName())
                                    .replaceAll("<map>", Parkour.getParkourRunInstance(player).getParkourMap().getName())
                                    .replaceAll("<ckid>", String.valueOf(ck.getId()))
                                    .replaceAll("<time>", Parkour.getParkourRunInstance(player).getTime()),
                            5, 20, 5);
                } else {
                    if (ck.getId() > Parkour.getParkourRunInstance(player).getReachedCheckpointId()) {
                        player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.checkpoint.skip")))
                                .replaceAll("<player>", player.getName())
                                .replaceAll("<playerdisplay>", player.getDisplayName())
                                .replaceAll("<map>", Parkour.getParkourRunInstance(player).getParkourMap().getName())
                                .replaceAll("<ckid>", String.valueOf(ck.getId()))
                                .replaceAll("<time>", Parkour.getParkourRunInstance(player).getTime()));
                    } else {
                        player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.checkpoint.reached")))
                                .replaceAll("<player>", player.getName())
                                .replaceAll("<playerdisplay>", player.getDisplayName())
                                .replaceAll("<map>", Parkour.getParkourRunInstance(player).getParkourMap().getName())
                                .replaceAll("<ckid>", String.valueOf(ck.getId()))
                                .replaceAll("<time>", Parkour.getParkourRunInstance(player).getTime()));
                    }
                }
                return;
            }
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.checkpoint.other-parkour")))
                .replaceAll("<player>", player.getName())
                .replaceAll("<playerdisplay>", player.getDisplayName()));
        player.playSound(player.getEyeLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
    }

    public void playerGotoLastCheckpoint(Player player) {
        if (Parkour.getParkourRunInstance(player) != null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.checkpoint.item-success")))
                    .replaceAll("<player>", player.getName())
                    .replaceAll("<playerdisplay>", player.getDisplayName())
                    .replaceAll("<map>", Parkour.getParkourRunInstance(player).getParkourMap().getName())
                    .replaceAll("<time>", Parkour.getParkourRunInstance(player).getTime()));
            player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 10, 1);
            if (Parkour.getParkourRunInstance(player).getReachedCheckpoint() != null) {
                player.teleport(Parkour.getParkourRunInstance(player).getReachedCheckpoint().getTpLocation());
            } else {
                player.teleport(Parkour.getParkourRunInstance(player).getParkourMap().getSpawnLocation());
            }
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(MessagesConfig.getMessage("games.parkour.player.checkpoint.item-no-parkour")))
                    .replaceAll("<player>", player.getName())
                    .replaceAll("<playerdisplay>", player.getDisplayName()));
        }
    }

    public void playerToggleOtherPlayerVisibilityInParkourMap(Player player) {
        if (Parkour.getParkourRunInstance(player) != null) {
            if (PlayerStatistics.getGamePlayer(player).getOtherPlayerVisibility()) {
                PlayerStatistics.getGamePlayer(player).setOtherPlayerVisibility(false);
                for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                    player.hidePlayer(main, otherPlayer);
                }
                player.sendMessage(ChatColor.DARK_PURPLE+"You toggled player visibility to "+ChatColor.LIGHT_PURPLE+"off.");
                player.getInventory().setItem(4, ParkourInventory.getVisItem2());
            } else {
                PlayerStatistics.getGamePlayer(player).setOtherPlayerVisibility(true);
                for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
                    player.showPlayer(main, otherPlayer);
                }
                player.sendMessage(ChatColor.DARK_PURPLE+"You toggled player visibility to "+ChatColor.LIGHT_PURPLE+"on.");
                player.getInventory().setItem(4, ParkourInventory.getVisItem1());
            }
            player.updateInventory();
        } else {
            player.sendMessage(ChatColor.RED+"You are not in a parkour.");
        }
    }
}
