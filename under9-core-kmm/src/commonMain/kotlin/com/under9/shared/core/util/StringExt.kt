package com.under9.shared.core.util

import kotlin.math.abs

val urlRegex = Regex("(http|https):\\/\\/[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&amp;:\\/~+#-]*[\\w@?^=%&amp;\\/~+#-])?")

inline fun String.convertCSVToStringArray(): Array<String> = split(",").toTypedArray()

// Ref:
// https://www.designedbyaturtle.co.uk/convert-string-to-hexidecimal-colour-with-javascript-vanilla/
/**
 * A simple method to hash a string to [Int]
 */
inline fun String.toSimpleStringHash(): Long {
    var hash = 0
    var i = 0
    val l = length
    while (i < l) {
        hash = this[i].code + ((hash shl 5) - hash)
        i++
    }
    return hash.toLong()
}

/**
 * This is used to generate an assigned slot for a string from hash
 * e.g. There are 100 colors, given a string, which slot it will be assigned to depends on the hash
 *
 * Hashing a [String] to [Int], then assign a modded value to it
 */
inline fun String.toHashedSlot(maxRange: Int): Int = abs(toSimpleStringHash() % maxRange).toInt()

/**
 * Convert a [List] to CSV
 */
inline fun List<String?>.convertToCSVString(): String = joinToString(",")

/**
 * Convert an [Array] to CSV
 */
inline fun Array<String?>.convertToCSVString(): String = joinToString(",")

/**
 * Find the start and end position of a given pattern
 * @return Pair of [Int], which indicates the start and end of a given pattern
 */
inline fun String.findFirstStartAndEndWithGivenPattern(regex: Regex): Pair<Int, Int>? {
    val matcher = regex.find(this)
    return matcher?.let { m ->
        val start = m.range.first
        val end = m.range.last
        return Pair(start, end)
    }
}

inline fun String.urlMatchFirstResult(): MatchResult? {
    return urlRegex.find(this)
}