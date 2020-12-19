package com.example.word_learning_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.example.word_learning_app.ui.cardstack.CardStackAdapter
import com.example.word_learning_app.ui.cardstack.CardStackCallback
import com.example.word_learning_app.R
import com.example.word_learning_app.WordLearningApplication
import com.example.word_learning_app.data.CardsViewModel
import com.example.word_learning_app.data.entity.Card
import com.example.word_learning_app.data.repository.CardsRepository
import com.example.word_learning_app.data.repository.WordRepository
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import kotlinx.coroutines.launch

private var count = 0

class CardsFragment : Fragment() {
    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter

    private lateinit var cardsViewModel: CardsViewModel

    private var cards: List<Card> = emptyList()

    private lateinit var currentCard: Card

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_cards, container, false)

        setupCardStackView(root)

        val db = (activity?.application as WordLearningApplication).db
        cardsViewModel = CardsViewModel(CardsRepository(db.cardDao()))
        cardsViewModel.allWordsForCards.observe(requireActivity()) {
            val cardsIsEmpty = cards.isEmpty()
            cards = it
            if (cardsIsEmpty) {
                paginate()
            }
        }

        adapter.setCards(cards)

        retainInstance = true

        return root
    }

    private fun setupCardStackView(root: View) {
        val cardStackView = root.findViewById<CardStackView>(R.id.card_stack_view)
        val cardStackListener = object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {
            }

            override fun onCardRewound() {
            }

            override fun onCardCanceled() {
            }

            override fun onCardAppeared(view: View?, position: Int) {
            }

            override fun onCardDisappeared(view: View?, position: Int) {
                Toast.makeText(requireContext(), "Disappeared + $position", Toast.LENGTH_SHORT).show()
                currentCard = adapter.getCards()[position]
            }

            override fun onCardSwiped(direction: Direction?) {
                if (direction == Direction.Right) {
                    currentCard.repeatCount = currentCard.repeatCount + 1

                    updateCurrentCard()
                } else if (direction == Direction.Left) {
                    if (currentCard.repeatCount == -1) {
                        currentCard.repeatCount = 100

                        updateCurrentCard()
                    }
                }

                Toast.makeText(root.context, "${manager.topPosition} ${adapter.itemCount} ${direction?.name}", Toast.LENGTH_SHORT).show()

                if (manager.topPosition == adapter.itemCount) {
                    paginate()
                }

            }
        }
        Toast.makeText(requireContext(), "Собираю", Toast.LENGTH_SHORT).show()
        manager = CardStackLayoutManager(context, cardStackListener)
        adapter = CardStackAdapter(cards)
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator = DefaultItemAnimator()
    }

    private fun paginate() {
        val old : List<Card> = adapter.getCards()
        val new = old.plus(cards)
        val callback = CardStackCallback(old, new)
        val diff = DiffUtil.calculateDiff(callback)
        adapter.setCards(new)
        diff.dispatchUpdatesTo(adapter)
    }

    private fun updateCurrentCard() {
        lifecycleScope.launch {
            val db = (activity?.application as WordLearningApplication).db

            val cardRepository = CardsRepository(db.cardDao())
            cardRepository.update(currentCard)

            val wordRepository = WordRepository(currentCard.categoryId, db.wordDao())
            wordRepository.update(currentCard.wordId, currentCard.repeatCount)
        }
    }
}