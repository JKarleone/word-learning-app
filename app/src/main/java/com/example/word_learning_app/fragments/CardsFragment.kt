package com.example.word_learning_app.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.example.word_learning_app.ui.cardstack.CardModel
import com.example.word_learning_app.ui.cardstack.CardStackAdapter
import com.example.word_learning_app.ui.cardstack.CardStackCallback
import com.example.word_learning_app.R
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CardsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var manager: CardStackLayoutManager
    private lateinit var adapter: CardStackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        println("onCreate working")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_cards, container, false)

        init(root)
        println("onCreateView working")
        retainInstance = true

        return root
    }

    private fun init(root: View) {
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
            }

            override fun onCardSwiped(direction: Direction?) {
                paginate()
                Toast.makeText(root.context, direction?.name, Toast.LENGTH_SHORT).show()
            }
        }
        manager = CardStackLayoutManager(context, cardStackListener)
        adapter = CardStackAdapter(getList())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator = DefaultItemAnimator()
    }

    private fun paginate() {
        val old : List<CardModel> = adapter.getCards()
        val new = getList()
        val callback = CardStackCallback(old, new)
        val diff = DiffUtil.calculateDiff(callback)
        adapter.setCards(new)
        diff.dispatchUpdatesTo(adapter)
    }

    private fun getList(): List<CardModel> {
        val cards = ArrayList<CardModel>()

        cards.add(CardModel("word", "[w3:d]", "Слово", "Новое слово", "Мой список"))
        cards.add(CardModel("hello", "[hello]", "привет", "Новое слово", "Мой новый список"))

        return cards
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CardsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CardsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}