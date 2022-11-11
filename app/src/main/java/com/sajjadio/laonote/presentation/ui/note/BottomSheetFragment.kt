package com.sajjadio.laonote.presentation.ui.note

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentNotesBottomSheetBinding
import com.sajjadio.laonote.utils.TAG_NOTE_BOTTOM_SHEET
import kotlinx.android.synthetic.main.fragment_note.*
import kotlinx.android.synthetic.main.fragment_notes_bottom_sheet.*

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var binding: FragmentNotesBottomSheetBinding? = null
    private var selectedColor: Int = 0
    private lateinit var selectColor: MutableList<ImageButton>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_notes_bottom_sheet,
            container,
            false
        )
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        selectColor = mutableListOf(
            imgNote1, imgNote2, imgNote3, imgNote4, imgNote5, imgNote6, imgNote7, imgNote8
        )
    }

    private fun broadcastSender(tag: String, action: String) {
        val intent = Intent(tag)
        intent.putExtra("action", action)
        intent.putExtra("selectedColor", selectedColor)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }

    private fun handelSelectedColor(view: ImageButton, color: Int) {
        selectedColor = color
        selectColor.forEach {
            if (it == view)
                view.setImageResource(R.drawable.ic_tick)
            else it.setImageResource(0)
        }
    }

    private fun setListener() {
        imgNote1.setOnClickListener {
            handelSelectedColor(imgNote1, R.color.colorBlue)
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, "Blue")
        }
        imgNote2.setOnClickListener {
            handelSelectedColor(imgNote2, R.color.colorYellowNote)
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, "Yellow")
        }
        imgNote3.setOnClickListener {
            handelSelectedColor(imgNote3, R.color.colorPurpleNote)
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, "Purple")
        }
        imgNote4.setOnClickListener {
            handelSelectedColor(imgNote4, R.color.colorGreenNote)
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, "Green")
        }
        imgNote5.setOnClickListener {
            handelSelectedColor(imgNote5, R.color.colorOrangeNote)
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, "Orange")
        }
        imgNote6.setOnClickListener {
            handelSelectedColor(imgNote6, R.color.colorBlackNote)
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, "Black")
        }

        imgNote7.setOnClickListener {
            handelSelectedColor(imgNote7, R.color.colorWhiteNote)
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, "White")
        }

        imgNote8.setOnClickListener {
            handelSelectedColor(imgNote8, R.color.colorRedNote)
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, "Red")
        }

        addImage.setOnClickListener {
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, "Image")
            dismiss()
        }
        addWebUrl.setOnClickListener {
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, "WebUrl")
            dismiss()
        }

        deleteNote.setOnClickListener {
            val intent = Intent(TAG_NOTE_BOTTOM_SHEET)
            intent.putExtra("action", "DeleteNote")
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, "DeleteNote")
            dismiss()
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        val view = LayoutInflater.from(context).inflate(R.layout.fragment_notes_bottom_sheet, null)
        dialog.setContentView(view)
        val param = (view.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = param.behavior

        if (behavior is BottomSheetBehavior<*>) {
            behavior.addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    TODO("Not yet implemented")
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    if (behavior.state != BottomSheetBehavior.STATE_EXPANDED)
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    else
                        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }

            })
        }
    }
}