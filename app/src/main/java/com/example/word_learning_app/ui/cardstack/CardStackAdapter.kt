package com.example.word_learning_app.ui.cardstack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.word_learning_app.MainActivity
import com.example.word_learning_app.R
import com.example.word_learning_app.WordLearningApplication
import com.example.word_learning_app.data.entity.Card
import com.example.word_learning_app.data.entity.Word
import kotlinx.coroutines.launch

class CardStackAdapter(private var cards : List<Card> = emptyList()) :
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
        // Word info
        private var word : TextView = view.findViewById(R.id.word)
        private var transcription : TextView = view.findViewById(R.id.word_transcription)
        private var translation : TextView= view.findViewById(R.id.word_translation)
        // Word status info
        private var wordStatusImg: ImageView = view.findViewById(R.id.word_status_img)
        private var wordStatusName : TextView = view.findViewById(R.id.word_status_name)
        // Word category info
        private var wordListImg: ImageView = view.findViewById(R.id.word_list_image)
        private var wordListName : TextView = view.findViewById(R.id.word_list_name)
        // Buttons
        private var leftButton: Button = view.findViewById(R.id.left_button)
        private var rightButton: Button = view.findViewById(R.id.right_button)

        fun setData(card: Card) {
            // Word info
            this.word.text = card.word
            transcription.text = card.transcription
            translation.text = card.translation

            // Word status info
            wordStatusImg.setImageResource(getStatusImg(card.repeatCount))
            wordStatusName.text = getStatusName(card.repeatCount)

            // Word category info
            wordListImg.setImageResource(getWordListImage(card.categoryImg!!))
            wordListName.text = card.categoryName

            // Buttons
            leftButton.text = getNegativeButtonText(card.repeatCount)
            rightButton.text = getPositiveButtonText(card.repeatCount)
        }

        private fun getStatusImg(status: Int): Int =
            when(status) {
                -1 -> R.drawable.icon_word_status_new_word
                else -> R.drawable.icon_word_status_learning_word
            }

        private fun getStatusName(status: Int): String =
            when(status) {
                -1 -> "Новое слово"
                else -> "Заучивание слова"
            }

        private fun getPositiveButtonText(status: Int): String =
            when(status) {
                -1 -> "Учить"
                else -> "Запомнил"
            }

        private fun getNegativeButtonText(status: Int): String =
            when(status) {
                -1 -> "Не учить"
                else -> "Повторять"
            }

        private fun getWordListImage(img: Int) : Int =
            when (img) {
                1 -> R.drawable.icon_category1
                else -> R.drawable.icon_category1
            }
    }

    fun getCards() : List<Card>{
        return cards
    }

    fun setCards(cards: List<Card>) {
        this.cards = cards
    }
}