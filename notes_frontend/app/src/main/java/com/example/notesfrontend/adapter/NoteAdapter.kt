package com.example.notesfrontend.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesfrontend.R
import com.example.notesfrontend.model.Note
import java.text.SimpleDateFormat
import java.util.*

// PUBLIC_INTERFACE
/**
 * RecyclerView adapter for displaying notes in the list.
 */
class NoteAdapter(
    private var notes: List<Note>,
    private val onNoteClick: (Note) -> Unit,
    private val onNoteLongClick: (Note) -> Unit
): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    class NoteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.text_note_title)
        val contentTextView: TextView = view.findViewById(R.id.text_note_content)
        val dateTextView: TextView = view.findViewById(R.id.text_note_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = if (note.content.length > 100) note.content.take(100) + "…" else note.content
        holder.dateTextView.text = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date(note.timestamp))
        holder.itemView.setOnClickListener { onNoteClick(note) }
        holder.itemView.setOnLongClickListener { onNoteLongClick(note); true }
    }

    // PUBLIC_INTERFACE
    /**
     * Updates the notes data set and refreshes the view.
     */
    fun updateNotes(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
