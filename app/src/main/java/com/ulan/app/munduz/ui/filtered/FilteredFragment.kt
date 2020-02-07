package com.ulan.app.munduz.ui.filtered

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.ulan.app.munduz.R
import com.ulan.app.munduz.adapter.ProductAdapter
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants
import com.ulan.app.munduz.helpers.Constants.Companion.CATEGORY_ARG
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.filtered_layout.*
import javax.inject.Inject

class FilteredFragment : BaseFragment(), FilteredView,  OnItemClickListener{

    @Inject
    lateinit var mPresenter: FilteredPresenter

    @Inject
    lateinit var mAdapter: ProductAdapter

    private lateinit var mCategory: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filtered_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mCategory = arguments!!.getString(CATEGORY_ARG)
        mPresenter.loadProductsByCategory(mCategory)
    }

    override fun showToolbar(){
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.GONE
        val toolbar = activity.findViewById<androidx.appcompat.widget.Toolbar>(R.id.main_toolbar)
        val textToolbar = toolbar.findViewById<TextView>(R.id.main_toolbar_text)
        textToolbar.text = mCategory
    }

    override fun showEmptyData() {
        empty_filter_catalog.visibility = View.VISIBLE
    }

    override fun showProducts(products: MutableList<Product>) {
        val layoutManager = GridLayoutManager(activity, 2)
        filter_recycler_view.layoutManager = layoutManager
        mAdapter.setProducts(products)
        filter_recycler_view.adapter = mAdapter
    }

    override fun onItemClick(product: Product?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(Constants.PRODUCT_ARG, product)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    companion object{
        fun newInstance(category: String): FilteredFragment {
            val fragment = FilteredFragment()
            val args = Bundle()
            args.putString(CATEGORY_ARG, category)
            fragment.arguments = args
            return fragment
        }
    }
}