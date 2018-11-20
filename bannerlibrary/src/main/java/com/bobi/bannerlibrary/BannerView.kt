package com.bobi.bannerlibrary

import android.content.Context
import android.os.Handler
import android.os.Message
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.view_viewpage.view.*
import java.lang.ref.WeakReference

class BannerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
    var originPosition = 1//初始viewpager要定位的位置
    private var pageHandler = PageHandler(this)
    private var marginStart=0
    private var marginEnd=0

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var rootView: View = inflater.inflate(R.layout.view_viewpage, this)
        val attr = context.obtainStyledAttributes(attrs, R.styleable.BannerView)
        originPosition=if(attr.getBoolean(R.styleable.BannerView_singleModel,true)) 1 else 2
        marginStart=attr.getDimensionPixelSize(R.styleable.BannerView_marginStart,0)
        marginEnd=attr.getDimensionPixelSize(R.styleable.BannerView_marginEnd,0)
        var params=pager.layoutParams as LinearLayout.LayoutParams
        params.leftMargin=marginStart
        params.rightMargin=marginEnd
        requestLayout()
        initListener()
    }

    private fun initListener() {
        var index = -1
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
                if (state != ViewPager.SCROLL_STATE_IDLE) {
                    pageHandler.removeMessages(pageHandler.nextPage)//当还处于滑动中时，取消发送轮询消息
                    if (index <= originPosition - 1 || index >= pager.adapter!!.count - originPosition) {//如果当前的位置小于等于实际上的第一个item的位置或者大于实际上的最后一个item的位置就执行跳转
                        if (index <=  originPosition-1) {
                            pager.setCurrentItem(pager.adapter!!.count - (originPosition + 1), false)//该跳转方法是不会有跳转动画执行的
                        } else if (index >= pager.adapter!!.count - originPosition) {
                            pager.setCurrentItem(originPosition, false)
                        }
                    }
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                sendLoopMsg()
                index = position
            }

        })
    }

    fun getPager():ViewPager{
        return pager
    }

    fun setAdapter(adapter: BannerAdapter<*>) {
        pager.adapter = adapter
        pager.currentItem = originPosition
        pager.offscreenPageLimit = if (adapter.count >= 3) 3 else adapter.count
    }

    private fun loopView() {
        if (pager.currentItem < pager.adapter!!.count - 1) {
            pager.currentItem = pager.currentItem + 1
        }
        sendLoopMsg()
    }

    private fun sendLoopMsg() {
        val msg = Message.obtain()
        msg.what = pageHandler.nextPage
        pageHandler.sendMessageDelayed(msg, 3000)
    }

    fun start() {
        if (!pageHandler.isStart) {
            pageHandler.isStart = true
            sendLoopMsg()
        }
    }

    /**
     * 通过弱引用的方式防止内存泄露
     */
    private class PageHandler(view: View) : Handler() {
        val nextPage = 1//轮询发送的消息
        var isStart: Boolean = false
        private val weakView: WeakReference<View> = WeakReference(view)

        override fun handleMessage(msg: Message) {
            if (weakView.get() == null) {
                return
            }
            val view = weakView.get()
            when (msg.what) {
                nextPage ->
                    if (isStart) {
                        (view as BannerView).loopView()
                    }
            }
        }
    }
}