package com.example.notesfrontend.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.notesfrontend.databinding.FragmentEditNoteBinding
import com.example.notesfrontend.model.Note
import com.example.notesfrontend.viewmodel.NotesViewModel
import com.example.notesfrontend.R
import java.util.UUID

/**
 * Fragment for creating or editing a note.
 */
class EditNoteFragment : Fragment() {
    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!
    private val notesViewModel: NotesViewModel by activityViewModels()

    private var noteId: String? = null
    private var editingNote: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        noteId = arguments?.getString("noteId")
        editingNote = notesViewModel.getNoteById(noteId)
        if (editingNote != null) {
            binding.editTitle.setText(editingNote?.title)
            binding.editContent.setText(editingNote?.content)
        }

        binding.buttonSave.setOnClickListener {
            saveNoteAndExit()
        }
    }

    // PUBLIC_INTERFACE
    private fun saveNoteAndExit() {
        val title = binding.editTitle.text.toString().trim()
        val content = binding.editContent.text.toString().trim()
        if (title.isEmpty()) {
            binding.editTitle.error = getString(R.string.error_title_required)
            return
        }
        val note = editingNote?.copy(
            title = title,
            content = content,
            timestamp = System.currentTimeMillis()
        ) ?: Note(UUID.randomUUID().toString(), title, content, System.currentTimeMillis())
        if (editingNote != null) {
            notesViewModel.updateNote(note)
        } else {
            notesViewModel.addNote(note)
        }
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
