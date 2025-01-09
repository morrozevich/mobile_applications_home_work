package com.example.myapplicationnew
import java.io.Serializable

data class Recipe(
    val id: Int,
    val name: String,
    val description: String,
    val imageResource: Int
) : Serializable

