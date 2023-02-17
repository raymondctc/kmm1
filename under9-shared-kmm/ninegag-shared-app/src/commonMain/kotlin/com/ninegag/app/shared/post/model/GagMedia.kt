package com.ninegag.app.shared.post.model

data class GagMedia(
    val width: Int = 0,
    val height: Int = 0,
    val mediaType: Int = 0,
    val videoSourceType: Int = 0,
    val startTs: Long = 0,
    val endTs: Long = 0,
    val postID: String? = null,
    val url: String? = null,
    val webpUrl: String? = null,
    val videoId: String? = null
) {

    companion object {
        const val MEDIA_TYPE_460S = 0
        const val MEDIA_TYPE_700B = 1
        const val MEDIA_TYPE_460SA = 2
        const val MEDIA_TYPE_700BA = 3
        const val MEDIA_TYPE_VIDEO = 4
        const val MEDIA_TYPE_460C = 5
        const val MEDIA_TYPE_FB_THUMBNAIL = 6
        const val MEDIA_TYPE_460SV = 7

        const val VIDEO_SOURCE_STR_YOUTUBE = "YouTube"
        const val VIDEO_SOURCE_YOUTUBE = 0

        fun toYoutubeUrl(videoId: String) = "https://www.youtube.com/watch?v=$videoId"
    }

    fun videoUrl(): String? {
        return if (videoSourceType == VIDEO_SOURCE_YOUTUBE && videoId != null) {
            toYoutubeUrl(videoId)
        } else null
    }

    fun isYoutube(): Boolean {
        return mediaType == MEDIA_TYPE_VIDEO && videoSourceType == VIDEO_SOURCE_YOUTUBE
    }


    fun map(videoSource: String?): Int {
        return if (VIDEO_SOURCE_STR_YOUTUBE.equals(
                videoSource,
                ignoreCase = true
            )
        ) VIDEO_SOURCE_YOUTUBE else -1
    }
}