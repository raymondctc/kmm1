package com.under9.shared.ads

import io.github.aakira.napier.Napier
import kotlin.math.abs

/**
 * Assume only apply list banner ads
 *
 * Given the current position, the class will define what action
 * that receive need to handle
 * */
class AdsActionController(
        private val adsActionListener: AdsActionListener,
        private val preloadDistance: Int = AD_PRELOAD_DISTANCE,
        private val retainDistance: Int = AD_RETAIN_DISTANCE
) {

    // We can decided which ad type to retain in the cache
    // HashMap <adType, position>
    private val adTypeLastShowPosition = HashMap<Int, Int>()

    private val TAG = "preloadAdsFlow"
    private val TAG_PRELOAD_ADS = "CheckPreloadAds"
    private val TAG_CACHE = "CheckCache"

    private val initialAdPositions: ArrayList<Int> = ArrayList()
    private var repeatingOccurrence = 0

    private var triggeredPreloadAdsPosition = -1
    // detect scrolling direction
    private var isScrollDown = true
    private var lastCheckPosition = -1

    companion object {
        const val AD_RETAIN_DISTANCE = 3
        const val AD_PRELOAD_DISTANCE = 2
    }

    // TODO migrate to use kmm to fetch network config
    fun init(adsOccurrences: String) {
        val parsed: Array<Array<String>> = parseAdTokens(adsOccurrences)
        val tokens = parsed[0]
        val parsedInitialOccurrences = parsed[1]
        val initialOccurrences: ArrayList<Int> = ArrayList()

        for (value in parsedInitialOccurrences) {
            initialOccurrences.add(value.toInt())
        }
        // get the last item as a repeated occurrence
        repeatingOccurrence = tokens[tokens.size - 1].toInt()

        initialOccurrences.forEachIndexed { index, adInterval ->
            // Formula = P + N = ad position, which is pattern interval, N is inserted ad number
            initialAdPositions.add(adInterval + (index + 1))
        }
    }

    private fun parseAdTokens(occurrences: String?): Array<Array<String>> {
        return if (occurrences == null) {
            arrayOf(arrayOf(""), arrayOf(""))
        } else try {
            val tokens: Array<String> = occurrences.split("...").toTypedArray()
            val initialOccurrences: Array<String> = if (tokens[0].contains(",")) {
                tokens[0].split(",").toTypedArray()
            } else {
                arrayOf(tokens[0])
            }
            arrayOf(tokens, initialOccurrences)
        } catch (e: Exception) {
            Napier.e("error", e, tag = TAG)
            arrayOf(arrayOf(""), arrayOf(""))
        }
    }

    fun checkAction(currPos: Int) {
        if (initialAdPositions.size <= 0) return
        isScrollDown = currPos - lastCheckPosition >= 0
        // check for cached ads:

        // if current position match the preload ad position,
        // means the ad should visible in that ad placement
        // than put in to the map to track the position that it need to remove
        if (triggeredPreloadAdsPosition == currPos) {
            adTypeLastShowPosition[AdType.TYPE_LIST_BANNER_AD] = currPos
        } else {
            val toRemoveAdTypes = ArrayList<Int>()
            var shouldClean = false
            for ((adType, lastCachedPosition) in adTypeLastShowPosition) {
                Napier.d("map: key=$adType, currPos=${currPos}, lastCachedPosition=$lastCachedPosition", tag = TAG_CACHE)
                if (abs(currPos - lastCachedPosition) >= retainDistance) {
                    Napier.d("clean", tag = TAG_CACHE)
                    toRemoveAdTypes.add(adType)
                    shouldClean = true
                    adsActionListener.onRemoveFromCache(lastCachedPosition)
                }
            }

            if (shouldClean) {
                Napier.d("should remove")
                toRemoveAdTypes.forEach {
                    val iterator = adTypeLastShowPosition.iterator()
                    while (iterator.hasNext()) {
                        val adType = iterator.next().key
                        Napier.d("adType: adType=$adType, iterator.next().key=${adType}", tag = TAG_CACHE)
                        if (adType == it) {
                            iterator.remove()
                        }
                    }
                }
            }
        }
        // check for preload ads:

        // case 1 for scroll down
        val nextPos = if (isScrollDown) currPos + 1 else currPos - 1
        val checkDistance = if (isScrollDown) {
            nextPos until nextPos + preloadDistance
        } else {
            if (nextPos - preloadDistance >= 0) {
                nextPos downTo nextPos - preloadDistance
            } else {
                nextPos downTo 0
            }
        }

        for (i in checkDistance) {
//            Napier.d("currPos=${currPos}, i=${i}, previously triggeredPreloadAdsPosition=${triggeredPreloadAdsPosition}, isAdOccurrence(i)=${isAdOccurrence(i)}", tag = TAG_PRELOAD_ADS)
            if (isAdOccurrence(i) && triggeredPreloadAdsPosition != i) {
                triggeredPreloadAdsPosition = i
                adsActionListener.onPreloadAds(triggeredPreloadAdsPosition)
                Napier.d("should display ads in position=$i ", tag = TAG_PRELOAD_ADS)
            }
        }

        lastCheckPosition = currPos
    }

    private fun isAdOccurrence(position: Int): Boolean {
        if (initialAdPositions.size <= 0) return false

        if (initialAdPositions.contains(position)) {
            return true
        }

        val lastInitialAdPosition: Int = initialAdPositions[initialAdPositions.size - 1]
        if (position < lastInitialAdPosition) {
            return false
        }

        // reporting occurrence + 1 is for the ad
        val isMatch = (position - lastInitialAdPosition) % (repeatingOccurrence + 1) == 0
        return isMatch
    }
}

interface AdsActionListener {

    fun onPreloadAds(preloadAdPosition: Int)

    // return the position to remove ad(s)
    fun onRemoveFromCache(lastCachedPosition: Int)
}