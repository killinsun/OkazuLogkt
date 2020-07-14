package com.killinsun.android.okazulogkt.data

import com.google.firebase.firestore.DocumentSnapshot
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Ignore
import org.mockito.Mockito.mock
import org.w3c.dom.Document

class UserTest {

    private lateinit var user: User

    @Before
    fun setUp() {
        user = User(
            id = "hogehogeTest001x",
            gId = "hogehogeTestGroup001x",
            displayName = "Taro Tanaka",
            email = "test@example.com" ,
            isAdmin = false
        )

    }

    @Ignore
    fun mappingTest() {
        // TODO: DocumentSnapshotの取扱について学んでから書く
    }

    @Test
    fun getByHashMap() {
        val hashMapUser = user.getByHashMap()

        assertEquals(hashMapUser.getValue("userId"), "hogehogeTest001x")
        assertEquals(hashMapUser.getValue("gId"), "hogehogeTestGroup001x")
        assertEquals(hashMapUser.getValue("displayName"), "Taro Tanaka")
        assertEquals(hashMapUser.getValue("email"), "test@example.com")
        assertEquals(hashMapUser.getValue("isAdmin"), false)
    }
}