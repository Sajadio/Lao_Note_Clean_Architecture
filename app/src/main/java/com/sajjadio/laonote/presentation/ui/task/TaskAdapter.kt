package com.sajjadio.laonote.presentation.ui.task

import com.sajjadio.laonote.R
import com.sajjadio.laonote.databinding.ItemTaskBinding
import com.sajjadio.laonote.domain.model.Task
import com.sajjadio.laonote.presentation.base.BaseAdapter
import javax.inject.Inject

class TaskAdapter @Inject constructor() : BaseAdapter<ItemTaskBinding, Task>() {
    override val layoutId = R.layout.item_task


    private var onItemClickListener: ((Task) -> Unit)? = null
    fun onItemClickListener(listener: (Task) -> Unit) {
        onItemClickListener = listener
    }

    override fun binder(binding: ItemTaskBinding, item: Task) {
        binding.apply {
            task = item
            detailsLayout.setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
            executePendingBindings()
        }
    }

}