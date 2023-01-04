package com.sajjadio.laonote.presentation.ui.note

import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.ItemNoteBinding
import com.sajjadio.laonote.domain.model.Note
import com.sajjadio.laonote.presentation.base.BaseAdapter
import javax.inject.Inject

class NoteAdapter @Inject constructor() : BaseAdapter<ItemNoteBinding, Note>() {
    override val layoutId = R.layout.item_note


    private var onItemClickListener: ((Note) -> Unit)? = null
    fun onItemClickListener(listener: (Note) -> Unit) {
        onItemClickListener = listener
    }

    override fun binder(binding: ItemNoteBinding, item: Note) {
        binding.apply {
            note = item
            parent.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
            executePendingBindings()
        }
    }

}