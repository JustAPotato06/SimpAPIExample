package dev.potato.simpapiexample.menus.notes;

import dev.potato.simpapiexample.models.Note;
import dev.potato.simpapiexample.utilities.NoteStorageUtils;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ListNotesMenu extends Menu {
    public ListNotesMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "List of Notes";
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
            MenuManager.openMenu(NoteMenu.class, playerMenuUtility.getOwner());
        }
    }

    @Override
    public void setMenuItems() {
        List<Note> notes = NoteStorageUtils.findAllNotes();
        for (Note note : notes) {
            ItemStack item = makeItem(Material.PAPER, "Note #" + note.getId(), note.getMessage(), "Created by: " + note.getPlayerName());
            inventory.addItem(item);
        }
        inventory.setItem(49, makeItem(Material.BARRIER, "Close"));
        setFillerGlass();
    }
}
