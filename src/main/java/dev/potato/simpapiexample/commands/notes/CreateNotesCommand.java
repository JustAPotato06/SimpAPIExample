package dev.potato.simpapiexample.commands.notes;

import dev.potato.simpapiexample.utilities.NoteStorageUtils;
import me.kodysimpson.simpapi.command.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CreateNotesCommand extends SubCommand {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public List<String> getAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("c");
        return aliases;
    }

    @Override
    public String getDescription() {
        return "Creates a note and stores it in the server";
    }

    @Override
    public String getSyntax() {
        return "/notes create [message]";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (sender instanceof Player p) {
            if (args.length > 1) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < args.length - 1; i++) {
                    stringBuilder.append(args[i]).append(" ");
                }
                stringBuilder.append(args[args.length - 1]);
                NoteStorageUtils.createNote(p, stringBuilder.toString());
            }
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player p, String[] args) {
        List<String> arguments = new ArrayList<>();
        if (args.length == 2) {
            arguments.add("[message]");
        }
        return arguments;
    }
}
