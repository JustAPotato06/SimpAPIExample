package dev.potato.simpapiexample.commands.bank;

import dev.potato.simpapiexample.SimpAPIExample;
import me.kodysimpson.simpapi.command.SubCommand;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WithdrawSubCommand extends SubCommand {
    @Override
    public String getName() {
        return "withdraw";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("with");
        return aliases;
    }

    @Override
    public String getDescription() {
        return "Takes the given amount of money from your account";
    }

    @Override
    public String getSyntax() {
        return "/simplebank withdraw [amount]";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            if (args.length > 1) {
                try {
                    double amount = Double.parseDouble(args[1]);
                    if (amount <= 0) {
                        p.sendMessage(ChatColor.RED + "[SIMP API EXAMPLE] The amount withdrawn must be greater than 0! Please try again.");
                    } else {
                        EconomyResponse response = SimpAPIExample.getEcon().withdrawPlayer(p, amount);
                        if (response.type == EconomyResponse.ResponseType.FAILURE) {
                            p.sendMessage(ChatColor.RED + "[SIMP API EXAMPLE] There was an error withdrawing the given amount from your account. Please try again later.");
                        } else {
                            p.sendMessage(ChatColor.GREEN + "[SIMP API EXAMPLE] Success! " + ChatColor.GOLD + amount + ChatColor.GREEN + " dollar(s) was withdrawn from your account.");
                            p.sendMessage(ChatColor.GREEN + "[SIMP API EXAMPLE] Your new balance is: " + ChatColor.GOLD + response.balance);
                        }
                    }
                } catch (NumberFormatException e) {
                    p.sendMessage(ChatColor.RED + "[SIMP API EXAMPLE] The amount you provided is invalid! Please try again.");
                }
            } else {
                p.sendMessage(ChatColor.RED + "[SIMP API EXAMPLE] Please provide the amount you would like to withdraw.");
                p.sendMessage(ChatColor.RED + "[SIMP API EXAMPLE] Example: " + getSyntax());
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player p, String[] args) {
        List<String> arguments = new ArrayList<>();
        if (args.length == 2) {
            arguments.add("[amount]");
        }
        return arguments;
    }
}
