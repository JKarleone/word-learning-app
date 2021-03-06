package com.example.word_learning_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.word_learning_app.data.entity.WordCategory
import com.example.word_learning_app.fragments.ListsFragment

class WordCategoryAdapter(private val fragment: ListsFragment) :
        androidx.recyclerview.widget.ListAdapter<WordCategory, WordCategoryAdapter.ViewHolder>(WORDS_CATEGORY_COMPARATOR){

    interface OnItemClickListener {
        fun onItemClicked(position: Int): Boolean
        fun onItemLongClicked(position: Int): Boolean
    }

    interface OnCheckBoxClickListener {
        fun onCheckBoxClicked(checkBox: CheckBox, position: Int)
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

        val checkBox: CheckBox = holder.itemView.findViewById(R.id.category_check_box)
        checkBox.setOnClickListener {
            fragment.onCheckBoxClicked(checkBox, position)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryName : TextView = itemView.findViewById(R.id.category_name)
        private val categoryIcon : ImageView = itemView.findViewById(R.id.category_icon)
        private val checkBox : CheckBox = itemView.findViewById(R.id.category_check_box)

        fun setData(wordCategory: WordCategory) {
            categoryName.text = wordCategory.name
            categoryIcon.setImageResource(wordCategory.img)
            checkBox.isChecked = wordCategory.chosen
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