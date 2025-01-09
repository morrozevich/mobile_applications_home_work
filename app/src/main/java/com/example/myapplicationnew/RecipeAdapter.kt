package com.example.myapplicationnew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val listener: OnRecipeClickListener
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    interface OnRecipeClickListener {
        fun onRecipeClick(recipe: Recipe)
        fun onLikeClick(recipe: Recipe)
        fun onShareClick(recipe: Recipe)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe, listener)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        private val buttonLike: ImageButton = itemView.findViewById(R.id.buttonLike)
        private val buttonShare: ImageButton = itemView.findViewById(R.id.buttonShare)

        fun bind(recipe: Recipe, listener: OnRecipeClickListener) {
            imageView.setImageResource(recipe.imageResource)
            textViewName.text = recipe.name
            textViewDescription.text = recipe.description

            itemView.setOnClickListener { listener.onRecipeClick(recipe) }
            buttonLike.setOnClickListener { listener.onLikeClick(recipe) }
            buttonShare.setOnClickListener { listener.onShareClick(recipe) }
        }
    }
}
