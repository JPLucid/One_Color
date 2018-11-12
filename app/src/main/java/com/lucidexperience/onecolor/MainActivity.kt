package com.lucidexperience.onecolor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.credits_popup.*


class MainActivity : AppCompatActivity() {

    val myPreferences = "myPrefs"
    var musicState = false
    val MUSICSTATE = "musicState"
    var music_state_initial = false
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playButton.setOnClickListener {
            val intent = Intent(this, LevelScreen::class.java)
            startActivity(intent)
        }

        sharedPreferences = this.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val music_State = sharedPreferences!!.getBoolean(MUSICSTATE, music_state_initial)
        musicState = music_State

        if (musicState == true){
            val svc = Intent(this, BackgroundSoundService::class.java)
            startService(svc)
        }

        menu.setOnClickListener {

            val popupMenu = PopupMenu(this, it)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.music_switch -> {
                        if (musicState == false) {
                            musicState = true
                            music_state_initial = true
                            val svc = Intent(this, BackgroundSoundService::class.java)
                            startService(svc)
                            val editor = sharedPreferences!!.edit()
                            editor.putBoolean(MUSICSTATE, music_state_initial)
                            editor.apply()
                            true
                        } else {
                            musicState = false
                            music_state_initial = false
                            val svc = Intent(this, BackgroundSoundService::class.java)
                            stopService(svc)
                            val editor = sharedPreferences!!.edit()
                            editor.putBoolean(MUSICSTATE, music_state_initial)
                            editor.apply()
                            true
                        }
                    }
                    R.id.credits -> {
                        // Initialize a new layout inflater instance
                        val creditsInflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                        // Inflate a custom view using layout inflater
                        val creditsView = creditsInflater.inflate(R.layout.credits_popup,null)

                        // Initialize a new instance of popup window
                        val creditsPopup = PopupWindow(
                                creditsView, // Custom view to show in popup window
                                ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                                ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
                        )

                        creditsPopup.isFocusable = true
                        creditsPopup.background

                        // Finally, show the popup window on app
                        creditsPopup.showAtLocation(activity_main, Gravity.CENTER, 0, 0)
                        true
                    }
                    else -> false
                }
            }

            popupMenu.inflate(R.menu.options_menu)
            popupMenu.show()
        }
    }
}
