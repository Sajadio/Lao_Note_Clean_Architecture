package com.sajjadio.laonote.presentation.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentProfileBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.PermissionsHelper
import com.sajjadio.laonote.utils.REQUEST_CODE_PICK_PHOTO
import com.sajjadio.laonote.utils.extension.getPathFromUri
import com.sajjadio.laonote.utils.extension.moveToDestination
import com.sajjadio.laonote.utils.extension.setToolBar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {
    override val viewModelClass = ProfileViewModel::class.java

    @Inject
    lateinit var helper: PermissionsHelper

    @RequiresApi(Build.VERSION_CODES.P)
    override fun launchView() {
        binding?.apply {
            root.transitionName = TRANSITION_ELEMENT_ROOT
            noteActivity.setToolBar(materialToolbar)
            settings.moveToDestination(
                ProfileFragmentDirections.actionProfileFragmentToSettingsFragment()
            )
            logout.setOnClickListener {
                viewModel?.logOut()
                Navigation.findNavController(it).navigate(
                    ProfileFragmentDirections.actionProfileFragmentToAuthenticationFragment()
                )
            }
            logIn.moveToDestination(
                ProfileFragmentDirections.actionProfileFragmentToAuthenticationFragment()
            )

            editBtn.setOnClickListener {
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

        val inputStream = requireActivity().contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        binding?.profileImage?.setImageBitmap(bitmap)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun pickPhoto() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).also {
            it.type = "image/*"
            photoResultLauncher.launch(it)
        }
    }


}
