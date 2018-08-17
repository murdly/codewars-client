package com.akarbowy.codewarsclient.ui.search.router

import android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION
import com.akarbowy.codewarsclient.base.BaseRouter
import com.akarbowy.codewarsclient.ui.challenges.view.ChallengesActivity
import com.akarbowy.codewarsclient.ui.search.view.SearchActivity

const val EXTRA_USERNAME = "EXTRA_USERNAME"

class SearchRouterImpl(
        activity: SearchActivity
) : BaseRouter(activity), SearchRouter {

    override fun loadChallengesScreen(username: String) {
        val intent = getActivityIntent(ChallengesActivity::class.java)
        intent.putExtra(EXTRA_USERNAME, username)
        intent.addFlags(FLAG_ACTIVITY_NO_ANIMATION)
        loadActivity(intent)
    }

}