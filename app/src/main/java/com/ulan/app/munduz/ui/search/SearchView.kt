package com.ulan.app.munduz.ui.search

import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.ui.base.BaseView

interface SearchView : BaseView {

    fun showProducts(products: MutableList<Product>)

}