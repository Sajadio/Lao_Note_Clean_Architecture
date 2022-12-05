package com.sajjadio.laonote.presentation.ui.note


import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentNoteBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.extension.moveToDestination
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : BaseFragment<FragmentNoteBinding, NoteViewModel>(R.layout.fragment_note) {

    override val viewModelClass = NoteViewModel::class.java

    @Inject
    lateinit var noteAdapter: NoteAdapter

    override fun launchView() {
        binding?.apply {
            root.transitionName = TRANSITION_ELEMENT_ROOT

            recyclerView.adapter = noteAdapter

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