package com.sajjadio.laonote.presentation.ui.auth

import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentRegisterBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.utils.extension.moveToDestination

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(R.layout.fragment_register) {
    override fun launchView() {
        binding?.apply {
            skip.moveToDestination(
                RegisterFragmentDirections.actionRegisterFragmentToNoteFragment()
            )
        }
    }
}