package com.example.notesfrontend.util

import android.content.Context
import com.example.notesfrontend.model.Note
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Handles local storage of notes using SharedPreferences as simple key-value store.
 */
object NoteSharedPrefs {
    private const val PREFS_NAME = "notes_prefs"
    private const val NOTES_KEY = "notes"

    private val gson = Gson()

    // PUBLIC_INTERFACE
    /**
     * Loads all notes from SharedPreferences.
     */
    fun loadNotes(context: Context): MutableList<Note> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(NOTES_KEY, null)
        if (json != null) {
            val type = object : TypeToken<MutableList<Note>>() {}.type
            return gson.fromJson(json, type)
        }
        return mutableListOf()
    }

    // PUBLIC_INTERFACE
    /**
     * Saves all notes to SharedPreferences.
     */
    fun saveNotes(context: Context, notes: List<Note>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString(NOTES_KEY, gson.toJson(notes))
            apply()
        }
    }
}
