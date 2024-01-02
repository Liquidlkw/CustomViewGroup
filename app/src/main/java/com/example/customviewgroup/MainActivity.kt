package com.example.customviewgroup

import android.graphics.Rect
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.Method

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        /**
         * 获取屏幕的宽/高(屏幕展示高度)：
         * 注：
         * 如果手机没有底部虚拟导航栏则：此高度==屏幕完整高度
         * 如果手机有底部虚拟导航栏则：此高度==屏幕完整高度-导航栏高度
         *
         * 方法一
         */
        var dm1 = DisplayMetrics()
        this.getWindowManager().getDefaultDisplay().getMetrics(dm1)
        val screenW = dm1.widthPixels
        val screenH = dm1.heightPixels

        // /**
        //  * 方法二
        //  */
        // val windowManager = mActivity.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        // windowManager.defaultDisplay.getMetrics(dm)
        // val width = dm.widthPixels
        // val height = dm.heightPixels
        //
        // /**
        //  * 方法三
        //  */
        // val width2: Int = mActivity.getResources().getDisplayMetrics().widthPixels
        // val height2: Int = mActivity.getResources().getDisplayMetrics().heightPixels
        //
        // /**
        //  * 方法四
        //  */
        // val width3: Int = appContext.getResources().getDisplayMetrics().widthPixels
        // val height3: Int = appContext.getResources().getDisplayMetrics().heightPixels
        //
        // /**
        //  * 方法五
        //  */
        // val windowManager = appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        // windowManager.defaultDisplay.getMetrics(dm)
        // val width = dm.widthPixels
        // val height = dm.heightPixels

        //以上五种方法中：
        //方法一二三是通过Activity获取当前APP的宽高。
        //方法四五是通过ApplicationContext来获取屏幕的宽高。
        //两者区别：
        //当处于分屏模式时，前者只是当前分屏APP的宽高。
        //后者是这个设备屏幕宽高。

        //注：
        //某些情况下（具体原因待研究）带有底部虚拟导航栏的设备--在使用方法三四五获取屏幕展示高度时
        //不包含状态栏，此时可以可以换用方法一二来获取。
        // /**
        //  * 获取屏幕完整高度(屏幕展示高度+导航栏)[针对具有导航栏的手机]
        //  * 方法一
        //  */
        var realScreenH = 0
        if (VERSION.SDK_INT >= VERSION_CODES.JELLY_BEAN_MR1) {
            getWindowManager().defaultDisplay.getRealMetrics(dm1)
            realScreenH = dm1.heightPixels
        }
        /**
         * 方法二：通过反射获取
         */
        // val display: Display = this.getWindowManager().getDefaultDisplay()
        // val dm = DisplayMetrics()
        //  var realScreenH = 0
        // val c = Class.forName("android.view.Display")
        // val method: Method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
        // method.invoke(display, dm)
        // realScreenH = dm.heightPixels
        /**
         * 状态栏高度
         */
        val frame = Rect()
        window.decorView.getWindowVisibleDisplayFrame(frame)
        val statusBarHeight: Int = frame.top

        /**
         * 标题栏高度
         */
        val v: View = window.findViewById(Window.ID_ANDROID_CONTENT)
        val contentTop: Int = v.getTop()
        val titleBarHeight = contentTop - statusBarHeight

        /**
         * 获取虚拟导航栏高度
         */
        val navigationBarH = realScreenH - screenH
        Log.d("lkw",
            """
            screenW:$screenW
            screenH:$screenH
            realScreenH:$realScreenH
            statusBarHeight:$statusBarHeight
            titleBarHeight:$titleBarHeight
            navigationBarH:$navigationBarH
            """.trimIndent()
        )
    }
}