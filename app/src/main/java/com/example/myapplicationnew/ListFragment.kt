package com.example.myapplicationnew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListFragment : Fragment() {
    private lateinit var credentialsManager: CredentialsManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: RecipeAdapter
    private lateinit var recipeList: List<Recipe>
    private val viewModel: RecipesViewModel by viewModels {
        RecipesViewModel.RecipesViewModelFactory(credentialsManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        credentialsManager = (requireActivity().application as CustomApplication).credentialsManager

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.progressBar)
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RecipeAdapter(emptyList(), object : RecipeAdapter.OnRecipeClickListener {
            override fun onRecipeClick(recipe: Recipe) {
                Toast.makeText(requireContext(), "Recipe clicked: ${recipe.id}", Toast.LENGTH_SHORT).show()
            }

            override fun onLikeClick(recipe: Recipe) {
                Toast.makeText(requireContext(), "Liked: ${recipe.id}", Toast.LENGTH_SHORT).show()
            }

            override fun onShareClick(recipe: Recipe) {
                Toast.makeText(requireContext(), "Shared: ${recipe.id}", Toast.LENGTH_SHORT).show()
            }
        })

        recyclerView.adapter = adapter

        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

        lifecycleScope.launch {
            viewModel.fetchRecipes()
            delay(2000)
            viewModel.setRecipes(getRecipes())
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                progressBar.visibility = if (uiState.isLoading) View.VISIBLE else View.GONE
                recyclerView.visibility = if (uiState.isLoading) View.GONE else View.VISIBLE
                adapter.updateRecipes(uiState.recipes)
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

        logoutButton.setOnClickListener {
            credentialsManager.logout()
            navigateToLogin()
        }
    }

    private fun getRecipes(): List<Recipe> {
        return listOf(
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
    }

    private fun navigateToLogin() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, LoginFragment())
            .commit()
    }
}
