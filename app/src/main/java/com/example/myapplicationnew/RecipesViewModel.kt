package com.example.myapplicationnew

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UiState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList()
)

class RecipesViewModel(
    private val credentialsManager: CredentialsManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private var allRecipes: List<Recipe> = emptyList()

    fun fetchRecipes() {
        _uiState.value = UiState(isLoading = true)
    }

    fun setRecipes(recipes: List<Recipe>) {
        allRecipes = recipes
        _uiState.value = UiState(isLoading = false, recipes = recipes)
    }

    fun search(query: String) {
        if (query.length < 3) {
            _uiState.value = UiState(isLoading = false, recipes = allRecipes)
            return
        }
        _uiState.value = UiState(isLoading = true)
        viewModelScope.launch {
            delay(300)
            val filteredRecipes = allRecipes.filter {
                it.name.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
            }
            delay(2000)
            _uiState.value = UiState(isLoading = false, recipes = filteredRecipes)
        }
    }

    class RecipesViewModelFactory(
        private val credentialsManager: CredentialsManager
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RecipesViewModel(credentialsManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
