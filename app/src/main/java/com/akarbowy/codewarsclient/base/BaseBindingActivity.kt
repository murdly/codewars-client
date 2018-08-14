package com.akarbowy.codewarsclient.base

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import com.akarbowy.codewarsclient.BR
import javax.inject.Inject


abstract class BaseBindingActivity<B : ViewDataBinding, V : BaseViewModel<*, *>> : BaseActivity() {

    var binding: B? = null
        private set

    var viewModel: V? = null
        private set

    @Inject
    lateinit var viewModelFactory: ViewModelProviderFactory<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass())

        initBinding()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, layout())
        setBindingVariables(binding)
    }

    private fun setBindingVariables(binding: B?) {
        binding?.setVariable(BR.viewModel, viewModel)
    }

    @LayoutRes
    protected abstract fun layout(): Int

    protected abstract fun viewModelClass(): Class<V>

}
