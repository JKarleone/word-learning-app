package com.example.word_learning_app.cardstack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.word_learning_app.R

class CardStackAdapter(private var cards : List<CardModel>) :
    RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(cards[position])
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var word : TextView = view.findViewById(R.id.word)
        private var transcription : TextView = view.findViewById(R.id.word_transcription)
        private var translation : TextView= view.findViewById(R.id.word_translation)
        private var category : TextView = view.findViewById(R.id.word_category)
        private var listName : TextView = view.findViewById(R.id.word_list_name)

        fun setData(data : CardModel) {
            word.text = data.word
            transcription.text = data.transcription
            translation.text = data.translation
            category.text = data.category
            listName.text = data.listName
        }
    }

    fun getCards() : List<CardModel>{
        return cards
    }

    fun setCards(cards: List<CardModel>) {
        this.cards = cards
    }
}