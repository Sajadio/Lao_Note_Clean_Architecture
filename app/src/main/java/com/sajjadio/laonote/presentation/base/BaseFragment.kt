package com.sajjadio.laonote.presentation.base

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
import com.sajjadio.laonote.BR
import com.sajjadio.laonote.R
import com.sajjadio.laonote.presentation.ui.activities.AuthenticationActivity
import com.sajjadio.laonote.presentation.ui.activities.NoteActivity
import com.sajjadio.laonote.utils.NetworkResponse
import com.sajjadio.laonote.utils.extension.makeToast
import dmax.dialog.SpotsDialog

abstract class BaseFragment<VDB : ViewDataBinding, VM : BaseViewModel>(@LayoutRes private val layoutId: Int) :
    Fragment() {

    abstract val viewModelClass: Class<VM>
    private var _binding: VDB? = null
    val binding: VDB? get() = _binding
    lateinit var noteActivity: NoteActivity
    lateinit var authActivity: AuthenticationActivity
    lateinit var viewModel: VM
    lateinit var progressDialog: SpotsDialog


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
        initViewModel()
        initCastingActivity()
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        _binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.viewModel, viewModel)
            return root
        }
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = SpotsDialog(requireContext(), R.style.Theme_Dialog)
        launchView()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[viewModelClass]
    }

    abstract fun launchView()

    private fun initCastingActivity() {
        if (activity is NoteActivity)
            noteActivity = (activity as NoteActivity)
        else
            authActivity = (activity as AuthenticationActivity)
    }

    fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            execute()
        }
    }

    fun <T> checkResponseStatus(status: NetworkResponse<T>) = when (status) {
        is NetworkResponse.Loading -> {
            progressDialog.show()
            false
        }
        is NetworkResponse.Success -> {
            progressDialog.dismiss()
            true
        }
        is NetworkResponse.Error -> {
            progressDialog.dismiss()
            requireContext().makeToast(status.errorMessage.toString())
            false
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