@file:Suppress("unused")
package cn.tagux.calendar.initializer

import android.content.Context
import androidx.startup.Initializer
import com.kongzue.dialogx.DialogX

class DialogXInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        DialogX.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
