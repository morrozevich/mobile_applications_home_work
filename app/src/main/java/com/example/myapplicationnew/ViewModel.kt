package com.example.myapplicationnew

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {

    private val _filteredRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val filteredRecipes: StateFlow<List<Recipe>> = _filteredRecipes

    private var allRecipes: List<Recipe> = emptyList()

    fun setRecipes(recipes: List<Recipe>) {
        allRecipes = recipes
        _filteredRecipes.value = recipes
    }

    fun search(query: String) {
        viewModelScope.launch {
            if (query.length < 3) {
                _filteredRecipes.update { allRecipes } // Show all recipes for short queries
            } else {
                _filteredRecipes.update {
                    allRecipes.filter {
                        it.name.contains(query, ignoreCase = true) ||
                                it.description.contains(query, ignoreCase = true)
                    }
                }
            }
        }
    }
}
