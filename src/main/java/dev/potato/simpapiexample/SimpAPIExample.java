package dev.potato.simpapiexample;

import dev.potato.simpapiexample.commands.bank.BalanceSubCommand;
import dev.potato.simpapiexample.commands.bank.DepositSubCommand;
import dev.potato.simpapiexample.commands.bank.WithdrawSubCommand;
import dev.potato.simpapiexample.commands.iceberg.IcebergCommand;
import dev.potato.simpapiexample.commands.notes.CreateNotesCommand;
import dev.potato.simpapiexample.commands.notes.NoteMenuCommand;
import dev.potato.simpapiexample.listeners.FrozenPlayerListener;
import dev.potato.simpapiexample.utilities.NoteStorageUtils;
import me.kodysimpson.simpapi.colors.ColorTranslator;
import me.kodysimpson.simpapi.command.CommandList;
import me.kodysimpson.simpapi.command.CommandManager;
import me.kodysimpson.simpapi.command.SubCommand;
import me.kodysimpson.simpapi.menu.MenuManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class SimpAPIExample extends JavaPlugin {
    private static Economy econ = null;
    private static List<Player> frozenPlayers = new ArrayList<>();
    private static SimpAPIExample plugin;

    public static Economy getEcon() {
        return econ;
    }

    public static List<Player> getFrozenPlayers() {
        return frozenPlayers;
    }

    public static void setFrozenPlayers(List<Player> frozenPlayers) {
        SimpAPIExample.frozenPlayers = frozenPlayers;
    }

    public static SimpAPIExample getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // Command Manager
        try {
            List<String> aliases = new ArrayList<>();
            aliases.add("sb");
            aliases.add("bank");
            CommandManager.createCoreCommand(this, "simplebank", "A simple bank command for depositing and withdrawing money", "/simplebank", new CommandList() {
                @Override
                public void displayCommandList(CommandSender sender, List<SubCommand> list) {
                    if (sender instanceof Player p) {
                        p.sendMessage(ColorTranslator.translateColorCodes("&e---------------------------"));
                        list.forEach(subCommand -> {
                            p.sendMessage(ColorTranslator.translateColorCodes("&a" + subCommand.getSyntax() + " - &f" + subCommand.getDescription()));
                        });
                        p.sendMessage(ColorTranslator.translateColorCodes("&e---------------------------"));
                    }
                }
            }, aliases, DepositSubCommand.class, WithdrawSubCommand.class, BalanceSubCommand.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[SIMP API EXAMPLE] There was an error registering a core command!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getMessage());
        }

        // Menu Manager
        MenuManager.setup(getServer(), this);

        getCommand("iceberg").setExecutor(new IcebergCommand());

        getServer().getPluginManager().registerEvents(new FrozenPlayerListener(), this);

        // JSON File Storage
        plugin = this;

        try {
            CommandManager.createCoreCommand(this, "notes", "The central command for creating and updating notes", "/notes", new CommandList() {
                @Override
                public void displayCommandList(CommandSender sender, List<SubCommand> list) {
                    if (sender instanceof Player p) {
                        p.sendMessage("-------------------------");
                        for (SubCommand subCommand : list) {
                            p.sendMessage(subCommand.getSyntax() + " - " + subCommand.getDescription());
                        }
                        p.sendMessage("-------------------------");
                    }
                }
            }, CreateNotesCommand.class, NoteMenuCommand.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[SIMP API EXAMPLE] There was an error registering a core command!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getMessage());
        }

        NoteStorageUtils.loadNotes();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}