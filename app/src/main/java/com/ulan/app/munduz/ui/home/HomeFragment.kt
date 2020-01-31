package com.ulan.app.munduz.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.ulan.app.munduz.R
import com.ulan.app.munduz.adapter.ProductAdapter
import com.ulan.app.munduz.adapter.SliderAdapter
import com.ulan.app.munduz.data.model.SliderImage
import com.ulan.app.munduz.developer.Product
import com.ulan.app.munduz.helpers.Constants.Companion.PRODUCT_ARG
import com.ulan.app.munduz.listeners.OnItemClickListener
import com.ulan.app.munduz.ui.base.BaseFragment
import com.ulan.app.munduz.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.home_layout.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), HomeView, OnItemClickListener {

    @Inject
    lateinit var mPresenter: HomePresenter

    @Inject
    lateinit var mAdapter: ProductAdapter

    lateinit var handler: Handler
    private var page = 0
    private var delay: Long = 5000L
    private val runnable = object : Runnable{
        override fun run() {
            if(mAdapter.itemCount == page){
                page = 0
            }else{
                page++
            }
            view_pager.setCurrentItem(page, true)
            handler.postDelayed(this, delay)
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_layout, container, false)
    }

    override fun showToolbar(){
        val activity = (activity as AppCompatActivity)
        activity.findViewById<LinearLayout>(R.id.search_layout).visibility = View.VISIBLE
        activity.supportActionBar?.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = Handler()

        mPresenter.setToolbar()
        mPresenter.loadProducts()
        mPresenter.loadSliderImages()
    }

    override fun showProgress() {
        home_progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        home_progress_bar.visibility = View.GONE
    }

    override fun showProducts(products: MutableList<Product>) {
        val layoutManager = GridLayoutManager(activity, 2)
        home_recycler_view.layoutManager = layoutManager
        mAdapter.setProducts(products)
        mAdapter.setItemClickListener(this)
        home_recycler_view.adapter = mAdapter
    }

    override fun showNoProducts() {
        showNoProducts()
    }

    override fun showSliderImages(images: ArrayList<SliderImage>) {
        val sliderAdapter = SliderAdapter(activity!!.applicationContext, images)
        view_pager.adapter = sliderAdapter
        Log.d("ulanbek", "Slider adapter size  " + sliderAdapter.count.toString())
        view_pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                page = position
            }

        })
    }

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onItemClick(product: Product?) {
        val intent = Intent(activity, DetailsActivity::class.java)
        intent.putExtra(PRODUCT_ARG, product)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, delay)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }
}