package com.hdd.globalmovie.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide

class BookCoverAdapter(private val BookCoverList: ArrayList<String>): PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
       val bookCover = ImageView(container.context)
        Glide.with(container.context)
            .load(BookCoverList[position])
            .into(bookCover)
        val vp = container as ViewPager
        vp.addView(bookCover, 0)
        return bookCover
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ImageView)
    }
    override fun getCount(): Int {
        return BookCoverList.size
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }
}

