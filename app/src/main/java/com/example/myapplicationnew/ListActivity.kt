package com.example.myapplicationnew

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private lateinit var recipeList: List<Recipe>
    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        recyclerView = findViewById(R.id.recyclerView)
        val searchView = findViewById<SearchView>(R.id.searchView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recipeList = listOf(
            Recipe(1, "Spaghetti Carbonara", "Classic Italian pasta dish with creamy sauce.", R.drawable.placeholder_image),
            Recipe(2, "Caesar Salad", "Crisp romaine lettuce with Caesar dressing.", R.drawable.placeholder_image),
            Recipe(3, "Margherita Pizza", "Pizza topped with fresh tomatoes, mozzarella, and basil.", R.drawable.placeholder_image),
            Recipe(4, "Chicken Tikka Masala", "Rich and creamy curry with tender chicken pieces.", R.drawable.placeholder_image),
            Recipe(5, "Beef Stroganoff", "Sautéed beef in a creamy mushroom sauce.", R.drawable.placeholder_image),
            Recipe(6, "Vegetable Stir Fry", "Mixed vegetables stir-fried in a savory sauce.", R.drawable.placeholder_image),
            Recipe(7, "Chocolate Brownies", "Decadent chocolate dessert with a fudgy texture.", R.drawable.placeholder_image),
            Recipe(8, "Greek Yogurt Parfait", "Layered yogurt, granola, and fresh fruits.", R.drawable.placeholder_image),
            Recipe(9, "Shrimp Scampi", "Garlic butter shrimp served with pasta.", R.drawable.placeholder_image),
            Recipe(10, "French Onion Soup", "Warm and savory soup with caramelized onions and cheese.", R.drawable.placeholder_image)
        )


        adapter = RecipeAdapter(recipeList, object : RecipeAdapter.OnRecipeClickListener {
            override fun onRecipeClick(recipe: Recipe) {
                Toast.makeText(
                    this@ListActivity,
                    "Recipe clicked: ${recipe.id}",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(this@ListActivity, RecipeDetailActivity::class.java)
                println("Passing recipe: $recipe") // Debug log

                intent.putExtra("recipe", recipe)  // Pass the recipe to the detail activity
                startActivity(intent)  // Actually start the RecipeDetailActivity
            }

            override fun onLikeClick(recipe: Recipe) {
                Toast.makeText(this@ListActivity, "Liked: ${recipe.id}", Toast.LENGTH_SHORT).show()
            }

            override fun onShareClick(recipe: Recipe) {
                Toast.makeText(this@ListActivity, "Shared: ${recipe.id}", Toast.LENGTH_SHORT).show()
            }
        })
        recyclerView.adapter = adapter

        viewModel.setRecipes(recipeList)
        lifecycleScope.launch {
            viewModel.filteredRecipes.collectLatest { filteredRecipes ->
                adapter.updateRecipes(filteredRecipes)
            }
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText.orEmpty())
                return true
            }
        })

    }
}
