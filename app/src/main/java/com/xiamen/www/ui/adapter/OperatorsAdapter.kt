package com.xiamen.www.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xiamen.www.R
import com.xiamen.www.bean.OperatorModel

/**
 * Created by admin on 2018/2/1.
 */

abstract class OperatorsAdapter(data: MutableList<OperatorModel>?) : BaseQuickAdapter<OperatorModel, BaseViewHolder>(R.layout.layout_item_operator, data) {

    override fun convert(holder: BaseViewHolder, item: OperatorModel?) {
        if (item != null) {
            holder.setText(R.id.item_title, item.title)
                    .setText(R.id.item_des, item.des)
                    .getConvertView().setOnClickListener { onItemClick(holder.adapterPosition) }
        }
    }

    abstract fun onItemClick(position: Int)
}
