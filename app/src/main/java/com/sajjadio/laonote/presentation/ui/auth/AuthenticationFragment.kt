package com.sajjadio.laonote.presentation.ui.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.FragmentAuthenticationBinding
import com.sajjadio.laonote.presentation.base.BaseFragment
import com.sajjadio.laonote.presentation.ui.auth.viewModel.AuthViewModel
import com.sajjadio.laonote.utils.NetworkResponse
import com.sajjadio.laonote.utils.TAG
import com.sajjadio.laonote.utils.extension.moveToDestination
import com.sajjadio.laonote.utils.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.item_event.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AuthenticationFragment :
    BaseFragment<FragmentAuthenticationBinding, AuthViewModel>(R.layout.fragment_authentication) {
    override val viewModelClass = AuthViewModel::class.java

    @SuppressLint("NewApi")
    override fun launchView() {

        binding?.apply {
            viewModel?.eventResponse?.observeEvent(viewLifecycleOwner) { status ->
                checkResponseStatus(status)
            }
            skip.moveToDestination(
                AuthenticationFragmentDirections.actionAuthenticationFragmentToNoteFragment()
            )

            google.setOnClickListener {
                launchOnLifecycleScope {
                    val intent = viewModel?.requestSignInWithGoogle()?.signInIntent
                    signInGoogleResultLauncher.launch(intent)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getSigInGoogleResultIntent(result: ActivityResult) {
        viewModel.checkSignInWithGoogle(result)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private val signInGoogleResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                getSigInGoogleResultIntent(result)
            }
        }

    override fun onStart() {
        super.onStart()
        launchOnLifecycleScope {
            noteActivity.sessionManager.accessToken.collectLatest { token ->
                if (token?.isNotEmpty() == true) {
                    findNavController().navigate(R.id.action_authenticationFragment_to_noteFragment)
                }
            }
        }
    }

}