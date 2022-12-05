package com.sajjadio.laonote.presentation.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sajjadio.laonote.utils.ParentListAdapter

abstract class BaseAdapter<BINDING : ViewDataBinding, T : ParentListAdapter>:
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder<BINDING>>() {
    @get:LayoutRes
    abstract val layoutId: Int
    abstract fun binder(binding: BINDING, item: T)

    private val differ = AsyncListDiffer(this@BaseAdapter, DifferCallbacks)
    open fun updateItems(items:List<ParentListAdapter>){
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BaseViewHolder(
        DataBindingUtil.inflate<BINDING>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: BaseViewHolder<BINDING>, position: Int) {
        binder(holder.binder, differ.currentList[position] as T)
    }

    override fun getItemCount(): Int = differ.currentList.size

    object DifferCallbacks : DiffUtil.ItemCallback<ParentListAdapter>() {
        override fun areItemsTheSame(
            oldItem: ParentListAdapter,
            newItem: ParentListAdapter,
        ) =
            oldItem.item == newItem.item

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: ParentListAdapter,
            newItem: ParentListAdapter,
        ) =
            oldItem == newItem
    }

    class BaseViewHolder<BINDING : ViewDataBinding>(val binder: BINDING) :
        RecyclerView.ViewHolder(binder.root)
}