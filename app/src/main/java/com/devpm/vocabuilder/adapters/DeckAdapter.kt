package com.devpm.vocabuilder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devpm.vocabuilder.R
import com.devpm.vocabuilder.data.models.Deck
import androidx.recyclerview.widget.ItemTouchHelper

class DeckAdapter(private var decks: List<Deck>)
    : RecyclerView.Adapter<DeckAdapter.DeckViewHolder>() {
    inner class DeckViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.idView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleView)
        val uidTextView: TextView = itemView.findViewById(R.id.uidView)
    }
    inner class SwipeToDeleteCallback(
        private val adapter: DeckAdapter
    ) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            adapter.removeItem(position) // Реализуйте метод удаления элемента из адаптера и источника данных
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_deck, parent, false)
        return DeckViewHolder(view)
    }

    override fun getItemCount() = decks.size

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        val deck = decks[position]

        holder.idTextView.text = deck.id.toString()
        holder.titleTextView.text = deck.title
        holder.uidTextView.text = deck.userId.toString()
    }

    fun removeItem(position: Int) {
        val mutableDecks = decks.toMutableList()
        mutableDecks.removeAt(position)
        decks = mutableDecks
        notifyItemRemoved(position)
    }

    // Update list data
    fun updateDecks(newCards: List<Deck>) {
        decks = newCards
        notifyDataSetChanged()
    }
}
