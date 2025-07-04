package com.example.notesfrontend.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesfrontend.model.Note
import com.example.notesfrontend.util.NoteSharedPrefs

/**
 * ViewModel for managing note CRUD and search logic with LiveData.
 */
class NotesViewModel(application: Application): AndroidViewModel(application) {
    private val _notes = MutableLiveData<List<Note>>()
    val notes: LiveData<List<Note>>
        get() = _notes

    private var allNotes = mutableListOf<Note>()

    init {
        loadNotes()
    }

    // PUBLIC_INTERFACE
    /**
     * Loads all notes from storage.
     */
    fun loadNotes() {
        allNotes = NoteSharedPrefs.loadNotes(getApplication())
        _notes.value = allNotes.sortedByDescending { it.timestamp }
    }

    // PUBLIC_INTERFACE
    /**
     * Adds a new note and persists changes.
     */
    fun addNote(note: Note) {
        allNotes.add(0, note)
        saveAndUpdate()
    }

    // PUBLIC_INTERFACE
    /**
     * Updates an existing note and persists changes.
     */
    fun updateNote(note: Note) {
        val idx = allNotes.indexOfFirst { it.id == note.id }
        if (idx != -1) {
            allNotes[idx] = note
            saveAndUpdate()
        }
    }

    // PUBLIC_INTERFACE
    /**
     * Deletes a note and persists changes.
     */
    fun deleteNote(note: Note) {
        allNotes.removeAll { it.id == note.id }
        saveAndUpdate()
    }

    // PUBLIC_INTERFACE
    /**
     * Performs a case-insensitive search within titles and content.
     */
    fun searchNotes(query: String) {
        val q = query.trim().lowercase()
        if (q.isEmpty()) {
            _notes.value = allNotes.sortedByDescending { it.timestamp }
        } else {
            _notes.value = allNotes.filter {
                it.title.lowercase().contains(q) || it.content.lowercase().contains(q)
            }.sortedByDescending { it.timestamp }
        }
    }

    private fun saveAndUpdate() {
        NoteSharedPrefs.saveNotes(getApplication(), allNotes)
        _notes.value = allNotes.sortedByDescending { it.timestamp }
    }

    // PUBLIC_INTERFACE
    /**
     * Get a note by id.
     */
    fun getNoteById(id: String?): Note? = allNotes.find { it.id == id }
}
