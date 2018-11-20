# LoopBanner
可以自动轮播的无限循环banner（An infinite loop banner that can be automatically rotated）

简介：这个自定义的控件，不采用给adapter的getCount设置int类型的最大值的方法达到无限循环，而是通过给数据源的头尾分别插入尾部和头部数据，一旦滑动到首位和末位就会跳转到对应的位置,
通过处理使跳转时的卡顿达到最小

Gradle:

implementation 'com.github.bigbobi:LoopBanner:1.0.1'

具体使用：
有空再补，可以先看看demo中的使用方式
