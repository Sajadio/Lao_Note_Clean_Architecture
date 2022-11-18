package com.sajjadio.laonote.presentation.ui.profile

import android.util.Log
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentProfileBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.presentation.ui.auth.viewModel.AuthViewModel
import com.sajjadio.laonote.presentation.ui.note.viewModel.NoteViewModel
import com.sajjadio.laonote.utils.TAG
import com.sajjadio.laonote.utils.extension.moveToDestination
import com.sajjadio.laonote.utils.extension.setToolBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding,AuthViewModel>(R.layout.fragment_profile) {
    override val viewModelClass = AuthViewModel::class.java

    override fun launchView() {
        binding?.apply {
            root.transitionName = TRANSITION_ELEMENT_ROOT
            noteActivity.setToolBar(materialToolbar)
            settings.moveToDestination(
                ProfileFragmentDirections.actionProfileFragmentToSettingsFragment()
            )
            logout.apply {
                viewModel?.logOut()
                moveToDestination(
                    ProfileFragmentDirections.actionProfileFragmentToAuthenticationFragment()
                )
            }
        }

    }
}