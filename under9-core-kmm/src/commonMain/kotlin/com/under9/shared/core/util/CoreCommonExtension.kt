package com.under9.shared.core.util

fun <T> ArrayDeque<T>.popN(range: Int) {
    val remain = this.drop(range)
    this.clear()
    this.addAll(remain)
}