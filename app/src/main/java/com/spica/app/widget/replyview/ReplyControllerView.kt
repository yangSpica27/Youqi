package com.spica.app.widget.replyview

import android.animation.LayoutTransition
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import com.spica.app.databinding.ViewReplyControllerBinding
import com.spica.app.extensions.dp
import com.spica.app.extensions.hide
import com.spica.app.extensions.show
import io.noties.markwon.Markwon
import io.noties.markwon.editor.MarkwonEditor
import io.noties.markwon.editor.MarkwonEditorTextWatcher
import java.util.concurrent.Executors

@Suppress("unused")
class ReplyControllerView : LinearLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val markwon: Markwon by lazy {
        Markwon.create(context)
    }

    private val markwonEditor by lazy {
        MarkwonEditor.create(markwon)
    }

    private val _binding: ViewReplyControllerBinding

    private val binding get() = _binding

    init {
        orientation = VERTICAL
        _binding = ViewReplyControllerBinding.inflate(
            LayoutInflater.from(context),
            this
        )

        binding.chatEt
            .addTextChangedListener(
                MarkwonEditorTextWatcher.withPreRender(
                    markwonEditor,
                    Executors.newCachedThreadPool(),
                    binding.chatEt
                )
            )

        binding.chatEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (text.isNullOrEmpty()) {
                    initTransitions()
                    binding.chatImg.show()
                    binding.chatSend.hide()
                } else {
                    initTransitions()
                    binding.chatImg.hide()
                    binding.chatSend.show()
                }

            }

            override fun afterTextChanged(p0: Editable?) = Unit
        })
    }


    private fun initTransitions() {
        post {
            binding.sendLl.layoutTransition = createTransitions()
            binding.editLl.layoutTransition = createEditTransitions()
        }
    }


    @SuppressLint("ObjectAnimatorBinding")
    private fun createEditTransitions(): LayoutTransition {
        val scaleDownTransX = 24.dp
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            null as Any?,
            PropertyValuesHolder.ofFloat("scaleX", 1f, 0.3f),
            PropertyValuesHolder.ofFloat("scaleY", 1f, 0.3f),
            PropertyValuesHolder.ofFloat("alpha", 1f, 0f),
            PropertyValuesHolder.ofFloat("translationX", scaleDownTransX.toFloat())
        ).apply {
            duration = 50
            interpolator = DecelerateInterpolator()
        }

        val scaleUp = ObjectAnimator.ofPropertyValuesHolder(
            null as Any?,
            PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1f),
            PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1f),
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
            PropertyValuesHolder.ofFloat("translationX", 0f)
        ).apply {
            duration = 50
            interpolator = DecelerateInterpolator()
        }

        return getLayoutTransition(scaleUp, scaleDown)
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun createTransitions(): LayoutTransition {
        val scaleDownTransX = 24.dp
        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            null as Any?,
            PropertyValuesHolder.ofFloat("scaleX", 1f, 0.3f),
            PropertyValuesHolder.ofFloat("scaleY", 1f, 0.3f),
            PropertyValuesHolder.ofFloat("alpha", 1f, 0f),
            PropertyValuesHolder.ofFloat("translationX", -scaleDownTransX.toFloat())
        ).apply {
            duration = 350
            interpolator = DecelerateInterpolator()
        }

        val scaleUp = ObjectAnimator.ofPropertyValuesHolder(
            null as Any?,
            PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1f),
            PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1f),
            PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
            PropertyValuesHolder.ofFloat("translationX", 0f)
        ).apply {
            duration = 350
            interpolator = DecelerateInterpolator()
        }
        return getLayoutTransition(scaleUp, scaleDown)
    }


    private fun getLayoutTransition(
        scaleUp: ObjectAnimator,
        scaleDown: ObjectAnimator
    ): LayoutTransition {
        val layoutTransition = LayoutTransition()
        layoutTransition.setAnimator(LayoutTransition.APPEARING, scaleUp)
        layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, scaleDown)
        layoutTransition.setStartDelay(LayoutTransition.APPEARING, 0)
        layoutTransition.setStartDelay(LayoutTransition.DISAPPEARING, 0)
        layoutTransition.setDuration(LayoutTransition.CHANGE_APPEARING, 100)
        layoutTransition.setDuration(LayoutTransition.CHANGE_DISAPPEARING, 100)
        layoutTransition.setStartDelay(LayoutTransition.CHANGE_APPEARING, 0)
        layoutTransition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 0)
        return layoutTransition
    }


}