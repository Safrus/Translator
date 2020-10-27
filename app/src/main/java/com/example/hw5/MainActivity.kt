package com.example.hw5

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hw5.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        var isDark: Boolean = false
    }

    private var selectedFragment: Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        loadTheme()
        if (isDark) {
            super.setTheme(R.style.AppThemeDark)
        } else {
            super.setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_menu.setOnNavigationItemSelectedListener(navListener)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.container,
                TranslatorFragment()
            ).addToBackStack(TranslatorFragment::class.java.simpleName).commit()
        }

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }

    private fun loadTheme() {
        val sharedPref = getSharedPreferences(
            "theme",
            Context.MODE_PRIVATE
        )
        val theme = sharedPref.getBoolean("isDark", false)
        isDark = theme
    }

    private fun replaceFragment(fragment: Fragment) {
        val backStateName = fragment.javaClass.name
        val fragmentPopped: Boolean = supportFragmentManager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped && supportFragmentManager.findFragmentByTag(backStateName) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, backStateName)
                .addToBackStack(backStateName)
                .commit()
        }
    }


    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_translate -> selectedFragment = TranslatorFragment()
                R.id.nav_favorite -> selectedFragment = FavoriteFragment()
                R.id.nav_convert -> selectedFragment = ConverterFragment()
                R.id.nav_search -> selectedFragment = ContentFragment()
                R.id.nav_account -> selectedFragment = AccountFragment()
            }
            selectedFragment?.let { replaceFragment(it) }
            true
        }

}