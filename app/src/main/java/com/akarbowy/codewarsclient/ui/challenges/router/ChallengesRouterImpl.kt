package com.akarbowy.codewarsclient.ui.challenges.router

import com.akarbowy.codewarsclient.base.BaseRouter
import com.akarbowy.codewarsclient.ui.challenges.view.ChallengesActivity
import com.akarbowy.codewarsclient.ui.detail.view.ChallengeDetailActivity

const val EXTRA_CHALLENGE_ID = "EXTRA_CHALLENGE_ID"

class ChallengesRouterImpl(
        activity: ChallengesActivity
) : BaseRouter(activity), ChallengesRouter {

    override fun loadChallengeScreen(challengeId: String) {
        val intent = getActivityIntent(ChallengeDetailActivity::class.java)
        intent.putExtra(EXTRA_CHALLENGE_ID, challengeId)
        loadActivity(intent)
    }

}