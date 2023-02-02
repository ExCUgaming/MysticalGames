package com.mysticalsurvival.games.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ParkourInventory {

    private static Inventory inventory;
    private static ItemStack playerVis1;
    private static ItemStack playerVis2;

    public static void init() {

        Inventory inv = Bukkit.createInventory(null, 36);

        ItemStack gotoLastCheckpointItem = new ItemStack(Material.IRON_INGOT);
        ItemMeta gotoLastCheckpointItemMeta = gotoLastCheckpointItem.getItemMeta();
        gotoLastCheckpointItemMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Go to Last Checkpoint (Right Click)");
        gotoLastCheckpointItemMeta.setLocalizedName("Excu_");
        gotoLastCheckpointItem.setItemMeta(gotoLastCheckpointItemMeta);
        inv.setItem(0, gotoLastCheckpointItem);

        ItemStack resetItem = new ItemStack(Material.IRON_DOOR);
        ItemMeta resetItemMeta = resetItem.getItemMeta();
        resetItemMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Reset (Right Click)");
        resetItemMeta.setLocalizedName("Excu_");
        resetItem.setItemMeta(resetItemMeta);
        inv.setItem(2, resetItem);

        ItemStack leaveItem = new ItemStack(Material.RED_BED);
        ItemMeta leaveItemMeta = leaveItem.getItemMeta();
        leaveItemMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"Leave (Right Click)");
        leaveItemMeta.setLocalizedName("Excu_");
        leaveItem.setItemMeta(leaveItemMeta);
        inv.setItem(8, leaveItem);

        ItemStack visItem = new ItemStack(Material.LIME_DYE);
        ItemMeta visItemMeta = visItem.getItemMeta();
        visItemMeta.setDisplayName(ChatColor.GREEN+"Toggle Player Visibility (Right Click)");
        visItemMeta.setLocalizedName("Excu_");
        visItem.setItemMeta(visItemMeta);
        playerVis1 = visItem;

        ItemStack vis1Item = new ItemStack(Material.GRAY_DYE);
        ItemMeta vis1ItemMeta = vis1Item.getItemMeta();
        vis1ItemMeta.setDisplayName(ChatColor.GRAY+"Toggle Player Visibility (Right Click)");
        vis1ItemMeta.setLocalizedName("Excu_");
        vis1Item.setItemMeta(vis1ItemMeta);
        playerVis2 = vis1Item;

        inventory = inv;

    }

    public static Inventory getInventory() {
        return inventory;
    }

    public static ItemStack getVisItem1() {
        return playerVis1;
    }

    public static ItemStack getVisItem2() {
        return playerVis2;
    }

}
