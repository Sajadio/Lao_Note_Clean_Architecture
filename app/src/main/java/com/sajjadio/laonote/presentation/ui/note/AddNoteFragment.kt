package com.sajjadio.laonote.presentation.ui.note

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
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentAddNoteBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.presentation.ui.note.viewModel.NoteViewModel
import com.sajjadio.laonote.utils.ActionBottomSheet
import com.sajjadio.laonote.utils.*
import com.sajjadio.laonote.utils.extension.dateFormat
import com.sajjadio.laonote.utils.extension.getPathFromUri
import com.sajjadio.laonote.utils.extension.makeToast
import com.sajjadio.laonote.utils.extension.setToolBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.color_picker_dialog.*
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.android.synthetic.main.fragment_notes_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_notes_bottom_sheet.addWebUrl
import kotlinx.android.synthetic.main.fragment_notes_bottom_sheet.view.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding,NoteViewModel>(
    R.layout.fragment_add_note
) {
    override val viewModelClass = NoteViewModel::class.java
    var selectedColor = R.color.colorBlackNote
    private var selectedImagePath = ""

    @Inject
    lateinit var helper: PermissionsHelper

    @SuppressLint("SimpleDateFormat")
    override fun launchView() {
        binding?.apply {
            noteActivity.setToolBar(materialToolbar)

            viewModel?.note_color?.postValue(selectedColor.toString())
            pickerColor.setBackgroundColor(selectedColor)

            miscrollaneous.setOnClickListener {
                openBottomSheet()
            }

            clearWebURL.setOnClickListener {
                layoutWebUrl.visibility = View.GONE
                etWebLink.text.clear()
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
            if (!(Patterns.WEB_URL.matcher(etWebLink.text.toString()).matches()))
                noteActivity.makeToast(resources.getString(R.string.url_valid))
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
                        helper.message =
                            resources.getString(R.string.permission_storage_rationale_message)
                        helper.activity = noteActivity
                        helper.getRequestPermission(
                            REQUEST_CODE_PICK_PHOTO,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) {
                            pickPhoto()
                        }
                    }

                    "WebUrl" -> {
                        layout_Web_url.visibility = View.VISIBLE
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
        binding?.layoutImage?.visibility = View.VISIBLE
        viewModel.note_image.postValue(selectedImagePath)
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