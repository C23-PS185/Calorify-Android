package com.calorify.app.customview

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.calorify.app.R

class CustomEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    when (inputType) {
                        InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS + 1 -> validateEmail(s.toString())
                        InputType.TYPE_TEXT_VARIATION_PASSWORD + 1 -> validatePass(s.toString())
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing
            }
        })
    }

    private fun validateEmail(text: String) {
        error = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            context.getString(R.string.email_et_invalid)
        } else {
            null
        }
    }

    private fun validatePass(text: String) {
        error = if (text.length < 8) {
            context.getString(R.string.pass_et_invalid)
        } else {
            null
        }
    }
}