package com.sajjadio.laonote.presentation.ui.fragments.note

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.ColorPickerDialogBinding
import com.sajjadio.laonote.utils.TAG_PICKER_COLOR
import com.skydoves.colorpickerview.AlphaTileView
import com.skydoves.colorpickerview.ColorEnvelope
import com.skydoves.colorpickerview.ColorPickerView
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import kotlinx.android.synthetic.main.color_picker_dialog.*
import kotlinx.android.synthetic.main.fragment_notes_bottom_sheet.*


class PickerColorFragment : BottomSheetDialogFragment() {

    private var binding: ColorPickerDialogBinding? = null
    private var mColorPickerView: ColorPickerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.color_picker_dialog,
            container,
            false
        )
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        alphaTileView.setOnClickListener {
            dismiss()
        }
        binding?.apply {
            mColorPickerView = colorPickerView.apply { }
            colorPickerView.setColorListener(
                ColorEnvelopeListener { envelope: ColorEnvelope, _: Boolean ->
                    setLayoutColor(envelope)
                })
            colorPickerView.attachAlphaSlider(alphaSlideBar)
            val brightnessSlideBar = brightnessSlide
            colorPickerView.attachBrightnessSlider(brightnessSlideBar)
            colorPickerView.setLifecycleOwner(this@PickerColorFragment)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setLayoutColor(envelope: ColorEnvelope) {
        val color = binding?.textView!!
        color.text = "#" + envelope.hexCode
        val alphaTileView: AlphaTileView? = binding?.alphaTileView
        alphaTileView?.setPaintColor(envelope.color)
        val intent = Intent(TAG_PICKER_COLOR)
        intent.putExtra("selectedColor", envelope.color)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}