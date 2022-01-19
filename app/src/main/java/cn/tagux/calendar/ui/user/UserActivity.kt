package cn.tagux.calendar.ui.user

import android.view.LayoutInflater
import cn.tagux.calendar.base.BindingActivity
import cn.tagux.calendar.databinding.ActivityUserBinding
/**
 * 用户信息编辑页面
 */
class UserActivity : BindingActivity<ActivityUserBinding>() {

    override fun initializer() {
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityUserBinding =
        ActivityUserBinding.inflate(inflater)
}
