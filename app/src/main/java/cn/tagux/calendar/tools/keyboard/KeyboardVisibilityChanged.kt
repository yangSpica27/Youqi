package cn.tagux.calendar.tools.keyboard

data class KeyboardVisibilityChanged(
  val visible: Boolean,
  val contentHeight: Int,
  val contentHeightBeforeResize: Int
)