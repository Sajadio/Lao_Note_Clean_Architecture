package com.sajjadio.laonote.presentation.ui.settings

import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentSettingsBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.presentation.ui.note.NoteViewModel
import com.sajjadio.laonote.utils.extension.setToolBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment:BaseFragment<FragmentSettingsBinding, NoteViewModel>(R.layout.fragment_settings) {
    override val viewModelClass = NoteViewModel::class.java

    override fun launchView() {
        binding?.apply {
            root.transitionName = TRANSITION_ELEMENT_ROOT
            noteActivity.setToolBar(materialToolbar)
        }
    }
}