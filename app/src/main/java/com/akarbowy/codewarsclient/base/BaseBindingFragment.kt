package com.akarbowy.codewarsclient.base

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akarbowy.codewarsclient.BR
import javax.inject.Inject


abstract class BaseBindingFragment<B : ViewDataBinding, V : BaseViewModel<*, *>> : BaseFragment() {

    var binding: B? = null
        private set

    var viewModel: V? = null
        internal set

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        initBinding(inflater, container)

        return binding?.root
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = DataBindingUtil.inflate(inflater, layout(), container, false)
        setBindingVariables(binding)
    }


    protected fun setBindingVariables(binding: B?) {
        binding?.setVariable(BR.viewModel, viewModel)
    }

    @LayoutRes
    protected abstract fun layout(): Int

    protected abstract fun viewModelClass(): Class<V>
}
