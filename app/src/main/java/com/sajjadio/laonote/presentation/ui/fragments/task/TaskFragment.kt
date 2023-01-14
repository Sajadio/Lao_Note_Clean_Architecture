package com.sajjadio.laonote.presentation.ui.fragments.task

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.SearchView
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

    private val fromTop: Animation by lazy {
        AnimationUtils.loadAnimation(
            noteActivity,
            R.anim.from_top_anim
        )
    }
    private val toTop: Animation by lazy {
        AnimationUtils.loadAnimation(
            noteActivity,
            R.anim.to_top_anim
        )
    }

    private var clicked = false

    override fun launchView() {
        binding?.apply {
            noteActivity.setToolBar(materialToolbar)
            fabBtnAddTask.moveToDestination(
                TaskFragmentDirections.actionTaskFragmentToAddTaskFragment()
            )
            filter.setOnClickListener {
                onAddButtonClicked()
            }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(s: String?): Boolean {
                    viewModel?.getTasksByTitle(s.toString())
                    return true
                }
            })

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
                checkBoxListener { task_id, isDone ->
                    viewModel?.isTaskDoneUseCase(task_id, isDone)
                }
            }
        }
    }

    private fun onAddButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setVisibility(clicked: Boolean) {
        binding?.apply {
            if (!clicked) {
                filterLayout.root.visibility = View.VISIBLE
            } else {
                filterLayout.root.visibility = View.INVISIBLE

            }
        }
    }

    private fun setAnimation(clicked: Boolean) {
        binding?.apply {
            if (!clicked) {
                filterLayout.root.startAnimation(fromTop)
            } else {
                filterLayout.root.startAnimation(toTop)
            }
        }
    }
}