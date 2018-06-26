package com.siziksu.bluetooth.common.extension

import kotlin.math.PI

fun Float.toRadians(): Float {
    return (this / 180.0 * PI).toFloat()
}

fun Float.toDegrees(): Float {
    return (this * 180.0 / PI).toFloat()
}

fun Double.toRadians(): Double {
    return this / 180.0 * PI
}

fun Double.toDegrees(): Double {
    return this * 180.0 / PI
}
