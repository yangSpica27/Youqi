package com.spica.app.widget.replyview

import android.content.Context
import android.graphics.Color
import android.text.Spanned
import android.text.style.CharacterStyle
import android.text.style.ForegroundColorSpan
import android.util.ArraySet
import android.util.AttributeSet
import androidx.core.widget.addTextChangedListener
import java.util.regex.Pattern

class MentionEditText : ContentEditText {


    private val MENTION_COLOR by lazy { Color.parseColor("#5FA7E4") }

    private val mentionNumberPattern: Pattern by lazy {
        Pattern.compile("@[\\d]{4,}")
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    val mentionSet = ArraySet<String>()

    init {
        addTextChangedListener { renderMention() }
    }

    private fun renderMention() {
        if (text.isNullOrBlank()) return
        val matcher = mentionNumberPattern.matcher(text)
        val spansToRemove: Array<Any> = text?.getSpans(
            0, text?.length ?: 0,
            Any::class.java
        ) as Array<Any>
        for (span in spansToRemove) {
            if (span is CharacterStyle) text?.removeSpan(span)
        }
        while (matcher.find()) {
            if (mentionSet.contains(matcher.group().substring(1))) {
                matcher.start()
                text?.setSpan(
                    ForegroundColorSpan(MENTION_COLOR),
                    matcher.start(),
                    matcher.end(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }
}