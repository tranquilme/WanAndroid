package com.cl.androidstudy.common.navigator

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.FragmentNavigator

@Navigator.Name("custom_fragment")  // 节点名称
class CustomNavigator(
    private val context: Context,
    private val manager: FragmentManager,
    private val containerId: Int
) : FragmentNavigator(context, manager, containerId) {

    override fun navigate(  // 重写 navigate 方法
        destination: Destination,
        args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ): NavDestination? {
        val tag = destination.id.toString() // 跳转目的地
        val transaction = manager.beginTransaction() // 开启fragment事务

        val currentFragment = manager.primaryNavigationFragment // navigation 顶层fragment
        if (currentFragment != null) {
            transaction.hide(currentFragment)   // 隐藏当前fragment
        }

        var fragment = manager.findFragmentByTag(tag)   // 找到目的地fragment
        if (fragment == null) { // fragment未被初始化
            val className = destination.className
            fragment = manager.fragmentFactory.instantiate(context.classLoader, className) // 实例化 fragment
            transaction.add(containerId, fragment, tag) // 将碎片添加到容器中
        } else { // fragment 已经初始化过了
            transaction.show(fragment) // 显示fragment
        }

        transaction.setPrimaryNavigationFragment(fragment) //将fragment 设置为顶层fragment
        transaction.setReorderingAllowed(true)
        transaction.commitNow() // 提交事务

        return destination // 返回目的地，用于监听
    }
}