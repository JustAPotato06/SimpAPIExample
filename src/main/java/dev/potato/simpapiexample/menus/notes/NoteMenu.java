package dev.potato.simpapiexample.menus.notes;

import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class NoteMenu extends Menu {
    public NoteMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Note Menu";
    }

    @Override
    public int getSlots() {
        return 36;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {
        switch (e.getCurrentItem().getType()) {
            case PAPER:
                MenuManager.openMenu(ListNotesMenu.class, playerMenuUtility.getOwner());
                break;
            case BARRIER:
                playerMenuUtility.getOwner().closeInventory();
                break;
            case LAVA_BUCKET:
                MenuManager.openMenu(DeleteNoteMenu.class, playerMenuUtility.getOwner());
                break;
            case WRITABLE_BOOK:
                playerMenuUtility.getOwner().sendMessage(ChatColor.GREEN + "[SIMP API EXAMPLE] Please create a note using the create command!");
                playerMenuUtility.getOwner().sendMessage(ChatColor.GREEN + "[SIMP API EXAMPLE] Example: /notes create [message]");
                playerMenuUtility.getOwner().closeInventory();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack create = makeItem(Material.WRITABLE_BOOK, "Create Note");
        ItemStack list = makeItem(Material.PAPER, "List Notes");
        ItemStack delete = makeItem(Material.LAVA_BUCKET, "Delete Note");
        ItemStack close = makeItem(Material.BARRIER, "Close");
        inventory.setItem(11, create);
        inventory.setItem(13, list);
        inventory.setItem(15, delete);
        inventory.setItem(31, close);
        setFillerGlass();
    }
}
