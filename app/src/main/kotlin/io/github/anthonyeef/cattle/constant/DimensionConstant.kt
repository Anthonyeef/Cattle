package io.github.anthonyeef.cattle.constant

import org.jetbrains.anko.dip

/**
 * Some constants for Dimension: size, padding and margin.
 */

val fontSizeNormal = 16f
val fontSizeMedium = 18f

val pageHorizontalPadding = dip(8)

val verticalPaddingNormal = dip(12)

/*------ private method ---------*/
private fun dip(value: Int) = app.dip(value)