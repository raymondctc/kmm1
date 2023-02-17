package com.ninegag.app.shared.util

import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import com.ninegag.app.shared.res.MR
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.ResourceFormatted

/**
 * Order functions in alphabetical order
 */
object NinegagString {
    fun all9Gagger(): StringDesc = StringDesc.Resource(MR.strings.all_9gagger)
    fun allSnackbarBlockedUser(username: String): StringDesc = StringDesc.ResourceFormatted(MR.strings.all_snackbar_block_user, username)
    fun buttonOk(): StringDesc = StringDesc.Resource(MR.strings.all_btn_ok)
    fun buttonCancel(): StringDesc = StringDesc.Resource(MR.strings.all_btn_cancel)
    fun buttonHide(): StringDesc = StringDesc.Resource(MR.strings.all_btn_hide)
    fun buttonPost(): StringDesc = StringDesc.Resource(MR.strings.all_post)

    fun commentDialogHideOPCommentTitle(): StringDesc = StringDesc.Resource(MR.strings.comment_dialog_hide_op_comment_title)
    fun commentDialogHideOPCommentTitleDesc(): StringDesc = StringDesc.Resource(MR.strings.comment_dialog_hide_op_comment_desc)
    fun commentSnackbarHideOP(): StringDesc = StringDesc.Resource(MR.strings.comment_snackbar_hide_op)
    fun commentComposerPlaceholderComment(): StringDesc = StringDesc.Resource(MR.strings.comment_composer_placeholder_comment)
    fun commentComposerPlaceholderThread(): StringDesc = StringDesc.Resource(MR.strings.comment_composer_placeholder_thread)
    fun commentComposerSubmitBtn(): StringDesc = StringDesc.Resource(MR.strings.comment_composer_submit_btn)
    fun commentSnackbarCommentNotFound(): StringDesc = StringDesc.Resource(MR.strings.comment_snackbar_comment_not_found)
    fun commentSnackbarCommentPosted(): StringDesc = StringDesc.Resource(MR.strings.comment_snackbar_comment_posted)
    fun commentSnackbarCommentView(): StringDesc = StringDesc.Resource(MR.strings.comment_snackbar_comment_view)
    fun homeCustomizeTitle(): StringDesc = StringDesc.Resource(MR.strings.homecustomize_title)
    fun homeCustomizeHiddenTagsDesc(): StringDesc = StringDesc.Resource(MR.strings.homecustomize_hiddentags_desc)
    fun navlistHeaderFavorites(): StringDesc = StringDesc.Resource(MR.strings.navlist_header_favorites)
    fun navlistHeaderHidden(): StringDesc = StringDesc.Resource(MR.strings.navlist_header_hidden)
    fun navlistHeaderOtherTags(): StringDesc = StringDesc.Resource(MR.strings.navlist_header_othertags)
    fun navlistHeaderRecents(): StringDesc = StringDesc.Resource(MR.strings.navlist_header_recents)
    fun navlistHeaderRecentsClearBtn(): StringDesc = StringDesc.Resource(MR.strings.navlist_header_recentsclear_btn)
    fun navlistHeaderPopular(): StringDesc = StringDesc.Resource(MR.strings.navlist_header_popular)
    fun navlistRecentsClearDialogDesc(): StringDesc = StringDesc.Resource(MR.strings.navlist_recents_cleardialog_desc)
    fun navlistRecentsClearDialogConfirmBtn(): StringDesc = StringDesc.Resource(MR.strings.navlist_recents_cleardialogconfirm_btn)

    fun postAnonymousPromptTitle(): StringDesc = StringDesc.Resource(MR.strings.postanonymous_prompt_title)
    fun postAnonymityPromptDesc(): StringDesc = StringDesc.Resource(MR.strings.postanonymous_prompt_desc)
    fun postAnonymousBtnMakeMyPostAnonymous(): StringDesc = StringDesc.Resource(MR.strings.postanonymous_btn_make_my_post_anonymous)

    fun postAnonymousBtnMakeAnonymous(): StringDesc = StringDesc.Resource(MR.strings.postanonymous_btn_make_anonymous)
    fun postAnonymousBtnMakeVisible(): StringDesc = StringDesc.Resource(MR.strings.postanonymous_btn_make_visible)

    fun postAnonymousSnackbarAnonymous(): StringDesc = StringDesc.Resource(MR.strings.postanonymous_snack_anonymous)
    fun postAnonymousSnackbarVisible(): StringDesc = StringDesc.Resource(MR.strings.postanonymous_snack_visible)

    fun postAnonymousDialogTitle(): StringDesc = StringDesc.Resource(MR.strings.postanonymous_dialog_title)
    fun postAnonymousDialogDesc(): StringDesc = StringDesc.Resource(MR.strings.postanonymous_dialog_desc)


    fun postHeaderTabComments(): StringDesc = StringDesc.Resource(MR.strings.post_header_tab_comments)
    fun postHeaderTabRelated(): StringDesc = StringDesc.Resource(MR.strings.post_header_tab_related)
    fun postTags(): StringDesc = StringDesc.Resource(MR.strings.post_tags)
    fun postTooltipHeaderTabRelated(): StringDesc = StringDesc.Resource(MR.strings.post_tooltip_header_tab_related)

    fun postMenuHidePost(): StringDesc = StringDesc.Resource(MR.strings.post_menu_hide_post)
    fun postDialogHidePostTitle(): StringDesc = StringDesc.Resource(MR.strings.post_dialog_hide_post_title)
    fun postDialogHidePostDesc(): StringDesc = StringDesc.Resource(MR.strings.post_dialog_hide_post_desc)
    fun postDialogBtnHide(): StringDesc = StringDesc.Resource(MR.strings.post_dialog_btn_hide)
    fun postSnackbarHidePost(): StringDesc = StringDesc.Resource(MR.strings.post_snackbar_hide_post)
    fun postViewEmptyBlockTitle(username: String) = StringDesc.ResourceFormatted(MR.strings.postview_empty_block_title, username)
    fun postViewEmptyHideTitle() = StringDesc.Resource(MR.strings.postview_empty_hide_title)
    fun postSnackbarBlockUser(username: String) = StringDesc.ResourceFormatted(MR.strings.post_snackbar_block_user, username)
    fun postDialogBlockUserTitle(username: String) = StringDesc.ResourceFormatted(MR.strings.post_dialog_block_user_title, username)
    fun postDialogBlockUserDesc(username: String) = StringDesc.ResourceFormatted(MR.strings.post_dialog_block_user_desc, username)
    fun postListCommentedEmptyDesc(): StringDesc = StringDesc.Resource(MR.strings.postlist_comment_empty_desc)
    fun postListSavedEmptyDesc(): StringDesc = StringDesc.Resource(MR.strings.postlist_saved_empty_desc)
    fun postListUpvotedEmptyDesc(): StringDesc = StringDesc.Resource(MR.strings.postlist_upvoted_empty_desc)
    fun postListUploadUseTagDesc(): StringDesc = StringDesc.Resource(MR.strings.postlist_upload_usetag_desc)
    fun postListUploadedEmptyDesc(): StringDesc = StringDesc.Resource(MR.strings.postlist_uploaded_empty_desc)
    fun postTooltipsDisplayPostCreator(): StringDesc = StringDesc.Resource(MR.strings.post_tooltip_display_post_creator)
    fun postSnackbarPostShared(): StringDesc = StringDesc.Resource(MR.strings.post_snackbar_postShared)
    fun postSnackbarViewPost(): StringDesc = StringDesc.Resource(MR.strings.post_snackbar_viewPost)

    fun uploadSensitiveHint(): StringDesc = StringDesc.Resource(MR.strings.upload_sensitive_hint)
    fun uploadAnonymousOptionTitle(): StringDesc = StringDesc.Resource(MR.strings.upload_anonymous_option_title)
    fun userProfilePostListEmptyTitle(): StringDesc = StringDesc.Resource(MR.strings.userprofile_postslist_empty_title)
    fun userProfilePostListEmptyDesc(): StringDesc = StringDesc.Resource(MR.strings.userprofile_postslist_empty_desc)
    fun userProfilePostListEmptyBtnAction(): StringDesc = StringDesc.Resource(MR.strings.userprofile_postslist_empty_btn_action)
    fun uploadTooltipUploadedBefore(): StringDesc = StringDesc.Resource(MR.strings.upload_tooltip_uploaded_before)
    fun uploadTooltipNotUploadedBefore(): StringDesc = StringDesc.Resource(MR.strings.upload_tooltip_not_uploaded_before)
    fun uploadViolateRulesDialogEdit(): StringDesc = StringDesc.Resource(MR.strings.upload_violate_rules_dialog_edit)
    fun uploadViolateRulesDialogViewRules(): StringDesc = StringDesc.Resource(MR.strings.upload_violate_rules_dialog_view_rules)
    fun uploadErrorDialogTitleSmallerImage(): StringDesc = StringDesc.Resource(MR.strings.upload_error_dialog_title_smaller_image)
    fun uploadErrorDialogTitleSmallerGif(): StringDesc = StringDesc.Resource(MR.strings.upload_error_dialog_title_smaller_gif)
    fun uploadErrorDialogTitleSmallerVideo(): StringDesc = StringDesc.Resource(MR.strings.upload_error_dialog_title_smaller_video)
    fun uploadErrorDialogTitleShorterVideo(): StringDesc = StringDesc.Resource(MR.strings.upload_error_dialog_title_shorter_video)
    fun uploadErrorDialogTitleFailedMedia(): StringDesc = StringDesc.Resource(MR.strings.upload_error_dialog_title_failed_media)
    fun uploadErrorDialogTitleFallback(): StringDesc = StringDesc.Resource(MR.strings.upload_error_dialog_title_fallback)
    fun uploadErrorDialogMsgSmallerImage(): StringDesc = StringDesc.Resource(MR.strings.upload_error_dialog_msg_smaller_image)
    fun uploadErrorDialogMsgSmallerGif(): StringDesc = StringDesc.Resource(MR.strings.upload_error_dialog_msg_smaller_gif)
    fun uploadErrorDialogMsgSmallerVideo(): StringDesc = StringDesc.Resource(MR.strings.upload_error_dialog_msg_smaller_video)
    fun uploadErrorDialogMsgShorterVideo(): StringDesc = StringDesc.Resource(MR.strings.upload_error_dialog_msg_shorter_video)
    fun uploadErrorDialogMsgFallback(): StringDesc = StringDesc.Resource(MR.strings.upload_error_dialog_msg_fallback)
    fun uploadLoadingDialogMsg(): StringDesc = StringDesc.Resource(MR.strings.upload_loading_dialog_msg)

    fun homeTopTagTooltip(): StringDesc = StringDesc.Resource(MR.strings.home_top_tag_tooltip_notice)
    fun homeTrendingTagTooltip(): StringDesc = StringDesc.Resource(MR.strings.home_trending_tag_tooltip_notice)
    fun homeFreshTagTooltip(): StringDesc = StringDesc.Resource(MR.strings.home_fresh_tag_tooltip_notice)
}