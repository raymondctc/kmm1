package com.ninegag.app.shared.ui.tag

import com.ninegag.app.shared.data.tag.model.Tag
import com.ninegag.app.shared.data.tag.model.TagListModel
import com.ninegag.app.shared.ui.tag.model.SectionType
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class DrawerNavUiStateMapperTest {
    @Test
    fun given_ListOfFavAndHiddenTags_thenFilterFromPopularAndOtherList() {
        val list = TagListModel(
            mapOf(
                TagListModel.LIST_POPULAR to listOf(
                    Tag("tag 1", "https://example.com/tag1", isSensitive = false),
                    Tag("tag 2", "https://example.com/tag2", isSensitive = false)
                ),
                TagListModel.LIST_OTHER to listOf(
                    Tag("tag 3", "https://example.com/tag3", isSensitive = false),
                    Tag("tag 4", "https://example.com/tag4", isSensitive = false)
                ),
                TagListModel.LIST_FAVOURITE to listOf(
                    Tag("tag 1", "https://example.com/tag1", isSensitive = false)
                ),
                TagListModel.LIST_HIDDEN to listOf(
                    Tag("tag 3", "https://example.com/tag3", isSensitive = false)
                )
            )
        )
        val tagSectionedUiModel = DrawerNavUiStateMapper.mapTo(list)

        assertNotNull(tagSectionedUiModel.uiModels.find { it.sectionType == SectionType.FAVOURITED })
        assertNotNull(tagSectionedUiModel.uiModels.find { it.sectionType == SectionType.HIDDEN })

        // Asserting favoruited/hidden items won't appear in popular and other list again
        assertTrue {
            val popularIdx = tagSectionedUiModel.uiModels.indexOfFirst { it.sectionType == SectionType.POPULAR }
            tagSectionedUiModel.uiModels[popularIdx].tags.find { it.title == "tag 1" } == null
        }

        assertTrue {
            val popularIdx = tagSectionedUiModel.uiModels.indexOfFirst { it.sectionType == SectionType.OTHER }
            tagSectionedUiModel.uiModels[popularIdx].tags.find { it.title == "tag 3" } == null
        }
    }

    @Test
    fun given_ListOfPopularItemsExceededLimit_thenRemainingItemsGoToSpareList() {
        val tagList = mutableListOf<Tag>()
        for (i in 0 until 20) {
            tagList.add(Tag("tag $i", "https://example.com/tag$i", isSensitive = false))
        }

        val list = TagListModel(
            mapOf(
                TagListModel.LIST_POPULAR to tagList
            )
        )

        val maxPopularItems = 10
        val tagSectionedUiModel = DrawerNavUiStateMapper.mapTo(list)
        assertTrue { tagSectionedUiModel.uiModels[0].tags.size == maxPopularItems }
        assertTrue { tagSectionedUiModel.sparedPopularItems.size == maxPopularItems }

        tagSectionedUiModel.uiModels[0].tags.forEachIndexed { index, item ->
            assertTrue { item.title == "tag $index" }
        }

        tagSectionedUiModel.sparedPopularItems.forEachIndexed { index, item ->
            assertTrue { item.title == "tag ${index + maxPopularItems}" }
        }
    }
}