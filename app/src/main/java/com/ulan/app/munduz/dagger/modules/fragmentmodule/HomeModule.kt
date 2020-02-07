package com.ulan.app.munduz.dagger.modules.fragmentmodule

import android.content.Context
import com.ulan.app.munduz.adapter.ProductAdapter
import com.ulan.app.munduz.dagger.scopes.MainScope
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.home.HomeFragment
import com.ulan.app.munduz.ui.home.HomePresenter
import com.ulan.app.munduz.ui.home.HomePresenterImpl
import com.ulan.app.munduz.ui.home.HomeView
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class HomeModule {

    @MainScope
    @Binds
    abstract fun homeView(homeFragment: HomeFragment): HomeView

    @MainScope
    @Binds
    abstract fun homePresenter(homePresenter: HomePresenterImpl): HomePresenter


    @Module
    companion object{

        @JvmStatic
        @Provides
        fun provideAdapter(context: Context, listener: OnItemClickListener): ProductAdapter {
            return ProductAdapter(context, listener)
        }

        @JvmStatic
        @Provides
        fun provideListener(homeFragment: HomeFragment): OnItemClickListener{
            return homeFragment
        }
    }

}