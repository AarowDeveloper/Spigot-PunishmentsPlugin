package dev.aarow.punishments.menus;

import dev.aarow.punishments.adapters.menu.PaginatedMenu;
import dev.aarow.punishments.adapters.menu.buttons.Button;
import dev.aarow.punishments.data.player.Profile;
import dev.aarow.punishments.data.punishments.Punishment;
import dev.aarow.punishments.utility.general.DurationUtility;
import dev.aarow.punishments.utility.general.StringUtility;
import dev.aarow.punishments.utility.other.ItemBuilder;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PunishmentHistoryMenu extends PaginatedMenu {

    {
        setAutoRefresh(false);
    }

    private Profile targetProfile;

    public PunishmentHistoryMenu(Player player) {
        super(player);
    }

    public PunishmentHistoryMenu targetProfile(Profile targetProfile){
        this.targetProfile = targetProfile;
        return this;
    }

    @Override
    public String getName() {
        return "&8" + Bukkit.getOfflinePlayer(targetProfile.getUuid()).getName() + "'s Punishment History";
    }

    @Override
    public void onClose() {

    }

    @Override
    public Map<Integer, Button> getAlways() {
        return new HashMap<>(getSwitchButtons());
    }

    @Override
    public void buttonsGetter(List<Button> buttons) {
        List<Punishment> punishments = targetProfile.getPunishments().stream()
                .sorted(Comparator.comparingLong(Punishment::getPunishedAt))
                .toList();

        int index = punishments.size() - 1;

        for (int i = index; i >= 0; i--) {
            buttons.add(new PunishmentButton(punishments.get(i), i));
        }
    }

    @AllArgsConstructor
    private class PunishmentButton extends Button {

        private Punishment punishment;
        private int id;

        @Override
        public ItemStack getItem(Player player) {
            return new ItemBuilder(Material.PAPER)
                    .setName("&e&l#" + id)
                    .addLoreLine("&7&m----------------------------")
                    .addLoreLine(" &7&l• &eType&7: &f" + StringUtility.getNiceString(punishment.getPunishmentType().name()))
                    .addLoreLine(" &7&l• &eReason&7: &f" + punishment.getReason())
                    .addLoreLine(" &7&l• &eExpires At&7: &f" + (punishment.getDuration() != -1 ? DurationUtility.getExpireDate(punishment.getPunishedAt(), punishment.getDuration()) : "Permanent"))
                    .addLoreLine(" &7&l• &ePunished At&7: &f" + DurationUtility.getExpireDate(punishment.getPunishedAt(), 0))
                    .addLoreLine(" &7&l• &ePunished By&7: &f" + punishment.getPunishedBy())
                    .addLoreLine(" &7&l• &eActive&7: &r" + (punishment.isActive() ? "&a" : "&c") + punishment.isActive())
                    .addLoreLine("&7&m----------------------------").toItemStack();
        }

        @Override
        public void onClick(Player player) {

        }

        @Override
        public void onClickType(ClickType clickType) {

        }
    }
}
