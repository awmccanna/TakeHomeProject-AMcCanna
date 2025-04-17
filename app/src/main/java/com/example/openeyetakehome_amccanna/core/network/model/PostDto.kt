package com.example.openeyetakehome_amccanna.core.network.model

import com.example.openeyetakehome_amccanna.core.model.Post

data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String = "",
    val body: String = "",
)

fun PostDto.toPost(): Post {
    return Post(
        id = id,
        title = title,
        body = body
    )
}