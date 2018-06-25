package com.siziksu.bluetooth.ui.view.custom

class RoundedViewMaths {

    private var startDegree: Int = 0
    private var movingDegree: Int = 0
    private var origin: Int = 0
    private var value: Int = 0

    fun value(): Int {
        if (startDegree in 110..430 && movingDegree in 110..430) {
            value = origin + movingDegree - startDegree
            value = if (value <= 0) 0 else value
            value = if (value >= 300) 300 else value
        }
        return value
    }

    fun onActionDown(dx: Float, dy: Float) {
        startDegree = Math.toDegrees(Math.atan2(dy.toDouble(), dx.toDouble())).toInt()
        startDegree = if (startDegree < 0) startDegree + 360 else startDegree
        startDegree = if (startDegree in 0..60) startDegree + 360 else startDegree
        movingDegree = startDegree
        origin = value
    }

    fun onActionMove(dx: Float, dy: Float) {
        if (startDegree in 110..430) {
            movingDegree = Math.toDegrees(Math.atan2(dy.toDouble(), dx.toDouble())).toInt()
            movingDegree = if (movingDegree < 0) movingDegree + 360 else movingDegree
            movingDegree = if (movingDegree in 0..60) movingDegree + 360 else movingDegree
        }
    }

    fun update(value: Int) {
        this.value = (value / 0.4233333333333333f).toInt()
    }
}
