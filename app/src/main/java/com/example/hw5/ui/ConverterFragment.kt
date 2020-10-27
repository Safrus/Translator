package com.example.hw5.ui

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hw5.MainActivity.Companion.isDark
import com.example.hw5.MyDialog
import com.example.hw5.R
import com.example.hw5.adapters.ConverterMessagesAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_converter.*


class ConverterFragment : Fragment(R.layout.fragment_converter) {

    private var converterMessageManager: LinearLayoutManager? = null

    @RequiresApi(Build.VERSION_CODES.M)
    private val converterMessagesAdapter = ConverterMessagesAdapter(
        clickListenerMy = {
            //openDialog(it.myMessage, it.respondMessage)
            Log.d("my message:", it.myMessage)
            Log.d("respond message:", it.respondMessage)
        },
        clickListenerRespond = {
            //openDialog(it.myMessage, it.respondMessage)
            Log.d("respond message:", it.respondMessage)
            Log.d("my message:", it.myMessage)
        }
    )

    private var scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (converterMessageManager?.findFirstVisibleItemPosition() == 0) {
                floatingActionButtonGone()
            } else {
                floatingActionButtonVisible()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isDark) {
            toolbar.setLogo(R.drawable.ic_sun)
            mainScreenView.setBackgroundResource(R.mipmap.darkfon)
        }
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.title = "   " + "Кириллица - Latynsha"
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
                    // Do something
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
        setupConverterMessages()

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

        sendButton.setOnClickListener {
            if (enteredTextView.text.toString().trim().isNotEmpty()) {
                val myMessage = enteredTextView.text.toString().trim()
                val respondMessage = latin(enteredTextView.text.toString().trim())
                converterMessagesAdapter.addItems(myMessage, respondMessage)
                recyclerView.scrollToPosition(0)
                floatingActionButtonGone()
                enteredTextView.text.clear()
            } else {
                Toast.makeText(
                    activity,
                    "Aldymen mátіndі engіzіńіz!",
                    Toast.LENGTH_SHORT
                )
                    .show()
                enteredTextView.text.clear()
            }
        }

        floatingActionButton.setOnClickListener {
            recyclerView.scrollToPosition(0)
            floatingActionButtonGone()
        }


    }

    override fun onResume() {
        super.onResume()
        recyclerView.addOnScrollListener(scrollListener)
    }

    override fun onPause() {
        recyclerView.removeOnScrollListener(scrollListener)
        super.onPause()
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
    fun openDialog(myMessage: String, respondMessage: String) {
        val myDialog = MyDialog()
        activity?.supportFragmentManager?.let {
            myDialog.show(it, "dialog")
            myDialog.applyMessages(myMessage, respondMessage)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupConverterMessages() {
        converterMessageManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, true)
        converterMessageManager?.let {
            it.stackFromEnd = false
        }
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.apply {
            adapter = converterMessagesAdapter
            layoutManager = converterMessageManager
        }
    }


    private fun floatingActionButtonGone() {
        floatingActionButton.visibility = View.GONE
        //floatingActionButton.animate().alpha(0.0f).duration = 500
        floatingActionButton.animate().translationY(floatingActionButton.height.toFloat())
    }

    private fun floatingActionButtonVisible() {
        floatingActionButton.visibility = View.VISIBLE
        //floatingActionButton.animate().alpha(1.0f).duration = 500
        floatingActionButton.animate().translationY(0F)
    }

    private fun latin(word: String): String {
        val wordChar = word.toCharArray()
        val sb = StringBuilder()

        for (i: Int in wordChar.indices)
            when (wordChar[i]) {
                'А' -> sb.append('A')
                'а' -> sb.append('a')
                'Ә' -> sb.append('Á')
                'ә' -> sb.append('á')
                'Б' -> sb.append('B')
                'б' -> sb.append('b')
                'В' -> sb.append('V')
                'в' -> sb.append('v')
                'Г' -> sb.append('G')
                'г' -> sb.append('g')
                'Ғ' -> sb.append('Ǵ')
                'ғ' -> sb.append('ǵ')
                'Д' -> sb.append('D')
                'д' -> sb.append('d')
                'Е' -> sb.append('E')
                'е' -> sb.append('e')
                'Ё' -> sb.append("Yo")
                'ё' -> sb.append("yo")
                'Ж' -> sb.append('J')
                'ж' -> sb.append('j')
                'З' -> sb.append('Z')
                'з' -> sb.append('z')
                'И' -> sb.append('I')
                'и' -> sb.append('i')
                'Й' -> sb.append('I')
                'й' -> sb.append('i')
                'К' -> sb.append('K')
                'к' -> sb.append('k')
                'Қ' -> sb.append('Q')
                'қ' -> sb.append('q')
                'Л' -> sb.append('L')
                'л' -> sb.append('l')
                'М' -> sb.append('M')
                'м' -> sb.append('m')
                'Н' -> sb.append('N')
                'н' -> sb.append('n')
                'Ң' -> sb.append('Ń')
                'ң' -> sb.append('ń')
                'О' -> sb.append('O')
                'о' -> sb.append('o')
                'Ө' -> sb.append('Ó')
                'ө' -> sb.append('ó')
                'П' -> sb.append('P')
                'п' -> sb.append('p')
                'Р' -> sb.append('R')
                'р' -> sb.append('r')
                'С' -> sb.append('S')
                'с' -> sb.append('s')
                'Т' -> sb.append('T')
                'т' -> sb.append('t')
                'У' -> sb.append('Ý')
                'у' -> sb.append('ý')
                'Ұ' -> sb.append('U')
                'ұ' -> sb.append('u')
                'Ү' -> sb.append('Ú')
                'ү' -> sb.append('ú')
                'Ф' -> sb.append('F')
                'ф' -> sb.append('f')
                'Х' -> sb.append('H')
                'х' -> sb.append('h')
                'Һ' -> sb.append('H')
                'һ' -> sb.append('h')
                'Ц' -> sb.append("Ts")
                'ц' -> sb.append("ts")
                'Ч' -> sb.append("Ch")
                'ч' -> sb.append("ch")
                'Ш' -> sb.append("Sh")
                'ш' -> sb.append("sh")
                'Щ' -> sb.append("Shch")
                'щ' -> sb.append("shch")
                'ъ' -> sb.append('\"')
                'Ы' -> sb.append('Y')
                'ы' -> sb.append('y')
                'І' -> sb.append('I')
                'і' -> sb.append('i')
                'ь' -> sb.append('\'')
                'Э' -> sb.append('E')
                'э' -> sb.append('e')
                'Ю' -> sb.append("Yu")
                'ю' -> sb.append("yu")
                'Я' -> sb.append("Ya")
                'я' -> sb.append("ya")
                else -> {
                    sb.append(wordChar[i])
                }
            }
        return sb.toString()
    }
}