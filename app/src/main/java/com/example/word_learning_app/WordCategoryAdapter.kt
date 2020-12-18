package com.example.word_learning_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.word_learning_app.data.entity.WordCategory
import com.example.word_learning_app.fragments.ListsFragment

class WordCategoryAdapter(private val fragment: ListsFragment) :
        androidx.recyclerview.widget.ListAdapter<WordCategory, WordCategoryAdapter.ViewHolder>(WORDS_CATEGORY_COMPARATOR){

    interface OnItemLongClickListener {
        fun onItemLongClicked(position: Int): Boolean
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_word_category, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(getItem(position))

        holder.itemView.setOnClickListener {
            fragment.onItemClicked(position)
        }

        holder.itemView.setOnLongClickListener {
            fragment.onItemLongClicked(position)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryName : TextView = itemView.findViewById(R.id.category_name)
        private val categoryIcon : ImageView = itemView.findViewById(R.id.category_icon)
        private val checkBox : CheckBox = itemView.findViewById(R.id.category_check_box)

        fun setData(wordCategory: WordCategory) {
            categoryName.text = wordCategory.name
            categoryIcon.setImageDrawable(itemView.resources.getDrawable(getImage(wordCategory.img)))
            if (wordCategory.chosen)
                checkBox.toggle()
        }

        // TODO("Добавить другие иконки для категорий")
        private fun getImage(img: Int) : Int =
            when (img) {
                1 -> R.drawable.icon_category1
                else -> R.drawable.icon_category1
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