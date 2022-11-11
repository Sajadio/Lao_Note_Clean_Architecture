package com.sajjadio.laonote.presentation.ui.event

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentEventBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.extension.dateFormat
import com.sajjadio.laonote.utils.extension.setToolBar
import kotlinx.android.synthetic.main.dialog_add_event.*
import kotlinx.android.synthetic.main.dialog_add_event.view.*
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlinx.android.synthetic.main.item_event.*
import kotlinx.android.synthetic.main.item_event.view.*
import java.util.*


class EventFragment : BaseFragment<FragmentEventBinding>(R.layout.fragment_event) {

    private lateinit var customView: View
    private lateinit var alertDialog: AlertDialog.Builder

    @SuppressLint("NewApi", "SimpleDateFormat")
    override fun launchView() {
        binding?.apply {
            root.transitionName = TRANSITION_ELEMENT_ROOT
            noteActivity.setToolBar(materialToolbar)
            fabBtnAddEvent.setOnClickListener {
                initialAlertDialog()
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat")
    private fun initialAlertDialog() {
        customView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_event, null)
        alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setView(customView)
        val dialog = alertDialog.create()
        val cancel = customView.findViewById<ImageButton>(R.id.cancel)
        customView.tvDateTime?.apply {
            noteActivity.viewModel.date.observe(viewLifecycleOwner) {
                text = it
            }
            noteActivity.viewModel.date.postValue(
                Calendar.getInstance().time.toString().dateFormat()
            )
            setOnClickListener {
                pickDateTime()
            }
        }

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        cancel.setOnClickListener {
            dialog.cancel()
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
                noteActivity.viewModel.date.postValue(pickedDateTime.time.toString().dateFormat())
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }
}