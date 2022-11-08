package com.sajjadio.laonote.presentation.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.google.android.material.transition.MaterialFadeThrough
import com.sajjadio.laonote.presentation.ui.NoteActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment<VDB : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
    Fragment() {

    private var _binding: VDB? = null
    val binding: VDB? get() = _binding
    lateinit var noteActivity: NoteActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialFadeThrough().apply {
            duration = TRANSITION_DURATION
        }
        enterTransition = MaterialFadeThrough().apply {
            duration = TRANSITION_DURATION
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        initCastingActivity()
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        _binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            return root
        }
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchView()
    }


    abstract fun launchView()

    private fun initCastingActivity() {
        noteActivity = (activity as NoteActivity)
    }

    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            execute()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TRANSITION_DURATION = 200L
        const val TRANSITION_ELEMENT_ROOT = "transition:root:"
    }

}