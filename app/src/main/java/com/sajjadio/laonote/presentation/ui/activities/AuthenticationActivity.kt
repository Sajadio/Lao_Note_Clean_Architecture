package com.sajjadio.laonote.presentation.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.sajjadio.laonote.R
import com.sajjadio.laonote.data.local.data_storage.SessionManager
import com.sajjadio.laonote.databinding.ActivityAuthenticationBinding
import com.sajjadio.laonote.presentation.ui.fragments.settings.SettingsViewModel
import com.sajjadio.laonote.utils.ThemeHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {

    private var binding: ActivityAuthenticationBinding? = null
    val viewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            sessionManager.accessToken.collectLatest {}
        }
        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication)
        observeUiPreferences()
    }

    private fun observeUiPreferences() {
        viewModel.selectedTheme.observe(this) { uiMode ->
            uiMode?.let { ThemeHelper.applyTheme(it) }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.authNavHostNote).navigateUp()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}