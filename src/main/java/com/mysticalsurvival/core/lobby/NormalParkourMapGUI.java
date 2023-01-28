package com.mysticalsurvival.core.lobby;

import com.mysticalsurvival.core.parkour.Difficulty;
import com.mysticalsurvival.core.parkour.Parkour;
import com.mysticalsurvival.core.parkour.ParkourMap;
import com.mysticalsurvival.util.PageUtil;
import com.mysticalsurvival.util.PlayerStatistics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NormalParkourMapGUI {

    public NormalParkourMapGUI(Player player, int page) {
        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.YELLOW+"Normal Parkours - Page "+page);



        List<ItemStack> allItems = new ArrayList<>();
        ItemStack map = new ItemStack(Material.YELLOW_WOOL);
        List<String> lore = new ArrayList<>();

        for (ParkourMap pm : Parkour.getParkourMapInstances(Difficulty.NORMAL)) {
            ItemMeta mapMeta = map.getItemMeta();
            if (PlayerStatistics.getGamePlayer(player).getParkourFinishes(pm) == null) {
                mapMeta.addEnchant(Enchantment.OXYGEN, 1, true);
                mapMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                lore.add(ChatColor.DARK_PURPLE+"Times Finished: "+ChatColor.LIGHT_PURPLE+"N/A");
                lore.add(ChatColor.DARK_PURPLE+"Best Time: "+ChatColor.LIGHT_PURPLE+"N/A");
                mapMeta.setLore(lore);
                lore.clear();
            } else {
                lore.add(ChatColor.DARK_PURPLE+"Times Finished: "+ChatColor.LIGHT_PURPLE+PlayerStatistics.getGamePlayer(player).getParkourFinishes(pm));

                BigDecimal time = new BigDecimal(((Double) PlayerStatistics.getGamePlayer(player).getBestParkourMapTime(pm)).toString());

                BigDecimal seconds = time.remainder(new BigDecimal("60"));
                BigDecimal minutes = time.subtract(seconds).divide(new BigDecimal("60"));

                lore.add(ChatColor.DARK_PURPLE+"Best Time: "+ChatColor.LIGHT_PURPLE+minutes.intValue()+":"+seconds);
                mapMeta.setLore(lore);
                lore.clear();
            }
            mapMeta.setDisplayName(ChatColor.YELLOW + pm.getName());
            mapMeta.setLocalizedName(pm.getName());
            map.setItemMeta(mapMeta);
            allItems.add(map.clone());
        }




        ItemStack left;
        ItemMeta leftMeta;
        if (PageUtil.isPageValid(allItems, page - 1, 36)) {
            left = new ItemStack(Material.ARROW);
            leftMeta = left.getItemMeta();
            leftMeta.setDisplayName(ChatColor.GREEN+"Back");
        } else {
            left = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            leftMeta = left.getItemMeta();
            leftMeta.setDisplayName(" ");
        }
        leftMeta.setLocalizedName(page + "");
        left.setItemMeta(leftMeta);
        gui.setItem(45, left);

        ItemStack right;
        ItemMeta rightMeta;
        if (PageUtil.isPageValid(allItems, page + 1, 36)) {
            right = new ItemStack(Material.ARROW);
            rightMeta = right.getItemMeta();
            rightMeta.setDisplayName(ChatColor.GREEN+"Next");
        } else {
            right = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
            rightMeta = right.getItemMeta();
            rightMeta.setDisplayName(" ");
        }
        right.setItemMeta(rightMeta);
        gui.setItem(53, right);

        ItemStack item = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);

        for (int i = 0; i < 9; i++) {
            gui.setItem(i, item.clone());
        }

        ItemStack stats = new ItemStack(Material.PAPER);
        ItemMeta statsMeta = stats.getItemMeta();
        statsMeta.setDisplayName(ChatColor.DARK_PURPLE+"Your Stats:");

        List<String> statsLore = new ArrayList<>();
        statsLore.add(ChatColor.LIGHT_PURPLE+"Total Parkour Plays: "+ChatColor.WHITE+PlayerStatistics.getGamePlayer(player).getTotalFinishes());
        statsMeta.setLore(statsLore);

        stats.setItemMeta(statsMeta);
        gui.setItem(4, stats);

        for (int i = 46; i < 53; i++) {
            gui.setItem(i, item.clone());
        }

        if (page == 1) {
            ItemStack out;
            ItemMeta outMeta;
            out = new ItemStack(Material.BARRIER);
            outMeta = out.getItemMeta();
            outMeta.setDisplayName(ChatColor.GREEN+"Back to Difficulty Selection");
            out.setItemMeta(outMeta);
            gui.setItem(49, out);
        }

        for (ItemStack is : PageUtil.getPageItems(allItems, page, 36)) {
            gui.setItem(gui.firstEmpty(), is);
        }



        player.openInventory(gui);
    }

}
