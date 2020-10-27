package com.example.hw5.ui

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hw5.MainActivity.Companion.isDark
import com.example.hw5.R
import com.example.hw5.adapters.FavoriteMessagesAdapter
import com.example.hw5.itemDecorators.FavoriteMessagesItemDecorator
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    companion object {
        val favoriteMessagesAdapter = FavoriteMessagesAdapter()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isDark) {
            toolbar.setLogo(R.drawable.ic_sun)
        }
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.title = "   " + "Unaǵandar tіzіmі"
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_letter -> {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "message/rfc822"
                    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("russafarov222@gmail.com"))
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Avtorǵa hat jazý")
                    intent.setPackage("com.google.android.gm")
                    if (activity?.packageManager?.let { intent.resolveActivity(it) } != null) startActivity(
                        intent
                    ) else Toast.makeText(
                        activity,
                        "Gmail App is not installed",
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                R.id.menu_question_share -> {
                    Toast.makeText(
                        activity,
                        "Share!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    true
                }
                R.id.menu_plane -> {
                    val uri = Uri.parse("smsto:87775663653")
                    val i = Intent(Intent.ACTION_SENDTO, uri)
                    i.setPackage("com.whatsapp")
                    startActivity(i)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
        }
        setupFavoriteMessages()


        val logo: View = toolbar.getChildAt(1)
        logo.setOnClickListener {
            if (isDark) {
                isDark = false
                saveTheme()
                activity?.recreate()
            } else {
                isDark = true
                saveTheme()
                activity?.recreate()
            }
        }
    }

    private fun saveTheme() {
        val sharedPref: SharedPreferences? = activity?.getSharedPreferences(
            "theme",
            AppCompatActivity.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor? = sharedPref?.edit()
        editor?.putBoolean(
            "isDark", isDark
        )
        editor?.apply()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupFavoriteMessages() {
        val favoriteMessageManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        favoriteMessageManager.stackFromEnd = false
        favoriteRecyclerView.setHasFixedSize(true)
        favoriteRecyclerView.itemAnimator = DefaultItemAnimator()
        /*recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (favoriteMessageManager.findFirstVisibleItemPosition() == 0) {
                    floatingActionButtonGone()
                } else {
                    floatingActionButtonVisible()
                }
            }
        })*/
        val itemDecoration = FavoriteMessagesItemDecorator(10, 15)
        favoriteRecyclerView.addItemDecoration(itemDecoration)
        favoriteRecyclerView.apply {
            adapter = favoriteMessagesAdapter
            layoutManager = favoriteMessageManager
        }
    }
}