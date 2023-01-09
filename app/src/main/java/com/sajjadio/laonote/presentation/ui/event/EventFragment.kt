package com.sajjadio.laonote.presentation.ui.event

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentEventBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.extension.moveToDestination
import com.sajjadio.laonote.utils.extension.setToolBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EventFragment : BaseFragment<FragmentEventBinding, EventViewModel>(R.layout.fragment_event) {

    override val viewModelClass = EventViewModel::class.java

    @Inject
    lateinit var eventAdapter: EventAdapter

    @SuppressLint("NewApi", "SimpleDateFormat")
    override fun launchView() {
        binding?.apply {
            root.transitionName = TRANSITION_ELEMENT_ROOT
            noteActivity.setToolBar(materialToolbar)
            fabBtnAddEvent.moveToDestination(
                EventFragmentDirections.actionEventFragmentToAddEventFragment()
            )
            eventAdapter.apply {
                recyclerView.adapter = this
                onItemClickListener { event ->
                    val bundle = Bundle()
                    bundle.putParcelable("event", event)
                    findNavController().navigate(
                        R.id.action_eventFragment_to_addEventFragment,
                        bundle
                    )
                }
            }
            getDateFromCalender()
        }
    }

    private fun getDateFromCalender() {
        binding?.apply {
            calendarView
                .setOnDateChangeListener { _, year, month, day ->
                    val monthOfYear = (month + 1)
                    val dayOfMonth = if (day > 9) day.toString() else "0$day"
                    val date = if (monthOfYear > 9) "$monthOfYear/$dayOfMonth/$year"
                    else
                        "0${monthOfYear}/$dayOfMonth/$year"
                    viewModel?.getEventsByDate(date)
                }
        }
    }

}