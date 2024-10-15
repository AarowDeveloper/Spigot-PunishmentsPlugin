package dev.aarow.punishments.managers.impl;

import dev.aarow.punishments.adapters.menu.Menu;
import dev.aarow.punishments.adapters.menu.PaginatedMenu;
import dev.aarow.punishments.managers.Manager;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class MenuManager extends Manager {

    private Map<UUID, Menu> menuCache = new HashMap<>();
    private Map<UUID, PaginatedMenu> paginatedMenuCache = new HashMap<>();


    @Override
    public void setup() {}
}