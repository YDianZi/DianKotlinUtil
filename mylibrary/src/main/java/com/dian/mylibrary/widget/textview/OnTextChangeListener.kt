package com.dian.mylibrary.widget.textview

import android.text.Editable
import android.text.TextWatcher

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/11/6 10:12 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/11/6 10:12 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
open class OnTextChangeListener : TextWatcher{
    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }
}