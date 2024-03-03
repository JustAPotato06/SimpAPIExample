package dev.potato.simpapiexample.utilities;

import com.google.gson.Gson;
import dev.potato.simpapiexample.SimpAPIExample;
import dev.potato.simpapiexample.models.Note;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NoteStorageUtils {
    // CRUD - Create, Read, Update, Delete
    private static ArrayList<Note> notes = new ArrayList<>();

    public static Note createNote(Player p, String message) {
        Note note = new Note(p.getDisplayName(), message);
        notes.add(note);
        saveNotes();
        return note;
    }

    public static Note findNote(String id) {
        for (Note note : notes) {
            if (note.getId().equalsIgnoreCase(id)) {
                return note;
            }
        }
        return null;
    }

    public static void deleteNote(String id) {
        for (Note note : notes) {
            if (note.getId().equalsIgnoreCase(id)) {
                notes.remove(note);
                break;
            }
        }
        saveNotes();
    }

    public static Note updateNote(String id, Note newNote) {
        for (Note note : notes) {
            if (note.getId().equalsIgnoreCase(id)) {
                note.setPlayerName(newNote.getPlayerName());
                note.setMessage(newNote.getMessage());
                saveNotes();
                return note;
            }
        }
        return null;
    }

    public static List<Note> findAllNotes() {
        return notes;
    }

    public static void saveNotes() {
        Gson gson = new Gson();
        File file = new File(SimpAPIExample.getPlugin().getDataFolder().getAbsolutePath() + "/notes.json");
        file.getParentFile().mkdir();
        try {
            file.createNewFile();
            Writer writer = new FileWriter(file, false);
            gson.toJson(notes, writer);
            writer.flush();
            writer.close();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[SIMP API EXAMPLE] Notes were saved successfully!");
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[SIMP API EXAMPLE] There was an error saving the notes to the JSON file!");
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getMessage());
        }
    }

    public static void loadNotes() {
        Gson gson = new Gson();
        File file = new File(SimpAPIExample.getPlugin().getDataFolder().getAbsolutePath() + "/notes.json");
        if (file.exists()) {
            try {
                Reader reader = new FileReader(file);
                Note[] notesArray = gson.fromJson(reader, Note[].class);
                notes = new ArrayList<>(Arrays.asList(notesArray));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[SIMP API EXAMPLE] Notes were loaded successfully!");
            } catch (FileNotFoundException e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[SIMP API EXAMPLE] There was an error loading the notes from the JSON file!");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getMessage());
            }
        }
    }
}