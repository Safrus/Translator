package com.example.hw3

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var yourMessage: TextView
    private lateinit var respondMessage: TextView
    private var backPressedTime: Long = 0
    private lateinit var backToast: Toast
    private var backRespond: String? = null
    private var backYour: String? = null
    private var boolean: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        yourMessage = findViewById(R.id.yourMessage)
        respondMessage = findViewById(R.id.respondMessage)


        sendButton.setOnClickListener {
            if (enteredText.text.toString().trim().isNotEmpty()) {
                yourMessage.text = enteredText.text.toString().trim()
                respondMessage.text = latin(enteredText.text.toString().trim())
                backRespond = respondMessage.text.toString()
                backYour = yourMessage.text.toString()
                yourMessage.visibility = View.VISIBLE
                respondMessage.visibility = View.VISIBLE
                enteredText.text.clear()
                boolean = true
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Aldymen mátіndі engіzіńіz!",
                    Toast.LENGTH_SHORT
                )
                    .show()
                enteredText.text.clear()
            }
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        backRespond = savedInstanceState.getString("respond").toString()
        backYour = savedInstanceState.getString("your").toString()
        boolean = savedInstanceState.getBoolean("boolean")
        if (boolean) {
            respondMessage.text = backRespond
            yourMessage.text = backYour
        } else {
            respondMessage.text = "Qazaqsha - latynsha"
            yourMessage.text = "Қазақша - кириллица"
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("respond", backRespond)
        outState.putString("your", backYour)
        outState.putBoolean("boolean", boolean)
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel()
            super.onBackPressed()
            return
        } else {
            backToast =
                Toast.makeText(this@MainActivity, "Press back again to exit", Toast.LENGTH_SHORT)
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
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
