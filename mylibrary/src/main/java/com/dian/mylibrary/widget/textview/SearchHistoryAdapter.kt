package com.dian.mylibrary.widget.textview

import android.R
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.dian.mylibrary.utils.ktx.L
import java.util.ArrayList

/**
 *
 * @Description: java类作用描述
 * @Author: Little
 * @CreateDate: 2020/11/6 10:14 AM
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/11/6 10:14 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
typealias onItemClickListener = (Any, Int, View?) -> Unit

class SearchHistoryAdapter(
    datas: MutableList<String>?,
    context: Context?
) : BaseAdapter(), Filterable {
    private var datas: MutableList<String>? = null
    private var context: Context? = null
    private var newDatas: ArrayList<String>? = ArrayList()
    private var onItemClickListener: onItemClickListener? = null

    init {
        this.datas = datas
        this.context = context
    }

    fun setDatas(datas: MutableList<String>?) {
        this.datas = datas
    }

    fun setOnItemClickListener(click: onItemClickListener) {
        onItemClickListener = click
    }

    override fun getCount(): Int {
        return if (newDatas != null) newDatas!!.size else 0
    }

    override fun getItem(position: Int): String? {
        if (newDatas != null) newDatas!![position]
        return ""
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        val str = newDatas!![position]
        val textView = View.inflate(context, R.layout.simple_list_item_1, null) as TextView
        textView.text = str
        textView.setOnClickListener { v: View? ->
            onItemClickListener?.let {
                it(
                    str,
                    position,
                    textView
                )
            }
        }
        return textView
    }


    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                L.d("constraint===$constraint")
                val results = FilterResults()
                if (datas == null) {
                    results.count = 0
                    results.values = null
                    return results
                }
                var newData: MutableList<String> =
                    ArrayList()
                if (constraint != null) {
                    for (data in datas!!) {
                        if (data.contains(constraint)) {
                            newData.add(data)
                        }
                    }
                    if (newData.size > 0) newData.add("清除历史记录")
                } else {
                    newData = datas as MutableList<String>
                }
                results.values = newData
                results.count = newData.size
                return results
            }

            override fun publishResults(
                constraint: CharSequence?,
                results: FilterResults
            ) {
                newDatas = if (results.values != null) {
                    results.values as ArrayList<String>
                }else{
                    ArrayList()
                }
                notifyDataSetChanged()
            }
        }
    }
}