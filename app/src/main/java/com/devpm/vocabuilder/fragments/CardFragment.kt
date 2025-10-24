package com.devpm.vocabuilder.fragments

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
import com.devpm.vocabuilder.data.models.User
import com.devpm.vocabuilder.databinding.FragmentCardBinding
import com.devpm.vocabuilder.databinding.FragmentDecksBinding
import kotlinx.coroutines.CoroutineScope
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
 * Use the [CardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardFragment : Fragment() {
    private var deckId: Int? = null

    private val binding: FragmentCardBinding by lazy {
        FragmentCardBinding.inflate(layoutInflater)
    }

    private val app: App by lazy { requireActivity().application as App }

    private val cardDao by lazy {
        app.db.cardDao()
    }

    private fun addCard() {
        // Validate
        val card = Card(
            term = binding.termView.getValue()!!,
            phonetics = binding.phoneticsView.getValue(),
            definition = binding.definitionView.getValue()!!,
            deckId = deckId!!,
            userId = app.user!!.id,
            created = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        )
        CoroutineScope(Dispatchers.IO).launch {
            cardDao.insertCard(card)

        }
        Toast.makeText(context, "Карточка добавлена",
            Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            deckId = arguments?.getInt("deckId")
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

        binding.addBtn.setOnClickListener {
            addCard()
            (activity as? MainActivity)?.toggleFragment("decks")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}