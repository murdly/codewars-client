package com.akarbowy.codewarsclient.ui.challenges.injection

import com.akarbowy.codewarsclient.base.ViewModelProviderFactory
import com.akarbowy.codewarsclient.data.repository.challenges.ChallengeRepository
import com.akarbowy.codewarsclient.ui.challenges.interactor.ChallengesInteractor
import com.akarbowy.codewarsclient.ui.challenges.interactor.ChallengesInteractorImpl
import com.akarbowy.codewarsclient.ui.challenges.router.ChallengesRouter
import com.akarbowy.codewarsclient.ui.challenges.router.ChallengesRouterImpl
import com.akarbowy.codewarsclient.ui.challenges.view.ChallengesActivity
import com.akarbowy.codewarsclient.ui.challenges.viewmodel.ChallengesViewModel
import dagger.Module
import dagger.Provides

@Module
class ChallengesModule {

    @Provides
    internal fun provideInteractor(repository: ChallengeRepository): ChallengesInteractor {
        return ChallengesInteractorImpl(repository)
    }

    @Provides
    internal fun provideRouter(activity: ChallengesActivity): ChallengesRouter {
        return ChallengesRouterImpl(activity)
    }

    @Provides
    internal fun provideViewModel(interactor: ChallengesInteractor, router: ChallengesRouter): ViewModelProviderFactory<ChallengesViewModel> {
        return object : ViewModelProviderFactory<ChallengesViewModel>() {
            override fun create() = ChallengesViewModel(interactor, router)
        }
    }
}