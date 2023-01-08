package com.sajjadio.laonote.presentation.ui.note


import android.os.Bundle
import androidx.navigation.fragment.findNavController
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

            noteAdapter.apply {
                recyclerView.adapter = this
                onItemClickListener { note ->
                    val bundle = Bundle()
                    bundle.putParcelable("note", note)
                    findNavController().navigate(
                        R.id.action_noteFragment_to_addNoteFragment,
                        bundle
                    )
                }
            }

            fabBtnAddNote.moveToDestination(
                NoteFragmentDirections.actionNoteFragmentToAddNoteFragment(null)
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