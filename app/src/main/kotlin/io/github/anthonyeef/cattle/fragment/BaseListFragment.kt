package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.constant.app
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter
import org.jetbrains.anko.findOptional

/**
 *
 */
abstract class BaseListFragment : BaseFragment() {

    protected lateinit var adapter: MultiTypeAdapter
    protected lateinit var items: Items

    var list: RecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        items = Items()
        adapter = MultiTypeAdapter(items)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val root = inflater?.inflate(R.layout.fragment_list, container, false)
        list = root?.findOptional(android.R.id.list)
        list?.adapter = adapter
        list?.layoutManager = LinearLayoutManager(app)

        return root
    }
}