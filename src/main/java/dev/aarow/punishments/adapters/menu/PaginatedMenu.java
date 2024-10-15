package dev.aarow.punishments.adapters.menu;

import dev.aarow.punishments.PunishmentPlugin;
import dev.aarow.punishments.adapters.menu.buttons.Button;
import dev.aarow.punishments.adapters.menu.buttons.impl.CloseMenuButton;
import dev.aarow.punishments.adapters.menu.buttons.impl.NextPageButton;
import dev.aarow.punishments.adapters.menu.buttons.impl.PreviousPageButton;
import dev.aarow.punishments.utility.chat.CC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class PaginatedMenu {

    public PunishmentPlugin plugin = PunishmentPlugin.getInstance();

    @Setter private boolean autoRefresh = true;

    public abstract String getName();
    public abstract void onClose();

    @Setter @Getter private int page = 0;

    public Player player;

    public abstract Map<Integer, Button> getAlways();

    private Inventory inventory;

    public static int[] paginatedSlots = new int[]{10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43};

    public PaginatedMenu(Player player){
        this.player = player;
    }

    public void open(int newPage) {
        this.page = newPage;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            this.inventory = Bukkit.createInventory(player, 54, CC.translate(getName() + " &8[" + (page+1)  + "/" + getTotalPages() + "]"));

            plugin.getMenuManager().getPaginatedMenuCache().put(player.getUniqueId(), this);

            this.setBorders();

            List<Button> pageItems = getForPage(page);

            for(int i = 0; i < pageItems.size(); i++){
                this.inventory.setItem(paginatedSlots[i], pageItems.get(i).getItem(player));
            }

            getAlways().forEach((slot, button) -> this.inventory.setItem(44 + slot, button.getItem(player)));

            player.openInventory(inventory);

            if(autoRefresh){
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        if(!plugin.getMenuManager().getPaginatedMenuCache().containsKey(player.getUniqueId())){
                            this.cancel();
                            return;
                        }
                        if(!plugin.getMenuManager().getPaginatedMenuCache().get(player.getUniqueId()).getName().equals(getName())){
                            this.cancel();
                            return;
                        }

                        inventory.clear();

                        setBorders();

                        getAlways().forEach((slot, button) -> inventory.setItem(44 + slot, button.getItem(player)));

                        List<Button> pageItems = getForPage(page);

                        for(int i = 0; i < pageItems.size(); i++){
                            inventory.setItem(paginatedSlots[i], pageItems.get(i).getItem(player));
                        }
                    }
                }.runTaskTimer(plugin, 0, 1L);
            }
        }, 2L);


    }

    public abstract void buttonsGetter(List<Button> buttons);

    public Map<Integer, Button> getSwitchButtons(){
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(2, new PreviousPageButton(this));
        buttons.put(5, new CloseMenuButton());
        buttons.put(8, new NextPageButton(this));

        return buttons;
    }

    public List<Button> getForPage(int page){
        List<Button> forPage = new ArrayList<>();

        List<Button> buttons = new ArrayList<>();
        buttonsGetter(buttons);

        int start = (page * 28);
        int end = start + 28;

        for(int index = start; index < end; index++){
            if(buttons.size() <= index) break;

            forPage.add(buttons.get(index));
        }

        return forPage;
    }

    public int getTotalPages(){
        List<Button> buttons = new ArrayList<>();
        buttonsGetter(buttons);

        return (buttons.size()/28) + (buttons.size() % 28 != 0 ? 1 : 0);
    }

    private void setBorders(){
        for(int x = 0; x < 54; x++){
            if(x < 9 || x > 44){
                inventory.setItem(x, Button.PLACEHOLDER.getItem(player));
                continue;
            }
            if(x % 9 == 0){
                inventory.setItem(x, Button.PLACEHOLDER.getItem(player));
                inventory.setItem(x-1, Button.PLACEHOLDER.getItem(player));
                continue;
            }
        }
        inventory.setItem(44, Button.PLACEHOLDER.getItem(player));
    }
}