package com.killinsun.android.okazulogkt.data

import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import java.util.*

class CategoryTest {

    private lateinit var category: Category

    @Before
    fun setUp() {
        category = Category(
            id = "idHogehoge001",
            gId = "gIdHogehoge001",
            name = "cat001",
            timestamp = Date()
        )
    }

    @Test
    fun getByHashMap() {
        val hashMapCategory= category.getByHashMap()

        assertEquals(hashMapCategory.getValue("id"), "idHogehoge001")
        assertEquals(hashMapCategory.getValue("gId"), "gIdHogehoge001")
        assertEquals(hashMapCategory.getValue("name"), "cat001")
        assertThat(hashMapCategory.getValue("timestamp"), instanceOf(Date::class.java))
    }

    @Test
    fun getPositionById() {
        val categories: MutableList<Category> = mutableListOf()
        categories.add(category)
        categories.add(
            Category(
                id = "idHogehoge002",
                gId = "gIdHogehoge002",
                name = "cat002",
                timestamp = Date()
            )
        )
        categories.add(
            Category(
                id = "idHogehoge003",
                gId = "gIdHogehoge003",
                name = "cat003",
                timestamp = Date()
            )
        )
        val position = category.getPositionById(categories, "idHogehoge002")
        assertEquals(position, 1)
    }

    @Test
    fun getPositionByIdWithPositionZero() {
        val categories: MutableList<Category> = mutableListOf()
        categories.add(category)
        categories.add(
            Category(
                id = "idHogehoge002",
                gId = "gIdHogehoge002",
                name = "cat002",
                timestamp = Date()
            )
        )
        categories.add(
            Category(
                id = "idHogehoge003",
                gId = "gIdHogehoge003",
                name = "cat003",
                timestamp = Date()
            )
        )
        val position = category.getPositionById(categories, "idHogehoge001")
        assertEquals(position, 0)
    }

    @Test
    fun getPositionByIdWithoutCorrectId() {
        val categories: MutableList<Category> = mutableListOf()
        categories.add(category)
        categories.add(
            Category(
                id = "idHogehoge002",
                gId = "gIdHogehoge002",
                name = "cat002",
                timestamp = Date()
            )
        )
        val position = category.getPositionById(categories, "idHogehoge003")
        assertEquals(position, 0)
    }
}