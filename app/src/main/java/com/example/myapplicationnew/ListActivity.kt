package com.example.myapplicationnew

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var recipeList: List<Recipe> // List to hold the items

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Example data
        recipeList = listOf(
            Recipe(1, "Recipe 1", "Description of Recipe 1", R.drawable.placeholder_image),
            Recipe(2, "Recipe 2", "Description of Recipe 2", R.drawable.placeholder_image)
        )

        // Set adapter
        // Example code from your ListActivity
        val adapter = RecipeAdapter(recipeList, object : RecipeAdapter.OnRecipeClickListener {
            override fun onRecipeClick(recipe: Recipe) {
                // Handle item click
                Toast.makeText(this@ListActivity, "Recipe clicked: ${recipe.name}", Toast.LENGTH_SHORT).show()
            }

            override fun onLikeClick(recipe: Recipe) {
                // Handle like button click
                Toast.makeText(this@ListActivity, "Liked: ${recipe.name}", Toast.LENGTH_SHORT).show()
            }

            override fun onShareClick(recipe: Recipe) {
                // Handle share button click
                Toast.makeText(this@ListActivity, "Shared: ${recipe.name}", Toast.LENGTH_SHORT).show()
            }
        })
        recyclerView.adapter = adapter

    }
}
