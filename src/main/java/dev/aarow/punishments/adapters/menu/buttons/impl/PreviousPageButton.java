package dev.aarow.punishments.adapters.menu.buttons.impl;

import dev.aarow.punishments.adapters.menu.PaginatedMenu;
import dev.aarow.punishments.adapters.menu.buttons.Button;
import dev.aarow.punishments.utility.other.ItemBuilder;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PreviousPageButton extends Button {

    private PaginatedMenu paginatedMenu;

    @Override
    public ItemStack getItem(Player player) {
        ItemBuilder item = new ItemBuilder(Material.PAPER);
        item.setName("&b&lPrevious Page");

        return item.toItemStack();
    }

    @Override
    public void onClick(Player player) {
        if(paginatedMenu.getPage() == 0) return;
        if(paginatedMenu.getForPage(paginatedMenu.getPage()-1).isEmpty()) return;

        player.closeInventory();

        paginatedMenu.open(paginatedMenu.getPage()-1);
    }

    @Override
    public void onClickType(ClickType clickType) {

    }
}