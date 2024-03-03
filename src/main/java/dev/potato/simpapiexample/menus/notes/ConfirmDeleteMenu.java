package dev.potato.simpapiexample.menus.notes;

import dev.potato.simpapiexample.models.Note;
import dev.potato.simpapiexample.utilities.NoteStorageUtils;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ConfirmDeleteMenu extends Menu {
    public ConfirmDeleteMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Confirm Delete Note?";
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
            case RED_CONCRETE:
                MenuManager.openMenu(DeleteNoteMenu.class, playerMenuUtility.getOwner());
                break;
            case LIME_CONCRETE:
                Note noteToDelete = playerMenuUtility.getData(PMUDataNotes.NOTE_TO_DELETE, Note.class);
                NoteStorageUtils.deleteNote(noteToDelete.getId());
                playerMenuUtility.getOwner().sendMessage(ChatColor.GREEN + "[SIMP API EXAMPLE] The note has been deleted successfully!");
                playerMenuUtility.getOwner().closeInventory();
                break;
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
