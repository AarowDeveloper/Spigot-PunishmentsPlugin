package dev.aarow.punishments.adapters.menu;

import dev.aarow.punishments.PunishmentPlugin;
import dev.aarow.punishments.adapters.menu.buttons.Button;
import dev.aarow.punishments.utility.chat.CC;
import dev.aarow.punishments.utility.other.Task;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

@Getter
public abstract class Menu {

    public abstract int getSize();
    public abstract String getName();
    public abstract Map<Integer, Button> getButtons();
    public abstract void onClose();

    public PunishmentPlugin plugin = PunishmentPlugin.getInstance();

    @Getter @Setter private Player player;

    @Setter private boolean autoRefresh;
    @Getter private boolean closed = false;

    private Inventory inventory;

    public void open(Player player) {
        this.player = player;

        player.closeInventory();

        Task.runLater(() -> {
            inventory = Bukkit.createInventory(player, getSize(), CC.translate(getName()));

            getButtons().keySet().forEach(slot -> {
                Button button = getButtons().get(slot);

                inventory.setItem(slot - 1, button.getItem(player));
            });

            for (int i = 0; i < getSize(); i++) {
                if (getButtons().containsKey(i + 1)) continue;

                inventory.setItem(i, Button.PLACEHOLDER.getItem(player));
            }

            plugin.getMenuManager().getMenuCache().put(player.getUniqueId(), this);

            player.openInventory(inventory);

            if (autoRefresh) {
                new BukkitRunnable() {
                    public void run() {
                        if (!plugin.getMenuManager().getMenuCache().containsKey(player.getUniqueId())) {
                            this.cancel();
                            return;
                        }
                        if (!plugin.getMenuManager().getMenuCache().get(player.getUniqueId()).getName().equals(getName())) {
                            this.cancel();
                            return;
                        }

                        refresh();
                    }
                }.runTaskTimer(plugin, 0L, 2L);
            }
        });
    }

    private void refresh(){
        Task.runASync(() -> {
            inventory.clear();

            getButtons().keySet().forEach(slot -> {
                Button button = getButtons().get(slot);

                inventory.setItem(slot - 1, button.getItem(player));
            });

            for (int i = 0; i < getSize(); i++) {
                if (getButtons().containsKey(i + 1)) continue;

                inventory.setItem(i, Button.PLACEHOLDER.getItem(player));
            }
        });
    }
}