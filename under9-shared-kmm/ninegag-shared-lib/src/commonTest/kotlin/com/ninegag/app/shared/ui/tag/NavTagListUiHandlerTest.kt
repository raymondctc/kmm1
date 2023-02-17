package com.ninegag.app.shared.ui.tag

import com.ninegag.app.shared.data.tag.FakeTagListRepositoryImpl
import com.ninegag.app.shared.data.tag.model.Tag
import com.ninegag.app.shared.data.tag.model.TagListModel
import com.ninegag.app.shared.domain.tag.FetchNavTagListUseCase
import com.ninegag.app.shared.domain.tag.UpdateFavHiddenRecentStatusUseCase
import com.ninegag.app.shared.ui.tag.model.SectionType
import com.ninegag.app.shared.ui.tag.model.TagSectionedUiModel
import com.ninegag.app.shared.ui.tag.model.TagUiModel
import com.ninegag.app.shared.util.NinegagString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlin.test.Test
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class NavTagListUiHandlerTest {
    private val scope = TestScope()
    private val standardDispatcher = StandardTestDispatcher()
    private val tagListRepository = FakeTagListRepositoryImpl()

    private val updateFavHiddenRecentStatusUseCase = UpdateFavHiddenRecentStatusUseCase(
        tagListRepository, standardDispatcher
    )
    private val fetchNavTagListUseCase = FetchNavTagListUseCase(
        tagListRepository, standardDispatcher
    )
    private val navTagListUiHandler = NavTagListUiHandler(
        scope,
        fetchNavTagListUseCase, updateFavHiddenRecentStatusUseCase
    )

    @Test
    fun given_emptyFavList_whenFavItem_thenAddedToFavList() {
        val uiModels = listOf(
            TagSectionedUiModel(
                NinegagString.navlistHeaderPopular(),
                SectionType.POPULAR,
                tags = listOf(
                    TagUiModel("Funny", "https://test.com/funny", isSensitive = false),
                    TagUiModel("Meme", "https://test.com/meme", isSensitive = false)
                )
            ),
            TagSectionedUiModel(
                NinegagString.navlistHeaderOtherTags(),
                SectionType.OTHER,
                tags = listOf(
                    TagUiModel("Other tag 1", "https://test.com/other-tag-1", isSensitive = false),
                    TagUiModel("Other tag 2", "https://test.com/other-tag-2", isSensitive = false)
                )
            )
        )
        navTagListUiHandler.favItem(
            0, 1, uiModels, emptyList()
        ).also { pair ->
            val (newList, newSparedItemList) = pair
            assertTrue { newList.first().sectionType == SectionType.FAVOURITED }
            assertTrue { newList.first().tags.first().title == "Meme" }
            assertTrue { newList.first { it.sectionType == SectionType.POPULAR }.tags.indexOfFirst { it.title == "Meme" } == -1 }
        }
    }

    @Test
    fun given_favList_whenFavItem_thenAddItemToEndOfFavList() {
        val uiModels = listOf(
            TagSectionedUiModel(
                NinegagString.navlistHeaderPopular(),
                SectionType.FAVOURITED,
                tags = listOf(
                    TagUiModel("Awesome", "https://test.com/funny", isSensitive = false),
                )
            ),
            TagSectionedUiModel(
                NinegagString.navlistHeaderPopular(),
                SectionType.POPULAR,
                tags = listOf(
                    TagUiModel("Funny", "https://test.com/funny", isSensitive = false),
                    TagUiModel("Meme", "https://test.com/meme", isSensitive = false)
                )
            ),
            TagSectionedUiModel(
                NinegagString.navlistHeaderOtherTags(),
                SectionType.OTHER,
                tags = listOf(
                    TagUiModel("Other tag 1", "https://test.com/other-tag-1", isSensitive = false),
                    TagUiModel("Other tag 2", "https://test.com/other-tag-2", isSensitive = false)
                )
            )
        )

        navTagListUiHandler.favItem(
            1, 1, uiModels, emptyList()
        ).also { pair ->
            val (newList, newSparedItemList) = pair
            assertTrue { newList.first().sectionType == SectionType.FAVOURITED }
            assertTrue { newList.first().tags.first().title == "Meme" }
            assertTrue { newList.first { it.sectionType == SectionType.POPULAR }.tags.indexOfFirst { it.title == "Meme" } == -1 }
        }
    }

    @Test
    fun given_favList_whenFavItem_thenSparedItemListWillBeUsedIfNotEmpty() {
        val uiModels = listOf(
            TagSectionedUiModel(
                NinegagString.navlistHeaderPopular(),
                SectionType.FAVOURITED,
                tags = listOf(
                    TagUiModel("Awesome", "https://test.com/funny", isSensitive = false),
                )
            ),
            TagSectionedUiModel(
                NinegagString.navlistHeaderPopular(),
                SectionType.POPULAR,
                tags = listOf(
                    TagUiModel("Funny", "https://test.com/funny", isSensitive = false),
                    TagUiModel("Meme", "https://test.com/meme", isSensitive = false)
                )
            ),
            TagSectionedUiModel(
                NinegagString.navlistHeaderOtherTags(),
                SectionType.OTHER,
                tags = listOf(
                    TagUiModel("Other tag 1", "https://test.com/other-tag-1", isSensitive = false),
                    TagUiModel("Other tag 2", "https://test.com/other-tag-2", isSensitive = false)
                )
            )
        )

        val spareList = listOf(
            TagUiModel("Spared item 1", "https://test.com/sparedItem1", isSensitive = false),
            TagUiModel("Spared item 2", "https://test.com/sparedItem2", isSensitive = false),
            TagUiModel("Spared item 3", "https://test.com/sparedItem3", isSensitive = false)
        )

        navTagListUiHandler.favItem(
            1, 1, uiModels, spareList
        ).also { pair ->
            val (newList, newSparedItemList) = pair

            //
            assertTrue { newSparedItemList.size == 2 }
            assertTrue { newSparedItemList.indexOfFirst { it.url == "https://test.com/sparedItem1" } == -1 }
        }
    }

    @Test
    fun given_ListOfTags_whenGoRecent_thenKeepCopy() {
        val uiModels = listOf(
            TagSectionedUiModel(
                NinegagString.navlistHeaderPopular(),
                SectionType.POPULAR,
                tags = listOf(
                    TagUiModel("Funny", "https://test.com/funny", isSensitive = false),
                    TagUiModel("Meme", "https://test.com/meme", isSensitive = false)
                )
            )
        )
        navTagListUiHandler.updateRecentList(
            0, 0,
            uiModels = uiModels
        ).also {
            assertTrue { it[0].sectionType == SectionType.RECENT }
            assertTrue { it[0].tags[0].title == "Funny" }
        }
    }

    @Test
    fun given_ListOfTags_whenUnFavATagThatDoesntExistInListAnyMore_thenRemoveIt() {
        val removedTagUrl = "https://test.com/a-removed-tag"
        val uiModels = listOf(
            TagSectionedUiModel(
                NinegagString.navlistHeaderFavorites(),
                SectionType.FAVOURITED,
                tags = listOf(
                    TagUiModel("A removed tag", removedTagUrl, isSensitive = false),
                )
            ),
            TagSectionedUiModel(
                NinegagString.navlistHeaderPopular(),
                SectionType.POPULAR,
                tags = listOf(
                    TagUiModel("Funny", "https://test.com/funny", isSensitive = false),
                    TagUiModel("Meme", "https://test.com/meme", isSensitive = false)
                )
            )
        )
        val originalList = TagListModel(
            mapOf(
                TagListModel.LIST_POPULAR to listOf(
                    Tag("Funny", "https://test.com/funny", isSensitive = false),
                    Tag("Meme", "https://test.com/meme", isSensitive = false)
                )
            )
        )

        navTagListUiHandler.unFavItem(
            0, 0,
            uiModels = uiModels,
            originalList,
            emptyList()
        ).also { pair ->
            val (newList, _) = pair

            assertTrue { newList[0].tags.firstOrNull { it.url == removedTagUrl } == null }
            assertTrue { newList[1].tags.firstOrNull { it.url == removedTagUrl } == null }
        }
    }
}