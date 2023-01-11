package com.sajjadio.laonote.presentation.ui.fragments.profile

import android.os.Build
import androidx.annotation.RequiresApi
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentProfileBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.presentation.ui.activities.AuthenticationActivity
import com.sajjadio.laonote.utils.extension.moveToDestination
import com.sajjadio.laonote.utils.extension.navigateActivity
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
                noteActivity.navigateActivity<AuthenticationActivity>(isFinish = true)
            }
            logIn.setOnClickListener {
                noteActivity.navigateActivity<AuthenticationActivity>(isFinish = true)
            }
        }
    }
}
