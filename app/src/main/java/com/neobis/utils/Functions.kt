package com.neobis.utils

import android.text.Editable
import com.google.android.material.textfield.TextInputEditText


fun TextInputEditText.setTextCorrectly(text: CharSequence) {
    setText(text)
    setSelection(text.length)
}


fun TextInputEditText.listenChanges(block: (text: String) -> Unit) {
    addTextChangedListener(object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            block.invoke(s.toString())
        }
    })
}