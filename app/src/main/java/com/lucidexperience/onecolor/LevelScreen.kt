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
import kotlinx.android.synthetic.main.level_screen.*
import android.view.View
import android.widget.Button
import android.widget.PopupWindow


class LevelScreen : AppCompatActivity() {

    val myPreferences = "myPrefs"
    var sharedPreferences: SharedPreferences? = null
    var musicState = false
    val MUSICSTATE = "musicState"
    var music_state_initial = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.level_screen)

        sharedPreferences = this.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val music_State = sharedPreferences!!.getBoolean(MUSICSTATE, music_state_initial)
        musicState = music_State

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
                        creditsPopup.showAtLocation(level_select, Gravity.CENTER, 0, 0)
                        true
                    }
                    else -> false
                }
            }

            popupMenu.inflate(R.menu.options_menu)
            popupMenu.show()
        }
    }

    fun levelClick(view: View) {
        val levelSelected = view as Button
        when(levelSelected){
            back_to_main_btn -> {val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)}
            tutorial_btn -> {val intent = Intent(this, Tutorial::class.java)
                startActivity(intent)}
            level_1_btn -> {val intent = Intent(this, Level1::class.java)
                startActivity(intent)}
            level_2_btn -> {val intent = Intent(this, Level2::class.java)
                startActivity(intent)}
            level_3_btn -> {val intent = Intent(this, Level3::class.java)
                startActivity(intent)}
            level_4_btn -> {val intent = Intent(this, Level4::class.java)
                startActivity(intent)}
            level_5_btn -> {val intent = Intent(this, Level5::class.java)
                startActivity(intent)}
            level_6_btn -> {val intent = Intent(this, Level6::class.java)
                startActivity(intent)}
            level_7_btn -> {val intent = Intent(this, Level7::class.java)
                startActivity(intent)}
            level_8_btn -> {val intent = Intent(this, Level8::class.java)
                startActivity(intent)}
            level_9_btn -> {val intent = Intent(this, Level9::class.java)
                startActivity(intent)}
            level_10_btn -> {val intent = Intent(this, Level10::class.java)
                startActivity(intent)}
            level_11_btn -> {val intent = Intent(this, Level11::class.java)
                startActivity(intent)}
            level_12_btn -> {val intent = Intent(this, Level12::class.java)
                startActivity(intent)}
            level_13_btn -> {val intent = Intent(this, Level13::class.java)
                startActivity(intent)}
            level_14_btn -> {val intent = Intent(this, Level14::class.java)
                startActivity(intent)}
            level_15_btn -> {val intent = Intent(this, Level15::class.java)
                startActivity(intent)}
            level_16_btn -> {val intent = Intent(this, Level16::class.java)
                startActivity(intent)}
            level_17_btn -> {val intent = Intent(this, Level17::class.java)
                startActivity(intent)}
            level_18_btn -> {val intent = Intent(this, Level18::class.java)
                startActivity(intent)}
            level_19_btn -> {val intent = Intent(this, Level19::class.java)
                startActivity(intent)}
            level_20_btn -> {val intent = Intent(this, Level20::class.java)
                startActivity(intent)}
            level_21_btn -> {val intent = Intent(this, Level21::class.java)
                startActivity(intent)}
            level_22_btn -> {val intent = Intent(this, Level22::class.java)
                startActivity(intent)}
            level_23_btn -> {val intent = Intent(this, Level23::class.java)
                startActivity(intent)}
            level_24_btn -> {val intent = Intent(this, Level24::class.java)
                startActivity(intent)}
            level_25_btn -> {val intent = Intent(this, Level25::class.java)
                startActivity(intent)}
            level_26_btn -> {val intent = Intent(this, Level26::class.java)
                startActivity(intent)}
            level_27_btn -> {val intent = Intent(this, Level27::class.java)
                startActivity(intent)}
            level_28_btn -> {val intent = Intent(this, Level28::class.java)
                startActivity(intent)}
            level_29_btn -> {val intent = Intent(this, Level29::class.java)
                startActivity(intent)}
            level_30_btn -> {val intent = Intent(this, Level30::class.java)
                startActivity(intent)}
            level_31_btn -> {val intent = Intent(this, Level31::class.java)
                startActivity(intent)}
            level_32_btn -> {val intent = Intent(this, Level32::class.java)
                startActivity(intent)}
            level_33_btn -> {val intent = Intent(this, Level33::class.java)
                startActivity(intent)}
            level_34_btn -> {val intent = Intent(this, Level34::class.java)
                startActivity(intent)}
            level_35_btn -> {val intent = Intent(this, Level35::class.java)
                startActivity(intent)}
            level_36_btn -> {val intent = Intent(this, Level36::class.java)
                startActivity(intent)}
            level_37_btn -> {val intent = Intent(this, Level37::class.java)
                startActivity(intent)}
            level_38_btn -> {val intent = Intent(this, Level38::class.java)
                startActivity(intent)}
            level_39_btn -> {val intent = Intent(this, Level39::class.java)
                startActivity(intent)}
            level_40_btn -> {val intent = Intent(this, Level40::class.java)
                startActivity(intent)}
            level_41_btn -> {val intent = Intent(this, Level41::class.java)
                startActivity(intent)}
            level_42_btn -> {val intent = Intent(this, Level42::class.java)
                startActivity(intent)}
            level_43_btn -> {val intent = Intent(this, Level43::class.java)
                startActivity(intent)}
            level_44_btn -> {val intent = Intent(this, Level44::class.java)
                startActivity(intent)}
            level_45_btn -> {val intent = Intent(this, Level45::class.java)
                startActivity(intent)}
            level_46_btn -> {val intent = Intent(this, Level46::class.java)
                startActivity(intent)}
            level_47_btn -> {val intent = Intent(this, Level47::class.java)
                startActivity(intent)}
            level_48_btn -> {val intent = Intent(this, Level48::class.java)
                startActivity(intent)}
            level_49_btn -> {val intent = Intent(this, Level49::class.java)
                startActivity(intent)}
            level_50_btn -> {val intent = Intent(this, Level50::class.java)
                startActivity(intent)}
            level_51_btn -> {val intent = Intent(this, Level51::class.java)
                startActivity(intent)}
            level_52_btn -> {val intent = Intent(this, Level52::class.java)
                startActivity(intent)}
            level_53_btn -> {val intent = Intent(this, Level53::class.java)
                startActivity(intent)}
            level_54_btn -> {val intent = Intent(this, Level54::class.java)
                startActivity(intent)}
            level_55_btn -> {val intent = Intent(this, Level55::class.java)
                startActivity(intent)}
            level_56_btn -> {val intent = Intent(this, Level56::class.java)
                startActivity(intent)}
            level_57_btn -> {val intent = Intent(this, Level57::class.java)
                startActivity(intent)}
            level_58_btn -> {val intent = Intent(this, Level58::class.java)
                startActivity(intent)}
            level_59_btn -> {val intent = Intent(this, Level59::class.java)
                startActivity(intent)}
            level_60_btn -> {val intent = Intent(this, Level60::class.java)
                startActivity(intent)}
        }
    }
}