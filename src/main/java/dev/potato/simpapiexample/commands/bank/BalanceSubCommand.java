package dev.potato.simpapiexample.commands.bank;

import dev.potato.simpapiexample.SimpAPIExample;
import me.kodysimpson.simpapi.command.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BalanceSubCommand extends SubCommand {
    @Override
    public String getName() {
        return "balance";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("bal");
        return aliases;
    }

    @Override
    public String getDescription() {
        return "Displays your current account balance";
    }

    @Override
    public String getSyntax() {
        return "/simplebank balance";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            double balance = SimpAPIExample.getEcon().getBalance(p);
            p.sendMessage(ChatColor.GREEN + "Your current balance is: " + ChatColor.GOLD + balance);
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] strings) {
        return null;
    }
}
