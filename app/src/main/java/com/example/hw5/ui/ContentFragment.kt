package com.example.hw5.ui

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hw5.MainActivity.Companion.isDark
import com.example.hw5.R
import kotlinx.android.synthetic.main.fragment_content.*

class ContentFragment : Fragment(R.layout.fragment_content) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isDark) {
            toolbar.setLogo(R.drawable.ic_sun)
        }
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.title = "   " + "Qyzyqty kontent"
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
        //http://docs.google.com/uc?export=download&id=129IEVOJOvCvR-7Jqg5_bZrTKdegaFUnU    Blinding Lights
        //http://docs.google.com/uc?export=download&id=1AT1SRuJrzszZb5UguIf7wLizo3-UOoiT   Akim
        //http://docs.google.com/uc?export=download&id=1OFv6-Omvb9HxBNdkHsTNXvx_vQYehWky   Starboy
        playButton.setOnClickListener {
            videoPlayerView.play("http://docs.google.com/uc?export=download&id=129IEVOJOvCvR-7Jqg5_bZrTKdegaFUnU")
        }
        resumeButton.setOnClickListener {
            videoPlayerView.resume()
        }
        pauseButton.setOnClickListener {
            videoPlayerView.pause()
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
}