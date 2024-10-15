package dev.aarow.punishments.handlers.impl;


import dev.aarow.punishments.adapters.menu.Menu;
import dev.aarow.punishments.adapters.menu.PaginatedMenu;
import dev.aarow.punishments.adapters.menu.buttons.Button;
import dev.aarow.punishments.handlers.HandlerAdapter;
import dev.aarow.punishments.utility.other.Task;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MenuHandler extends HandlerAdapter {

    @EventHandler
    public void onClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        if(!plugin.getMenuManager().getMenuCache().containsKey(player.getUniqueId())) return;

        Menu menu = plugin.getMenuManager().getMenuCache().get(player.getUniqueId());

        event.setCancelled(true);

        if(!menu.getButtons().containsKey((event.getSlot()+1))) return;

        menu.getButtons().get(event.getSlot()+1).onClick(player);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();

        if(!plugin.getMenuManager().getMenuCache().containsKey(player.getUniqueId())) return;

        Menu menu = plugin.getMenuManager().getMenuCache().get(player.getUniqueId());

        Task.runLater(() -> {
            menu.onClose();

            plugin.getMenuManager().getMenuCache().remove(player.getUniqueId());
        });
    }

    @EventHandler
    public void onPaginatedClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!plugin.getMenuManager().getPaginatedMenuCache().containsKey(player.getUniqueId())) return;

        PaginatedMenu menu = plugin.getMenuManager().getPaginatedMenuCache().get(player.getUniqueId());

        event.setCancelled(true);

        int slot = event.getSlot(); // Use the original slot

        if(slot == 9){
            event.setCancelled(true);
            return;
        }

        if (slot >= 45) {
            int adjustedSlot = slot - 44;

            if (!menu.getAlways().containsKey(adjustedSlot)) return;

            if (event.getClick() == ClickType.LEFT) {
                menu.getAlways().get(adjustedSlot).onClick(player);
            } else {
                menu.getAlways().get(adjustedSlot).onClickType(event.getClick());
            }
            return;
        }

        if(event.getSlot() == 16 || event.getSlot() == 25 || event.getSlot() == 34 || event.getSlot() == 43){
            int index = 0;

            switch(event.getSlot()){
                case 16:
                    index = 6;
                    break;
                case 25:
                    index = 13;
                    break;
                case 34:
                    index = 20;
                    break;
                case 43:
                    index = 27;
                    break;
            }

            List<Button> buttons = menu.getForPage(menu.getPage());

            if(buttons.size() <= index) return;

            if (event.getClick() == ClickType.LEFT) {
                buttons.get(index).onClick(player);
            } else {
                buttons.get(index).onClickType(event.getClick());
            }
            return;
        }

        if (Arrays.stream(PaginatedMenu.paginatedSlots).noneMatch(paginatedSlot -> paginatedSlot == slot+1)) return;

        int itemIndex = (menu.getPage() * PaginatedMenu.paginatedSlots.length) + indexFromPaginatedSlot(slot+1);

        List<Button> buttons = new ArrayList<>();
        menu.buttonsGetter(buttons);

        if (buttons.size() <= itemIndex-1) return;

        if (event.getClick() == ClickType.LEFT) {
            buttons.get(itemIndex-1).onClick(player);
        } else {
            buttons.get(itemIndex-1).onClickType(event.getClick());
        }
    }


    @EventHandler
    public void onPaginatedClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();

        if(!plugin.getMenuManager().getPaginatedMenuCache().containsKey(player.getUniqueId())) return;

        PaginatedMenu menu = plugin.getMenuManager().getPaginatedMenuCache().get(player.getUniqueId());

        Task.runASync(() -> {
            menu.onClose();

            plugin.getMenuManager().getPaginatedMenuCache().remove(player.getUniqueId());
        });
    }

    private int indexFromPaginatedSlot(int paginatedSlot){
        List<Integer> paginatedSlots = Arrays.stream(PaginatedMenu.paginatedSlots).boxed().collect(Collectors.toList());

        return paginatedSlots.indexOf(paginatedSlot);
    }
}