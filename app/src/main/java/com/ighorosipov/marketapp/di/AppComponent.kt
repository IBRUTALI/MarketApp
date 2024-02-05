package com.ighorosipov.marketapp.di

import android.app.Application
import com.ighorosipov.marketapp.presentation.MainActivity
import com.ighorosipov.marketapp.presentation.MainActivityViewModel
import com.ighorosipov.marketapp.presentation.account.AccountFragment
import com.ighorosipov.marketapp.presentation.account.AccountViewModel
import com.ighorosipov.marketapp.presentation.catalog.CatalogFragment
import com.ighorosipov.marketapp.presentation.catalog.CatalogViewModel
import com.ighorosipov.marketapp.presentation.favorite.FavoriteFragment
import com.ighorosipov.marketapp.presentation.favorite.FavoriteViewModel
import com.ighorosipov.marketapp.presentation.login.LoginFragment
import com.ighorosipov.marketapp.presentation.login.LoginViewModel
import com.ighorosipov.marketapp.presentation.main.MainFragment
import com.ighorosipov.marketapp.presentation.main.MainViewModel
import com.ighorosipov.marketapp.presentation.product.ProductFragment
import com.ighorosipov.marketapp.presentation.product.ProductViewModel
import com.ighorosipov.marketapp.presentation.tabs.TabsFragment
import com.ighorosipov.marketapp.presentation.tabs.TabsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, NetworkModule::class, RepositoryModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

    fun inject(mainActivity: MainActivity)
    fun inject(loginFragment: LoginFragment)
    fun inject(tabsFragment: TabsFragment)
    fun inject(mainFragment: MainFragment)
    fun inject(catalogFragment: CatalogFragment)
    fun inject(productFragment: ProductFragment)
    fun inject(favoriteFragment: FavoriteFragment)
    fun inject(accountFragment: AccountFragment)

    fun mainActivityViewModel(): MainActivityViewModel.Factory
    fun loginViewModel(): LoginViewModel.Factory
    fun tabsViewModel(): TabsViewModel.Factory
    fun mainViewModel(): MainViewModel.Factory
    fun catalogViewModel(): CatalogViewModel.Factory
    fun productViewModel(): ProductViewModel.Factory
    fun favoriteViewModel(): FavoriteViewModel.Factory
    fun accountViewModel(): AccountViewModel.Factory

}