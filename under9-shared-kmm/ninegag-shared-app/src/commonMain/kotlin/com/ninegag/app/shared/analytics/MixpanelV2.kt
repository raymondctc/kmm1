package com.ninegag.app.shared.analytics

import kotlin.jvm.JvmField

/**
 * v2 of tracking events refer to this spreadsheet
 * @see [v2 specs](https://docs.google.com/spreadsheets/d/1gU04kBvIDuht1ospyX9xlWdQ9wzL-SFt_XKO6o87HV0/edit#gid=1436703936)
 */

object _MixpanelInternal {
	object Platform {
		const val Key = "Platform"
		val Values = _Values
		object _Values {
			const val Android = "Android"
			const val iOS = "iOS"
		}
	}

	object Page {
		const val Key = "Page"
		@JvmField val Values = _Values
		object _Values {
			const val Home = "Home"
			const val Section = "Section"
			const val Tag = "Tag"
			const val Interest = "Interest"
			const val Search = "Search"
			const val Profile = "Profile"
			const val Post = "Post"
			const val Board = "Board"
			const val Thread = "Thread"
			const val Login = "Login"
			const val Signup = "Signup"
			const val Notifications = "Notifications"
			const val Settings = "Settings"
			const val CustomizeMenu = "Customize Menu"
			const val MediaOverlay = "Media Overlay"
			const val PaidPlan = "Paid Plan"
			const val Top = "Top"
			const val Saved = "Saved"
			const val Upload = "Upload"
		}
	}

	object Utm {
		const val UtmSource = "utm_source"
		const val UtmMedium = "utm_medium"
		const val UtmCampaign = "utm_campaign"
		const val UtmTerm = "utm_term"
		const val UtmContent = "utm_content"

		const val LastUtmSource = "Last UTM Source"
		const val LastUtmMedium = "Last UTM Medium"
	}

	object PostSwitched {
		const val Key = "Post Switched"
		@JvmField val Values = _Values
		object _Values {
			const val Home = "Under Comments"
			const val Section = "You May Like"
			const val Tag = "Swipe"
		}
	}

	object _AuthButtonPositionValues {
		const val UserMenu = "User Menu"
		const val TopMenu = "Top Menu" // web only
		const val SideMenu = "Side Menu" // web only
		const val PostList = "Post List"
		const val Comment = "Post Comment"
		const val Board = "Board"
		const val Signup = "Signup"
		const val Login = "Login"
	}

	object _LoginButtonPosition {
		const val Key = "Login Button Position"
		val Values = _AuthButtonPositionValues
	}

	object _SignupButtonPosition {
		const val Key = "Signup Button Position"
		val Values = _AuthButtonPositionValues
	}

	object LoginClicked {
		val Event = "Login Clicked"
		val Props = _Props
		object _Props {
			val LoginButtonPosition = _LoginButtonPosition
		}
	}

	object PasswordLoginCompleted {
		val Event = "Password Login Completed"
	}

	object LogoutClicked {
		val Event = "Logout Clicked"
	}

	object NotificationEntry {
		const val Key = "Notification Entry"
		val Values = _Values
		object _Values {
			const val NotificationList = "Notification list"
			const val Push = "Push"
		}
	}
	object NotificationType {
		const val Key = "Notification Type"
		val Values = _Values
		object _Values {
			const val DailyFav = "DAILY_FAV_NOTI"
			const val DailySuggested = "DAILY_SUGGESTED_NOTI"
			const val StreakReminder = "STREAK_REMINDER"
			const val OpenAppReminder = "OPEN_APP_REMINDER"
			const val ReviewSavedPosts = "REVIEW_SAVED_POST_NOTI"
			const val FeaturedPost = "FEATURED_POST"
			const val BoardNoti = "BOARD_NOTI"
		}
	}

	object SignupClicked {
		val Event = "Signup Clicked"
		val Props = _Props
		object _Props {
			val SignupButtonPosition = _SignupButtonPosition
		}
	}

	object _ConnectButtonPosition {
		const val Key = "Connect Button Position"
		val Values = _Values
		object _Values {
			// For tracking if that connect action is a signup or login
			const val Signup = "Signup"
			const val Login = "Login"
		}
	}

	object MemberType {
		const val Key = "Member Type" // Free, Pro, Pro+
		val Values = _Values
		object _Values {
			const val Free = "Free"
			const val Pro = "Pro"
			const val ProPlus = "Pro+"
		}
	}

	object Gender {
		const val Key = "Gender"
		val Values = _Values
		object _Values {
			const val M = "M"
			const val F = "F"
			const val Unspecified = "X"
		}
	}

	object SocialConnect {
		const val Key = "Social Connect"
		val Values = _Values
		object _Values {
			const val FB = "FB"
			const val Google = "Google"
			const val Apple = "Apple"
		}
	}

	object ViewUserButtonPosition {
		const val Key = "View User Button Position"
		val Values = _Values
		object _Values {
			// Avatar, User Name
			const val Avatar = "Avatar"
			const val UserName = "User Name"
			const val CommentMention = "Comment Mention"
		}
	}

	object ProfileEntryPosition {
		const val Key = "Profile Entry Position"
		val Values = _Values
		object _Values {
			// Avatar, User Name
			const val Post = "Post"
			const val Comment = "Comment"
			const val Menu = "Menu"
			const val Notification = "Notification"
		}
	}


	object Notification {
		const val Key = "Notification"
		val Values = _Values
		object _Values {
			const val Posts = "Posts"
			const val Comments = "Comments"
			const val Boards = "Boards"
			const val Sections = "Sections"
			const val Others = "Others"
		}
	}

	object CommentType {
		const val Key = "Comment Type"
		val Values = _Values
		object _Values {
			const val TypeText = "Text"
			const val TypeGIF = "GIF"
			const val TypePhoto = "Photo"
			const val TypeGIPHY = "GIPHY" // For Comment Published only
		}
	}

	object MessageType {
		const val Key = "Message Type"
		val Values = _Values
		object _Values {
			const val TypeText = "Text"
			const val TypeGIF = "GIF"
			const val TypePhoto = "Photo"
			const val TypeGIPHY = "GIPHY" // For Board Message Published only
		}
	}


	object ConnectFacebookClicked {
		val Event = "Connect Facebook Clicked"
		val Props = _Props
		object _Props {
			val ConnectButtonPosition = _ConnectButtonPosition
		}
	}

	object ConnectAppleClicked {
		val Event = "Connect Apple Clicked"
		val Props = _Props
		object _Props {
			val ConnectButtonPosition = _ConnectButtonPosition
		}
	}

	object ConnectGoogleClicked {
		val Event = "Connect Google Clicked"
		val Props = _Props
		object _Props {
			val ConnectButtonPosition = _ConnectButtonPosition
		}
	}


	object EmailSignupClicked {
		val Event = "Email Signup Clicked"
	}

	object EmailSignupCompleted {
		val Event = "Email Signup Completed"
	}

	object FollowType {
		val Key = "Follow Type"
		val Values = _Values
		object _Values {
			const val Follow = "Follow"
			const val Unfollow = "Unfollow"
		}
	}

	object _TriggerAction {
		const val Key = "Trigger Action"
		val Values = _Values
		object _Values {
			// Posts
			const val Upload = "Upload"
			const val SavePost = "Save Post"
			const val UpvotePost = "Upvote Post"
			const val DownvotePost = "Downvote Post"
			const val ReportPost = "Report Post"
			const val HidePost = "Hide Post"
			// Comments
			const val UpvoteComment = "Upvote Comment"
			const val DownvoteComment = "Downvote Comment"
			const val Comment = "Comment"
			const val Reply = "Reply"
			const val ReportComment = "Report Comment"
			const val FollowComment = "Follow Comment"
			const val ViewAllComments = "View All Comments"
			const val HideOPComment = "Hide OP Comment"
			// Boards
			const val JoinBoard = "Join Board"
			// Users
			const val BlockUser = "Block User"
			const val ReportUser = "Report User"
			// Others
			const val EditProfile = "Edit Profile"
			const val Settings = "Settings"
			const val PureDarkMode = "Pure Dark Mode"
			const val UnmaskSensitveContent = "Unmask Sensitive Content"
			const val ViewOffensiveComments = "View Offensive Comments"
			const val Membership = "Membership"
			const val Hey = "Hey"

		}
	}

	object PostType {
		const val Key = "Post Type"
		@JvmField val Values = _Values
		object _Values {
			const val GIF = "GIF"
			const val Photo = "Photo"
			const val Video = "Video"
			const val Article = "Article"
			const val YouTube = "YouTube"
		}
	}

	// When guest clicked member actions and prompt signup (Auth flow triggered)
	object SignupTriggered {
		val Event = "Signup Triggered"
		val Props = _Props
		object _Props {
			val TriggerAction = _TriggerAction
		}
	}

	object ShareDestination {
		const val Key = "Destination"
		val Values = _Values
		object _Values {
			const val CopyLink = "Copy Link"
			const val Discord = "Discord"
			const val Facebook = "Facebook"
			const val FacebookMessenger = "Facebook Messenger"
			const val GoogleMessenger = "Google Messenger"
			const val Gmail = "Gmail"
			const val IG = "Instagram"
			const val Pinterest = "Pinterest"
			const val SamsungMessage = "Samsung Message"
			const val Signal = "Signal"
			const val Snapchat = "Snapchat"
			const val Telegram = "Telegram"
			const val TelegramX = "Telegram X"
			const val Twitter = "Twitter"
			const val Viber = "Viber"
			const val WhatsApp = "WhatsApp"
			const val Mail = "Mail"
			const val AirDrop = "AirDrop"
			const val Message = "Message"
			const val SaveToCameraRoll = "Save To Camera Roll"
			const val OpenInBrowser = "Open In Browser"
		}
	}

	object SearchType {
		const val Key = "Search Type"
		val Values = _Values
		object _Values {
			const val Type = "Type"
			const val History = "History"
			const val Suggestion = "Suggestion"
			const val Popular = "Popular"
		}
	}

	object TagPosition {
		const val Key = "Tag Position"
		val Values = _Values
		object _Values {
			const val DrawerPopular = "Drawer-Popular"
			const val DrawerRecents = "Drawer-Recents"
			const val DrawerFavorites = "Drawer-Favorites"
			const val DrawerOtherTags = "Drawer-Other Tags"
			const val DrawerHidden = "Drawer-Hidden"
			const val RelatedTags = "Related Tags"
			const val Post = "Post"
			const val HomeFeatured = "Home-Featured"
		}
	}


	object _PostSorting {
		const val Key = "Post Sorting"
		val Values = _Values
		object _Values {
			const val Home = "Home"
			const val Hot = "Hot"
			const val Trending = "Trending"
			const val Fresh = "Fresh"
			const val Top = "Top"
			const val Boards = "Boards"
			const val Overview = "Overview"
			const val Posts = "Posts"
			const val Comments = "Comments"
			const val Upvotes = "Upvotes"
			const val Saved = "Saved"
			const val Search = "Search"
			const val Tag = "Tag"
		}
	}

	object _PostEntry {
		const val Key = "Post Entry"
		val Values = _Values
		object _Values {
			const val PostTitle = "Post Title"
			const val PostContent = "Post Content"
			const val CommentButton = "Comment Button"
			const val NotificationList = "Notification List"
			const val RelatedPostTitle = "Related Post-Title"
			const val RelatedPostPostContent = "Related Post-Content"
			const val RelatedPostCommentButton = "Related Post-Comment Button"
			const val ViewPostButton = "View Post Button"
		}
	}

	object NavigationType {
		const val Key = "Navigation Type"
		val Values = _Values
		object _Values {
			const val UnderComments = "Under Comments"
			const val YouMayLike = "You May Like"
			const val Tap = "Tap"
			const val Swipe = "Swipe"
		}
	}

	object SortingList {
		const val Key = "Sorting List"
		val Values = _Values
		object _Values {
			const val Posts = "Posts"
			const val Comments = "Comments"
			const val Profile = "Profile"
		}
	}

	object SortingType {
		const val Key = "Sorting Type"
		val Values = _Values
		object _Values {
			const val Home = "Home"
			const val Hot = "Hot"
			const val Trending = "Trending"
			const val Fresh = "Fresh"
			const val Top = "Top"
			const val Overview = "Overview"
			const val Posts = "Posts"
			const val Comments = "Comments"
			const val Upvotes = "Upvotes"
			const val Saved = "Saved"
		}
	}

	object CommentSorting {
		const val Key = "Comment Sorting"
		val Values = _Values
		object _Values {
			const val Hot = "Hot"
			const val Fresh = "Fresh"
			const val Old = "Old"
		}
	}

	object TabBarSorting {
		const val Key = "Tab Bar Sorting"
		val Values = _Values
		object _Values {
			const val CommentTab = "Comment Tab"
			const val RelatedTab = "Related Tab"
		}
	}

	object VoteType {
		const val Key = "Vote Type"
		val Values = _Values
		object _Values {
			const val Up = "Up"
			const val Down = "Down"
		}
	}

	object SaveType {
		const val Key = "Save Type"
		@JvmField val Values = _Values
		object _Values {
			const val Save = "Save"
			const val Unsave = "Unsave"
		}
	}

	object SaveButtonPosition {
		const val Key = "Save Button Position"
		val Values = _Values
		object _Values {
			const val SaveButton = "Save Button"
			const val MoreButton = "More Button"
		}
	}

	object ShareButtonPosition {
		const val Key = "Share Button Position"
		val Values = _Values
		object _Values {
			const val ShareButton = "Share Button" // For Android, all are from ShareButton
			const val LongPress = "Long Press"
			const val MoreMenu = "More Button"
		}
	}

	object BoardEntry {
		const val Key = "Board Entry"
		val Values = _Values
		object _Values {
			const val Menu = "Menu"
			const val Section = "Section"
			const val BoardList = "Board List"
		}
	}

	object MuteType {
		const val Key = "Mute Type"
		val Values = _Values
		object _Values {
			const val Mute = "Mute"
			const val Unmute = "Unmute"
		}
	}

	@Deprecated("Section deprecated")
	object SectionAction {
		const val Key = "Section Action"
		val Values = _Values
		object _Values {
			const val Favorite = "Favorite"
			const val Unfavorite = "Unfavorite"
			const val Hide = "Hide"
			const val Unhide = "Unhide"
		}
	}

	@Deprecated("Section deprecated")
	object SectionEntry {
		const val Key = "Section Entry"
		val Values = _Values
		object _Values {
			const val AllSections = "All Sections"
			const val Favorites = "Favorites"
			const val Recents = "Recents"
			const val Popular = "Popular"
			const val Hidden = "Hidden"
		}
	}

	@Deprecated("Section deprecated")
	object SectionActionEntry {
		const val Key = "Section Action Entry"
		val Values = _Values
		object _Values {
			const val SectionMoreMenu = "Section More Menu"
			const val SideMenu = "Side Menu"
			const val PostList = "Post List"
			const val CustomizeMenu = "Customize Menu"
		}
	}

	object TagAction {
		const val Key = "Tag Action"
		val Values = _Values
		object _Values {
			const val Favorite = "Favorite"
			const val Unfavorite = "Unfavorite"
			const val Hide = "Hide"
			const val Unhide = "Unhide"
		}
	}

	object TagActionPosition {
		const val Key = "Tag Action Position"
		val Values = _Values
		object _Values {
			const val SideMenu = "Side Menu"
			const val CustomizeMenu = "Customize Menu"
		}
	}

	object PaidPlanType {
		const val Key = "Paid Plan Type"
		val Values = _Values
		object _Values {
			const val Pro = "Pro"
			const val ProPlus = "Pro+"
		}
	}

	object PaidPlanEntry {
		// Menu, Profile, Hide Ads, Badge, Saved Post
		const val Key = "Paid Plan Entry"
		val Values = _Values
		object _Values {
			const val Menu = "Menu"
			const val OverlayHD = "Overlay HD"
			const val HomeBottomSheet = "Home Bottomsheet"
			const val HomeBottomSheetPureDarkMode = "Home Bottomsheet Pure Dark Mode"
			const val HomePromo = "Home Promo"
			const val HomeHideAds = "Home Hide Ads"
			const val HideSection = "Hide Section"
			const val ProfileProPlusBadge = "Profile Pro+ Badge"
			const val ProfileProBadge = "Profile Pro Badge"
			const val PostListSavePostLimit = "Post List Save Post Limit"
			const val PostListSavePostHeader = "Save Post Promo Header"
			const val PostListSavePostFooter = "Save Post Promo Footer"
			const val BoardListFollowBoardLimit = "Board List Follow Limit"
			const val CommentProBadge = "Comment Pro Badge"
			const val CommentProPlusBadge = "Comment Pro+ Badge"
			const val CommentChangeAccentColor = "Comment Change Accent Color"
			const val EditProfileAccentColor = "Edit Profile Accent Color"
			const val EditProfileProExclusiveStatus = "Edit Profile Pro Exclusive Status"
			const val EditProfileProPlusExclusiveStatus = "Edit Profile Pro+ Exclusive Status"
			const val EditProfileHideProBadge = "Edit Profile Hide Badge"
			const val EditProfileHideOnlineStatus = "Edit Profile Hide Online Status"
			const val CustomizeHomePageHideLimit = "Customize Hide Limit"
			const val SettingManagePro = "Setting Manage Pro"
			const val SettingAutoDarkMode = "Setting Auto Dark Mode"
			const val SettingPureDarkMode = "Setting Pure Dark Mode"
			const val SettingHideNewPost = "Setting Hide New Post"
			const val SettingBedMode = "Setting Bed Mode"
			const val SettingHideAds = "Setting Hide Ads"
			const val SettingHDMode = "Setting HD"
			const val Deeplink = "Deeplink"
		}
	}

	object UpgradeFrom {
		const val Key = "Upgrade From"
		val Values = _Values
		object _Values {
			const val Free = "Free"
			const val Pro = "Pro"
		}
	}

	object Reason {
		const val Key = "Reason"
		val Values = _Values
		object _Values {
			const val Code1Copyright = "Copyright and trademark infringement"
			const val Code2Spam = "Spam"
			const val Code3Pornography = "Pornography"
			const val Code4Seen = "I've seen this"
			const val Code5HatredBullying = "Hatred and bullying"
			const val Code6SelfHarm = "Self-Harm"
			const val Code7Violent = "Violent, gory and harmful content"
			const val Code8ChildPorn = "Child Porn"
			const val Code9IllegalActivities = "Illegal activities e.g. Drug Uses"
			const val Code10DeceptiveContent = "Deceptive content"
			const val Code11Impersonation = "Impersonation"
			const val Code12Dislike = "I just don't like this"
			const val Code13InappropriateUserInfo = "Inappropriate Username / Profile Picture"
			const val Code14IncorrectTag = "Incorrect tag"
		}
	}

	object ShopButtonEntry {
		const val Key = "Shop Entry"
		val Values = _Values
		object _Values {
			const val TopMenu = "Top Menu"
			const val SideMenu = "Side Menu"
		}
	}

	object HelpShiftEntry {
		const val Key = "HelpShift Entry"
		val Values = _Values
		object _Values {
			const val SendFeedback = "Send Feedback"
			const val NineGagRules = "9GAG Rules"
			const val FAQ = "FAQ"
		}
	}

	object UploadContentType {
		const val Key = "Content Type"
		val Values = _Values
		object _Values {
			const val Photo = "Photo"
			const val Video = "Video"
			const val GIF = "GIF"
			const val URL = "URL"
			const val Memeful = "Memeful"
			const val Article = "Article"
		}
	}
	object UploadNextStep {
		const val Key = "Next Step"
		val Values = _Values
		object _Values {
			const val Info = "Info"
			const val Tag = "Tag"
		}
	}

	object UploadFailedType {
		const val Key = "Upload Failed Type"
		val Values = _Values
		object _Values {
			const val UploadQuotaExceeded = "UPLOAD_QUOTA_EXCEEDED"
			const val AccountUnverified = "ACCOUNT_UNVERIFIED"
			const val AccountTooNew = "ACCOUNT_TOO_NEW"
			const val AccountSecurityCheck = "ACCOUNT_SECURITY_CHECK"
			const val ActionBlocked = "ACTION_BLOCKED"
			const val InvalidFileSize = "INVALID_FILE_SIZE"
			const val InvalidType = "INVALID_TYPE"
			const val InvalidSource = "INVALID_SOURCE"
			const val InvalidTitle = "INVALID_TITLE"
			const val InvalidImageDimension = "INVALID_IMAGE_DIMENSION"
			const val ViolatingRules = "VIOLATING_RULES"
		}
	}

	object AutoModerationNextStepClicked {
		const val Key = "Auto Moderation Next Step Clicked"
		val Values = _Values
		object _Values {
			const val EditPost = "Edit Post"
			const val View9GAGRules = "View 9GAG Rules"
		}
	}

	object UploadFurthestStep {
		const val Key = "Furthest Step"
		val Values = _Values
		object _Values {
			const val Menu = "Menu"
			const val Media = "Media"
			const val Info = "Info"
			const val Tag = "Tag"
		}
	}

	object CommentWarningType {
		const val Key = "Warning Type"
		val Values = _Values
		object _Values {
			const val Quota = "Quota"
			const val Account = "Account"
			const val Media = "Media"
		}
	}

	object UploadEntry {
		const val Key = "Upload Entry"
		val Values = _Values
		object _Values {
			const val UseTagButton = "Use Tag Button"
			const val UploadButton = "Upload Button"
		}
	}

	object UploadWarningType {
		const val Key = "Warning Type"
		val Values = _Values
		object _Values {
			const val MinWordLimit = "Min Word Limit"
			const val MaxWordLimit = "Max Word Limit"
			const val Quota = "Quota"
			const val Account = "Account"
			const val URL = "URL"
			const val Media = "Media"
			const val InvalidVideoDuration = "Invalid Video Duration"
		}
	}

	object AnonymousPostPromptType {
		const val Key = "Prompt Type"
		val Values = _Values
		object _Values {
			const val Notice = "Notice"
			const val Consent = "Consent"
		}
	}

	object AnonymousPostPromptResponseAction {
		const val Key = "Response Action"
		val Values = _Values
		object _Values {
			const val Visible = "Visible"
			const val Anonymous = "Anonymous"
		}
	}

	object BlockActionEntry {
		const val Key = "Block Action Entry"
		val Values = _Values
		object _Values {
			const val Comment = "Comment"
			const val Post = "Post"
			const val Profile = "Profile"
		}
	}

	object UnmaskType {
		const val Key = "Unmask Type"
		val Values = _Values
		object _Values {
			const val All = "All"
			const val Single = "Single"
		}
	}

	object SensitiveType {
		const val Key = "Sensitive Type"
		val Values = _Values
		object _Values {
			const val Downvote = "Downvote"
			const val Offensive = "Offensive"
			const val Sensitive = "Sensitive"
		}
	}

	object _AutoPlayOptionValues {
		const val Always = "Always"
		const val WiFiOnly = "Wi-Fi only"
		const val Never = "Never"
	}

	object _AutoPlayGIFs {
		const val Key = "Autoplay GIFs"
		val Values = _AutoPlayOptionValues
	}

	object _AutoPlayVideos {
		const val Key = "Autoplay Videos"
		val Values = _AutoPlayOptionValues
	}

	object _Theme {
		const val Key = "Theme"
		val Values = _Values
		object _Values {
			const val Light = "Light"
			const val Dark = "Dark"
			const val System = "System"
		}
	}

	object CampaignPosition {
		const val Key = "Campaign Position"
		val Values = _Values

		object _Values {
			const val CampaignCard = "Campaign Card" // Not available for mobile apps
			const val CampaignBanner = "Campaign Banner"
		}
	}

	object TagSource {
		const val Key = "Tag Source"
		val Values = _Values

		object _Values {
			//Recently Used, Trending Suggestion, Related Suggestion, Search Suggestion, User Input
			const val RecentlyUsed = "Recently Used"
			const val TrendingSuggestion = "Trending Suggestion"
			const val RelatedSuggestion = "Related Suggestion"
			const val SearchSuggestion = "Search Suggestion"
			const val UserInput = "User Input"
			const val UseTagButton = "Use Tag Button"
		}
	}

	object PostAuthor {
		const val Key = "Post Author"
		val Values = _Values
		object _Values {
			const val Anonymous = "Anonymous"
		}
	}

}

object MixpanelV2 {
	object App {
		const val Visits = "Visits"
	}

	object Auth {
		val ConnectAppleClicked = _MixpanelInternal.ConnectAppleClicked
		val ConnectFacebookClicked = _MixpanelInternal.ConnectFacebookClicked
		val ConnectGoogleClicked = _MixpanelInternal.ConnectGoogleClicked
		val SignupTriggered = _MixpanelInternal.SignupTriggered

		// Login
		val LoginClicked = _MixpanelInternal.LoginClicked
		val LogoutClicked = _MixpanelInternal.LogoutClicked
		val LoginCompleted = _MixpanelInternal.PasswordLoginCompleted

		// Sign up
		val SignupClicked = _MixpanelInternal.SignupClicked
		val EmailSignupClicked = _MixpanelInternal.EmailSignupClicked
		val EmailSignupCompleted = _MixpanelInternal.EmailSignupCompleted
	}

	object Board {
		const val BoardJoined = "Board Joined"
		const val BoardMuted = "Board Muted"
		const val BoardLeft = "Board Left"
		const val BoardMessagePublished = "Board Message Published"
		const val MessageAuthorReplyTo = "Message Author (reply to)"

		const val Muted = "Board Muted"
		const val BoardName = "Board Name"
		const val IsReply = "Is Reply"
		const val MessageAuthor = "Message Author"
		const val MessagePosted = "Message Posted"
		const val NumberofMembers = "Number of Members"
		const val SinceLastActivity = "Since Last Activity"

		@JvmField val MessageType = _MixpanelInternal.MessageType
		@JvmField val BoardEntry = _MixpanelInternal.BoardEntry
		@JvmField val MuteType = _MixpanelInternal.MuteType

		object MemberAction {
			const val JoinBoard = "Join Board"
		}
	}

	object Comment {
		const val CommentFollowed = "Comment Followed"
		const val CommentPublished = "Comment Published"
		const val CommentVoted = "Comment Voted"
		const val SortingChanged = "Sorting Changed"
		const val CommentWarningShown = "Comment Warning Shown"
		const val CommentFailed = "Comment Failed"
		const val OPCommentHidden = "OP Comment Hidden"
		const val ViewCommentClicked = "View Comment Clicked"

		@JvmField val PostAuthor = _MixpanelInternal.PostAuthor
		const val CommentAuthor = "Comment Author"
		const val CommentAuthorReplyTo = "Comment Author (reply to)"
		const val Id = "Comment ID"
		const val IsReply = "Is Reply"
		const val Date = "Comment Date"
		const val Sensitive = "Sensitive Comment"
		const val CommentFailedType = "Comment Failed Type"
		const val CommentSensitiveType = "Comment Sensitive Type"
		const val CommentAnonymity = "Comment Anonymity"

		@JvmField val FollowType = _MixpanelInternal.FollowType
		@JvmField val Type = _MixpanelInternal.CommentType
		@JvmField val VoteType = _MixpanelInternal.VoteType

		@JvmField val SortingList = _MixpanelInternal.SortingList
		@JvmField val SortingType = _MixpanelInternal.SortingType
		@JvmField val CommentSorting = _MixpanelInternal.CommentSorting

		@JvmField val WarningType = _MixpanelInternal.CommentWarningType

		@JvmField val UnmaskType = _MixpanelInternal.UnmaskType
		@JvmField val SensitiveType = _MixpanelInternal.SensitiveType
	}

	object Membership {
		const val PaidPlansViewed = "Paid Plans Viewed"
		const val PaidPlansEnabled = "Paid Plans Enabled"

		val PaidPlanType = _MixpanelInternal.PaidPlanType
		val PaidPlanEntry = _MixpanelInternal.PaidPlanEntry
		val UpgradeFrom = _MixpanelInternal.UpgradeFrom
	}

	object Notification {
		const val NotificationOpened = "Notification Opened"
		const val NotificationReceived = "Notification Received"
		const val NotificationType = "Notification Type"
		@JvmField
		val Entry = _MixpanelInternal.NotificationEntry
		@JvmField
		val Type = _MixpanelInternal.NotificationType
	}

	object Campaign {
		const val CampaignClicked = "Campaign Clicked"

		@JvmField
		val CampaignPosition = _MixpanelInternal.CampaignPosition
		const val CampaignURLDestination = "Campaign URL Destination"
	}

	object Post {
		const val PostDownloaded = "Post Downloaded"
		const val PostShared = "Post Shared"
		const val PostSaved = "Post Saved"
		const val PostVoted = "Post Voted"
		const val SortingChanged = "Sorting Changed"
		const val PostOpened = "Post Opened"
		const val SavedListOpened = "Saved List Opened"
		const val HomeListOpened = "Home List Opened"
		const val PostHidden = "Post Hidden"
		const val TabBarClicked = "Tab Bar Clicked"

		const val Id = "Post ID"
		const val Date = "Post Date"
		const val Section = "Post Section"
		const val SectionTag = "Section Tag"
		const val Tags = "Tags"
		const val Sensitive = "Sensitive Post"
		const val PostAnonymity = "Post Anonymity"
		const val IsOP = "Is OP"

		const val ListDataSource = "List Data Source"
		const val FeedId = "Feed ID"
		const val EndOfListTabBarShown = "End of List Tab Bar Shown"

		@JvmField
		val Author = _MixpanelInternal.PostAuthor

		@JvmField
		val PostEntry = _MixpanelInternal._PostEntry

		@JvmField
		val PostSorting = _MixpanelInternal._PostSorting

		@JvmField
		val Type = _MixpanelInternal.PostType

		@JvmField
		val VoteType = _MixpanelInternal.VoteType

		@JvmField
		val SaveType = _MixpanelInternal.SaveType

		@JvmField
		val SaveButtonPosition = _MixpanelInternal.SaveButtonPosition

		@JvmField
		val ShareButtonPosition = _MixpanelInternal.ShareButtonPosition

		@JvmField
		val SortingList = _MixpanelInternal.SortingList

		@JvmField
		val SortingType = _MixpanelInternal.SortingType

		@JvmField
		val NavigationType = _MixpanelInternal.NavigationType

		@JvmField
		val TabBarSorting = _MixpanelInternal.TabBarSorting
	}

	object Report {
		val Reason = _MixpanelInternal.Reason
	}

	object Search {
		const val SearchSubmitted = "Search Submitted"
		const val Keywords = "Keywords"
		val Type = _MixpanelInternal.SearchType
	}

	object Section {
		const val SectionFavorited = "Section Favorited"
		const val SectionHidden = "Section Hidden"
		const val SectionClicked = "Section Clicked"

		const val PostSection = "Post Section"
		const val SectionTag = "Section Tag"

		@JvmField
		val Action = _MixpanelInternal.SectionAction
		@JvmField
		val Entry = _MixpanelInternal.SectionEntry
		val ActionEntry = _MixpanelInternal.SectionActionEntry
	}

	object Share {
		val Destination = _MixpanelInternal.ShareDestination
	}

	object Tag {
		const val TagClicked = "Tag Clicked"
		const val TagName = "Tag Name"
		const val TagPageLinkClicked = "Tag Page Link Clicked"
		const val TagURLDestinationn = "Tag Page URL Destination"
		const val TagFavorited = "Tag Favorited"
		const val TagHidden = "Tag Hidden"

		val TagPosition = _MixpanelInternal.TagPosition

		@JvmField
		val Action = _MixpanelInternal.TagAction
		@JvmField
		val Entry = _MixpanelInternal.SectionEntry
		val ActionPosition = _MixpanelInternal.TagActionPosition
	}

	object Upload {
		const val UploadClicked = "Upload Clicked"
		const val UploadCompleted = "Upload Completed"
		const val UploadMediaClicked = "Upload Media Clicked"
		const val UploadCameraClicked = "Upload Camera Clicked"
		const val UploadURLClicked = "Upload URL Clicked"
		const val UploadArticleClicked = "Upload Article Clicked"
		const val UploadContentSucceed = "Upload Content Succeed"
		const val UploadPhotoEditClicked = "Upload Photo Edit Clicked"
		const val NextButtonClicked = "Next Button Clicked"
		const val UploadCanceled = "Upload Canceled"
		const val UploadWarningShown = "Upload Warning Shown"
		const val UploadFailed = "Upload Failed"

		const val LatestMedia = "Latest Media"
		const val TitleFilled = "Title Filled"
		const val NumberofTags = "Number of Tags"
		const val Anonymity = "Anonymity"
		const val Title = "Title"
		const val Tags = "Tags"
		val TagSource = _MixpanelInternal.TagSource
		const val TagAmount = "Tag Amount"
		const val VideoDuration = "Video Duration"
		// Upload triggered via 3rd party app sharing
		const val External = "External"
		// Auto Moderation
		const val AutoModerationBlockShown = "Auto Moderation Block Shown"

		@JvmField val UploadEntry = _MixpanelInternal.UploadEntry
		@JvmField val UploadFailedType = _MixpanelInternal.UploadFailedType
		@JvmField val ContentType = _MixpanelInternal.UploadContentType
		@JvmField val NextStep = _MixpanelInternal.UploadNextStep
		@JvmField val FurthestStep = _MixpanelInternal.UploadFurthestStep
		@JvmField val WarningType = _MixpanelInternal.UploadWarningType
		@JvmField val AutoModerationNextStepClicked = _MixpanelInternal.AutoModerationNextStepClicked
		@JvmField val PostAuthor = _MixpanelInternal.PostAuthor
	}

	object Anonymity {
		const val AnonymousPostPromptSeen = "Anonymous Post Prompt Seen"
		const val AnonymousPostPromptResponded = "Anonymous Post Prompt Responded"

		@JvmField val AnonymousPostPromptType = _MixpanelInternal.AnonymousPostPromptType
		@JvmField val AnonymousPostPromptResponseAction =
			_MixpanelInternal.AnonymousPostPromptResponseAction
	}

	object User {
		const val Viewed = "User Viewed"
		const val Blocked = "User Blocked"
		const val SettingsSaved = "Settings Saved"

		const val AccountId = "Account ID"

		val ViewUserButtonPosition = _MixpanelInternal.ViewUserButtonPosition
		val ProfileEntryPosition = _MixpanelInternal.ProfileEntryPosition

		@JvmField val BlockActionEntry = _MixpanelInternal.BlockActionEntry
	}

	object Super {
		const val Bucket = "Bucket"

		val Platform = _MixpanelInternal.Platform
		@JvmField val Page = _MixpanelInternal.Page

		const val PostViewed = "Post Viewed" // Start from 1
		const val CommentUnmasked = "Comment Unmasked"

		val Utm = _MixpanelInternal.Utm
		val PostSwitched = _MixpanelInternal.PostSwitched
	}

	object UserProps {
		const val AccountCreationDate = "Account Creation Date"
		const val AccountId = "Account ID"
		const val Age = "Age"
		const val Avatar = "\$avatar"
		const val DeviceId = "Device ID"
		const val DeviceUsed = "Device Used"
		const val Email = "\$email"
		@Deprecated("Section deprecated")
		const val FavoriteSections = "Favorite Sections"
		@Deprecated("Section deprecated")
		const val HiddenSections = "Hidden Sections"
		const val FavoriteTags = "Favorite Tags"
		const val HiddenTags = "Hidden Tags"

		const val LifetimeUserReported = "Lifetime User Reported"
		const val LifetimeUserBlocked = "Lifetime User Blocked"
		const val LifetimeCommentPublished = "Lifetime Comment Published"
		const val LifetimeCommentVoted = "Lifetime Comment Voted"
		const val LifetimeCommentReported = "Lifetime Comment Reported"
		const val LifetimePurchase = "Lifetime Purchase"
		const val LifetimePostUploaded = "Lifetime Post Uploaded"
		const val LifetimePostVoted = "Lifetime Post Voted"
		const val LifetimePostSaved = "Lifetime Post Saved"
		const val LifetimePostShared = "Lifetime Post Shared"
		const val LifetimePostReported = "Lifetime Post Reported"
		const val LastUtmSource = "Last UTM Source"
		const val LastUtmMedium = "Last UTM Medium"

		const val Name = "\$name"

		val Gender = _MixpanelInternal.Gender
		val MemberType = _MixpanelInternal.MemberType
		val SocialConnect = _MixpanelInternal.SocialConnect
		val Notification = _MixpanelInternal.Notification

		const val PlatformsUsed = "Platforms Used"
		const val ProStartDate = "Pro Start Date"
		const val AllDeviceNotificationOff = "All Device Notification Off"
		const val NotificationOff = "Notification Off"
		const val Streak = "Streak"
		const val ContentMaskOn = "Content Mask On"
		const val SensitiveContentOn = "Sensitive Content On"
		const val FollowReplyThreadOn = "Follow Reply Thread On"
		const val CommentMaskOn = "Comment Mask On"
		const val HideProfileOn = "Hide Profile On"
		@JvmField val AutoplayGIFs = _MixpanelInternal._AutoPlayGIFs
		@JvmField val AutoplayVideos = _MixpanelInternal._AutoPlayVideos
		@JvmField val Theme = _MixpanelInternal._Theme

		const val UserId = "\$user_id"
	}

	object Shop {
		const val ShopClicked = "Shop Clicked"
		@JvmField val ShopButtonEntry = _MixpanelInternal.ShopButtonEntry
		const val ShopButtonCopy = "Shop Button Copy"
	}

	object Misc {
		const val HelpShiftClicked = "HelpShift Clicked"
		@JvmField val HelpShiftEntry = _MixpanelInternal.HelpShiftEntry
	}
}
