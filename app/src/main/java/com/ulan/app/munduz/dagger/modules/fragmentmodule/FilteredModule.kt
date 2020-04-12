package com.ulan.app.munduz.dagger.modules.fragmentmodule

import android.content.Context
import com.ulan.app.munduz.adapter.ProductsAdapter
import com.ulan.app.munduz.dagger.modules.RoomModule
import com.ulan.app.munduz.dagger.scopes.FilteredScope
import com.ulan.app.munduz.data.firebase.FirebaseRepository
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.filtered.FilteredFragment
import com.ulan.app.munduz.ui.filtered.FilteredPresenter
import com.ulan.app.munduz.ui.filtered.FilteredPresenterImpl
import com.ulan.app.munduz.ui.filtered.FilteredView
import dagger.Module
import dagger.Provides

@Module(includes = [RoomModule::class])
class FilteredModule {

    @FilteredScope
    @Provides
    fun filteredView(filteredFragment: FilteredFragment): FilteredView{
        return filteredFragment
    }

    @FilteredScope
    @Provides
    fun filteredPresenter(filteredView: FilteredView, repository: FirebaseRepository): FilteredPresenter{
        return FilteredPresenterImpl(filteredView, repository)
    }

    @FilteredScope
    @Provides
    fun clickListener(filteredFragment: FilteredFragment):OnItemClickListener{
        return filteredFragment
    }

    @FilteredScope
    @Provides
    fun productsAdapter(context: Context, clickListener: OnItemClickListener): ProductsAdapter{
        return ProductsAdapter(context, clickListener)
    }

}