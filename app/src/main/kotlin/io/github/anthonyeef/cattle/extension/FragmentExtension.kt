package io.github.anthonyeef.cattle.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import io.github.anthonyeef.cattle.activity.GeneralActivity
import io.github.anthonyeef.cattle.constant.app
import io.github.anthonyeef.cattle.fragment.FragmentInstanceCache

/**
 *
 */
fun <T: Fragment> T.show(fragment: Fragment) {
    if (fragment.activity.isFragmentContainer()) {
        fragment.activity.showFragment(this)
    }
}

inline fun <reified T : Activity> Fragment.navigateTo() {
    val intent = Intent(this.context, T::class.java)
    startActivity(intent)
}

fun Fragment.show(tintStatusBar: Boolean = true) {
  FragmentInstanceCache.put(this)
    val intent = Intent(app, GeneralActivity::class.java)
    intent.putExtra(GeneralActivity.KEY_FRAG, this.getKey())
    intent.putExtra(GeneralActivity.KEY_TINT_STATUS_BAR, tintStatusBar)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    app.startActivity(intent)
}

fun Fragment.getKey(): String {
  return String.format("%s@%s", this.javaClass.simpleName, this.hashCode())
}

fun Fragment.withArguments(args: Pair<String, String?>): Fragment {
    val bundle = Bundle()
    bundle.putString(args.first, args.second)
    this.arguments = bundle
    return this
}

