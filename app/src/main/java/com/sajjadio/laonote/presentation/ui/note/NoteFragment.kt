package com.sajjadio.laonote.presentation.ui.note


import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentNoteBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.extension.moveToDestination

class NoteFragment : BaseFragment<FragmentNoteBinding>(R.layout.fragment_note) {

    override fun launchView() {
        binding?.apply {
            root.transitionName = TRANSITION_ELEMENT_ROOT

            fabBtnAddNote.moveToDestination(
                NoteFragmentDirections.actionNoteFragmentToAddNoteFragment()
            )
            profileFragment.moveToDestination(
                NoteFragmentDirections.actionNoteFragmentToProfileFragment()
            )
            taskFragment.moveToDestination(
                NoteFragmentDirections.actionNoteFragmentToTaskFragment()
            )
            eventFragment.moveToDestination(
                NoteFragmentDirections.actionNoteFragmentToEventFragment()
            )
        }
    }

}