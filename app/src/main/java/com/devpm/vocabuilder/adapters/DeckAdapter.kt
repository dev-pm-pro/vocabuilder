package com.devpm.vocabuilder.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devpm.vocabuilder.R
import com.devpm.vocabuilder.data.models.Deck

class DeckAdapter(private var decks: List<Deck>)
    : RecyclerView.Adapter<DeckAdapter.DeckViewHolder>() {
    inner class DeckViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.idView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleView)
        val uidTextView: TextView = itemView.findViewById(R.id.uidView)
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

    // Метод для обновления данных и обновления списка
    fun updateCards(newCards: List<Deck>) {
        decks = newCards
        notifyDataSetChanged()
    }
}
