package com.under9.shared.core.util

import kotlin.test.*

class StringExtTest {
    @Test
    fun given_regexPattern_then_ReturnCorrectPosition() {
        // Email pattern, obtained from android
        val pattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+"
        val string = "abc a@b.com def"

        val result = string.findFirstStartAndEndWithGivenPattern(Regex(pattern))
        assertNotNull(result)
        assertTrue("First position is incorrect, expected = 4, current = ${result.first}") { result.first == 4 }
        assertTrue("End position is incorrect, expected = 10, current = ${result.second}") { result.second == 10 }
    }

    @Test
    fun given_listOfItemsWithNonNull_then_ReturnCSVWithAllItems() {
        val items = listOf("a", "b")
        val csv = items.convertToCSVString()
        assertEquals("a,b", csv)
    }

    @Test
    fun given_listOfItemsWithNull_then_ReturnCSVStringWithNullInIt() {
        val items = listOf("a", "b", null)
        val csv = items.convertToCSVString()
        assertEquals("a,b,null", csv)
    }

    @Test
    fun given_string_then_ConvertToLong() {
        val hash1 = "test1".toSimpleStringHash()
        val hash2 = "test2".toSimpleStringHash()
        assertNotEquals(hash1, hash2)
    }

    @Test
    fun given_string_then_AssignASlotWithinGivenRange() {
        val slot1 = "test1".toHashedSlot(100)
        assertTrue(slot1 in 1..100)
    }

    @Test
    fun given_stringWithUrlMixedWithText_then_Identified() {
        val url = "https://test.com/"
        val matchResult = "$url this is the test site".urlMatchFirstResult()
        assertNotNull(matchResult)
        assertTrue("Url found is = ${matchResult.groupValues[0]}, expected = $url") { matchResult.groupValues[0] == url }
    }

    @Test
    fun given_stringWithPureUrl_then_Identified() {
        val url = "https://test.com/"
        val matchResult = url.urlMatchFirstResult()
        assertNotNull(matchResult)
        assertTrue("") { matchResult.groupValues[0] == url }
    }

    @Test
    fun given_stringWithPureUrl_then_IdentifiedThe() {
        val url = "https://example.com/"
        val matchResult = url.urlMatchFirstResult()
        assertNotNull(matchResult)
        assertTrue("") { matchResult.groupValues[0] == url }
    }
}