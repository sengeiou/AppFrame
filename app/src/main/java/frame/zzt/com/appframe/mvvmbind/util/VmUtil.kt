package frame.zzt.com.appframe.mvvmbind.util

/**
 * 这种方法可以直接使用的布局种，用来展示显示信息
 * 使用：
 *  <data>
 *      <import type="frame.zzt.com.appframe.mvvmbind.util.VmUtilKt" />
 *  </data>
 *
 *         <TextView
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="@{ VmUtilKt.showClickCount( vmData.clickCount) }" />

 *
 */
fun showClickCount(count: Int): String {
    return "点击的数量：${count} "
}

