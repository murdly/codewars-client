package com.akarbowy.codewarsclient.ui.challenges.view

import android.os.Bundle
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.base.BaseBindingActivity
import com.akarbowy.codewarsclient.databinding.ActivityChallengesBinding
import com.akarbowy.codewarsclient.ui.challenges.viewmodel.ChallengesViewModel
import com.akarbowy.codewarsclient.ui.search.router.EXTRA_USERNAME
import kotlinx.android.synthetic.main.activity_challenges.*

class ChallengesActivity : BaseBindingActivity<ActivityChallengesBinding, ChallengesViewModel>() {

    override fun layout() = R.layout.activity_challenges

    override fun viewModelClass() = ChallengesViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    private fun initUi() {
        setUpToolbar()

        val username = intent.getStringExtra(EXTRA_USERNAME)

        username?.let {
            viewModel?.start(it)
        }
    }

    private fun setUpToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener { finish() }
    }

    override fun onPause() {
        super.onPause()

        setExitAndEnterAnimation()
    }

    private fun setExitAndEnterAnimation() {
        if (isFinishing) {
            overridePendingTransition(0, R.anim.slide_out_right)
        } else {
            overridePendingTransition(0, 0)
        }
    }

}
