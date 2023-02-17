package com.ninegag.app.shared.domain.report

import com.ninegag.app.shared.analytics.MixpanelV2
import kotlin.jvm.JvmStatic

/***
 * The display order to report code mapping is based on:
 * https://docs.google.com/spreadsheets/d/1Y0YG1pdjmEIxIRRoW4BA67IcFwCnCwabakAN2V875kw/edit#gid=1019251018
 */
object ReportCodeConverter {
    /**
     * Post report codes
     */
    const val REPORT_CODE_POST_SPAM = 2
    const val REPORT_CODE_POST_PORNOGRAPHY = 3
    const val REPORT_CODE_POST_HATRED_AND_BULLYING = 5
    const val REPORT_CODE_POST_SELF_HARM= 6
    const val REPORT_CODE_POST_VIOLENT_GORY_AND_HARMFUL_CONTENT = 7
    const val REPORT_CODE_POST_CHILD_PORN= 8
    const val REPORT_CODE_POST_ILLEGAL_ACTIVITIES = 9
    const val REPORT_CODE_POST_DECEPTIVE_CONTENT = 10
    const val REPORT_CODE_POST_COPYRIGHT_AND_TRADEMARK_INFRINGEMENT = 1
    const val REPORT_CODE_POST_INCORRECT_TAG = 14

    /**
     * Comment report codes
     */
    const val REPORT_CODE_COMMENT_SPAM = 2
    const val REPORT_CODE_COMMENT_PORNOGRAPHY = 3
    const val REPORT_CODE_COMMENT_HATRED_AND_BULLYING = 5
    const val REPORT_CODE_COMMENT_VIOLENT_GORY_AND_HARMFUL_CONTENT = 7
    const val REPORT_CODE_COMMENT_CHILD_PORN = 8
    const val REPORT_CODE_COMMENT_ILLEGAL_ACTIVITIES = 9
    const val REPORT_CODE_COMMENT_SELF_HARM = 6
    const val REPORT_CODE_COMMENT_IMPERSONATION = 11
    const val REPORT_CODE_COMMENT_I_DONT_LIKE_THIS= 12

    /**
     * Profile report codes
     */
    const val REPORT_CODE_PROFILE_INAPPROPRIATE_USERNAME_PROFILE_PICTURE= 13
    const val REPORT_CODE_PROFILE_SPAM = 2
    const val REPORT_CODE_PROFILE_PORNOGRAPHY = 3
    const val REPORT_CODE_PROFILE_HATRED_AND_BULLYING = 5
    const val REPORT_CODE_PROFILE_SELF_HARM = 6
    const val REPORT_CODE_PROFILE_VIOLENT_GORY_AND_HARMFUL_CONTENT = 7
    const val REPORT_CODE_PROFILE_CHILD_PORN = 8
    const val REPORT_CODE_PROFILE_ILLEGAL_ACTIVITIES = 9
    const val REPORT_CODE_PROFILE_DECEPTIVE_CONTENT = 10
    const val REPORT_CODE_PROFILE_IMPERSONATION = 11
    const val REPORT_CODE_PROFILE_COPYRIGHT_AND_TRADEMARK_INFRINGEMENT = 1
    const val REPORT_CODE_PROFILE_I_DONT_LIKE_THIS = 12

    fun convertPostReportCodeToMixpanelString(reportCode: ReportCode): String {
        return when (reportCode) {
            REPORT_CODE_POST_SPAM -> MixpanelV2.Report.Reason.Values.Code2Spam
            REPORT_CODE_POST_PORNOGRAPHY -> MixpanelV2.Report.Reason.Values.Code3Pornography
            REPORT_CODE_POST_HATRED_AND_BULLYING -> MixpanelV2.Report.Reason.Values.Code5HatredBullying
            REPORT_CODE_POST_SELF_HARM -> MixpanelV2.Report.Reason.Values.Code6SelfHarm
            REPORT_CODE_POST_VIOLENT_GORY_AND_HARMFUL_CONTENT -> MixpanelV2.Report.Reason.Values.Code7Violent
            REPORT_CODE_POST_CHILD_PORN -> MixpanelV2.Report.Reason.Values.Code8ChildPorn
            REPORT_CODE_POST_ILLEGAL_ACTIVITIES -> MixpanelV2.Report.Reason.Values.Code9IllegalActivities
            REPORT_CODE_POST_DECEPTIVE_CONTENT -> MixpanelV2.Report.Reason.Values.Code10DeceptiveContent
            REPORT_CODE_POST_COPYRIGHT_AND_TRADEMARK_INFRINGEMENT -> MixpanelV2.Report.Reason.Values.Code1Copyright
            REPORT_CODE_POST_INCORRECT_TAG -> MixpanelV2.Report.Reason.Values.Code14IncorrectTag
            else -> error("Unknown post report code, reportCode=$reportCode")
        }
    }

    fun convertCommentReportCodeToMixpanelString(reportCode: ReportCode): String {
        return when (reportCode) {
            REPORT_CODE_COMMENT_SPAM -> MixpanelV2.Report.Reason.Values.Code2Spam
            REPORT_CODE_COMMENT_HATRED_AND_BULLYING -> MixpanelV2.Report.Reason.Values.Code5HatredBullying
            REPORT_CODE_COMMENT_PORNOGRAPHY -> MixpanelV2.Report.Reason.Values.Code3Pornography
            REPORT_CODE_COMMENT_VIOLENT_GORY_AND_HARMFUL_CONTENT -> MixpanelV2.Report.Reason.Values.Code7Violent
            REPORT_CODE_COMMENT_CHILD_PORN -> MixpanelV2.Report.Reason.Values.Code8ChildPorn
            REPORT_CODE_COMMENT_ILLEGAL_ACTIVITIES -> MixpanelV2.Report.Reason.Values.Code9IllegalActivities
            REPORT_CODE_COMMENT_SELF_HARM -> MixpanelV2.Report.Reason.Values.Code6SelfHarm
            REPORT_CODE_COMMENT_IMPERSONATION -> MixpanelV2.Report.Reason.Values.Code11Impersonation
            REPORT_CODE_COMMENT_I_DONT_LIKE_THIS -> MixpanelV2.Report.Reason.Values.Code12Dislike
            else -> error("Unknown comment report code, reportCode=$reportCode")
        }
    }

    fun convertProfileReportCodeToMixpanelString(reportCode: ReportCode): String {
        return when (reportCode) {
            REPORT_CODE_PROFILE_INAPPROPRIATE_USERNAME_PROFILE_PICTURE -> MixpanelV2.Report.Reason.Values.Code13InappropriateUserInfo
            REPORT_CODE_PROFILE_SPAM -> MixpanelV2.Report.Reason.Values.Code2Spam
            REPORT_CODE_PROFILE_PORNOGRAPHY -> MixpanelV2.Report.Reason.Values.Code3Pornography
            REPORT_CODE_PROFILE_HATRED_AND_BULLYING -> MixpanelV2.Report.Reason.Values.Code5HatredBullying
            REPORT_CODE_PROFILE_SELF_HARM -> MixpanelV2.Report.Reason.Values.Code6SelfHarm
            REPORT_CODE_PROFILE_VIOLENT_GORY_AND_HARMFUL_CONTENT -> MixpanelV2.Report.Reason.Values.Code7Violent
            REPORT_CODE_PROFILE_CHILD_PORN -> MixpanelV2.Report.Reason.Values.Code8ChildPorn
            REPORT_CODE_PROFILE_ILLEGAL_ACTIVITIES -> MixpanelV2.Report.Reason.Values.Code9IllegalActivities
            REPORT_CODE_PROFILE_DECEPTIVE_CONTENT -> MixpanelV2.Report.Reason.Values.Code10DeceptiveContent
            REPORT_CODE_PROFILE_IMPERSONATION -> MixpanelV2.Report.Reason.Values.Code11Impersonation
            REPORT_CODE_PROFILE_COPYRIGHT_AND_TRADEMARK_INFRINGEMENT -> MixpanelV2.Report.Reason.Values.Code1Copyright
            REPORT_CODE_PROFILE_I_DONT_LIKE_THIS -> MixpanelV2.Report.Reason.Values.Code12Dislike
            else -> error("Unknown user report code, reportCode=$reportCode")
        }
    }

    /***
     * [postDisplayOrderToReportCode] is post report only, not include profile and comment
     *
     * @param displayOrder should start from 1 not 0
     */
    @JvmStatic
    fun postDisplayOrderToReportCode(displayOrder: DisplayOrder): ReportCode {
        return when (displayOrder) {
            1 -> REPORT_CODE_POST_SPAM
            2 -> REPORT_CODE_POST_PORNOGRAPHY
            3 -> REPORT_CODE_POST_HATRED_AND_BULLYING
            4 -> REPORT_CODE_POST_SELF_HARM
            5 -> REPORT_CODE_POST_VIOLENT_GORY_AND_HARMFUL_CONTENT
            6 -> REPORT_CODE_POST_CHILD_PORN
            7 -> REPORT_CODE_POST_ILLEGAL_ACTIVITIES
            8 -> REPORT_CODE_POST_DECEPTIVE_CONTENT
            9 -> REPORT_CODE_POST_COPYRIGHT_AND_TRADEMARK_INFRINGEMENT
            10 -> REPORT_CODE_POST_INCORRECT_TAG
            else -> error("Unknown post report code")
        }
    }


    /***
     * [commentDisplayOrderToReportCode] is post report only, not include profile and comment
     *
     * @param displayOrder should start from 1 not 0
     */
    @JvmStatic
    fun commentDisplayOrderToReportCode(displayOrder: DisplayOrder): ReportCode {
        return when (displayOrder) {
            1 -> REPORT_CODE_COMMENT_SPAM
            2 -> REPORT_CODE_COMMENT_PORNOGRAPHY
            3 -> REPORT_CODE_COMMENT_HATRED_AND_BULLYING
            4 -> REPORT_CODE_COMMENT_SELF_HARM
            5 -> REPORT_CODE_COMMENT_VIOLENT_GORY_AND_HARMFUL_CONTENT
            6 -> REPORT_CODE_COMMENT_CHILD_PORN
            7 -> REPORT_CODE_COMMENT_ILLEGAL_ACTIVITIES
            8 -> REPORT_CODE_COMMENT_IMPERSONATION
            else -> error("Unknown comment report code")
        }
    }

    /***
     * [profileDisplayOrderToReportCode] is post report only, not include profile and comment
     *
     * @param displayOrder should start from 1 not 0
     */
    @JvmStatic
    fun profileDisplayOrderToReportCode(displayOrder: DisplayOrder): ReportCode {
        return when (displayOrder) {
            1 -> REPORT_CODE_PROFILE_INAPPROPRIATE_USERNAME_PROFILE_PICTURE
            2 -> REPORT_CODE_PROFILE_SPAM
            3 -> REPORT_CODE_PROFILE_PORNOGRAPHY
            4 -> REPORT_CODE_PROFILE_HATRED_AND_BULLYING
            5 -> REPORT_CODE_PROFILE_SELF_HARM
            6 -> REPORT_CODE_PROFILE_VIOLENT_GORY_AND_HARMFUL_CONTENT
            7 -> REPORT_CODE_PROFILE_CHILD_PORN
            8 -> REPORT_CODE_PROFILE_ILLEGAL_ACTIVITIES
            9 -> REPORT_CODE_PROFILE_DECEPTIVE_CONTENT
            10 -> REPORT_CODE_PROFILE_IMPERSONATION
            11 -> REPORT_CODE_PROFILE_COPYRIGHT_AND_TRADEMARK_INFRINGEMENT
            12 -> REPORT_CODE_PROFILE_I_DONT_LIKE_THIS
            else -> error("Unknown profile report code")
        }
    }
}

typealias DisplayOrder = Int
typealias ReportCode = Int