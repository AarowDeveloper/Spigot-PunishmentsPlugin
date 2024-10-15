package dev.aarow.punishments.adapters.menu.buttons.impl;

import dev.aarow.punishments.adapters.menu.buttons.Button;
import dev.aarow.punishments.utility.other.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class CloseMenuButton extends Button {

    @Override
    public ItemStack getItem(Player player) {
        ItemBuilder item = new ItemBuilder(Material.REDSTONE);
        item.setName("&c&lClose Menu");

        return item.toItemStack();
    }

    @Override
    public void onClick(Player player) {
        player.closeInventory();
    }

    @Override
    public void onClickType(ClickType clickType) {

    }
}