package com.killinsun.android.okazulogkt.data

import com.google.common.primitives.UnsignedInts.toLong
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class RecipieTest {

    private lateinit var recipie: Recipie

    @Before
    fun setUp() {
        recipie = Recipie(
            id = "recipieId001",
            gId = "groupId001",
            name = "おかず001",
            lastDate = Date(),
            count = 1,
            favorited = false,
            imageSrc = "/noimage.jpg",
            detail = "This is a detail",
            categoryId = "categoryid001"
        )
    }

    @Test
    fun getByHashMap() {
        val hashMapRecipie= recipie.getByHashMap()

        assertEquals(hashMapRecipie.getValue("id"), "recipieId001")
        assertEquals(hashMapRecipie.getValue("gId"), "groupId001")
        assertEquals(hashMapRecipie.getValue("name"), "おかず001")
        assertThat(hashMapRecipie.getValue("lastDate"), CoreMatchers.instanceOf(Date::class.java))
        assertEquals(hashMapRecipie.getValue("count"), toLong(1))
        assertEquals(hashMapRecipie.getValue("favorited"), false)
        assertEquals(hashMapRecipie.getValue("imageSrc"), "/noimage.jpg")
        assertEquals(hashMapRecipie.getValue("detail"), "This is a detail")
        assertEquals(hashMapRecipie.getValue("categoryId"), "categoryid001")
    }

    @Test
    fun testToString() {
        val output = recipie.toString()

        assertEquals(output, "ID: recipieId001, " +
                "gId: groupId001, " +
                "Name: おかず001, " +
                "lastDate: ${recipie.lastDate}," +
                "CookCount: 1," +
                "Favorited: false," +
                "ImageSrc: /noimage.jpg, " +
                "Detail: This is a detail, " +
                "CategoryId: categoryid001"
        )
    }
}