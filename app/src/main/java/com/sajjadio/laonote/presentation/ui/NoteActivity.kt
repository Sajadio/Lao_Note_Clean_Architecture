package com.sajjadio.laonote.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.sajjadio.laonote.BR
import com.sajjadio.laonote.R
import com.sajjadio.laonote.data.local.data_storage.SessionManager
import com.sajjadio.laonote.databinding.ActivityNoteBinding
import com.sajjadio.laonote.presentation.ui.note.viewModel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteActivity : AppCompatActivity() {

    private var binding: ActivityNoteBinding? = null

    @Inject
    lateinit var sessionManager:SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note)

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