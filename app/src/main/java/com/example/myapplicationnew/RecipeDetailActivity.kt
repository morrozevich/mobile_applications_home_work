package com.example.myapplicationnew

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var imageViewDetail: ImageView
    private lateinit var textViewDetailName: TextView
    private lateinit var textViewDetailDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        imageViewDetail = findViewById(R.id.imageViewDetail)
        textViewDetailName = findViewById(R.id.textViewDetailName)
        textViewDetailDescription = findViewById(R.id.textViewDetailDescription)

        val recipe = intent.getSerializableExtra("recipe") as Recipe

        imageViewDetail.setImageResource(recipe.imageResource)
        textViewDetailName.text = recipe.name
        textViewDetailDescription.text = recipe.description

    }
}
