package com.sajjadio.laonote.presentation.ui.fragments.note

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
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentAddNoteBinding
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.*
import com.sajjadio.laonote.utils.extension.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.color_picker_dialog.*
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_add_task.*
import kotlinx.android.synthetic.main.fragment_notes_bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_notes_bottom_sheet.view.*
import kotlinx.android.synthetic.main.item_event.*
import java.util.*
import javax.inject.Inject


@Suppress("DUPLICATE_LABEL_IN_WHEN")
@AndroidEntryPoint
class AddNoteFragment : BaseFragment<FragmentAddNoteBinding, NoteViewModel>(
    R.layout.fragment_add_note
) {
    @Inject
    lateinit var helper: PermissionsHelper
    override val viewModelClass = NoteViewModel::class.java
    var selectedNoteColor = R.color.colorSecond
    var selectedFontColor = R.color.colorHint
    private val args: AddNoteFragmentArgs by navArgs()

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("SimpleDateFormat")
    override fun launchView() {
        binding?.apply {
            noteActivity.setToolBar(materialToolbar)
            setNoteColor(selectedNoteColor)
            setFontColor(selectedFontColor)
            noteDetails(args.note)
            viewModel?.apply {
                eventResponse.observeEvent(viewLifecycleOwner) { status ->
                    checkResponseStatus(status)
                    if (status is NetworkResponse.Success) {
                        findNavController().navigate(R.id.action_addNoteFragment_to_noteFragment)
                    }
                }
                eventManageImageStorage.observeEvent(viewLifecycleOwner) { status ->
                    checkResponseStatus(status)
                }
            }
            miscrollaneous.setOnClickListener {
                openBottomSheet()
            }
            clearWebURL.setOnClickListener {
                layoutWebUrl.visibility = View.GONE
                etNoteWebLink.text.clear()
            }
            imgDelete.setOnClickListener {
                layoutImage.visibility = View.GONE
                viewModel?.note_image?.postValue("")
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            broadcastReceiver, IntentFilter(TAG_NOTE_BOTTOM_SHEET)
        )
    }

    private fun noteDetails(note: Note?) {
        viewModel.apply {
            note?.let {
                showNoteDetails(it)
                binding?.apply {
                    materialToolbar.title = resources.getString(R.string.edit)
                    layoutWebUrl.isVisible = note.note_webUrl?.isNotEmpty() == true
                    deleteNote.isVisible = true
                    update.isVisible = true
                    save.isVisible = false
                    tvLastDateTime.isVisible = true
                }
            }
        }
    }

    private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.P)
        override fun onReceive(p0: Context?, p1: Intent?) {
            binding?.apply {
                when (p1?.getStringExtra("action")) {
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
                        layoutWebUrl.visibility = View.VISIBLE
                        etNoteWebLink.showKeyboard()
                    }
                    "FontYellow", "FontBlue", "FontPurple", "FontGreen", "FontOrange", "FontBlack", "FontWhite", "FontRed" -> {
                        selectedFontColor = p1.getIntExtra("selectedFontColors", 0)
                        setFontColor(selectedFontColor)
                    }
                    "Blue", "Yellow", "Purple", "Green", "Orange", "Black", "White", "Red" -> {
                        selectedNoteColor = p1.getIntExtra("selectedNoteColors", 0)
                        setNoteColor(selectedNoteColor)
                    }
                }
            }
        }

    }


    private fun setNoteColor(color: Int) {
        viewModel.note_color.postValue(color)
    }

    private fun setFontColor(color: Int) {
        viewModel.font_color.postValue(color)
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
        binding?.apply {
            val imageUri = result.data!!.data!!
            viewModel?.manageImageStorageUseCase(imageUri)
            layoutImage.visibility = View.VISIBLE
            val inputStream = requireActivity().contentResolver.openInputStream(imageUri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            imgNote.setImageBitmap(bitmap)
        }
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