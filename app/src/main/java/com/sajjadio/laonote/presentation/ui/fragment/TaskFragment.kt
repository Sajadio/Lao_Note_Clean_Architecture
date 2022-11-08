package com.sajjadio.laonote.presentation.ui.fragment

import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentTaskBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.extension.moveToDestination
import com.sajjadio.laonote.utils.extension.setToolBar
import kotlinx.android.synthetic.main.fragment_task.*

class TaskFragment : BaseFragment<FragmentTaskBinding>(R.layout.fragment_task) {
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