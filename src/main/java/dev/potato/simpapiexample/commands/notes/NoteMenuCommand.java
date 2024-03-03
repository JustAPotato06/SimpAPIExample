package dev.potato.simpapiexample.commands.notes;

import dev.potato.simpapiexample.menus.notes.NoteMenu;
import me.kodysimpson.simpapi.command.SubCommand;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NoteMenuCommand extends SubCommand {
    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("m");
        return aliases;
    }

    @Override
    public String getDescription() {
        return "Open the notes GUI";
    }

    @Override
    public String getSyntax() {
        return "/notes menu";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            try {
                MenuManager.openMenu(NoteMenu.class, p);
            } catch (MenuManagerException | MenuManagerNotSetupException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[SIMP API EXAMPLE] There was an error opening the Note menu for the player!");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getMessage());
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] strings) {
        return null;
    }
}
