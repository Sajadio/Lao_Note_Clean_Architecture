package com.sajjadio.laonote.presentation.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentAddTaskBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.PermissionsHelper
import com.sajjadio.laonote.utils.REQUEST_CODE_PICK_PHOTO
import com.sajjadio.laonote.utils.extension.dateFormat
import com.sajjadio.laonote.utils.extension.getPathFromUri
import com.sajjadio.laonote.utils.extension.setToolBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_add_event.view.*
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.android.synthetic.main.fragment_task.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddTaskFragment : BaseFragment<FragmentAddTaskBinding>(R.layout.fragment_add_task) {

    private var webLink = ""
    private var selectedImagePath = ""

    @Inject
    lateinit var helper: PermissionsHelper

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SimpleDateFormat", "NewApi")
    override fun launchView() {
        binding?.apply {
            root.transitionName = TRANSITION_ELEMENT_ROOT
            noteActivity.setToolBar(materialToolbar)
            noteActivity.viewModel.date.postValue(
                Calendar.getInstance().time.toString().dateFormat()
            )
            tvDateTime.setOnClickListener {
                pickDateTime()
            }
            addImage.setOnClickListener {
                helper.message = resources.getString(R.string.permission_storage_rationale_message)
                helper.activity = noteActivity
                helper.getRequestPermission(
                    REQUEST_CODE_PICK_PHOTO,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) {
                    pickPhoto()
                }
            }
            addURL.setOnClickListener {
                addWebUrl.visibility = View.VISIBLE
            }
            imgDelete.setOnClickListener {
                selectedImagePath = ""
                layoutImage.visibility = View.GONE
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
        }
    }

    private fun checkWebUrl() {
        binding?.apply {
            if (Patterns.WEB_URL.matcher(etWebLink.text.toString()).matches()) {
                addWebUrl.visibility = View.GONE
                etWebLink.isEnabled = false
                webLink = etWebLink.text.toString()
                tvWebLink.visibility = View.VISIBLE
                tvWebLink.text = webLink
            } else {
                Toast.makeText(requireContext(), "Url is not valid", Toast.LENGTH_SHORT).show()
            }
        }
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
                noteActivity.viewModel.date.postValue(
                    pickedDateTime.time.toString().dateFormat()
                )
            }, startHour, startMinute, false).show()
        }, startYear, startMonth, startDay).show()
    }

}