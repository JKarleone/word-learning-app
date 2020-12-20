package com.example.word_learning_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.word_learning_app.data.entity.WordCategory

class ProfileAdapter: ListAdapter<WordCategory, ProfileAdapter.ViewHolder>(WORDS_CATEGORY_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_word_category_progress, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val categoryName : TextView = itemView.findViewById(R.id.profile_category_name)
        private val categoryIcon : ImageView = itemView.findViewById(R.id.profile_category_icon)

        fun setData(wordCategory: WordCategory) {
            categoryName.text = wordCategory.name
            categoryIcon.setImageResource(wordCategory.img)
        }
    }

    companion object {
        private val WORDS_CATEGORY_COMPARATOR = object : DiffUtil.ItemCallback<WordCategory>() {
            override fun areItemsTheSame(oldItem: WordCategory, newItem: WordCategory): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: WordCategory, newItem: WordCategory): Boolean {
                return  oldItem.name == newItem.name &&
                        oldItem.img == newItem.img &&
                        oldItem.chosen == newItem.chosen
            }
        }
    }
}