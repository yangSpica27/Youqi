package com.spica.app.widget.replyview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewAnimationUtils
import android.widget.LinearLayout
import androidx.core.widget.doOnTextChanged
import com.spica.app.databinding.ViewReplyControllerBinding
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


        binding.chatEt.doOnTextChanged { text, _, _, _ ->
           run {
                if (text.isNullOrEmpty()) {
                    binding.chatSend.hide()
                } else {
                    binding.chatSend.show()
                }
            }
        }


    }







}