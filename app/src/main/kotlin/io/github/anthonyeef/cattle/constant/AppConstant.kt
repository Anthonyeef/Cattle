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
val CONTAINER_ID = R.id.container
val TOOLBAR_ID = R.id.toolbar

/**
 * The date Fanfou come back.
 * 11/25/2010 21:30 PM
 */
val TIME_GOD_CREAT_LIGHT: Long = 1290763800000L