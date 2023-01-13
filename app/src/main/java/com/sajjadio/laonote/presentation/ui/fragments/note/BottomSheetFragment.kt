package com.sajjadio.laonote.presentation.ui.fragments.note

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
    private var selectNoteColors = R.color.colorSecond
    private var selectFontColors = R.color.colorHint

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
        setListenerNoteColor()
        setListenerFontColor()
    }

    private fun broadcastSender(tag: String, action: String) {
        val intent = Intent(tag)
        intent.putExtra("action", action)
        intent.putExtra("selectedNoteColors", selectNoteColors)
        intent.putExtra("selectedFontColors", selectFontColors)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }

    private fun handelSelectedNoteColor(view: ImageButton, color: Int) {
        selectNoteColors = color
        val noteColors = mutableListOf(
            noteColor1,
            noteColor2,
            noteColor3,
            noteColor4,
            noteColor5,
            noteColor6,
            noteColor7,
            noteColor8
        )
        noteColors.forEach {
            if (it == view) view.setImageResource(R.drawable.ic_tick) else it.setImageResource(0)
        }
    }

    private fun handelSelectedFontColor(view: ImageButton, color: Int) {
        selectFontColors = color
        val fontColors = mutableListOf(
            fontColor1,
            fontColor2,
            fontColor3,
            fontColor4,
            fontColor5,
            fontColor6,
            fontColor7,
            fontColor8
        )
        fontColors.forEach {
            if (it == view) view.setImageResource(R.drawable.ic_tick) else it.setImageResource(0)
        }
    }

    private fun ImageButton.setListenerNoteColorBtn(color: Int, action: String) {
        this.setOnClickListener {
            handelSelectedNoteColor(this, color)
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, action)
        }
    }

    private fun ImageButton.setListenerFontColorBtn(color: Int, action: String) {
        this.setOnClickListener {
            handelSelectedFontColor(this, color)
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, action)
        }
    }


    private fun setListenerNoteColor() {
        noteColor1.setListenerNoteColorBtn(R.color.colorBlue, "Blue")
        noteColor2.setListenerNoteColorBtn(R.color.colorYellowNote, "Yellow")
        noteColor3.setListenerNoteColorBtn(R.color.colorPurpleNote, "Purple")
        noteColor4.setListenerNoteColorBtn(R.color.colorGreenNote, "Green")
        noteColor5.setListenerNoteColorBtn(R.color.colorOrangeNote, "Orange")
        noteColor6.setListenerNoteColorBtn(R.color.colorBlackNote, "Black")
        noteColor7.setListenerNoteColorBtn(R.color.colorSecond, "White")
        noteColor8.setListenerNoteColorBtn(R.color.colorRedNote, "Red")
    }

    private fun setListenerFontColor() {
        fontColor1.setListenerFontColorBtn(R.color.colorBlue, "FontBlue")
        fontColor2.setListenerFontColorBtn(R.color.colorYellowNote, "FontYellow")
        fontColor3.setListenerFontColorBtn(R.color.colorPurpleNote, "FontPurple")
        fontColor4.setListenerFontColorBtn(R.color.colorGreenNote, "FontGreen")
        fontColor5.setListenerFontColorBtn(R.color.colorOrangeNote, "FontOrange")
        fontColor6.setListenerFontColorBtn(R.color.colorText, "FontBlack")
        fontColor7.setListenerFontColorBtn(R.color.colorWhite, "FontWhite")
        fontColor8.setListenerFontColorBtn(R.color.colorRedNote, "FontRed")
    }

    private fun setListener() {
        addImage.setOnClickListener {
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, "Image")
            dismiss()
        }
        addWebUrl.setOnClickListener {
            broadcastSender(TAG_NOTE_BOTTOM_SHEET, "WebUrl")
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