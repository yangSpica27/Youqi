package com.spica.app.ui.mine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.LinearLayout
import coil.load
import com.spica.app.R
import com.spica.app.databinding.ViewSettingItemBinding

class SettingItem : LinearLayout {

  private var viewBinding: ViewSettingItemBinding

  constructor(context: Context?) : super(context)
  constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
    initView(attrs)
  }

  constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  ) {
    initView(attrs)
  }

  init {
    inflate(context, R.layout.view_setting_item, this)
    viewBinding = ViewSettingItemBinding.bind(this)
  }

  private fun initView(attrs: AttributeSet) {
    val array: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingItem)
    viewBinding.tvTitle.text = array.getText(R.styleable.SettingItem_name)
    viewBinding.image.load(array.getDrawable(R.styleable.SettingItem_image_src))
    array.recycle()
  }


}