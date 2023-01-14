package com.sajjadio.laonote.presentation.ui.fragments.event

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentAddEventBinding
import com.sajjadio.laonote.domain.model.EventModel
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.FULL_DATE
import com.sajjadio.laonote.utils.STANDARD_DATE
import com.sajjadio.laonote.utils.extension.formatDate
import com.sajjadio.laonote.utils.extension.setToolBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.android.synthetic.main.fragment_task.*
import java.util.*

@AndroidEntryPoint
class AddEventFragment :
    BaseFragment<FragmentAddEventBinding, EventViewModel>(R.layout.fragment_add_event) {

    override val viewModelClass = EventViewModel::class.java
    private val args: AddEventFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat", "NewApi")
    override fun launchView() {
        binding?.apply {
            noteActivity.setToolBar(materialToolbar)
            eventDetails(args.event)
            observeNetworkResponse()
            tvDateTime.setOnClickListener {
                pickDateTime()
            }
        }
    }

    private fun observeNetworkResponse() {
        viewModel.apply {
            checkResponseStatus(eventResponse, R.id.action_addEventFragment_to_eventFragment)
        }
    }

    private fun eventDetails(event: EventModel?) {
        viewModel.apply {
            event?.let {
                showEventDetails(it)
                binding?.apply {
                    materialToolbar.title = resources.getString(R.string.edit)
                    deleteTask.isVisible = true
                    update.isVisible = true
                    save.isVisible = false
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
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
                    pickedDateTime.time.toString().formatDate(
                        STANDARD_DATE,
                        FULL_DATE
                    )
                )
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }

}