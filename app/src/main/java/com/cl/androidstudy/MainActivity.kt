package com.cl.androidstudy

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.cl.androidstudy.common.navigator.CustomNavigator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_icon_layout.*
import kotlinx.android.synthetic.main.me_icon_layout.*
import kotlinx.android.synthetic.main.navigation_icon_layout.*
import kotlinx.android.synthetic.main.system_icon_layout.*

class MainActivity : AppCompatActivity() {
    private var bottomState = true
    private var isExit = false
    private val exitHandler = android.os.Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT > 23) {
            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // 设置decorView的样式
            window.statusBarColor = Color.WHITE // 状态栏颜色
        }
        val destinationMap = mapOf(         // 简化重复代码, 可用于有大量重复的键值对数据
            R.id.home_dest to homeLayout,
            R.id.system_dest to systemLayout,
            R.id.navigation_dest to navigationLayout,
            R.id.me_dest to meLayout
        )

        val navController = Navigation.findNavController(this, R.id.fragment) // 创建navController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment)!!
        val navigator = CustomNavigator(
            this,
            navHostFragment.childFragmentManager,
            R.id.fragment
        )// 生成自定义Navigator对象
        navController.navigatorProvider.addNavigator("custom_fragment", navigator) // 添加 key, value
        navController.setGraph(R.navigation.my_nav)  // 要在 CustomNavigator 类被加载之后添加graph，不然找不到 custom_fragment节点
        destinationMap.forEach {           // 循环map中的entity
                map ->
            map.value.setOnClickListener {
                navController.navigate(map.key)  // 先获取节点名称，然后从hashmap中取出对应的navigator，最后调用navigator的navigate方法
            }
        }

        navController.addOnDestinationChangedListener { controller, destination, _ -> // 设置底部导航栏图标动画
            controller.popBackStack()
            destinationMap.forEach { map -> map.value.progress = 0f } // 循环设置 layout 状态为初始状态
//            when(destination.id) {
//                R.id.homeFragment -> homeLayout.transitionToEnd()
//                R.id.systemFragment -> systemLayout.transitionToEnd()
//                R.id.squareFragment -> squareLayout.transitionToEnd()
//                R.id.meFragment -> meLayout.transitionToEnd()
//            }
            destinationMap[destination.id]?.transitionToEnd() // 跳转对应 layout 状态至最终状态
        }


    }

    fun bottomAnimation(showBottom: Boolean) {
        if (bottomState == showBottom)      // 如果是连续上滑或者下滑，则只执行一次动画就可以了
            return
        if (showBottom) {       // 需要显示底部导航栏
            bottom_linearlayout.animate()   // 设置动画
                .translationY(0f)
                .duration = 200
            bottomState = showBottom
        } else {        // 不需要展示底部导航栏
            bottom_linearlayout.animate()
                .translationY(bottom_linearlayout.height.toFloat())
                .duration = 200
            bottomState = showBottom
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isExit) {
                finish()
            } else {
                Toast.makeText(this, "再次点击退出", Toast.LENGTH_SHORT).show()
                isExit = true
                val runnable = {
                    isExit = false
                }
                exitHandler.postDelayed(runnable, 2000)
            }
            return false
        }
        return super.onKeyDown(keyCode, event)
    }


}