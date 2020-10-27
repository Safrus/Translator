package com.example.hw5.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hw5.R
import com.example.hw5.model.FavoriteMessages

import kotlinx.android.synthetic.main.item_favorite_message.view.*


class FavoriteMessagesViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_favorite_message, parent, false)) {
    val myFavoriteTextView: TextView = itemView.myFavoriteTextView
    private val respondFavoriteTextView = itemView.respondFavoriteTextView
    val deleteButton: ImageView = itemView.deleteButton

    fun bind(
        item: FavoriteMessages
    ) {
        myFavoriteTextView.text = item.myFavoriteMessage
        respondFavoriteTextView.text = item.respondFavoriteMessage
    }
}