package com.sajjadio.laonote.presentation.ui.task

import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentTaskBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.presentation.ui.note.viewModel.NoteViewModel
import com.sajjadio.laonote.utils.extension.moveToDestination
import com.sajjadio.laonote.utils.extension.setToolBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskFragment : BaseFragment<FragmentTaskBinding,NoteViewModel>(R.layout.fragment_task) {

    override val viewModelClass = NoteViewModel::class.java

    override fun launchView() {
        binding?.apply {
            root.transitionName = TRANSITION_ELEMENT_ROOT
            noteActivity.setToolBar(materialToolbar)
            fabBtnAddTask.moveToDestination(
                TaskFragmentDirections.actionTaskFragmentToAddTaskFragment()
            )
        }
    }
}