package com.sajjadio.laonote.presentation.ui.task

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentAddTaskBinding
import com.sajjadio.laonote.domain.model.Task
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.FULL_DATE
import com.sajjadio.laonote.utils.NetworkResponse
import com.sajjadio.laonote.utils.STANDARD_DATE
import com.sajjadio.laonote.utils.extension.formatDate
import com.sajjadio.laonote.utils.extension.observeEvent
import com.sajjadio.laonote.utils.extension.setToolBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.android.synthetic.main.fragment_task.*
import java.util.*

@AndroidEntryPoint
class AddTaskFragment :
    BaseFragment<FragmentAddTaskBinding, TaskViewModel>(R.layout.fragment_add_task) {

    override val viewModelClass = TaskViewModel::class.java
    private val args: AddTaskFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat", "NewApi")
    override fun launchView() {
        binding?.apply {
            root.transitionName = TRANSITION_ELEMENT_ROOT
            noteActivity.setToolBar(materialToolbar)
            taskDetails(args.task)
            viewModel?.apply {
                eventResponse.observeEvent(viewLifecycleOwner) { status ->
                    checkResponseStatus(status)
                    if (status is NetworkResponse.Success) {
                        findNavController().navigate(R.id.action_addTaskFragment_to_taskFragment)
                    }
                }
            }
//
            tvDateTime.setOnClickListener {
                pickDateTime()
            }
            addURL.setOnClickListener {
                layoutWebUrlTask.visibility = View.VISIBLE
            }
            clearWebURL.setOnClickListener {
                layoutWebUrlTask.visibility = View.GONE
                viewModel?.task_webUrl?.postValue(null)
            }
        }
    }

    private fun taskDetails(task: Task?) {
        viewModel.apply {
            task?.let {
                showTaskDetails(it)
                binding?.apply {
                    materialToolbar.title = resources.getString(R.string.edit)
                    layoutWebUrlTask.isVisible = task.task_webUrl?.isNotEmpty() == true
                    deleteTask.isVisible = true
                    update.isVisible = true
                    save.isVisible = false
                }
            }
        }
    }


    private fun pickDateTime() {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)
        val pickedDateTime = Calendar.getInstance()
        DatePickerDialog(requireContext(), { _, year, month, day ->
            TimePickerDialog(requireContext(), { _, hour, minute ->
                pickedDateTime.set(year, month, day, hour, minute)
                viewModel.date.postValue(
                    pickedDateTime.time.toString().formatDate(STANDARD_DATE,FULL_DATE)
                )
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }

}