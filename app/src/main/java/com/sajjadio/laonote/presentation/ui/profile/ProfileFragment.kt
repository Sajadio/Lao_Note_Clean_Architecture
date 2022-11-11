package com.sajjadio.laonote.presentation.ui.profile

import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentProfileBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.extension.moveToDestination
import com.sajjadio.laonote.utils.extension.setToolBar

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    override fun launchView() {
        binding?.apply {
            root.transitionName = TRANSITION_ELEMENT_ROOT
            noteActivity.setToolBar(materialToolbar)
            settings.moveToDestination(
                ProfileFragmentDirections.actionProfileFragmentToSettingsFragment()
            )
        }

    }
}