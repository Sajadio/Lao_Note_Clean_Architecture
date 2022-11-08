package com.sajjadio.laonote.presentation.ui.fragment

import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentSettingsBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.extension.setToolBar

class SettingsFragment:BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {
    override fun launchView() {
        binding?.apply {
            root.transitionName = TRANSITION_ELEMENT_ROOT
            noteActivity.setToolBar(materialToolbar)
        }
    }
}