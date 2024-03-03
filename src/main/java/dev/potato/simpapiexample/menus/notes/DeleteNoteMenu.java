package dev.potato.simpapiexample.menus.notes;

import dev.potato.simpapiexample.SimpAPIExample;
import dev.potato.simpapiexample.models.Note;
import dev.potato.simpapiexample.utilities.NoteStorageUtils;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class DeleteNoteMenu extends Menu {
    public DeleteNoteMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Choose a Note to Delete";
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
        } else if (e.getCurrentItem().getType() == Material.PAPER) {
            String noteID = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(SimpAPIExample.getPlugin(), "note-id"), PersistentDataType.STRING);
            Note note = NoteStorageUtils.findNote(noteID);
            playerMenuUtility.setData(PMUDataNotes.NOTE_TO_DELETE, note);
            MenuManager.openMenu(ConfirmDeleteMenu.class, playerMenuUtility.getOwner());
        }
    }

    @Override
    public void setMenuItems() {
        List<Note> notes = NoteStorageUtils.findAllNotes();
        for (Note note : notes) {
            ItemStack item = makeItem(Material.PAPER, "Note #" + note.getId(), note.getMessage(), "Created by: " + note.getPlayerName());
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.getPersistentDataContainer().set(new NamespacedKey(SimpAPIExample.getPlugin(), "note-id"), PersistentDataType.STRING, note.getId());
            item.setItemMeta(itemMeta);
            inventory.addItem(item);
        }
        inventory.setItem(49, makeItem(Material.BARRIER, "Close"));
        setFillerGlass();
    }
}
