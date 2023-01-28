package com.mysticalsurvival.core.lobby;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ParkourGUI {

    public ParkourGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, ChatColor.RED+"Difficulty Selection");

        ItemStack fillerItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillerMeta = fillerItem.getItemMeta();
        fillerMeta.setDisplayName(" ");
        fillerItem.setItemMeta(fillerMeta);

        for (int i = 0; i < 11; i++) {
            gui.setItem(i, fillerItem.clone());
        }

        ItemStack easyItem = new ItemStack(Material.LIME_WOOL);
        ItemMeta easyMeta = easyItem.getItemMeta();
        easyMeta.setDisplayName(ChatColor.GREEN+"Easy");
        easyItem.setItemMeta(easyMeta);
        gui.setItem(11, easyItem);

        gui.setItem(12, fillerItem);

        ItemStack normalItem = new ItemStack(Material.YELLOW_WOOL);
        ItemMeta normalMeta = normalItem.getItemMeta();
        normalMeta.setDisplayName(ChatColor.YELLOW+"Normal");
        normalItem.setItemMeta(normalMeta);
        gui.setItem(13, normalItem);

        gui.setItem(14, fillerItem);

        ItemStack hardItem = new ItemStack(Material.RED_WOOL);
        ItemMeta hardMeta = hardItem.getItemMeta();
        hardMeta.setDisplayName(ChatColor.RED+"Hard");
        hardItem.setItemMeta(hardMeta);
        gui.setItem(15, hardItem);

        for (int i = 16; i < 27; i++) {
            gui.setItem(i, fillerItem.clone());
        }

        player.openInventory(gui);

    }

}