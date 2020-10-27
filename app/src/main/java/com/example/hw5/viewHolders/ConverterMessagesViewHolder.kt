package com.example.hw5.viewHolders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hw5.R
import com.example.hw5.model.ConverterMessages
import kotlinx.android.synthetic.main.item_converter_message.view.*

class ConverterMessagesViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_converter_message, parent, false)) {
    val myMessageTextView: TextView = itemView.myMessage
    val respondMessageTextView: TextView = itemView.respondMessage

    fun bind(
        item: ConverterMessages,
        clickListenerMy: (myMessage: ConverterMessages) -> Unit,
        clickListenerRespond: (respondMessage: ConverterMessages) -> Unit
    ) {
        myMessageTextView.text = item.myMessage
        respondMessageTextView.text = item.respondMessage

        myMessageTextView.setOnClickListener {
            clickListenerMy(item)
        }

        respondMessageTextView.setOnClickListener {
            clickListenerRespond(item)
        }
    }
}