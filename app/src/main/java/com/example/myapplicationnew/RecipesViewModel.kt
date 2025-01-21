package com.example.myapplicationnew
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
data class UiState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val error: String? = null
)
class RecipesViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private var allRecipes: List<Recipe> = emptyList()

    fun setRecipes(recipes: List<Recipe>) {
        allRecipes = recipes
        _uiState.value = _uiState.value.copy(recipes = recipes)
    }

    fun search(query: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, recipes = emptyList())
            delay(300)

            if (query.isNotBlank()) {
                delay(1700)
                val filteredRecipes = allRecipes.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.description.contains(query, ignoreCase = true)
                }
                _uiState.value = _uiState.value.copy(isLoading = false, recipes = filteredRecipes)
            } else {
                _uiState.value = _uiState.value.copy(isLoading = false, recipes = allRecipes)
            }
        }
    }
}

