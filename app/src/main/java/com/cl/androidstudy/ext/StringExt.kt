package com.cl.androidstudy.ext

import androidx.core.text.HtmlCompat

// 转换html文本为String
fun String.htmlToString() = HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)