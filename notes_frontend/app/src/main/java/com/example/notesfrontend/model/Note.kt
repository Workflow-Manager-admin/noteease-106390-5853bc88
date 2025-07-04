package com.example.notesfrontend.model

import java.util.UUID

// PUBLIC_INTERFACE
/**
 * Represents a Note entity for storage and display.
 * @property id unique identifier for the note.
 * @property title note title.
 * @property content note content/body.
 * @property timestamp last modified or created.
 */
data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)
