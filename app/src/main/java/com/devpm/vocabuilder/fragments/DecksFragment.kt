package com.devpm.vocabuilder.fragments

import android.util.Log
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.devpm.vocabuilder.App
import com.devpm.vocabuilder.R
import com.devpm.vocabuilder.activities.MainActivity
import com.devpm.vocabuilder.data.models.Card
import com.devpm.vocabuilder.data.models.Deck
import com.devpm.vocabuilder.databinding.FragmentDecksBinding
import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.devpm.vocabuilder.adapters.DeckAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DecksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DecksFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var deckAdapter: DeckAdapter

    private val binding: FragmentDecksBinding by lazy {
        FragmentDecksBinding.inflate(layoutInflater)
    }

    private val app: App by lazy { requireActivity().application as App }

    private val deckDao by lazy {
        app.db.deckDao()
    }

    private fun addDeck() {
        // Validate
        val deck = Deck(
            title = binding.titleView.getValue()!!,
            userId = app.user!!.id,
            created = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
        CoroutineScope(Dispatchers.IO).launch {
            deckDao.insertDeck(deck)

        }
        Toast.makeText(context, "Колода добавлена",
            Toast.LENGTH_SHORT).show()
    }

    private fun loadDecks() {
        lifecycleScope.launch {
            val decks = deckDao.getDecksByUid(app.user!!.id)
            Log.d("DecksFragment", "Loaded decks: $decks")
            deckAdapter.updateDecks(decks)
        }
    }

    private fun goToNewCard() {
        val cardFragment = CardFragment()
        val bundle = Bundle()
        bundle.putInt("deckId", 1)
        cardFragment.arguments = bundle

        (activity as? MainActivity)?.toggleFragment(cardFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.decksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        deckAdapter = DeckAdapter(emptyList(), deckDao)
        binding.decksRecyclerView.adapter = deckAdapter

        // Create callback ojbect from adapter
        val swipeCallback = deckAdapter.SwipeToDeleteCallback(deckAdapter)
        val itemTouchHelper = ItemTouchHelper(swipeCallback)
        // Bind ItemTouchHelper to RecyclerView
        itemTouchHelper.attachToRecyclerView(binding.decksRecyclerView)

        val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.decksRecyclerView.addItemDecoration(dividerItemDecoration)

        loadDecks()

        binding.addCardBtn.setOnClickListener {
            goToNewCard()
        }

        binding.addCardBtn.setOnClickListener {
            goToNewCard()
        }
        binding.addDeckBtn.setOnClickListener {
            addDeck()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DecksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DecksFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
