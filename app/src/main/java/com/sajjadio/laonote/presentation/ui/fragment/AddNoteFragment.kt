package com.sajjadio.laonote.presentation.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentAddNoteBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.presentation.ui.ActionBottomSheet
import com.sajjadio.laonote.utils.*
import com.sajjadio.laonote.utils.extension.dateFormat
import com.sajjadio.laonote.utils.extension.getPathFromUri
import com.sajjadio.laonote.utils.extension.setToolBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.color_picker_dialog.*
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_notes_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_notes_bottom_sheet.view.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding>(
    R.layout.fragment_add_note
) {

    var selectedColor = R.color.colorBlackNote
    private var webLink = ""
    private var selectedImagePath = ""

    @Inject
    lateinit var helper: PermissionsHelper

    @SuppressLint("SimpleDateFormat")
    override fun launchView() {
        binding?.apply {
            noteActivity.setToolBar(materialToolbar)

            noteActivity.viewModel.date.postValue(
                Calendar.getInstance().time.toString().dateFormat()
            )

            pickerColor.setBackgroundColor(selectedColor)

            miscrollaneous.setOnClickListener {
                openBottomSheet()
            }
            btnOk.setOnClickListener {
                if (etWebLink.text.toString().trim().isNotEmpty()) {
                    checkWebUrl()
                } else {
                    Toast.makeText(requireContext(), "Url is Required", Toast.LENGTH_SHORT).show()
                }
            }

            btnCancel.setOnClickListener {
                addWebUrl.visibility = View.GONE
            }

            imgDelete.setOnClickListener {
                selectedImagePath = ""
                layoutImage.visibility = View.GONE
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            broadcastReceiver, IntentFilter(TAG_NOTE_BOTTOM_SHEET)
        )
    }

    private fun checkWebUrl() {
        binding?.apply {
            if (Patterns.WEB_URL.matcher(etWebLink.text.toString()).matches()) {
                addWebUrl.visibility = View.GONE
                etWebLink.isEnabled = false
                webLink = etWebLink.text.toString()
                tvWebLink.visibility = View.VISIBLE
                tvWebLink.text = etWebLink.text.toString()
            } else {
                Toast.makeText(requireContext(), "Url is not valid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.P)
        override fun onReceive(p0: Context?, p1: Intent?) {
            binding?.apply {
                when (p1?.getStringExtra("action")) {

                    "Blue" -> {
                        selectedColor = p1.getIntExtra("selectedColor", 0)
                        pickerColor.setBackgroundResource(selectedColor)
                    }
                    "Yellow" -> {
                        selectedColor = p1.getIntExtra("selectedColor", 0)
                        pickerColor.setBackgroundResource(selectedColor)
                    }
                    "Purple" -> {
                        selectedColor = p1.getIntExtra("selectedColor", 0)
                        pickerColor.setBackgroundResource(selectedColor)
                    }
                    "Green" -> {
                        selectedColor = p1.getIntExtra("selectedColor", 0)
                        pickerColor.setBackgroundResource(selectedColor)
                    }
                    "Orange" -> {
                        selectedColor = p1.getIntExtra("selectedColor", 0)
                        pickerColor.setBackgroundResource(selectedColor)
                    }
                    "Black" -> {
                        selectedColor = p1.getIntExtra("selectedColor", 0)
                        pickerColor.setBackgroundResource(selectedColor)
                    }
                    "White" -> {
                        selectedColor = p1.getIntExtra("selectedColor", 0)
                        pickerColor.setBackgroundResource(selectedColor)
                    }
                    "Red" -> {
                        selectedColor = p1.getIntExtra("selectedColor", 0)
                        pickerColor.setBackgroundResource(selectedColor)
                    }

                    "Image" -> {
                        helper.message = resources.getString(R.string.permission_storage_rationale_message)
                        helper.activity = noteActivity
                        helper.getRequestPermission(
                            REQUEST_CODE_PICK_PHOTO,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) {
                            pickPhoto()
                        }
                    }

                    "WebUrl" -> {
                        addWebUrl.visibility = View.VISIBLE
                    }
                    "DeleteNote" -> {
                        selectedColor = p1.getIntExtra("selectedColor", 0)
                        pickerColor.setBackgroundResource(selectedColor)
                    }


                    else -> {
                        layoutImage.visibility = View.GONE
                        addWebUrl.visibility = View.GONE
                        selectedColor = p1?.getIntExtra("selectedColor", 0)!!
                        pickerColor.setBackgroundResource(selectedColor)
                    }
                }
            }
        }

    }

    private fun openBottomSheet() {
        ActionBottomSheet.newInstanceOfNoteBottomSheet().show(
            noteActivity.supportFragmentManager,
            TAG_NOTE_BOTTOM_SHEET
        )
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private val photoResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                getPhotoResultIntent(result)
            }
        }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getPhotoResultIntent(result: ActivityResult) {
        val imageUri = result.data!!.data!!
        val path = requireActivity().getPathFromUri(imageUri)
        selectedImagePath = path.toString()
        layoutImage.visibility = View.VISIBLE
        val inputStream = requireActivity().contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        binding?.imgNote?.setImageBitmap(bitmap)
    }


    @RequiresApi(Build.VERSION_CODES.P)
    private fun pickPhoto() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also {
            it.type = "image/*"
            photoResultLauncher.launch(it)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(broadcastReceiver)
    }
}