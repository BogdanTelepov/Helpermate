package com.neobis.utils

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

class MyClickableSpan(private val lambda: (view: View) -> Unit) : ClickableSpan() {

    override fun onClick(widget: View) = lambda.invoke(widget)
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = true
    }
}