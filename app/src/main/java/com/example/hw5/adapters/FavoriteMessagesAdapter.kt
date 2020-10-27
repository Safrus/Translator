package com.example.hw5.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hw5.model.FavoriteMessages
import com.example.hw5.viewHolders.FavoriteMessagesViewHolder
import com.google.android.material.snackbar.Snackbar


class FavoriteMessagesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        var favoriteMessages = mutableListOf<FavoriteMessages>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FavoriteMessagesViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = favoriteMessages.size

    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FavoriteMessagesViewHolder).bind(favoriteMessages[position])
        val text: String = holder.myFavoriteTextView.text.toString()
        holder.deleteButton.setOnClickListener {
            val favoriteMessage: FavoriteMessages = favoriteMessages[position]
            favoriteMessages.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, favoriteMessages.size)
            val snackbar: Snackbar = Snackbar.make(
                it,
                HtmlCompat.fromHtml("<b>Oshirildi:</b> $text", HtmlCompat.FROM_HTML_MODE_LEGACY),
                Snackbar.LENGTH_LONG
            )
            snackbar.setAction(
                HtmlCompat.fromHtml(
                    "Keri qaitaru",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            ) {
                favoriteMessages.add(position, favoriteMessage)
                notifyItemInserted(position)
                notifyItemRangeChanged(position, favoriteMessages.size)
                val snackbar: Snackbar = Snackbar.make(
                    it,
                    HtmlCompat.fromHtml(
                        "<b>Message returned!</b>",
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    ),
                    Snackbar.LENGTH_SHORT
                )
                val snackBarView = snackbar.view
                val tv: TextView =
                    snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
                tv.textSize = 16F
                tv.maxLines = 1
                tv.setTextColor(Color.parseColor("#393737"))
                snackBarView.setBackgroundColor(Color.parseColor("#CCD8D8D8"))
                snackbar.show()
            }
            val snackbarActionTextView: TextView =
                snackbar.view.findViewById(com.google.android.material.R.id.snackbar_action)
            snackbarActionTextView.isAllCaps = false
            snackbarActionTextView.textSize = 16F
            snackbar.setActionTextColor(Color.parseColor("#1789DB"))
            val snackBarView = snackbar.view
            val tv: TextView =
                snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
            tv.textSize = 16F
            tv.maxLines = 1
            tv.setTextColor(Color.parseColor("#393737"))
            snackBarView.setBackgroundColor(Color.parseColor("#CCD8D8D8"))
            snackbar.show()
        }
    }

    fun addItems(myMessage: String, respondMessage: String) {
        favoriteMessages.add(0, FavoriteMessages(myMessage, respondMessage))
        notifyDataSetChanged()
    }
}
