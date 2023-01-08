package com.sajjadio.laonote.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.sajjadio.laonote.R
import com.sajjadio.laonote.data.local.data_storage.SessionManager
import com.sajjadio.laonote.databinding.ActivityNoteBinding
import com.sajjadio.laonote.presentation.ui.settings.SettingsViewModel
import com.sajjadio.laonote.utils.ThemeHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteActivity : AppCompatActivity() {

    private var binding: ActivityNoteBinding? = null
    val viewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note)
        observeUiPreferences()
    }

    private fun observeUiPreferences() {
        viewModel.selectedTheme.observe(this) { uiMode ->
            uiMode?.let { ThemeHelper.applyTheme(it) }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        findNavController(R.id.navHostNote).navigateUp()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}