package com.sajjadio.laonote.presentation.ui.note


import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentNoteBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.presentation.ui.note.viewModel.NoteViewModel
import com.sajjadio.laonote.utils.extension.moveToDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : BaseFragment<FragmentNoteBinding,NoteViewModel>(R.layout.fragment_note) {

    override val viewModelClass = NoteViewModel::class.java

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