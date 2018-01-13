package io.github.anthonyeef.cattle.viewbinder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.entity.BottomRefreshEntity
import me.drakeet.multitype.ItemViewBinder
import org.jetbrains.anko.findOptional

/**
 *
 */
class BottomRefreshViewProvider : ItemViewBinder<BottomRefreshEntity, BottomRefreshViewProvider.BottomRefreshViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): BottomRefreshViewHolder {
        val root = inflater.inflate(R.layout.view_bottom_refresh, parent, false)
        return BottomRefreshViewHolder(root)
    }

    override fun onBindViewHolder(holder: BottomRefreshViewHolder, t: BottomRefreshEntity) {
        holder.setBottomRefreshErrorHint(t.errorHint)
    }

    inner class BottomRefreshViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val hint: TextView? = itemView.findOptional<TextView>(R.id.bottom_error)
        fun setBottomRefreshErrorHint(errorHint: String) {
            hint?.text = errorHint
        }
    }
}