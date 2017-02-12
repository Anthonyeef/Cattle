package io.github.anthonyeef.cattle.constant

import io.github.anthonyeef.cattle.App
import io.github.anthonyeef.cattle.R

/**
 * Application singleton
 */
val app = App.get()
val bus = App.getRxBusSingleton()

/**
 * General viewGroup id to hold fragment
 */
val CONTAINER_ID = 110
val TOOLBAR_ID = R.id.toolbar
