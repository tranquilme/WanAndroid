package com.cl.androidstudy.common.articleitem

/**
 *@author clll
 *@date   2021/3/9 17:53
 */
object CollectState {
    var state: Boolean = false
    val unCollectSet = HashSet<Int>() // 未收藏文章，~
    val collectSet = HashSet<Int>()  // 已收藏的文章，该变量主要用于 解决热门文章的收藏状态问题
}