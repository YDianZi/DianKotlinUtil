package com.dian.mylibrary.widget.textview

import android.app.AlertDialog
import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.dian.mylibrary.R
import com.dian.mylibrary.utils.ktx.SP
import com.dian.mylibrary.utils.ktx.jsonToList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/11/6 9:25 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/11/6 9:25 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
typealias OnSearchListener = (String) -> Unit

class AutoCompleteSearchEditText(
    context: Context,
    attrs: AttributeSet?
) : CompoundClickAutoCompleteTextView(context, attrs) {

    private var onSearchListener: OnSearchListener? = null
    private var SEARCH_HISTORY_KEY: String = "search_history_key"
    private lateinit var searchHistoryAdapter: SearchHistoryAdapter
    private var canClear = false

    /**
     * 动态设置历史记录key
     *
     * @param SEARCH_HISTORY_KEY
     */
    fun setSEARCH_HISTORY_KEY(SEARCH_HISTORY_KEY: String) {
        this.SEARCH_HISTORY_KEY = SEARCH_HISTORY_KEY
        //设置后初始化数据
        initAutoSearchHistory()
    }

    fun setOnSearchListener(onSearchListener: OnSearchListener?) {
        this.onSearchListener = onSearchListener
    }

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.AutoCompleteSearchEditText, 0, 0)
        SEARCH_HISTORY_KEY =
            typedArray.getString(R.styleable.AutoCompleteSearchEditText_search_history_key)
                .toString()
        canClear =
            typedArray.getBoolean(R.styleable.AutoCompleteSearchEditText_search_can_clear, false)
        typedArray.recycle()
        setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0)
        addTextChangedListener(object : OnTextChangeListener() {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                if (canClear) {
                    val s1 = text.toString()
                    if (TextUtils.isEmpty(s1)) {
                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search, 0, 0, 0)
                    } else {
                        setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_search,
                            0,
                            R.drawable.ic_clear,
                            0
                        )
                    }
                }
            }
        })
        isSingleLine = true
        imeOptions = EditorInfo.IME_ACTION_SEARCH
        setOnDrawableRightListener { v, right -> setText("") }
        setOnDrawableLeftListener { v, left ->
            if (onSearchListener != null) {
                val searchKey = text.toString().trim()
                onSearchListener?.let { it(searchKey) }
                //开始搜索 保存历史数据 关闭历史搜索下拉框
                saveSearchHistory(searchKey)
                dismissDropDown()
            }
        }
        setOnEditorActionListener { v, actionId, event ->
            if (actionId === EditorInfo.IME_ACTION_SEARCH) {
                val imm = v.context
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                val searchKey = text.toString().trim()
                onSearchListener?.let { it(searchKey) }
                //开始搜索  保存历史数据  关闭历史搜索下拉框
                saveSearchHistory(searchKey)
                dismissDropDown()
            }
            false
        }

        //初始化自动填充的账号输入框数据
        initAutoSearchHistory()
    }

    /**
     * 保存搜索历史记录
     *
     * @param searchKey
     */
    fun saveSearchHistory(searchKey: String) {
        if (TextUtils.isEmpty(searchKey)) return
        //保存搜索历史记录
        val searchHistories: String = SP.getData(SEARCH_HISTORY_KEY, "")
        var searchHistoryList: MutableList<String> =
            ArrayList()
        if (!TextUtils.isEmpty(searchHistories)) {
            searchHistoryList = Gson().fromJson(
                searchHistories,
                object : TypeToken<List<String?>?>() {}.type
            )
        }
        if (!searchHistoryList.contains(searchKey)) {
            searchHistoryList.add(searchKey)
        } else {
            searchHistoryList.remove(searchKey)
            searchHistoryList.add(0, searchKey)
        }
        searchHistoryAdapter?.setDatas(searchHistoryList)
        searchHistoryAdapter?.notifyDataSetChanged()
        SP.putData(mapOf(SEARCH_HISTORY_KEY to searchHistoryList))
    }

    /**
     * 初始化自动填充的账号输入框数据
     * android:completionThreshold="1"  打一个字就找
     */
    private fun initAutoSearchHistory() {
        val searchHistories: String = SP.getData(SEARCH_HISTORY_KEY, "")
        val searchHistoryList: List<String> = searchHistories.jsonToList()
        searchHistoryAdapter =
            SearchHistoryAdapter(searchHistoryList as MutableList<String>, context)
        searchHistoryAdapter.setOnItemClickListener { data, position, view ->
            val strData = data as String
            if ("清除历史记录" == strData) {
                AlertDialog.Builder(context)
                    .setMessage("确定清除历史记录？")
                    .setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
                    .setPositiveButton("确定") { dialog, which ->
                        dialog.dismiss()
                        SP.removeData(SEARCH_HISTORY_KEY)
                        searchHistoryAdapter?.setDatas(null)
                    }.show()
            } else {
                setText(strData)
                setSelection(strData.length)
            }
            dismissDropDown()
        }
        setAdapter(searchHistoryAdapter)
    }
}