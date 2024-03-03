package dev.potato.simpapiexample.menus.iceberg;

import dev.potato.simpapiexample.SimpAPIExample;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConfirmFreezeMenu extends Menu {
    private final Player playerToFreeze;

    public ConfirmFreezeMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
        playerToFreeze = playerMenuUtility.getData(PMUDataIceberg.PLAYER_TO_FREEZE, Player.class);
    }

    @Override
    public String getMenuName() {
        return "Confirm Freeze: " + playerToFreeze.getDisplayName();
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        switch (e.getCurrentItem().getType()) {
            case LIME_CONCRETE:
                List<Player> frozenPlayers = SimpAPIExample.getFrozenPlayers();
                frozenPlayers.add(playerToFreeze);
                SimpAPIExample.setFrozenPlayers(frozenPlayers);
                playerMenuUtility.getOwner().closeInventory();
                playerMenuUtility.getOwner().sendMessage(ChatColor.GREEN + "[SIMP API EXAMPLE] " + playerToFreeze.getDisplayName() + " has been frozen successfully!");
                break;
            case RED_CONCRETE:
                MenuManager.openMenu(FreezeMenu.class, playerMenuUtility.getOwner());
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack confirm = makeItem(Material.LIME_CONCRETE, ChatColor.GREEN + "Confirm");
        ItemStack decline = makeItem(Material.RED_CONCRETE, ChatColor.RED + "Decline");
        inventory.setItem(3, confirm);
        inventory.setItem(5, decline);
        setFillerGlass();
    }
}
