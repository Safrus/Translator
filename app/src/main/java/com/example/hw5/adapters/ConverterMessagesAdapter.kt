package com.example.hw5.adapters

import android.os.Build
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.hw5.R
import com.example.hw5.model.ConverterMessages
import com.example.hw5.ui.FavoriteFragment.Companion.favoriteMessagesAdapter
import com.example.hw5.viewHolders.ConverterMessagesViewHolder

class ConverterMessagesAdapter(
    private val clickListenerMy: (myMessage: ConverterMessages) -> Unit,
    private val clickListenerRespond: (respondMessage: ConverterMessages) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        var converterMessages = mutableListOf<ConverterMessages>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ConverterMessagesViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = converterMessages.size

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ConverterMessagesViewHolder).bind(
            converterMessages[position],
            clickListenerMy,
            clickListenerRespond
        )
        holder.myMessageTextView.setOnClickListener {
            val wrapper = ContextThemeWrapper(it.context, R.style.BasePopupMenu)
            val popupMenu = PopupMenu(wrapper, it)
            popupMenu.gravity = Gravity.END
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_copy -> {
                        Toast.makeText(it.context, "Coming soon!", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_share -> {
                        Toast.makeText(it.context, "Coming soon!", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_delete -> {
                        converterMessages.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, converterMessages.size)
                        Toast.makeText(it.context, "Deleted!", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_like -> {
                        val myMessage = converterMessages[position].myMessage
                        val respondMessage = converterMessages[position].respondMessage
                        favoriteMessagesAdapter.addItems(myMessage, respondMessage)
                        Toast.makeText(it.context, "Liked!", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }

            popupMenu.inflate(R.menu.popup_menu_recycler)

            try {
                val fieldPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldPopup.isAccessible = true
                val mPopup = fieldPopup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e: Exception) {
                Log.e("Main", "Error showing menu icons.", e)
            } finally {
                popupMenu.show()
            }
        }

        holder.respondMessageTextView.setOnClickListener {
            val wrapper = ContextThemeWrapper(it.context, R.style.BasePopupMenu)
            val popupMenu = PopupMenu(wrapper, it)
            popupMenu.gravity = Gravity.TOP
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.menu_copy -> {
                        Toast.makeText(it.context, "Coming soon!", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_share -> {
                        Toast.makeText(it.context, "Coming soon!", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_delete -> {
                        converterMessages.removeAt(position)
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, converterMessages.size)
                        Toast.makeText(it.context, "Deleted!", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_like -> {
                        val myMessage = converterMessages[position].myMessage
                        val respondMessage = converterMessages[position].respondMessage
                        favoriteMessagesAdapter.addItems(myMessage, respondMessage)
                        Toast.makeText(it.context, "Liked!", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }

            popupMenu.inflate(R.menu.popup_menu_recycler)

            try {
                val fieldPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldPopup.isAccessible = true
                val mPopup = fieldPopup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e: Exception) {
                Log.e("Main", "Error showing menu icons.", e)
            } finally {
                popupMenu.show()
            }
        }
    }


    fun addItems(myMessage: String, respondMessage: String) {
        converterMessages.add(0, ConverterMessages(myMessage, respondMessage))
        notifyDataSetChanged()
    }
}