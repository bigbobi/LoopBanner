package com.bobi.bannerlibrary

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup


abstract class BannerAdapter<T> : PagerAdapter {
    var context: Context
    private var data = mutableListOf<T>()

    constructor(context: Context) {
        this.context = context
    }

    constructor(context: Context, data: MutableList<T>,bannerView: BannerView) {
        this.context = context
        this.data=fixData(data,bannerView)
    }

    fun getItemData(position: Int): T? {
        return if (position < this.data.size) {
            data[position]
        } else null
    }

    fun setData(data: MutableList<T>, bannerView: BannerView) {
        this.data = fixData(data,bannerView)
        notifyDataSetChanged()
    }

    fun getList(): List<T> {
        return ArrayList(data)
    }

    fun fixData(data: MutableList<T>, bannerView: BannerView):MutableList<T>{
        if (bannerView.originPosition==1){
            data.add(0, data[data.size - 1])
            data.add(data[1])
        }else{
            data.add(0, data[data.size - 1])
            data.add(0, data[data.size - 2])
            data.add(data[2])
            data.add(data[3])
        }
        return data
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getCount(): Int {
        return if (data.isEmpty()) 0 else data.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if (`object` is View)
            container.removeView(`object`)
    }

}
