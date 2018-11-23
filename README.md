# LoopBanner
可以自动轮播的无限循环banner（An infinite loop banner that can be automatically rotated）

简介：这个自定义的控件，不采用给adapter的getCount设置int类型的最大值的方法达到无限循环，而是通过给数据源的头尾分别插入尾部和头部数据，一旦滑动到首位和末位就会跳转到对应的位置,
通过处理使跳转时的卡顿达到最小

Gradle:

implementation 'com.github.bigbobi:LoopBanner:1.0.2.1'

具体使用：

1、布局中的使用

<com.bobi.bannerlibrary.BannerView

        android:id="@+id/bvTest"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:marginEnd="40dp"
        app:marginStart="40dp"
        app:singleModel="false" />

singleModel属性代表是否显示多个item

marginStart和marginEnd代表左右2边分别展示多少内容

2、代码中使用

创建Adapter
![image](https://github.com/bigbobi/LoopBanner/blob/master/%E5%88%9B%E5%BB%BAAdapter.png)

就酱,收工
