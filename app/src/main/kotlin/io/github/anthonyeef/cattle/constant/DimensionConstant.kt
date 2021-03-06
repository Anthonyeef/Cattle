package io.github.anthonyeef.cattle.constant

import io.github.anthonyeef.cattle.utils.dp2px

/**
 * Some constants for Dimension: size, padding and margin.
 */

val fontSizeNormal = 16f
val fontSizeMedium = 18f

val pageHorizontalPadding = dip(8)

val horizontalPaddingNormal = dip(12)
val horizontalPaddingMedium = dip(16)

val verticalPaddingNormal = dip(12)
val verticalPaddingMedium = dip(16)
val verticalPaddingLarge = dip(20)

val paddingMedium = dip(16)

val OPERATION_BTN_SIZE_SMALL = dip(16)

/*------ private method ---------*/
fun dip(value: Int) = dp2px(app, value)