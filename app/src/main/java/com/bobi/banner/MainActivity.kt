package com.bobi.banner

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.bobi.bannerlibrary.BannerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_test.view.*

class MainActivity : AppCompatActivity() {

    private var testList= mutableListOf(0,1,2,3,4,5)//测试用的数据源

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        bvTest.setAdapter(object : BannerAdapter<Int>(this, testList, bvTest) {
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val v = View.inflate(context, R.layout.item_test, null)// your item layout
                v.tvTest.text = getItemData(position)!!.toString()
                container.addView(v)
                return v
            }
        })

    }
}
