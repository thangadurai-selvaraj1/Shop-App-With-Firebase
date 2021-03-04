package com.alvin.shopappwithfirebase.data.model

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val imageUrl: String = "",
    val mobile: String = "",
    val gender: String = "",
    val profileCompleted: Int = 0
)
