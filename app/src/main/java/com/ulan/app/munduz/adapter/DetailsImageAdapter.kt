package com.ulan.app.munduz.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ulan.app.munduz.R
import com.ulan.app.munduz.data.models.Picture
import com.ulan.app.munduz.helpers.Constants

class DetailsImageAdapter: PagerAdapter {

    private var context: Context
    private var images : MutableList<Picture>

    constructor(context: Context, images: MutableList<Picture>) : super() {
        this.context = context
        this.images = images
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater= context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_slider, null)
        val image = view.findViewById<ImageView>(R.id.item_image)
        val progress = view.findViewById<ProgressBar>(R.id.item_progress_bar)
        val picture = images.get(position)
        progress.visibility = View.VISIBLE
        Picasso.get()
            .load(picture.urlImage)
            .fit()
            .into(image, object : Callback {
                override fun onSuccess() {
                    progress.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    image.setImageResource(R.drawable.ic_error_image_black_24dp)
                    Log.e(Constants.TAG, "Error loading image")
                }

            })

        val viewPager = container as ViewPager
        viewPager.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }

}