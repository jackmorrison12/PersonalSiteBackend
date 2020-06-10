package com.jackmorrison.personalsitebackend

import java.util.*

data class APIFragment(
    val type: String,
    val imgUrl: String,
    val time: Date,
    val body: String
)
