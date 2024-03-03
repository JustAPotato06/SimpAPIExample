package dev.potato.simpapiexample.commands.iceberg;

import dev.potato.simpapiexample.menus.iceberg.MainMenu;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class IcebergCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player p) {
            try {
                MenuManager.openMenu(MainMenu.class, p);
            } catch (MenuManagerException | MenuManagerNotSetupException e) {
                p.sendMessage(ChatColor.RED + "[SIMP API EXAMPLE] There was an error opening the menu. Please check the console for more details.");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getMessage());
            }
        }

        return true;
    }
}
