package com.example.notesfrontend.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesfrontend.R
import com.example.notesfrontend.adapter.NoteAdapter
import com.example.notesfrontend.databinding.FragmentNoteListBinding
import com.example.notesfrontend.viewmodel.NotesViewModel

/**
 * Main list fragment: shows all notes and handles search, add, and navigation.
 */
class NoteListFragment : Fragment() {
    private var _binding: FragmentNoteListBinding? = null
    private val binding get() = _binding!!
    private val notesViewModel: NotesViewModel by activityViewModels()
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NoteAdapter(emptyList(),
            onNoteClick = { note ->
                // Use Bundle to pass argument instead of generated directions class
                val bundle = Bundle()
                bundle.putString("noteId", note.id)
                findNavController().navigate(
                    R.id.editNoteFragment,
                    bundle
                )
            },
            onNoteLongClick = { note ->
                // Confirm delete via dialog
                DeleteNoteDialogFragment(note) {
                    notesViewModel.deleteNote(note)
                }.show(parentFragmentManager, "DeleteNoteDialog")
            }
        )
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewNotes.adapter = adapter

        notesViewModel.notes.observe(viewLifecycleOwner) { notes ->
            adapter.updateNotes(notes)
            binding.textEmpty.visibility = if (notes.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.fabAddNote.setOnClickListener {
            // Passing null/empty for a new note
            val bundle = Bundle()
            // Don't set noteId for a new note (argument is nullable)
            findNavController().navigate(R.id.editNoteFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // PUBLIC_INTERFACE
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_note_list, menu)
        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem?.actionView as? SearchView
        searchView?.queryHint = getString(R.string.search_hint)
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                notesViewModel.searchNotes(query ?: "")
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                notesViewModel.searchNotes(newText ?: "")
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}
