package com.sajjadio.laonote.presentation.ui.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentProfileBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.extension.moveToDestination
import com.sajjadio.laonote.utils.extension.setToolBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(R.layout.fragment_profile) {
    override val viewModelClass = ProfileViewModel::class.java


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
        }
    }
}
