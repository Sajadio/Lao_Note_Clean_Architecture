package com.sajjadio.laonote.presentation.ui.task

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentTaskBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.extension.moveToDestination
import com.sajjadio.laonote.utils.extension.setToolBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TaskFragment : BaseFragment<FragmentTaskBinding, TaskViewModel>(R.layout.fragment_task) {

    override val viewModelClass = TaskViewModel::class.java
    @Inject
    lateinit var taskAdapter: TaskAdapter

    override fun launchView() {
        binding?.apply {
            root.transitionName = TRANSITION_ELEMENT_ROOT
            noteActivity.setToolBar(materialToolbar)
            fabBtnAddTask.moveToDestination(
                TaskFragmentDirections.actionTaskFragmentToAddTaskFragment()
            )

            taskAdapter.apply {
                recyclerView.adapter = this
                onItemClickListener { task ->
                    val bundle = Bundle()
                    bundle.putParcelable("task", task)
                    findNavController().navigate(
                        R.id.action_taskFragment_to_addTaskFragment,
                        bundle
                    )
                }
            }
        }
    }
}