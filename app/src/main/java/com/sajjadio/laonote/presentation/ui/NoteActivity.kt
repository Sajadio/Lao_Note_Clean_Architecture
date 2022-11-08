package com.sajjadio.laonote.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.ActivityNoteBinding
import com.sajjadio.laonote.presentation.ui.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteActivity : AppCompatActivity() {

    private var binding: ActivityNoteBinding? = null
    val viewModel: NoteViewModel by viewModels()

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