package com.akarbowy.codewarsclient.ui.detail.view

import android.os.Bundle
import com.akarbowy.codewarsclient.R
import com.akarbowy.codewarsclient.base.BaseBindingActivity
import com.akarbowy.codewarsclient.databinding.ActivityChallengeDetailBinding
import com.akarbowy.codewarsclient.ui.challenges.router.EXTRA_CHALLENGE_ID
import com.akarbowy.codewarsclient.ui.detail.viewmodel.ChallengeDetailViewModel
import kotlinx.android.synthetic.main.activity_challenge_detail.*

class ChallengeDetailActivity : BaseBindingActivity<ActivityChallengeDetailBinding, ChallengeDetailViewModel>() {

    override fun layout() = R.layout.activity_challenge_detail

    override fun viewModelClass() = ChallengeDetailViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
    }

    private fun initUi() {
        setUpToolbar()

        val challengeId = intent.getStringExtra(EXTRA_CHALLENGE_ID)

        challengeId?.let {
            viewModel?.start(it)
        }
    }

    private fun setUpToolbar() {
        toolbar_detail.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar_detail.setNavigationOnClickListener { finish() }
    }

    override fun onPause() {
        super.onPause()

        setExitAndEnterAnimation()
    }

    private fun setExitAndEnterAnimation() {
        if (isFinishing) {
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }


}
