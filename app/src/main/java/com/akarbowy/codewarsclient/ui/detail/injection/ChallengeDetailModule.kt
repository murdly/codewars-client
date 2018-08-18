package com.akarbowy.codewarsclient.ui.detail.injection

import com.akarbowy.codewarsclient.base.ViewModelProviderFactory
import com.akarbowy.codewarsclient.data.repository.challenges.ChallengeRepository
import com.akarbowy.codewarsclient.ui.detail.interactor.ChallengeDetailInteractor
import com.akarbowy.codewarsclient.ui.detail.interactor.ChallengeDetailInteractorImpl
import com.akarbowy.codewarsclient.ui.detail.router.ChallengeDetailRouter
import com.akarbowy.codewarsclient.ui.detail.router.ChallengeDetailRouterImpl
import com.akarbowy.codewarsclient.ui.detail.view.ChallengeDetailActivity
import com.akarbowy.codewarsclient.ui.detail.viewmodel.ChallengeDetailViewModel
import dagger.Module
import dagger.Provides

@Module
class ChallengeDetailModule {

    @Provides
    internal fun provideInteractor(repository: ChallengeRepository): ChallengeDetailInteractor {
        return ChallengeDetailInteractorImpl(repository)
    }

    @Provides
    internal fun provideRouter(activity: ChallengeDetailActivity): ChallengeDetailRouter {
        return ChallengeDetailRouterImpl(activity)
    }

    @Provides
    internal fun provideViewModel(interactor: ChallengeDetailInteractor, router: ChallengeDetailRouter): ViewModelProviderFactory<ChallengeDetailViewModel> {
        return object : ViewModelProviderFactory<ChallengeDetailViewModel>() {
            override fun create() = ChallengeDetailViewModel(interactor, router)
        }
    }

}