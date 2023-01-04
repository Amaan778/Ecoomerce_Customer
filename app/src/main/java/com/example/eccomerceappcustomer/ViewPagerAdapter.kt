package com.example.eccomerceappcustomer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter

class ViewPagerAdapter(private var context: Context, private var viewPagerAdapter: List<Boarding> ): PagerAdapter() {
    override fun getCount(): Int {
        return viewPagerAdapter.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view= LayoutInflater.from(context).inflate(R.layout.boarding_screen,null)
        val imageview : ImageView
        val title: TextView

        imageview=view.findViewById(R.id.imageView)
        title=view.findViewById(R.id.title)

        imageview.setImageResource(viewPagerAdapter[position].imgaeuri)
        title.text=viewPagerAdapter[position].title

        container.addView(view)
        return view
    }
}