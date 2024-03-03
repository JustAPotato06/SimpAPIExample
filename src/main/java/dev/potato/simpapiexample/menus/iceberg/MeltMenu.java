package dev.potato.simpapiexample.menus.iceberg;

import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MeltMenu extends Menu {
    public MeltMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Choose a Player to Melt";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        if (e.getCurrentItem().getType() == Material.BARRIER) {
            MenuManager.openMenu(MainMenu.class, playerMenuUtility.getOwner());
        } else if (e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
            Player target = Bukkit.getPlayer(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
            playerMenuUtility.setData(PMUDataIceberg.PLAYER_TO_MELT, target);
            MenuManager.openMenu(ConfirmMeltMenu.class, playerMenuUtility.getOwner());
        }
    }

    @Override
    public void setMenuItems() {
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            ItemStack head = makeItem(Material.PLAYER_HEAD, player.getDisplayName());
            inventory.addItem(head);
        });
        inventory.setItem(49, makeItem(Material.BARRIER, ColorTranslator.translateColorCodes("&4&lBack")));
        setFillerGlass();
    }
}
