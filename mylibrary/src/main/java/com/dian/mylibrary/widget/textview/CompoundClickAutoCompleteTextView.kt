package com.dian.mylibrary.widget.textview

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatAutoCompleteTextView

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/11/6 9:20 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/11/6 9:20 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * textview上下左右图标可单独点击
 */
typealias OnDrawableRightListener = (Any, Any) -> Unit
typealias OnDrawableLeftListener = (Any, Any) -> Unit
typealias OnDrawableListener = (Any, Any) -> Unit

open class CompoundClickAutoCompleteTextView(
    context: Context,
    attrs: AttributeSet?
) : AppCompatAutoCompleteTextView(context, attrs) {
    /**
     * TextView四周drawable的序号。
     * 0 left,  1 top, 2 right, 3 bottom
     */
    private val LEFT = 0
    private val RIGHT = 2
    private var onDrawableListener: OnDrawableListener? = null
    private var onDrawableRightListener: OnDrawableRightListener? = null
    private var onDrawableLeftListener: OnDrawableLeftListener? = null

    fun setOnDrawableListener(onDrawableListener: OnDrawableListener?) {
        this.onDrawableListener = onDrawableListener
    }

    fun setOnDrawableRightListener(onDrawableRightListener: OnDrawableRightListener) {
        this.onDrawableRightListener = onDrawableRightListener
    }

    fun setOnDrawableLeftListener(onDrawableLeftListener: OnDrawableLeftListener) {
        this.onDrawableLeftListener = onDrawableLeftListener
    }


    init {
        @SuppressLint("ClickableViewAccessibility")
        val mOnTouchListener = OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> {
                    val drawableLeft =
                        compoundDrawables[LEFT]
                    if (drawableLeft != null) {
                        val width = drawableLeft.bounds.width()
                        val left = left
                        val rawX = event.rawX
                        if (event.rawX <= getLeft() + drawableLeft.bounds.width()) {
                            onDrawableListener?.let {
                                it(
                                    v,
                                    drawableLeft
                                )
                            }
                            onDrawableLeftListener?.let {
                                it(
                                    v,
                                    drawableLeft
                                )
                            }
                            return@OnTouchListener false
                        }
                    }
                    val drawableRight =
                        compoundDrawables[RIGHT]
                    if (drawableRight != null) {
                        val width1 = drawableRight.bounds.width()
                        val right = right
                        if (event.rawX >= getRight() - drawableRight.bounds.width()) {
                            onDrawableListener?.let {
                                it(
                                    v,
                                    drawableRight
                                )
                            }
                            onDrawableRightListener?.let {
                                it(
                                    v,
                                    drawableRight
                                )
                            }
                            return@OnTouchListener false
                        }
                    }
                }
            }
            false
        }
        setOnTouchListener(mOnTouchListener)
    }

}