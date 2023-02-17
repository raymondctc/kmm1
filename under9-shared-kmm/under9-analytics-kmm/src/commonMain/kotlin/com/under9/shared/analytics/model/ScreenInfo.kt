package com.under9.shared.analytics.model

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

/**
 * A parcelable to store current screen's information
 *
 * @param name Name of the screen
 * @param triggerAction Name of the trigger action
 * @param triggerPosition Name of the trigger position, where the action is triggered from
 * @constructor Create a screen information
 */
@Parcelize
data class ScreenInfo(
    val name: String,
    val triggerAction: String? = null,
    val triggerPosition: String? = null
): Parcelable

