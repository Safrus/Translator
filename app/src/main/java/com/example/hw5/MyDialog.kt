package com.example.hw5

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.hw5.ui.FavoriteFragment.Companion.favoriteMessagesAdapter
import kotlinx.android.synthetic.main.dialog_fragment.*


class MyDialog : DialogFragment() {

    var myMessage: String? = null
    var respondMessage: String? = null

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        copyButton.setOnClickListener {
            Toast.makeText(
                activity?.applicationContext,
                "Will be in the future",
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        }
        shareButton.setOnClickListener {
            Toast.makeText(
                activity?.applicationContext,
                "Will be in the future",
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        }
        deleteButton.setOnClickListener {
            Toast.makeText(
                activity?.applicationContext,
                "Will be in the future",
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        }
        likeButton.setOnClickListener {
            myMessage?.let { it1 ->
                respondMessage?.let { it2 ->
                    favoriteMessagesAdapter.addItems(
                        it1,
                        it2
                    )
                }
            }
            Toast.makeText(
                activity?.applicationContext,
                "Liked!",
                Toast.LENGTH_SHORT
            ).show()
            dismiss()
        }

    }

    fun applyMessages(myMessage: String, respondMessage: String) {
        this.myMessage = myMessage
        this.respondMessage = respondMessage
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.dialog_fragment, container, false)
    }
}
