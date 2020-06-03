package com.killinsun.android.okazulogkt.data

import java.util.*

data class Recipie (
    val id: String = "",
    val name: String = "",
    val lastDate: Date = Date(),
    val count: Int = 0,
    val favorited: Boolean = false,
    val imageSrc: String = "/noimage.jpg"
)

