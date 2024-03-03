package dev.potato.simpapiexample.menus.iceberg;

import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MainMenu extends Menu {
    public MainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Iceberg";
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
            case PACKED_ICE:
                MenuManager.openMenu(FreezeMenu.class, playerMenuUtility.getOwner());
                break;
            case LAVA_BUCKET:
                MenuManager.openMenu(MeltMenu.class, playerMenuUtility.getOwner());
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack freezePlayer = makeItem(Material.PACKED_ICE, ColorTranslator.translateColorCodes("&b&lFreeze Player"));
        ItemStack meltPlayer = makeItem(Material.LAVA_BUCKET, ColorTranslator.translateColorCodes("&e&lMelt Player"));
        inventory.setItem(3, freezePlayer);
        inventory.setItem(5, meltPlayer);
        setFillerGlass();
    }
}
