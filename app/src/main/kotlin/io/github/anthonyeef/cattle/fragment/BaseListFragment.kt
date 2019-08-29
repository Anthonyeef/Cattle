package io.github.anthonyeef.cattle.fragment

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import io.github.anthonyeef.cattle.R
import io.github.anthonyeef.cattle.constant.app
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter

/**
 *
 */
abstract class BaseListFragment : BaseFragment() {
  protected lateinit var adapter: MultiTypeAdapter
  protected lateinit var items: Items
  protected var swipeRefreshLayout: SwipeRefreshLayout? = null

  protected var list: RecyclerView? = null

  open fun customizeRecyclerView() { }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    items = Items()
    adapter = MultiTypeAdapter()
    adapter.items = items
  }


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    super.onCreateView(inflater, container, savedInstanceState)

    val root = inflater.inflate(R.layout.fragment_list, container, false)
    list = root?.findViewById(android.R.id.list)

    swipeRefreshLayout = root?.findViewById(R.id.swipe_refresh_layout)

    return root
  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    list?.adapter = adapter
    list?.layoutManager = LinearLayoutManager(app)

    customizeRecyclerView()
  }


  fun doScrollToTop() {
    list?.scrollToPosition(0)
  }


  fun disablePullToRefresh() {
    swipeRefreshLayout?.isEnabled = false
  }


  protected fun runLayoutAnimation() {
    val animation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom)
    list?.layoutAnimation = animation
    list?.scheduleLayoutAnimation()
  }
}