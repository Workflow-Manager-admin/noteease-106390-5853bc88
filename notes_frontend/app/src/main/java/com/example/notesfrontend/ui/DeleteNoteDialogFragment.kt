package com.example.notesfrontend.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.notesfrontend.model.Note
import com.example.notesfrontend.R

/**
 * Dialog to confirm deletion of a note.
 */
// PUBLIC_INTERFACE
class DeleteNoteDialogFragment(
    private val note: Note,
    private val onConfirm: () -> Unit
): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_note_title)
            .setMessage(R.string.delete_note_message)
            .setPositiveButton(android.R.string.ok) { _, _ -> onConfirm() }
            .setNegativeButton(android.R.string.cancel, null)
            .create()
    }
}
