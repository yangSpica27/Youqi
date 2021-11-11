package com.spica.app.ui.blacfragemnt

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.spica.app.R
import com.spica.app.base.BaseLayout

class BlankFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return EmptyView(requireContext())
  }


  private class EmptyView(context: Context) : BaseLayout(context) {

    init {
      val lp = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
      layoutParams = lp
      setBackgroundColor(getContext().getColor(R.color.black))
    }

    private val emptyText = TextView(context).apply {
      text = "暂无"
      setTextColor(getContext().getColor(R.color.black))
      autoAddView(MATCH_PARENT, WRAP_CONTENT)
    }


    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
      emptyText.layoutCenter()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
      super.onMeasure(widthMeasureSpec, heightMeasureSpec)
      autoMeasure(emptyText)
    }
  }

}

