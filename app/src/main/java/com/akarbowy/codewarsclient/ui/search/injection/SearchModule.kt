package com.akarbowy.codewarsclient.ui.search.injection

import com.akarbowy.codewarsclient.base.ViewModelProviderFactory
import com.akarbowy.codewarsclient.data.users.repository.UserRepository
import com.akarbowy.codewarsclient.ui.search.interactor.SearchInteractor
import com.akarbowy.codewarsclient.ui.search.interactor.SearchInteractorImpl
import com.akarbowy.codewarsclient.ui.search.router.SearchRouter
import com.akarbowy.codewarsclient.ui.search.router.SearchRouterImpl
import com.akarbowy.codewarsclient.ui.search.view.SearchActivity
import com.akarbowy.codewarsclient.ui.search.viewmodel.SearchViewModel
import dagger.Module
import dagger.Provides

@Module
class SearchModule {

    @Provides
    internal fun provideInteractor(userRepository: UserRepository): SearchInteractor {
        return SearchInteractorImpl(userRepository)
    }

    @Provides
    internal fun provideRouter(activity: SearchActivity): SearchRouter {
        return SearchRouterImpl(activity)
    }

    @Provides
    internal fun provideViewModel(interactor: SearchInteractor, router: SearchRouter): ViewModelProviderFactory<SearchViewModel> {
        return object : ViewModelProviderFactory<SearchViewModel>() {
            override fun create() = SearchViewModel(interactor, router)
        }
    }
}