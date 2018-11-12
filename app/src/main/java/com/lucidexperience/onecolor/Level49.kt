package com.lucidexperience.onecolor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PopupMenu
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import kotlinx.android.synthetic.main.level_49.*
import kotlinx.android.synthetic.main.next_popup.*
import java.util.*

class Level49 : AppCompatActivity() {

    var move_count = 0
    val myPreferences = "myPrefs"
    val HIGH_SCORE = "high_score_level49"
    var high_score = "--"
    var sharedPreferences: SharedPreferences? = null
    var musicState = false
    val MUSICSTATE = "musicState"
    var music_state_initial = false
    val levelLockState = "lockState"
    var level_lock_initial = 1
    var level_unlock = 50
    val btnStack = Stack<Button>()

    fun winnerSelect(v: View){
        val winnerBu = v as Button
        when(winnerBu) {
            retry_btn -> {val intent = Intent(this, Level49::class.java)
                startActivity(intent)}
            back_btn -> {val intent = Intent(this, LevelScreen::class.java)
                startActivity(intent)}
            next_level_btn -> {val intent = Intent(this, Level50::class.java)
                startActivity(intent)}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.level_49)

        sharedPreferences = this.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)

        if (high_score_num.text != null) {
            val highScore = sharedPreferences!!.getString(HIGH_SCORE, high_score)
            high_score_num.text = highScore
        }

        val music_State = sharedPreferences!!.getBoolean(MUSICSTATE, music_state_initial)
        musicState = music_State

        val saved_level_lock = sharedPreferences!!.getInt(levelLockState, level_lock_initial)
        level_lock_initial = saved_level_lock

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
                        creditsPopup.showAtLocation(level_49, Gravity.CENTER, 0, 0)
                        true
                    }
                    else -> false
                }
            }

            popupMenu.inflate(R.menu.options_menu)
            popupMenu.show()
        }

    }

    fun buClick(view: View) {
        val buSelected = view as Button
        btnStack.push(buSelected)

        playGame(buSelected)
    }

    fun undoSelect(view: View){
        if (!btnStack.empty()){
            undoLastMove(btnStack.pop())
            move_count -= 1
            val moves = move_count.toString()
            move_counter_49.text = moves
        } else {
            Toast.makeText(this, "There is nothing to undo.", Toast.LENGTH_LONG).show()
        }
        checkColor()
    }

    private fun playGame(buSelected: Button){
        move_count += 1
        val moves = move_count.toString()
        move_counter_49.text = moves
        checkText(buSelected)
        checkColor()
        checkComplete()
    }

    private fun undoLastMove(buSelected: Button){
        /** If the selected button is green */
        if (buSelected.text == "G") {
            buSelected.text = "Y"

            /** when the selected button is bu1 */
            if (buSelected.id == R.id.bu1) {
                if (bu2.text == "G"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "R"){
                    bu2.text = "G"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "R"
                }
            }
            if (buSelected.id == R.id.bu1) {
                if (bu6.text == "G"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "R"){
                    bu6.text = "G"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "R"
                }
            }

            /** when the selected button is bu2 */
            if (buSelected.id == R.id.bu2) {
                if (bu1.text == "G"){
                    bu1.text = "Y"
                }
                else if (bu1.text == "R"){
                    bu1.text = "G"
                }
                else if (bu1.text == "Y"){
                    bu1.text = "R"
                }
            }
            if (buSelected.id == R.id.bu2) {
                if (bu3.text == "G"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "R"){
                    bu3.text = "G"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "R"
                }
            }
            if (buSelected.id == R.id.bu2) {
                if (bu7.text == "G"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "R"){
                    bu7.text = "G"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "R"
                }
            }

            /** when the selected button is bu3 */
            if (buSelected.id == R.id.bu3) {
                if (bu2.text == "G"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "R"){
                    bu2.text = "G"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "R"
                }
            }
            if (buSelected.id == R.id.bu3) {
                if (bu4.text == "G"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "R"){
                    bu4.text = "G"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "R"
                }
            }
            if (buSelected.id == R.id.bu3) {
                if (bu8.text == "G"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "R"){
                    bu8.text = "G"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "R"
                }
            }

            /** when the selected button is bu4 */
            if (buSelected.id == R.id.bu4) {
                if (bu3.text == "G"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "R"){
                    bu3.text = "G"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "R"
                }
            }
            if (buSelected.id == R.id.bu4) {
                if (bu5.text == "G"){
                    bu5.text = "Y"
                }
                else if (bu5.text == "R"){
                    bu5.text = "G"
                }
                else if (bu5.text == "Y"){
                    bu5.text = "R"
                }
            }
            if (buSelected.id == R.id.bu4) {
                if (bu9.text == "G"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "R"){
                    bu9.text = "G"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "R"
                }
            }

            /** when the selected button is bu5 */
            if (buSelected.id == R.id.bu5) {
                if (bu4.text == "G"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "R"){
                    bu4.text = "G"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "R"
                }
            }
            if (buSelected.id == R.id.bu5) {
                if (bu10.text == "G"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "R"){
                    bu10.text = "G"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "R"
                }
            }

            /** when the selected button is bu6 */
            if (buSelected.id == R.id.bu6) {
                if (bu1.text == "G"){
                    bu1.text = "Y"
                }
                else if (bu1.text == "R"){
                    bu1.text = "G"
                }
                else if (bu1.text == "Y"){
                    bu1.text = "R"
                }
            }
            if (buSelected.id == R.id.bu6) {
                if (bu7.text == "G"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "R"){
                    bu7.text = "G"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "R"
                }
            }
            if (buSelected.id == R.id.bu6) {
                if (bu11.text == "G"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "R"){
                    bu11.text = "G"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "R"
                }
            }

            /** when the selected button is bu7 */
            if (buSelected.id == R.id.bu7) {
                if (bu2.text == "G"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "R"){
                    bu2.text = "G"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "R"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu6.text == "G"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "R"){
                    bu6.text = "G"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "R"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu8.text == "G"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "R"){
                    bu8.text = "G"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "R"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu12.text == "G"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "R"){
                    bu12.text = "G"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "R"
                }
            }

            /** when the selected button is bu8 */
            if (buSelected.id == R.id.bu8) {
                if (bu3.text == "G"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "R"){
                    bu3.text = "G"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "R"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu7.text == "G"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "R"){
                    bu7.text = "G"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "R"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu9.text == "G"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "R"){
                    bu9.text = "G"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "R"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu13.text == "G"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "R"){
                    bu13.text = "G"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "R"
                }
            }

            /** when the selected button is bu9 */
            if (buSelected.id == R.id.bu9) {
                if (bu4.text == "G"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "R"){
                    bu4.text = "G"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "R"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu8.text == "G"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "R"){
                    bu8.text = "G"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "R"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu10.text == "G"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "R"){
                    bu10.text = "G"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "R"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu14.text == "G"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "R"){
                    bu14.text = "G"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "R"
                }
            }

            /** when the selected button is bu10 */
            if (buSelected.id == R.id.bu10) {
                if (bu5.text == "G"){
                    bu5.text = "Y"
                }
                else if (bu5.text == "R"){
                    bu5.text = "G"
                }
                else if (bu5.text == "Y"){
                    bu5.text = "R"
                }
            }
            if (buSelected.id == R.id.bu10) {
                if (bu9.text == "G"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "R"){
                    bu9.text = "G"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "R"
                }
            }
            if (buSelected.id == R.id.bu10) {
                if (bu15.text == "G"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "R"){
                    bu15.text = "G"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "R"
                }
            }

            /** when the selected button is bu11 */
            if (buSelected.id == R.id.bu11) {
                if (bu6.text == "G"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "R"){
                    bu6.text = "G"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "R"
                }
            }
            if (buSelected.id == R.id.bu11) {
                if (bu12.text == "G"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "R"){
                    bu12.text = "G"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "R"
                }
            }
            if (buSelected.id == R.id.bu11) {
                if (bu16.text == "G"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "R"){
                    bu16.text = "G"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "R"
                }
            }

            /** when the selected button is bu12 */
            if (buSelected.id == R.id.bu12) {
                if (bu7.text == "G"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "R"){
                    bu7.text = "G"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "R"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu11.text == "G"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "R"){
                    bu11.text = "G"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "R"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu13.text == "G"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "R"){
                    bu13.text = "G"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "R"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu17.text == "G"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "R"){
                    bu17.text = "G"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "R"
                }
            }

            /** when the selected button is bu13 */
            if (buSelected.id == R.id.bu13) {
                if (bu8.text == "G"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "R"){
                    bu8.text = "G"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "R"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu12.text == "G"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "R"){
                    bu12.text = "G"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "R"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu14.text == "G"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "R"){
                    bu14.text = "G"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "R"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu18.text == "G"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "R"){
                    bu18.text = "G"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "R"
                }
            }

            /** when the selected button is bu14 */
            if (buSelected.id == R.id.bu14) {
                if (bu9.text == "G"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "R"){
                    bu9.text = "G"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "R"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu13.text == "G"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "R"){
                    bu13.text = "G"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "R"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu15.text == "G"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "R"){
                    bu15.text = "G"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "R"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu19.text == "G"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "R"){
                    bu19.text = "G"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "R"
                }
            }

            /** when the selected button is bu15 */
            if (buSelected.id == R.id.bu15) {
                if (bu10.text == "G"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "R"){
                    bu10.text = "G"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "R"
                }
            }
            if (buSelected.id == R.id.bu15) {
                if (bu14.text == "G"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "R"){
                    bu14.text = "G"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "R"
                }
            }
            if (buSelected.id == R.id.bu15) {
                if (bu20.text == "G"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "R"){
                    bu20.text = "G"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "R"
                }
            }

            /** when the selected button is bu16 */
            if (buSelected.id == R.id.bu16) {
                if (bu11.text == "G"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "R"){
                    bu11.text = "G"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "R"
                }
            }
            if (buSelected.id == R.id.bu16) {
                if (bu17.text == "G"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "R"){
                    bu17.text = "G"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "R"
                }
            }
            if (buSelected.id == R.id.bu16) {
                if (bu21.text == "G"){
                    bu21.text = "Y"
                }
                else if (bu21.text == "R"){
                    bu21.text = "G"
                }
                else if (bu21.text == "Y"){
                    bu21.text = "R"
                }
            }

            /** when the selected button is bu17 */
            if (buSelected.id == R.id.bu17) {
                if (bu12.text == "G"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "R"){
                    bu12.text = "G"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "R"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu16.text == "G"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "R"){
                    bu16.text = "G"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "R"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu18.text == "G"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "R"){
                    bu18.text = "G"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "R"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu22.text == "G"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "R"){
                    bu22.text = "G"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "R"
                }
            }

            /** when the selected button is bu18 */
            if (buSelected.id == R.id.bu18) {
                if (bu13.text == "G"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "R"){
                    bu13.text = "G"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "R"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu17.text == "G"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "R"){
                    bu17.text = "G"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "R"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu19.text == "G"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "R"){
                    bu19.text = "G"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "R"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu23.text == "G"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "R"){
                    bu23.text = "G"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "R"
                }
            }

            /** when the selected button is bu19 */
            if (buSelected.id == R.id.bu19) {
                if (bu14.text == "G"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "R"){
                    bu14.text = "G"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "R"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu18.text == "G"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "R"){
                    bu18.text = "G"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "R"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu20.text == "G"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "R"){
                    bu20.text = "G"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "R"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu24.text == "G"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "R"){
                    bu24.text = "G"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "R"
                }
            }

            /** when the selected button is bu20 */
            if (buSelected.id == R.id.bu20) {
                if (bu15.text == "G"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "R"){
                    bu15.text = "G"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "R"
                }
            }
            if (buSelected.id == R.id.bu20) {
                if (bu19.text == "G"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "R"){
                    bu19.text = "G"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "R"
                }
            }
            if (buSelected.id == R.id.bu20) {
                if (bu25.text == "G"){
                    bu25.text = "Y"
                }
                else if (bu25.text == "R"){
                    bu25.text = "G"
                }
                else if (bu25.text == "Y"){
                    bu25.text = "R"
                }
            }

            /** when the selected button is bu21 */
            if (buSelected.id == R.id.bu21) {
                if (bu16.text == "G"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "R"){
                    bu16.text = "G"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "R"
                }
            }
            if (buSelected.id == R.id.bu21) {
                if (bu22.text == "G"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "R"){
                    bu22.text = "G"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "R"
                }
            }

            /** when the selected button is bu22 */
            if (buSelected.id == R.id.bu22) {
                if (bu17.text == "G"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "R"){
                    bu17.text = "G"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "R"
                }
            }
            if (buSelected.id == R.id.bu22) {
                if (bu21.text == "G"){
                    bu21.text = "Y"
                }
                else if (bu21.text == "R"){
                    bu21.text = "G"
                }
                else if (bu21.text == "Y"){
                    bu21.text = "R"
                }
            }
            if (buSelected.id == R.id.bu22) {
                if (bu23.text == "G"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "R"){
                    bu23.text = "G"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "R"
                }
            }

            /** when the selected button is bu23 */
            if (buSelected.id == R.id.bu23) {
                if (bu18.text == "G"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "R"){
                    bu18.text = "G"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "R"
                }
            }
            if (buSelected.id == R.id.bu23) {
                if (bu22.text == "G"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "R"){
                    bu22.text = "G"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "R"
                }
            }
            if (buSelected.id == R.id.bu23) {
                if (bu24.text == "G"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "R"){
                    bu24.text = "G"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "R"
                }
            }

            /** when the selected button is bu24 */
            if (buSelected.id == R.id.bu24) {
                if (bu19.text == "G"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "R"){
                    bu19.text = "G"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "R"
                }
            }
            if (buSelected.id == R.id.bu24) {
                if (bu23.text == "G"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "R"){
                    bu23.text = "G"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "R"
                }
            }
            if (buSelected.id == R.id.bu24) {
                if (bu25.text == "G"){
                    bu25.text = "Y"
                }
                else if (bu25.text == "R"){
                    bu25.text = "G"
                }
                else if (bu25.text == "Y"){
                    bu25.text = "R"
                }
            }

            /** when the selected button is bu25 */
            if (buSelected.id == R.id.bu25) {
                if (bu20.text == "G"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "R"){
                    bu20.text = "G"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "R"
                }
            }
            if (buSelected.id == R.id.bu25) {
                if (bu24.text == "G"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "R"){
                    bu24.text = "G"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "R"
                }
            }
        }
        /** If the selected button is red */
        else if (buSelected.text == "R") {
            buSelected.text = "G"

            /** when the selected button is bu1 */
            if (buSelected.id == R.id.bu1) {
                if (bu2.text == "G"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "R"){
                    bu2.text = "G"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "R"
                }
            }
            if (buSelected.id == R.id.bu1) {
                if (bu6.text == "G"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "R"){
                    bu6.text = "G"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "R"
                }
            }

            /** when the selected button is bu2 */
            if (buSelected.id == R.id.bu2) {
                if (bu1.text == "G"){
                    bu1.text = "Y"
                }
                else if (bu1.text == "R"){
                    bu1.text = "G"
                }
                else if (bu1.text == "Y"){
                    bu1.text = "R"
                }
            }
            if (buSelected.id == R.id.bu2) {
                if (bu3.text == "G"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "R"){
                    bu3.text = "G"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "R"
                }
            }
            if (buSelected.id == R.id.bu2) {
                if (bu7.text == "G"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "R"){
                    bu7.text = "G"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "R"
                }
            }

            /** when the selected button is bu3 */
            if (buSelected.id == R.id.bu3) {
                if (bu2.text == "G"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "R"){
                    bu2.text = "G"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "R"
                }
            }
            if (buSelected.id == R.id.bu3) {
                if (bu4.text == "G"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "R"){
                    bu4.text = "G"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "R"
                }
            }
            if (buSelected.id == R.id.bu3) {
                if (bu8.text == "G"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "R"){
                    bu8.text = "G"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "R"
                }
            }

            /** when the selected button is bu4 */
            if (buSelected.id == R.id.bu4) {
                if (bu3.text == "G"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "R"){
                    bu3.text = "G"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "R"
                }
            }
            if (buSelected.id == R.id.bu4) {
                if (bu5.text == "G"){
                    bu5.text = "Y"
                }
                else if (bu5.text == "R"){
                    bu5.text = "G"
                }
                else if (bu5.text == "Y"){
                    bu5.text = "R"
                }
            }
            if (buSelected.id == R.id.bu4) {
                if (bu9.text == "G"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "R"){
                    bu9.text = "G"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "R"
                }
            }

            /** when the selected button is bu5 */
            if (buSelected.id == R.id.bu5) {
                if (bu4.text == "G"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "R"){
                    bu4.text = "G"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "R"
                }
            }
            if (buSelected.id == R.id.bu5) {
                if (bu10.text == "G"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "R"){
                    bu10.text = "G"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "R"
                }
            }

            /** when the selected button is bu6 */
            if (buSelected.id == R.id.bu6) {
                if (bu1.text == "G"){
                    bu1.text = "Y"
                }
                else if (bu1.text == "R"){
                    bu1.text = "G"
                }
                else if (bu1.text == "Y"){
                    bu1.text = "R"
                }
            }
            if (buSelected.id == R.id.bu6) {
                if (bu7.text == "G"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "R"){
                    bu7.text = "G"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "R"
                }
            }
            if (buSelected.id == R.id.bu6) {
                if (bu11.text == "G"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "R"){
                    bu11.text = "G"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "R"
                }
            }

            /** when the selected button is bu7 */
            if (buSelected.id == R.id.bu7) {
                if (bu2.text == "G"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "R"){
                    bu2.text = "G"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "R"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu6.text == "G"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "R"){
                    bu6.text = "G"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "R"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu8.text == "G"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "R"){
                    bu8.text = "G"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "R"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu12.text == "G"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "R"){
                    bu12.text = "G"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "R"
                }
            }

            /** when the selected button is bu8 */
            if (buSelected.id == R.id.bu8) {
                if (bu3.text == "G"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "R"){
                    bu3.text = "G"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "R"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu7.text == "G"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "R"){
                    bu7.text = "G"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "R"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu9.text == "G"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "R"){
                    bu9.text = "G"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "R"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu13.text == "G"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "R"){
                    bu13.text = "G"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "R"
                }
            }

            /** when the selected button is bu9 */
            if (buSelected.id == R.id.bu9) {
                if (bu4.text == "G"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "R"){
                    bu4.text = "G"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "R"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu8.text == "G"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "R"){
                    bu8.text = "G"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "R"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu10.text == "G"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "R"){
                    bu10.text = "G"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "R"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu14.text == "G"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "R"){
                    bu14.text = "G"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "R"
                }
            }

            /** when the selected button is bu10 */
            if (buSelected.id == R.id.bu10) {
                if (bu5.text == "G"){
                    bu5.text = "Y"
                }
                else if (bu5.text == "R"){
                    bu5.text = "G"
                }
                else if (bu5.text == "Y"){
                    bu5.text = "R"
                }
            }
            if (buSelected.id == R.id.bu10) {
                if (bu9.text == "G"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "R"){
                    bu9.text = "G"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "R"
                }
            }
            if (buSelected.id == R.id.bu10) {
                if (bu15.text == "G"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "R"){
                    bu15.text = "G"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "R"
                }
            }

            /** when the selected button is bu11 */
            if (buSelected.id == R.id.bu11) {
                if (bu6.text == "G"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "R"){
                    bu6.text = "G"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "R"
                }
            }
            if (buSelected.id == R.id.bu11) {
                if (bu12.text == "G"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "R"){
                    bu12.text = "G"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "R"
                }
            }
            if (buSelected.id == R.id.bu11) {
                if (bu16.text == "G"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "R"){
                    bu16.text = "G"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "R"
                }
            }

            /** when the selected button is bu12 */
            if (buSelected.id == R.id.bu12) {
                if (bu7.text == "G"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "R"){
                    bu7.text = "G"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "R"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu11.text == "G"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "R"){
                    bu11.text = "G"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "R"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu13.text == "G"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "R"){
                    bu13.text = "G"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "R"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu17.text == "G"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "R"){
                    bu17.text = "G"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "R"
                }
            }

            /** when the selected button is bu13 */
            if (buSelected.id == R.id.bu13) {
                if (bu8.text == "G"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "R"){
                    bu8.text = "G"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "R"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu12.text == "G"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "R"){
                    bu12.text = "G"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "R"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu14.text == "G"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "R"){
                    bu14.text = "G"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "R"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu18.text == "G"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "R"){
                    bu18.text = "G"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "R"
                }
            }

            /** when the selected button is bu14 */
            if (buSelected.id == R.id.bu14) {
                if (bu9.text == "G"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "R"){
                    bu9.text = "G"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "R"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu13.text == "G"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "R"){
                    bu13.text = "G"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "R"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu15.text == "G"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "R"){
                    bu15.text = "G"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "R"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu19.text == "G"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "R"){
                    bu19.text = "G"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "R"
                }
            }

            /** when the selected button is bu15 */
            if (buSelected.id == R.id.bu15) {
                if (bu10.text == "G"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "R"){
                    bu10.text = "G"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "R"
                }
            }
            if (buSelected.id == R.id.bu15) {
                if (bu14.text == "G"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "R"){
                    bu14.text = "G"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "R"
                }
            }
            if (buSelected.id == R.id.bu15) {
                if (bu20.text == "G"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "R"){
                    bu20.text = "G"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "R"
                }
            }

            /** when the selected button is bu16 */
            if (buSelected.id == R.id.bu16) {
                if (bu11.text == "G"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "R"){
                    bu11.text = "G"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "R"
                }
            }
            if (buSelected.id == R.id.bu16) {
                if (bu17.text == "G"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "R"){
                    bu17.text = "G"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "R"
                }
            }
            if (buSelected.id == R.id.bu16) {
                if (bu21.text == "G"){
                    bu21.text = "Y"
                }
                else if (bu21.text == "R"){
                    bu21.text = "G"
                }
                else if (bu21.text == "Y"){
                    bu21.text = "R"
                }
            }

            /** when the selected button is bu17 */
            if (buSelected.id == R.id.bu17) {
                if (bu12.text == "G"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "R"){
                    bu12.text = "G"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "R"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu16.text == "G"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "R"){
                    bu16.text = "G"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "R"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu18.text == "G"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "R"){
                    bu18.text = "G"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "R"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu22.text == "G"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "R"){
                    bu22.text = "G"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "R"
                }
            }

            /** when the selected button is bu18 */
            if (buSelected.id == R.id.bu18) {
                if (bu13.text == "G"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "R"){
                    bu13.text = "G"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "R"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu17.text == "G"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "R"){
                    bu17.text = "G"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "R"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu19.text == "G"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "R"){
                    bu19.text = "G"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "R"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu23.text == "G"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "R"){
                    bu23.text = "G"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "R"
                }
            }

            /** when the selected button is bu19 */
            if (buSelected.id == R.id.bu19) {
                if (bu14.text == "G"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "R"){
                    bu14.text = "G"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "R"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu18.text == "G"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "R"){
                    bu18.text = "G"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "R"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu20.text == "G"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "R"){
                    bu20.text = "G"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "R"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu24.text == "G"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "R"){
                    bu24.text = "G"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "R"
                }
            }

            /** when the selected button is bu20 */
            if (buSelected.id == R.id.bu20) {
                if (bu15.text == "G"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "R"){
                    bu15.text = "G"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "R"
                }
            }
            if (buSelected.id == R.id.bu20) {
                if (bu19.text == "G"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "R"){
                    bu19.text = "G"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "R"
                }
            }
            if (buSelected.id == R.id.bu20) {
                if (bu25.text == "G"){
                    bu25.text = "Y"
                }
                else if (bu25.text == "R"){
                    bu25.text = "G"
                }
                else if (bu25.text == "Y"){
                    bu25.text = "R"
                }
            }

            /** when the selected button is bu21 */
            if (buSelected.id == R.id.bu21) {
                if (bu16.text == "G"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "R"){
                    bu16.text = "G"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "R"
                }
            }
            if (buSelected.id == R.id.bu21) {
                if (bu22.text == "G"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "R"){
                    bu22.text = "G"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "R"
                }
            }

            /** when the selected button is bu22 */
            if (buSelected.id == R.id.bu22) {
                if (bu17.text == "G"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "R"){
                    bu17.text = "G"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "R"
                }
            }
            if (buSelected.id == R.id.bu22) {
                if (bu21.text == "G"){
                    bu21.text = "Y"
                }
                else if (bu21.text == "R"){
                    bu21.text = "G"
                }
                else if (bu21.text == "Y"){
                    bu21.text = "R"
                }
            }
            if (buSelected.id == R.id.bu22) {
                if (bu23.text == "G"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "R"){
                    bu23.text = "G"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "R"
                }
            }

            /** when the selected button is bu23 */
            if (buSelected.id == R.id.bu23) {
                if (bu18.text == "G"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "R"){
                    bu18.text = "G"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "R"
                }
            }
            if (buSelected.id == R.id.bu23) {
                if (bu22.text == "G"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "R"){
                    bu22.text = "G"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "R"
                }
            }
            if (buSelected.id == R.id.bu23) {
                if (bu24.text == "G"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "R"){
                    bu24.text = "G"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "R"
                }
            }

            /** when the selected button is bu24 */
            if (buSelected.id == R.id.bu24) {
                if (bu19.text == "G"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "R"){
                    bu19.text = "G"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "R"
                }
            }
            if (buSelected.id == R.id.bu24) {
                if (bu23.text == "G"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "R"){
                    bu23.text = "G"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "R"
                }
            }
            if (buSelected.id == R.id.bu24) {
                if (bu25.text == "G"){
                    bu25.text = "Y"
                }
                else if (bu25.text == "R"){
                    bu25.text = "G"
                }
                else if (bu25.text == "Y"){
                    bu25.text = "R"
                }
            }

            /** when the selected button is bu25 */
            if (buSelected.id == R.id.bu25) {
                if (bu20.text == "G"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "R"){
                    bu20.text = "G"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "R"
                }
            }
            if (buSelected.id == R.id.bu25) {
                if (bu24.text == "G"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "R"){
                    bu24.text = "G"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "R"
                }
            }
        }
        /** If the selected button is yellow */
        else if (buSelected.text == "Y") {
            buSelected.text = "R"

            /** when the selected button is bu1 */
            if (buSelected.id == R.id.bu1) {
                if (bu2.text == "G"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "R"){
                    bu2.text = "G"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "R"
                }
            }
            if (buSelected.id == R.id.bu1) {
                if (bu6.text == "G"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "R"){
                    bu6.text = "G"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "R"
                }
            }

            /** when the selected button is bu2 */
            if (buSelected.id == R.id.bu2) {
                if (bu1.text == "G"){
                    bu1.text = "Y"
                }
                else if (bu1.text == "R"){
                    bu1.text = "G"
                }
                else if (bu1.text == "Y"){
                    bu1.text = "R"
                }
            }
            if (buSelected.id == R.id.bu2) {
                if (bu3.text == "G"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "R"){
                    bu3.text = "G"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "R"
                }
            }
            if (buSelected.id == R.id.bu2) {
                if (bu7.text == "G"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "R"){
                    bu7.text = "G"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "R"
                }
            }

            /** when the selected button is bu3 */
            if (buSelected.id == R.id.bu3) {
                if (bu2.text == "G"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "R"){
                    bu2.text = "G"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "R"
                }
            }
            if (buSelected.id == R.id.bu3) {
                if (bu4.text == "G"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "R"){
                    bu4.text = "G"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "R"
                }
            }
            if (buSelected.id == R.id.bu3) {
                if (bu8.text == "G"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "R"){
                    bu8.text = "G"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "R"
                }
            }

            /** when the selected button is bu4 */
            if (buSelected.id == R.id.bu4) {
                if (bu3.text == "G"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "R"){
                    bu3.text = "G"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "R"
                }
            }
            if (buSelected.id == R.id.bu4) {
                if (bu5.text == "G"){
                    bu5.text = "Y"
                }
                else if (bu5.text == "R"){
                    bu5.text = "G"
                }
                else if (bu5.text == "Y"){
                    bu5.text = "R"
                }
            }
            if (buSelected.id == R.id.bu4) {
                if (bu9.text == "G"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "R"){
                    bu9.text = "G"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "R"
                }
            }

            /** when the selected button is bu5 */
            if (buSelected.id == R.id.bu5) {
                if (bu4.text == "G"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "R"){
                    bu4.text = "G"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "R"
                }
            }
            if (buSelected.id == R.id.bu5) {
                if (bu10.text == "G"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "R"){
                    bu10.text = "G"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "R"
                }
            }

            /** when the selected button is bu6 */
            if (buSelected.id == R.id.bu6) {
                if (bu1.text == "G"){
                    bu1.text = "Y"
                }
                else if (bu1.text == "R"){
                    bu1.text = "G"
                }
                else if (bu1.text == "Y"){
                    bu1.text = "R"
                }
            }
            if (buSelected.id == R.id.bu6) {
                if (bu7.text == "G"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "R"){
                    bu7.text = "G"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "R"
                }
            }
            if (buSelected.id == R.id.bu6) {
                if (bu11.text == "G"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "R"){
                    bu11.text = "G"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "R"
                }
            }

            /** when the selected button is bu7 */
            if (buSelected.id == R.id.bu7) {
                if (bu2.text == "G"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "R"){
                    bu2.text = "G"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "R"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu6.text == "G"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "R"){
                    bu6.text = "G"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "R"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu8.text == "G"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "R"){
                    bu8.text = "G"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "R"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu12.text == "G"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "R"){
                    bu12.text = "G"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "R"
                }
            }

            /** when the selected button is bu8 */
            if (buSelected.id == R.id.bu8) {
                if (bu3.text == "G"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "R"){
                    bu3.text = "G"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "R"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu7.text == "G"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "R"){
                    bu7.text = "G"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "R"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu9.text == "G"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "R"){
                    bu9.text = "G"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "R"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu13.text == "G"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "R"){
                    bu13.text = "G"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "R"
                }
            }

            /** when the selected button is bu9 */
            if (buSelected.id == R.id.bu9) {
                if (bu4.text == "G"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "R"){
                    bu4.text = "G"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "R"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu8.text == "G"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "R"){
                    bu8.text = "G"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "R"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu10.text == "G"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "R"){
                    bu10.text = "G"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "R"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu14.text == "G"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "R"){
                    bu14.text = "G"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "R"
                }
            }

            /** when the selected button is bu10 */
            if (buSelected.id == R.id.bu10) {
                if (bu5.text == "G"){
                    bu5.text = "Y"
                }
                else if (bu5.text == "R"){
                    bu5.text = "G"
                }
                else if (bu5.text == "Y"){
                    bu5.text = "R"
                }
            }
            if (buSelected.id == R.id.bu10) {
                if (bu9.text == "G"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "R"){
                    bu9.text = "G"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "R"
                }
            }
            if (buSelected.id == R.id.bu10) {
                if (bu15.text == "G"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "R"){
                    bu15.text = "G"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "R"
                }
            }

            /** when the selected button is bu11 */
            if (buSelected.id == R.id.bu11) {
                if (bu6.text == "G"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "R"){
                    bu6.text = "G"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "R"
                }
            }
            if (buSelected.id == R.id.bu11) {
                if (bu12.text == "G"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "R"){
                    bu12.text = "G"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "R"
                }
            }
            if (buSelected.id == R.id.bu11) {
                if (bu16.text == "G"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "R"){
                    bu16.text = "G"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "R"
                }
            }

            /** when the selected button is bu12 */
            if (buSelected.id == R.id.bu12) {
                if (bu7.text == "G"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "R"){
                    bu7.text = "G"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "R"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu11.text == "G"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "R"){
                    bu11.text = "G"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "R"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu13.text == "G"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "R"){
                    bu13.text = "G"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "R"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu17.text == "G"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "R"){
                    bu17.text = "G"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "R"
                }
            }

            /** when the selected button is bu13 */
            if (buSelected.id == R.id.bu13) {
                if (bu8.text == "G"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "R"){
                    bu8.text = "G"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "R"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu12.text == "G"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "R"){
                    bu12.text = "G"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "R"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu14.text == "G"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "R"){
                    bu14.text = "G"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "R"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu18.text == "G"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "R"){
                    bu18.text = "G"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "R"
                }
            }

            /** when the selected button is bu14 */
            if (buSelected.id == R.id.bu14) {
                if (bu9.text == "G"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "R"){
                    bu9.text = "G"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "R"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu13.text == "G"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "R"){
                    bu13.text = "G"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "R"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu15.text == "G"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "R"){
                    bu15.text = "G"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "R"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu19.text == "G"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "R"){
                    bu19.text = "G"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "R"
                }
            }

            /** when the selected button is bu15 */
            if (buSelected.id == R.id.bu15) {
                if (bu10.text == "G"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "R"){
                    bu10.text = "G"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "R"
                }
            }
            if (buSelected.id == R.id.bu15) {
                if (bu14.text == "G"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "R"){
                    bu14.text = "G"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "R"
                }
            }
            if (buSelected.id == R.id.bu15) {
                if (bu20.text == "G"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "R"){
                    bu20.text = "G"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "R"
                }
            }

            /** when the selected button is bu16 */
            if (buSelected.id == R.id.bu16) {
                if (bu11.text == "G"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "R"){
                    bu11.text = "G"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "R"
                }
            }
            if (buSelected.id == R.id.bu16) {
                if (bu17.text == "G"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "R"){
                    bu17.text = "G"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "R"
                }
            }
            if (buSelected.id == R.id.bu16) {
                if (bu21.text == "G"){
                    bu21.text = "Y"
                }
                else if (bu21.text == "R"){
                    bu21.text = "G"
                }
                else if (bu21.text == "Y"){
                    bu21.text = "R"
                }
            }

            /** when the selected button is bu17 */
            if (buSelected.id == R.id.bu17) {
                if (bu12.text == "G"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "R"){
                    bu12.text = "G"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "R"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu16.text == "G"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "R"){
                    bu16.text = "G"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "R"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu18.text == "G"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "R"){
                    bu18.text = "G"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "R"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu22.text == "G"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "R"){
                    bu22.text = "G"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "R"
                }
            }

            /** when the selected button is bu18 */
            if (buSelected.id == R.id.bu18) {
                if (bu13.text == "G"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "R"){
                    bu13.text = "G"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "R"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu17.text == "G"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "R"){
                    bu17.text = "G"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "R"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu19.text == "G"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "R"){
                    bu19.text = "G"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "R"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu23.text == "G"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "R"){
                    bu23.text = "G"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "R"
                }
            }

            /** when the selected button is bu19 */
            if (buSelected.id == R.id.bu19) {
                if (bu14.text == "G"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "R"){
                    bu14.text = "G"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "R"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu18.text == "G"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "R"){
                    bu18.text = "G"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "R"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu20.text == "G"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "R"){
                    bu20.text = "G"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "R"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu24.text == "G"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "R"){
                    bu24.text = "G"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "R"
                }
            }

            /** when the selected button is bu20 */
            if (buSelected.id == R.id.bu20) {
                if (bu15.text == "G"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "R"){
                    bu15.text = "G"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "R"
                }
            }
            if (buSelected.id == R.id.bu20) {
                if (bu19.text == "G"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "R"){
                    bu19.text = "G"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "R"
                }
            }
            if (buSelected.id == R.id.bu20) {
                if (bu25.text == "G"){
                    bu25.text = "Y"
                }
                else if (bu25.text == "R"){
                    bu25.text = "G"
                }
                else if (bu25.text == "Y"){
                    bu25.text = "R"
                }
            }

            /** when the selected button is bu21 */
            if (buSelected.id == R.id.bu21) {
                if (bu16.text == "G"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "R"){
                    bu16.text = "G"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "R"
                }
            }
            if (buSelected.id == R.id.bu21) {
                if (bu22.text == "G"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "R"){
                    bu22.text = "G"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "R"
                }
            }

            /** when the selected button is bu22 */
            if (buSelected.id == R.id.bu22) {
                if (bu17.text == "G"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "R"){
                    bu17.text = "G"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "R"
                }
            }
            if (buSelected.id == R.id.bu22) {
                if (bu21.text == "G"){
                    bu21.text = "Y"
                }
                else if (bu21.text == "R"){
                    bu21.text = "G"
                }
                else if (bu21.text == "Y"){
                    bu21.text = "R"
                }
            }
            if (buSelected.id == R.id.bu22) {
                if (bu23.text == "G"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "R"){
                    bu23.text = "G"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "R"
                }
            }

            /** when the selected button is bu23 */
            if (buSelected.id == R.id.bu23) {
                if (bu18.text == "G"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "R"){
                    bu18.text = "G"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "R"
                }
            }
            if (buSelected.id == R.id.bu23) {
                if (bu22.text == "G"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "R"){
                    bu22.text = "G"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "R"
                }
            }
            if (buSelected.id == R.id.bu23) {
                if (bu24.text == "G"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "R"){
                    bu24.text = "G"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "R"
                }
            }

            /** when the selected button is bu24 */
            if (buSelected.id == R.id.bu24) {
                if (bu19.text == "G"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "R"){
                    bu19.text = "G"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "R"
                }
            }
            if (buSelected.id == R.id.bu24) {
                if (bu23.text == "G"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "R"){
                    bu23.text = "G"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "R"
                }
            }
            if (buSelected.id == R.id.bu24) {
                if (bu25.text == "G"){
                    bu25.text = "Y"
                }
                else if (bu25.text == "R"){
                    bu25.text = "G"
                }
                else if (bu25.text == "Y"){
                    bu25.text = "R"
                }
            }

            /** when the selected button is bu25 */
            if (buSelected.id == R.id.bu25) {
                if (bu20.text == "G"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "R"){
                    bu20.text = "G"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "R"
                }
            }
            if (buSelected.id == R.id.bu25) {
                if (bu24.text == "G"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "R"){
                    bu24.text = "G"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "R"
                }
            }
        }
    }

    private fun checkText(buSelected: Button){
        /** If the selected button is green */
        if (buSelected.text == "G") {
            buSelected.text = "R"

            /** when the selected button is bu1 */
            if (buSelected.id == R.id.bu1) {
                if (bu2.text == "G"){
                    bu2.text = "R"
                }
                else if (bu2.text == "R"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "G"
                }
            }
            if (buSelected.id == R.id.bu1) {
                if (bu6.text == "G"){
                    bu6.text = "R"
                }
                else if (bu6.text == "R"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "G"
                }
            }

            /** when the selected button is bu2 */
            if (buSelected.id == R.id.bu2) {
                if (bu1.text == "G"){
                    bu1.text = "R"
                }
                else if (bu1.text == "R"){
                    bu1.text = "Y"
                }
                else if (bu1.text == "Y"){
                    bu1.text = "G"
                }
            }
            if (buSelected.id == R.id.bu2) {
                if (bu3.text == "G"){
                    bu3.text = "R"
                }
                else if (bu3.text == "R"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "G"
                }
            }
            if (buSelected.id == R.id.bu2) {
                if (bu7.text == "G"){
                    bu7.text = "R"
                }
                else if (bu7.text == "R"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "G"
                }
            }

            /** when the selected button is bu3 */
            if (buSelected.id == R.id.bu3) {
                if (bu2.text == "G"){
                    bu2.text = "R"
                }
                else if (bu2.text == "R"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "G"
                }
            }
            if (buSelected.id == R.id.bu3) {
                if (bu4.text == "G"){
                    bu4.text = "R"
                }
                else if (bu4.text == "R"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "G"
                }
            }
            if (buSelected.id == R.id.bu3) {
                if (bu8.text == "G"){
                    bu8.text = "R"
                }
                else if (bu8.text == "R"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "G"
                }
            }

            /** when the selected button is bu4 */
            if (buSelected.id == R.id.bu4) {
                if (bu3.text == "G"){
                    bu3.text = "R"
                }
                else if (bu3.text == "R"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "G"
                }
            }
            if (buSelected.id == R.id.bu4) {
                if (bu5.text == "G"){
                    bu5.text = "R"
                }
                else if (bu5.text == "R"){
                    bu5.text = "Y"
                }
                else if (bu5.text == "Y"){
                    bu5.text = "G"
                }
            }
            if (buSelected.id == R.id.bu4) {
                if (bu9.text == "G"){
                    bu9.text = "R"
                }
                else if (bu9.text == "R"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "G"
                }
            }

            /** when the selected button is bu5 */
            if (buSelected.id == R.id.bu5) {
                if (bu4.text == "G"){
                    bu4.text = "R"
                }
                else if (bu4.text == "R"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "G"
                }
            }
            if (buSelected.id == R.id.bu5) {
                if (bu10.text == "G"){
                    bu10.text = "R"
                }
                else if (bu10.text == "R"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "G"
                }
            }

            /** when the selected button is bu6 */
            if (buSelected.id == R.id.bu6) {
                if (bu1.text == "G"){
                    bu1.text = "R"
                }
                else if (bu1.text == "R"){
                    bu1.text = "Y"
                }
                else if (bu1.text == "Y"){
                    bu1.text = "G"
                }
            }
            if (buSelected.id == R.id.bu6) {
                if (bu7.text == "G"){
                    bu7.text = "R"
                }
                else if (bu7.text == "R"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "G"
                }
            }
            if (buSelected.id == R.id.bu6) {
                if (bu11.text == "G"){
                    bu11.text = "R"
                }
                else if (bu11.text == "R"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "G"
                }
            }

            /** when the selected button is bu7 */
            if (buSelected.id == R.id.bu7) {
                if (bu2.text == "G"){
                    bu2.text = "R"
                }
                else if (bu2.text == "R"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "G"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu6.text == "G"){
                    bu6.text = "R"
                }
                else if (bu6.text == "R"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "G"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu8.text == "G"){
                    bu8.text = "R"
                }
                else if (bu8.text == "R"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "G"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu12.text == "G"){
                    bu12.text = "R"
                }
                else if (bu12.text == "R"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "G"
                }
            }

            /** when the selected button is bu8 */
            if (buSelected.id == R.id.bu8) {
                if (bu3.text == "G"){
                    bu3.text = "R"
                }
                else if (bu3.text == "R"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "G"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu7.text == "G"){
                    bu7.text = "R"
                }
                else if (bu7.text == "R"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "G"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu9.text == "G"){
                    bu9.text = "R"
                }
                else if (bu9.text == "R"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "G"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu13.text == "G"){
                    bu13.text = "R"
                }
                else if (bu13.text == "R"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "G"
                }
            }

            /** when the selected button is bu9 */
            if (buSelected.id == R.id.bu9) {
                if (bu4.text == "G"){
                    bu4.text = "R"
                }
                else if (bu4.text == "R"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "G"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu8.text == "G"){
                    bu8.text = "R"
                }
                else if (bu8.text == "R"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "G"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu10.text == "G"){
                    bu10.text = "R"
                }
                else if (bu10.text == "R"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "G"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu14.text == "G"){
                    bu14.text = "R"
                }
                else if (bu14.text == "R"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "G"
                }
            }

            /** when the selected button is bu10 */
            if (buSelected.id == R.id.bu10) {
                if (bu5.text == "G"){
                    bu5.text = "R"
                }
                else if (bu5.text == "R"){
                    bu5.text = "Y"
                }
                else if (bu5.text == "Y"){
                    bu5.text = "G"
                }
            }
            if (buSelected.id == R.id.bu10) {
                if (bu9.text == "G"){
                    bu9.text = "R"
                }
                else if (bu9.text == "R"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "G"
                }
            }
            if (buSelected.id == R.id.bu10) {
                if (bu15.text == "G"){
                    bu15.text = "R"
                }
                else if (bu15.text == "R"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "G"
                }
            }

            /** when the selected button is bu11 */
            if (buSelected.id == R.id.bu11) {
                if (bu6.text == "G"){
                    bu6.text = "R"
                }
                else if (bu6.text == "R"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "G"
                }
            }
            if (buSelected.id == R.id.bu11) {
                if (bu12.text == "G"){
                    bu12.text = "R"
                }
                else if (bu12.text == "R"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "G"
                }
            }
            if (buSelected.id == R.id.bu11) {
                if (bu16.text == "G"){
                    bu16.text = "R"
                }
                else if (bu16.text == "R"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "G"
                }
            }

            /** when the selected button is bu12 */
            if (buSelected.id == R.id.bu12) {
                if (bu7.text == "G"){
                    bu7.text = "R"
                }
                else if (bu7.text == "R"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "G"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu11.text == "G"){
                    bu11.text = "R"
                }
                else if (bu11.text == "R"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "G"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu13.text == "G"){
                    bu13.text = "R"
                }
                else if (bu13.text == "R"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "G"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu17.text == "G"){
                    bu17.text = "R"
                }
                else if (bu17.text == "R"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "G"
                }
            }

            /** when the selected button is bu13 */
            if (buSelected.id == R.id.bu13) {
                if (bu8.text == "G"){
                    bu8.text = "R"
                }
                else if (bu8.text == "R"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "G"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu12.text == "G"){
                    bu12.text = "R"
                }
                else if (bu12.text == "R"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "G"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu14.text == "G"){
                    bu14.text = "R"
                }
                else if (bu14.text == "R"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "G"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu18.text == "G"){
                    bu18.text = "R"
                }
                else if (bu18.text == "R"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "G"
                }
            }

            /** when the selected button is bu14 */
            if (buSelected.id == R.id.bu14) {
                if (bu9.text == "G"){
                    bu9.text = "R"
                }
                else if (bu9.text == "R"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "G"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu13.text == "G"){
                    bu13.text = "R"
                }
                else if (bu13.text == "R"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "G"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu15.text == "G"){
                    bu15.text = "R"
                }
                else if (bu15.text == "R"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "G"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu19.text == "G"){
                    bu19.text = "R"
                }
                else if (bu19.text == "R"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "G"
                }
            }

            /** when the selected button is bu15 */
            if (buSelected.id == R.id.bu15) {
                if (bu10.text == "G"){
                    bu10.text = "R"
                }
                else if (bu10.text == "R"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "G"
                }
            }
            if (buSelected.id == R.id.bu15) {
                if (bu14.text == "G"){
                    bu14.text = "R"
                }
                else if (bu14.text == "R"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "G"
                }
            }
            if (buSelected.id == R.id.bu15) {
                if (bu20.text == "G"){
                    bu20.text = "R"
                }
                else if (bu20.text == "R"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "G"
                }
            }

            /** when the selected button is bu16 */
            if (buSelected.id == R.id.bu16) {
                if (bu11.text == "G"){
                    bu11.text = "R"
                }
                else if (bu11.text == "R"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "G"
                }
            }
            if (buSelected.id == R.id.bu16) {
                if (bu17.text == "G"){
                    bu17.text = "R"
                }
                else if (bu17.text == "R"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "G"
                }
            }
            if (buSelected.id == R.id.bu16) {
                if (bu21.text == "G"){
                    bu21.text = "R"
                }
                else if (bu21.text == "R"){
                    bu21.text = "Y"
                }
                else if (bu21.text == "Y"){
                    bu21.text = "G"
                }
            }

            /** when the selected button is bu17 */
            if (buSelected.id == R.id.bu17) {
                if (bu12.text == "G"){
                    bu12.text = "R"
                }
                else if (bu12.text == "R"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "G"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu16.text == "G"){
                    bu16.text = "R"
                }
                else if (bu16.text == "R"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "G"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu18.text == "G"){
                    bu18.text = "R"
                }
                else if (bu18.text == "R"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "G"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu22.text == "G"){
                    bu22.text = "R"
                }
                else if (bu22.text == "R"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "G"
                }
            }

            /** when the selected button is bu18 */
            if (buSelected.id == R.id.bu18) {
                if (bu13.text == "G"){
                    bu13.text = "R"
                }
                else if (bu13.text == "R"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "G"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu17.text == "G"){
                    bu17.text = "R"
                }
                else if (bu17.text == "R"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "G"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu19.text == "G"){
                    bu19.text = "R"
                }
                else if (bu19.text == "R"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "G"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu23.text == "G"){
                    bu23.text = "R"
                }
                else if (bu23.text == "R"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "G"
                }
            }

            /** when the selected button is bu19 */
            if (buSelected.id == R.id.bu19) {
                if (bu14.text == "G"){
                    bu14.text = "R"
                }
                else if (bu14.text == "R"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "G"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu18.text == "G"){
                    bu18.text = "R"
                }
                else if (bu18.text == "R"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "G"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu20.text == "G"){
                    bu20.text = "R"
                }
                else if (bu20.text == "R"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "G"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu24.text == "G"){
                    bu24.text = "R"
                }
                else if (bu24.text == "R"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "G"
                }
            }

            /** when the selected button is bu20 */
            if (buSelected.id == R.id.bu20) {
                if (bu15.text == "G"){
                    bu15.text = "R"
                }
                else if (bu15.text == "R"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "G"
                }
            }
            if (buSelected.id == R.id.bu20) {
                if (bu19.text == "G"){
                    bu19.text = "R"
                }
                else if (bu19.text == "R"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "G"
                }
            }
            if (buSelected.id == R.id.bu20) {
                if (bu25.text == "G"){
                    bu25.text = "R"
                }
                else if (bu25.text == "R"){
                    bu25.text = "Y"
                }
                else if (bu25.text == "Y"){
                    bu25.text = "G"
                }
            }

            /** when the selected button is bu21 */
            if (buSelected.id == R.id.bu21) {
                if (bu16.text == "G"){
                    bu16.text = "R"
                }
                else if (bu16.text == "R"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "G"
                }
            }
            if (buSelected.id == R.id.bu21) {
                if (bu22.text == "G"){
                    bu22.text = "R"
                }
                else if (bu22.text == "R"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "G"
                }
            }

            /** when the selected button is bu22 */
            if (buSelected.id == R.id.bu22) {
                if (bu17.text == "G"){
                    bu17.text = "R"
                }
                else if (bu17.text == "R"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "G"
                }
            }
            if (buSelected.id == R.id.bu22) {
                if (bu21.text == "G"){
                    bu21.text = "R"
                }
                else if (bu21.text == "R"){
                    bu21.text = "Y"
                }
                else if (bu21.text == "Y"){
                    bu21.text = "G"
                }
            }
            if (buSelected.id == R.id.bu22) {
                if (bu23.text == "G"){
                    bu23.text = "R"
                }
                else if (bu23.text == "R"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "G"
                }
            }

            /** when the selected button is bu23 */
            if (buSelected.id == R.id.bu23) {
                if (bu18.text == "G"){
                    bu18.text = "R"
                }
                else if (bu18.text == "R"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "G"
                }
            }
            if (buSelected.id == R.id.bu23) {
                if (bu22.text == "G"){
                    bu22.text = "R"
                }
                else if (bu22.text == "R"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "G"
                }
            }
            if (buSelected.id == R.id.bu23) {
                if (bu24.text == "G"){
                    bu24.text = "R"
                }
                else if (bu24.text == "R"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "G"
                }
            }

            /** when the selected button is bu24 */
            if (buSelected.id == R.id.bu24) {
                if (bu19.text == "G"){
                    bu19.text = "R"
                }
                else if (bu19.text == "R"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "G"
                }
            }
            if (buSelected.id == R.id.bu24) {
                if (bu23.text == "G"){
                    bu23.text = "R"
                }
                else if (bu23.text == "R"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "G"
                }
            }
            if (buSelected.id == R.id.bu24) {
                if (bu25.text == "G"){
                    bu25.text = "R"
                }
                else if (bu25.text == "R"){
                    bu25.text = "Y"
                }
                else if (bu25.text == "Y"){
                    bu25.text = "G"
                }
            }

            /** when the selected button is bu25 */
            if (buSelected.id == R.id.bu25) {
                if (bu20.text == "G"){
                    bu20.text = "R"
                }
                else if (bu20.text == "R"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "G"
                }
            }
            if (buSelected.id == R.id.bu25) {
                if (bu24.text == "G"){
                    bu24.text = "R"
                }
                else if (bu24.text == "R"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "G"
                }
            }
        }
        /** If the selected button is red */
        else if (buSelected.text == "R") {
            buSelected.text = "Y"

            /** when the selected button is bu1 */
            if (buSelected.id == R.id.bu1) {
                if (bu2.text == "G"){
                    bu2.text = "R"
                }
                else if (bu2.text == "R"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "G"
                }
            }
            if (buSelected.id == R.id.bu1) {
                if (bu6.text == "G"){
                    bu6.text = "R"
                }
                else if (bu6.text == "R"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "G"
                }
            }

            /** when the selected button is bu2 */
            if (buSelected.id == R.id.bu2) {
                if (bu1.text == "G"){
                    bu1.text = "R"
                }
                else if (bu1.text == "R"){
                    bu1.text = "Y"
                }
                else if (bu1.text == "Y"){
                    bu1.text = "G"
                }
            }
            if (buSelected.id == R.id.bu2) {
                if (bu3.text == "G"){
                    bu3.text = "R"
                }
                else if (bu3.text == "R"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "G"
                }
            }
            if (buSelected.id == R.id.bu2) {
                if (bu7.text == "G"){
                    bu7.text = "R"
                }
                else if (bu7.text == "R"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "G"
                }
            }

            /** when the selected button is bu3 */
            if (buSelected.id == R.id.bu3) {
                if (bu2.text == "G"){
                    bu2.text = "R"
                }
                else if (bu2.text == "R"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "G"
                }
            }
            if (buSelected.id == R.id.bu3) {
                if (bu4.text == "G"){
                    bu4.text = "R"
                }
                else if (bu4.text == "R"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "G"
                }
            }
            if (buSelected.id == R.id.bu3) {
                if (bu8.text == "G"){
                    bu8.text = "R"
                }
                else if (bu8.text == "R"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "G"
                }
            }

            /** when the selected button is bu4 */
            if (buSelected.id == R.id.bu4) {
                if (bu3.text == "G"){
                    bu3.text = "R"
                }
                else if (bu3.text == "R"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "G"
                }
            }
            if (buSelected.id == R.id.bu4) {
                if (bu5.text == "G"){
                    bu5.text = "R"
                }
                else if (bu5.text == "R"){
                    bu5.text = "Y"
                }
                else if (bu5.text == "Y"){
                    bu5.text = "G"
                }
            }
            if (buSelected.id == R.id.bu4) {
                if (bu9.text == "G"){
                    bu9.text = "R"
                }
                else if (bu9.text == "R"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "G"
                }
            }

            /** when the selected button is bu5 */
            if (buSelected.id == R.id.bu5) {
                if (bu4.text == "G"){
                    bu4.text = "R"
                }
                else if (bu4.text == "R"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "G"
                }
            }
            if (buSelected.id == R.id.bu5) {
                if (bu10.text == "G"){
                    bu10.text = "R"
                }
                else if (bu10.text == "R"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "G"
                }
            }

            /** when the selected button is bu6 */
            if (buSelected.id == R.id.bu6) {
                if (bu1.text == "G"){
                    bu1.text = "R"
                }
                else if (bu1.text == "R"){
                    bu1.text = "Y"
                }
                else if (bu1.text == "Y"){
                    bu1.text = "G"
                }
            }
            if (buSelected.id == R.id.bu6) {
                if (bu7.text == "G"){
                    bu7.text = "R"
                }
                else if (bu7.text == "R"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "G"
                }
            }
            if (buSelected.id == R.id.bu6) {
                if (bu11.text == "G"){
                    bu11.text = "R"
                }
                else if (bu11.text == "R"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "G"
                }
            }

            /** when the selected button is bu7 */
            if (buSelected.id == R.id.bu7) {
                if (bu2.text == "G"){
                    bu2.text = "R"
                }
                else if (bu2.text == "R"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "G"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu6.text == "G"){
                    bu6.text = "R"
                }
                else if (bu6.text == "R"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "G"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu8.text == "G"){
                    bu8.text = "R"
                }
                else if (bu8.text == "R"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "G"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu12.text == "G"){
                    bu12.text = "R"
                }
                else if (bu12.text == "R"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "G"
                }
            }

            /** when the selected button is bu8 */
            if (buSelected.id == R.id.bu8) {
                if (bu3.text == "G"){
                    bu3.text = "R"
                }
                else if (bu3.text == "R"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "G"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu7.text == "G"){
                    bu7.text = "R"
                }
                else if (bu7.text == "R"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "G"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu9.text == "G"){
                    bu9.text = "R"
                }
                else if (bu9.text == "R"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "G"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu13.text == "G"){
                    bu13.text = "R"
                }
                else if (bu13.text == "R"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "G"
                }
            }

            /** when the selected button is bu9 */
            if (buSelected.id == R.id.bu9) {
                if (bu4.text == "G"){
                    bu4.text = "R"
                }
                else if (bu4.text == "R"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "G"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu8.text == "G"){
                    bu8.text = "R"
                }
                else if (bu8.text == "R"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "G"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu10.text == "G"){
                    bu10.text = "R"
                }
                else if (bu10.text == "R"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "G"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu14.text == "G"){
                    bu14.text = "R"
                }
                else if (bu14.text == "R"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "G"
                }
            }

            /** when the selected button is bu10 */
            if (buSelected.id == R.id.bu10) {
                if (bu5.text == "G"){
                    bu5.text = "R"
                }
                else if (bu5.text == "R"){
                    bu5.text = "Y"
                }
                else if (bu5.text == "Y"){
                    bu5.text = "G"
                }
            }
            if (buSelected.id == R.id.bu10) {
                if (bu9.text == "G"){
                    bu9.text = "R"
                }
                else if (bu9.text == "R"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "G"
                }
            }
            if (buSelected.id == R.id.bu10) {
                if (bu15.text == "G"){
                    bu15.text = "R"
                }
                else if (bu15.text == "R"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "G"
                }
            }

            /** when the selected button is bu11 */
            if (buSelected.id == R.id.bu11) {
                if (bu6.text == "G"){
                    bu6.text = "R"
                }
                else if (bu6.text == "R"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "G"
                }
            }
            if (buSelected.id == R.id.bu11) {
                if (bu12.text == "G"){
                    bu12.text = "R"
                }
                else if (bu12.text == "R"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "G"
                }
            }
            if (buSelected.id == R.id.bu11) {
                if (bu16.text == "G"){
                    bu16.text = "R"
                }
                else if (bu16.text == "R"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "G"
                }
            }

            /** when the selected button is bu12 */
            if (buSelected.id == R.id.bu12) {
                if (bu7.text == "G"){
                    bu7.text = "R"
                }
                else if (bu7.text == "R"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "G"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu11.text == "G"){
                    bu11.text = "R"
                }
                else if (bu11.text == "R"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "G"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu13.text == "G"){
                    bu13.text = "R"
                }
                else if (bu13.text == "R"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "G"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu17.text == "G"){
                    bu17.text = "R"
                }
                else if (bu17.text == "R"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "G"
                }
            }

            /** when the selected button is bu13 */
            if (buSelected.id == R.id.bu13) {
                if (bu8.text == "G"){
                    bu8.text = "R"
                }
                else if (bu8.text == "R"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "G"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu12.text == "G"){
                    bu12.text = "R"
                }
                else if (bu12.text == "R"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "G"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu14.text == "G"){
                    bu14.text = "R"
                }
                else if (bu14.text == "R"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "G"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu18.text == "G"){
                    bu18.text = "R"
                }
                else if (bu18.text == "R"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "G"
                }
            }

            /** when the selected button is bu14 */
            if (buSelected.id == R.id.bu14) {
                if (bu9.text == "G"){
                    bu9.text = "R"
                }
                else if (bu9.text == "R"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "G"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu13.text == "G"){
                    bu13.text = "R"
                }
                else if (bu13.text == "R"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "G"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu15.text == "G"){
                    bu15.text = "R"
                }
                else if (bu15.text == "R"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "G"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu19.text == "G"){
                    bu19.text = "R"
                }
                else if (bu19.text == "R"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "G"
                }
            }

            /** when the selected button is bu15 */
            if (buSelected.id == R.id.bu15) {
                if (bu10.text == "G"){
                    bu10.text = "R"
                }
                else if (bu10.text == "R"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "G"
                }
            }
            if (buSelected.id == R.id.bu15) {
                if (bu14.text == "G"){
                    bu14.text = "R"
                }
                else if (bu14.text == "R"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "G"
                }
            }
            if (buSelected.id == R.id.bu15) {
                if (bu20.text == "G"){
                    bu20.text = "R"
                }
                else if (bu20.text == "R"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "G"
                }
            }

            /** when the selected button is bu16 */
            if (buSelected.id == R.id.bu16) {
                if (bu11.text == "G"){
                    bu11.text = "R"
                }
                else if (bu11.text == "R"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "G"
                }
            }
            if (buSelected.id == R.id.bu16) {
                if (bu17.text == "G"){
                    bu17.text = "R"
                }
                else if (bu17.text == "R"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "G"
                }
            }
            if (buSelected.id == R.id.bu16) {
                if (bu21.text == "G"){
                    bu21.text = "R"
                }
                else if (bu21.text == "R"){
                    bu21.text = "Y"
                }
                else if (bu21.text == "Y"){
                    bu21.text = "G"
                }
            }

            /** when the selected button is bu17 */
            if (buSelected.id == R.id.bu17) {
                if (bu12.text == "G"){
                    bu12.text = "R"
                }
                else if (bu12.text == "R"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "G"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu16.text == "G"){
                    bu16.text = "R"
                }
                else if (bu16.text == "R"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "G"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu18.text == "G"){
                    bu18.text = "R"
                }
                else if (bu18.text == "R"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "G"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu22.text == "G"){
                    bu22.text = "R"
                }
                else if (bu22.text == "R"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "G"
                }
            }

            /** when the selected button is bu18 */
            if (buSelected.id == R.id.bu18) {
                if (bu13.text == "G"){
                    bu13.text = "R"
                }
                else if (bu13.text == "R"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "G"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu17.text == "G"){
                    bu17.text = "R"
                }
                else if (bu17.text == "R"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "G"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu19.text == "G"){
                    bu19.text = "R"
                }
                else if (bu19.text == "R"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "G"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu23.text == "G"){
                    bu23.text = "R"
                }
                else if (bu23.text == "R"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "G"
                }
            }

            /** when the selected button is bu19 */
            if (buSelected.id == R.id.bu19) {
                if (bu14.text == "G"){
                    bu14.text = "R"
                }
                else if (bu14.text == "R"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "G"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu18.text == "G"){
                    bu18.text = "R"
                }
                else if (bu18.text == "R"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "G"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu20.text == "G"){
                    bu20.text = "R"
                }
                else if (bu20.text == "R"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "G"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu24.text == "G"){
                    bu24.text = "R"
                }
                else if (bu24.text == "R"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "G"
                }
            }

            /** when the selected button is bu20 */
            if (buSelected.id == R.id.bu20) {
                if (bu15.text == "G"){
                    bu15.text = "R"
                }
                else if (bu15.text == "R"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "G"
                }
            }
            if (buSelected.id == R.id.bu20) {
                if (bu19.text == "G"){
                    bu19.text = "R"
                }
                else if (bu19.text == "R"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "G"
                }
            }
            if (buSelected.id == R.id.bu20) {
                if (bu25.text == "G"){
                    bu25.text = "R"
                }
                else if (bu25.text == "R"){
                    bu25.text = "Y"
                }
                else if (bu25.text == "Y"){
                    bu25.text = "G"
                }
            }

            /** when the selected button is bu21 */
            if (buSelected.id == R.id.bu21) {
                if (bu16.text == "G"){
                    bu16.text = "R"
                }
                else if (bu16.text == "R"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "G"
                }
            }
            if (buSelected.id == R.id.bu21) {
                if (bu22.text == "G"){
                    bu22.text = "R"
                }
                else if (bu22.text == "R"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "G"
                }
            }

            /** when the selected button is bu22 */
            if (buSelected.id == R.id.bu22) {
                if (bu17.text == "G"){
                    bu17.text = "R"
                }
                else if (bu17.text == "R"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "G"
                }
            }
            if (buSelected.id == R.id.bu22) {
                if (bu21.text == "G"){
                    bu21.text = "R"
                }
                else if (bu21.text == "R"){
                    bu21.text = "Y"
                }
                else if (bu21.text == "Y"){
                    bu21.text = "G"
                }
            }
            if (buSelected.id == R.id.bu22) {
                if (bu23.text == "G"){
                    bu23.text = "R"
                }
                else if (bu23.text == "R"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "G"
                }
            }

            /** when the selected button is bu23 */
            if (buSelected.id == R.id.bu23) {
                if (bu18.text == "G"){
                    bu18.text = "R"
                }
                else if (bu18.text == "R"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "G"
                }
            }
            if (buSelected.id == R.id.bu23) {
                if (bu22.text == "G"){
                    bu22.text = "R"
                }
                else if (bu22.text == "R"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "G"
                }
            }
            if (buSelected.id == R.id.bu23) {
                if (bu24.text == "G"){
                    bu24.text = "R"
                }
                else if (bu24.text == "R"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "G"
                }
            }

            /** when the selected button is bu24 */
            if (buSelected.id == R.id.bu24) {
                if (bu19.text == "G"){
                    bu19.text = "R"
                }
                else if (bu19.text == "R"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "G"
                }
            }
            if (buSelected.id == R.id.bu24) {
                if (bu23.text == "G"){
                    bu23.text = "R"
                }
                else if (bu23.text == "R"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "G"
                }
            }
            if (buSelected.id == R.id.bu24) {
                if (bu25.text == "G"){
                    bu25.text = "R"
                }
                else if (bu25.text == "R"){
                    bu25.text = "Y"
                }
                else if (bu25.text == "Y"){
                    bu25.text = "G"
                }
            }

            /** when the selected button is bu25 */
            if (buSelected.id == R.id.bu25) {
                if (bu20.text == "G"){
                    bu20.text = "R"
                }
                else if (bu20.text == "R"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "G"
                }
            }
            if (buSelected.id == R.id.bu25) {
                if (bu24.text == "G"){
                    bu24.text = "R"
                }
                else if (bu24.text == "R"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "G"
                }
            }
        }
        /** If the selected button is yellow */
        else if (buSelected.text == "Y") {
            buSelected.text = "G"

            /** when the selected button is bu1 */
            if (buSelected.id == R.id.bu1) {
                if (bu2.text == "G"){
                    bu2.text = "R"
                }
                else if (bu2.text == "R"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "G"
                }
            }
            if (buSelected.id == R.id.bu1) {
                if (bu6.text == "G"){
                    bu6.text = "R"
                }
                else if (bu6.text == "R"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "G"
                }
            }

            /** when the selected button is bu2 */
            if (buSelected.id == R.id.bu2) {
                if (bu1.text == "G"){
                    bu1.text = "R"
                }
                else if (bu1.text == "R"){
                    bu1.text = "Y"
                }
                else if (bu1.text == "Y"){
                    bu1.text = "G"
                }
            }
            if (buSelected.id == R.id.bu2) {
                if (bu3.text == "G"){
                    bu3.text = "R"
                }
                else if (bu3.text == "R"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "G"
                }
            }
            if (buSelected.id == R.id.bu2) {
                if (bu7.text == "G"){
                    bu7.text = "R"
                }
                else if (bu7.text == "R"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "G"
                }
            }

            /** when the selected button is bu3 */
            if (buSelected.id == R.id.bu3) {
                if (bu2.text == "G"){
                    bu2.text = "R"
                }
                else if (bu2.text == "R"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "G"
                }
            }
            if (buSelected.id == R.id.bu3) {
                if (bu4.text == "G"){
                    bu4.text = "R"
                }
                else if (bu4.text == "R"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "G"
                }
            }
            if (buSelected.id == R.id.bu3) {
                if (bu8.text == "G"){
                    bu8.text = "R"
                }
                else if (bu8.text == "R"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "G"
                }
            }

            /** when the selected button is bu4 */
            if (buSelected.id == R.id.bu4) {
                if (bu3.text == "G"){
                    bu3.text = "R"
                }
                else if (bu3.text == "R"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "G"
                }
            }
            if (buSelected.id == R.id.bu4) {
                if (bu5.text == "G"){
                    bu5.text = "R"
                }
                else if (bu5.text == "R"){
                    bu5.text = "Y"
                }
                else if (bu5.text == "Y"){
                    bu5.text = "G"
                }
            }
            if (buSelected.id == R.id.bu4) {
                if (bu9.text == "G"){
                    bu9.text = "R"
                }
                else if (bu9.text == "R"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "G"
                }
            }

            /** when the selected button is bu5 */
            if (buSelected.id == R.id.bu5) {
                if (bu4.text == "G"){
                    bu4.text = "R"
                }
                else if (bu4.text == "R"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "G"
                }
            }
            if (buSelected.id == R.id.bu5) {
                if (bu10.text == "G"){
                    bu10.text = "R"
                }
                else if (bu10.text == "R"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "G"
                }
            }

            /** when the selected button is bu6 */
            if (buSelected.id == R.id.bu6) {
                if (bu1.text == "G"){
                    bu1.text = "R"
                }
                else if (bu1.text == "R"){
                    bu1.text = "Y"
                }
                else if (bu1.text == "Y"){
                    bu1.text = "G"
                }
            }
            if (buSelected.id == R.id.bu6) {
                if (bu7.text == "G"){
                    bu7.text = "R"
                }
                else if (bu7.text == "R"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "G"
                }
            }
            if (buSelected.id == R.id.bu6) {
                if (bu11.text == "G"){
                    bu11.text = "R"
                }
                else if (bu11.text == "R"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "G"
                }
            }

            /** when the selected button is bu7 */
            if (buSelected.id == R.id.bu7) {
                if (bu2.text == "G"){
                    bu2.text = "R"
                }
                else if (bu2.text == "R"){
                    bu2.text = "Y"
                }
                else if (bu2.text == "Y"){
                    bu2.text = "G"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu6.text == "G"){
                    bu6.text = "R"
                }
                else if (bu6.text == "R"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "G"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu8.text == "G"){
                    bu8.text = "R"
                }
                else if (bu8.text == "R"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "G"
                }
            }
            if (buSelected.id == R.id.bu7) {
                if (bu12.text == "G"){
                    bu12.text = "R"
                }
                else if (bu12.text == "R"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "G"
                }
            }

            /** when the selected button is bu8 */
            if (buSelected.id == R.id.bu8) {
                if (bu3.text == "G"){
                    bu3.text = "R"
                }
                else if (bu3.text == "R"){
                    bu3.text = "Y"
                }
                else if (bu3.text == "Y"){
                    bu3.text = "G"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu7.text == "G"){
                    bu7.text = "R"
                }
                else if (bu7.text == "R"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "G"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu9.text == "G"){
                    bu9.text = "R"
                }
                else if (bu9.text == "R"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "G"
                }
            }
            if (buSelected.id == R.id.bu8) {
                if (bu13.text == "G"){
                    bu13.text = "R"
                }
                else if (bu13.text == "R"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "G"
                }
            }

            /** when the selected button is bu9 */
            if (buSelected.id == R.id.bu9) {
                if (bu4.text == "G"){
                    bu4.text = "R"
                }
                else if (bu4.text == "R"){
                    bu4.text = "Y"
                }
                else if (bu4.text == "Y"){
                    bu4.text = "G"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu8.text == "G"){
                    bu8.text = "R"
                }
                else if (bu8.text == "R"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "G"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu10.text == "G"){
                    bu10.text = "R"
                }
                else if (bu10.text == "R"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "G"
                }
            }
            if (buSelected.id == R.id.bu9) {
                if (bu14.text == "G"){
                    bu14.text = "R"
                }
                else if (bu14.text == "R"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "G"
                }
            }

            /** when the selected button is bu10 */
            if (buSelected.id == R.id.bu10) {
                if (bu5.text == "G"){
                    bu5.text = "R"
                }
                else if (bu5.text == "R"){
                    bu5.text = "Y"
                }
                else if (bu5.text == "Y"){
                    bu5.text = "G"
                }
            }
            if (buSelected.id == R.id.bu10) {
                if (bu9.text == "G"){
                    bu9.text = "R"
                }
                else if (bu9.text == "R"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "G"
                }
            }
            if (buSelected.id == R.id.bu10) {
                if (bu15.text == "G"){
                    bu15.text = "R"
                }
                else if (bu15.text == "R"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "G"
                }
            }

            /** when the selected button is bu11 */
            if (buSelected.id == R.id.bu11) {
                if (bu6.text == "G"){
                    bu6.text = "R"
                }
                else if (bu6.text == "R"){
                    bu6.text = "Y"
                }
                else if (bu6.text == "Y"){
                    bu6.text = "G"
                }
            }
            if (buSelected.id == R.id.bu11) {
                if (bu12.text == "G"){
                    bu12.text = "R"
                }
                else if (bu12.text == "R"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "G"
                }
            }
            if (buSelected.id == R.id.bu11) {
                if (bu16.text == "G"){
                    bu16.text = "R"
                }
                else if (bu16.text == "R"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "G"
                }
            }

            /** when the selected button is bu12 */
            if (buSelected.id == R.id.bu12) {
                if (bu7.text == "G"){
                    bu7.text = "R"
                }
                else if (bu7.text == "R"){
                    bu7.text = "Y"
                }
                else if (bu7.text == "Y"){
                    bu7.text = "G"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu11.text == "G"){
                    bu11.text = "R"
                }
                else if (bu11.text == "R"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "G"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu13.text == "G"){
                    bu13.text = "R"
                }
                else if (bu13.text == "R"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "G"
                }
            }
            if (buSelected.id == R.id.bu12) {
                if (bu17.text == "G"){
                    bu17.text = "R"
                }
                else if (bu17.text == "R"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "G"
                }
            }

            /** when the selected button is bu13 */
            if (buSelected.id == R.id.bu13) {
                if (bu8.text == "G"){
                    bu8.text = "R"
                }
                else if (bu8.text == "R"){
                    bu8.text = "Y"
                }
                else if (bu8.text == "Y"){
                    bu8.text = "G"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu12.text == "G"){
                    bu12.text = "R"
                }
                else if (bu12.text == "R"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "G"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu14.text == "G"){
                    bu14.text = "R"
                }
                else if (bu14.text == "R"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "G"
                }
            }
            if (buSelected.id == R.id.bu13) {
                if (bu18.text == "G"){
                    bu18.text = "R"
                }
                else if (bu18.text == "R"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "G"
                }
            }

            /** when the selected button is bu14 */
            if (buSelected.id == R.id.bu14) {
                if (bu9.text == "G"){
                    bu9.text = "R"
                }
                else if (bu9.text == "R"){
                    bu9.text = "Y"
                }
                else if (bu9.text == "Y"){
                    bu9.text = "G"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu13.text == "G"){
                    bu13.text = "R"
                }
                else if (bu13.text == "R"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "G"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu15.text == "G"){
                    bu15.text = "R"
                }
                else if (bu15.text == "R"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "G"
                }
            }
            if (buSelected.id == R.id.bu14) {
                if (bu19.text == "G"){
                    bu19.text = "R"
                }
                else if (bu19.text == "R"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "G"
                }
            }

            /** when the selected button is bu15 */
            if (buSelected.id == R.id.bu15) {
                if (bu10.text == "G"){
                    bu10.text = "R"
                }
                else if (bu10.text == "R"){
                    bu10.text = "Y"
                }
                else if (bu10.text == "Y"){
                    bu10.text = "G"
                }
            }
            if (buSelected.id == R.id.bu15) {
                if (bu14.text == "G"){
                    bu14.text = "R"
                }
                else if (bu14.text == "R"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "G"
                }
            }
            if (buSelected.id == R.id.bu15) {
                if (bu20.text == "G"){
                    bu20.text = "R"
                }
                else if (bu20.text == "R"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "G"
                }
            }

            /** when the selected button is bu16 */
            if (buSelected.id == R.id.bu16) {
                if (bu11.text == "G"){
                    bu11.text = "R"
                }
                else if (bu11.text == "R"){
                    bu11.text = "Y"
                }
                else if (bu11.text == "Y"){
                    bu11.text = "G"
                }
            }
            if (buSelected.id == R.id.bu16) {
                if (bu17.text == "G"){
                    bu17.text = "R"
                }
                else if (bu17.text == "R"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "G"
                }
            }
            if (buSelected.id == R.id.bu16) {
                if (bu21.text == "G"){
                    bu21.text = "R"
                }
                else if (bu21.text == "R"){
                    bu21.text = "Y"
                }
                else if (bu21.text == "Y"){
                    bu21.text = "G"
                }
            }

            /** when the selected button is bu17 */
            if (buSelected.id == R.id.bu17) {
                if (bu12.text == "G"){
                    bu12.text = "R"
                }
                else if (bu12.text == "R"){
                    bu12.text = "Y"
                }
                else if (bu12.text == "Y"){
                    bu12.text = "G"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu16.text == "G"){
                    bu16.text = "R"
                }
                else if (bu16.text == "R"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "G"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu18.text == "G"){
                    bu18.text = "R"
                }
                else if (bu18.text == "R"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "G"
                }
            }
            if (buSelected.id == R.id.bu17) {
                if (bu22.text == "G"){
                    bu22.text = "R"
                }
                else if (bu22.text == "R"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "G"
                }
            }

            /** when the selected button is bu18 */
            if (buSelected.id == R.id.bu18) {
                if (bu13.text == "G"){
                    bu13.text = "R"
                }
                else if (bu13.text == "R"){
                    bu13.text = "Y"
                }
                else if (bu13.text == "Y"){
                    bu13.text = "G"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu17.text == "G"){
                    bu17.text = "R"
                }
                else if (bu17.text == "R"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "G"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu19.text == "G"){
                    bu19.text = "R"
                }
                else if (bu19.text == "R"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "G"
                }
            }
            if (buSelected.id == R.id.bu18) {
                if (bu23.text == "G"){
                    bu23.text = "R"
                }
                else if (bu23.text == "R"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "G"
                }
            }

            /** when the selected button is bu19 */
            if (buSelected.id == R.id.bu19) {
                if (bu14.text == "G"){
                    bu14.text = "R"
                }
                else if (bu14.text == "R"){
                    bu14.text = "Y"
                }
                else if (bu14.text == "Y"){
                    bu14.text = "G"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu18.text == "G"){
                    bu18.text = "R"
                }
                else if (bu18.text == "R"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "G"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu20.text == "G"){
                    bu20.text = "R"
                }
                else if (bu20.text == "R"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "G"
                }
            }
            if (buSelected.id == R.id.bu19) {
                if (bu24.text == "G"){
                    bu24.text = "R"
                }
                else if (bu24.text == "R"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "G"
                }
            }

            /** when the selected button is bu20 */
            if (buSelected.id == R.id.bu20) {
                if (bu15.text == "G"){
                    bu15.text = "R"
                }
                else if (bu15.text == "R"){
                    bu15.text = "Y"
                }
                else if (bu15.text == "Y"){
                    bu15.text = "G"
                }
            }
            if (buSelected.id == R.id.bu20) {
                if (bu19.text == "G"){
                    bu19.text = "R"
                }
                else if (bu19.text == "R"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "G"
                }
            }
            if (buSelected.id == R.id.bu20) {
                if (bu25.text == "G"){
                    bu25.text = "R"
                }
                else if (bu25.text == "R"){
                    bu25.text = "Y"
                }
                else if (bu25.text == "Y"){
                    bu25.text = "G"
                }
            }

            /** when the selected button is bu21 */
            if (buSelected.id == R.id.bu21) {
                if (bu16.text == "G"){
                    bu16.text = "R"
                }
                else if (bu16.text == "R"){
                    bu16.text = "Y"
                }
                else if (bu16.text == "Y"){
                    bu16.text = "G"
                }
            }
            if (buSelected.id == R.id.bu21) {
                if (bu22.text == "G"){
                    bu22.text = "R"
                }
                else if (bu22.text == "R"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "G"
                }
            }

            /** when the selected button is bu22 */
            if (buSelected.id == R.id.bu22) {
                if (bu17.text == "G"){
                    bu17.text = "R"
                }
                else if (bu17.text == "R"){
                    bu17.text = "Y"
                }
                else if (bu17.text == "Y"){
                    bu17.text = "G"
                }
            }
            if (buSelected.id == R.id.bu22) {
                if (bu21.text == "G"){
                    bu21.text = "R"
                }
                else if (bu21.text == "R"){
                    bu21.text = "Y"
                }
                else if (bu21.text == "Y"){
                    bu21.text = "G"
                }
            }
            if (buSelected.id == R.id.bu22) {
                if (bu23.text == "G"){
                    bu23.text = "R"
                }
                else if (bu23.text == "R"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "G"
                }
            }

            /** when the selected button is bu23 */
            if (buSelected.id == R.id.bu23) {
                if (bu18.text == "G"){
                    bu18.text = "R"
                }
                else if (bu18.text == "R"){
                    bu18.text = "Y"
                }
                else if (bu18.text == "Y"){
                    bu18.text = "G"
                }
            }
            if (buSelected.id == R.id.bu23) {
                if (bu22.text == "G"){
                    bu22.text = "R"
                }
                else if (bu22.text == "R"){
                    bu22.text = "Y"
                }
                else if (bu22.text == "Y"){
                    bu22.text = "G"
                }
            }
            if (buSelected.id == R.id.bu23) {
                if (bu24.text == "G"){
                    bu24.text = "R"
                }
                else if (bu24.text == "R"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "G"
                }
            }

            /** when the selected button is bu24 */
            if (buSelected.id == R.id.bu24) {
                if (bu19.text == "G"){
                    bu19.text = "R"
                }
                else if (bu19.text == "R"){
                    bu19.text = "Y"
                }
                else if (bu19.text == "Y"){
                    bu19.text = "G"
                }
            }
            if (buSelected.id == R.id.bu24) {
                if (bu23.text == "G"){
                    bu23.text = "R"
                }
                else if (bu23.text == "R"){
                    bu23.text = "Y"
                }
                else if (bu23.text == "Y"){
                    bu23.text = "G"
                }
            }
            if (buSelected.id == R.id.bu24) {
                if (bu25.text == "G"){
                    bu25.text = "R"
                }
                else if (bu25.text == "R"){
                    bu25.text = "Y"
                }
                else if (bu25.text == "Y"){
                    bu25.text = "G"
                }
            }

            /** when the selected button is bu25 */
            if (buSelected.id == R.id.bu25) {
                if (bu20.text == "G"){
                    bu20.text = "R"
                }
                else if (bu20.text == "R"){
                    bu20.text = "Y"
                }
                else if (bu20.text == "Y"){
                    bu20.text = "G"
                }
            }
            if (buSelected.id == R.id.bu25) {
                if (bu24.text == "G"){
                    bu24.text = "R"
                }
                else if (bu24.text == "R"){
                    bu24.text = "Y"
                }
                else if (bu24.text == "Y"){
                    bu24.text = "G"
                }
            }
        }
    }

    private fun checkColor(){
        /** setting bu1 colors */
        if (bu1.text == "G"){
            bu1.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu1.text == "R"){
            bu1.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu1.text == "Y"){
            bu1.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu2 colors */
        if (bu2.text == "G"){
            bu2.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu2.text == "R"){
            bu2.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu2.text == "Y"){
            bu2.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu3 colors */
        if (bu3.text == "G"){
            bu3.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu3.text == "R"){
            bu3.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu3.text == "Y"){
            bu3.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu4 colors */
        if (bu4.text == "G"){
            bu4.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu4.text == "R"){
            bu4.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu4.text == "Y"){
            bu4.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu5 colors */
        if (bu5.text == "G"){
            bu5.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu5.text == "R"){
            bu5.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu5.text == "Y"){
            bu5.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu6 colors */
        if (bu6.text == "G"){
            bu6.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu6.text == "R"){
            bu6.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu6.text == "Y"){
            bu6.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu7 colors */
        if (bu7.text == "G"){
            bu7.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu7.text == "R"){
            bu7.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu7.text == "Y"){
            bu7.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu8 colors */
        if (bu8.text == "G"){
            bu8.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu8.text == "R"){
            bu8.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu8.text == "Y"){
            bu8.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu9 colors */
        if (bu9.text == "G"){
            bu9.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu9.text == "R"){
            bu9.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu9.text == "Y"){
            bu9.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu10 colors */
        if (bu10.text == "G"){
            bu10.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu10.text == "R"){
            bu10.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu10.text == "Y"){
            bu10.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu11 colors */
        if (bu11.text == "G"){
            bu11.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu11.text == "R"){
            bu11.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu11.text == "Y"){
            bu11.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu12 colors */
        if (bu12.text == "G"){
            bu12.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu12.text == "R"){
            bu12.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu12.text == "Y"){
            bu12.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu13 colors */
        if (bu13.text == "G"){
            bu13.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu13.text == "R"){
            bu13.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu13.text == "Y"){
            bu13.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu14 colors */
        if (bu14.text == "G"){
            bu14.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu14.text == "R"){
            bu14.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu14.text == "Y"){
            bu14.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu15 colors */
        if (bu15.text == "G"){
            bu15.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu15.text == "R"){
            bu15.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu15.text == "Y"){
            bu15.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu16 colors */
        if (bu16.text == "G"){
            bu16.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu16.text == "R"){
            bu16.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu16.text == "Y"){
            bu16.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu17 colors */
        if (bu17.text == "G"){
            bu17.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu17.text == "R"){
            bu17.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu17.text == "Y"){
            bu17.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu18 colors */
        if (bu18.text == "G"){
            bu18.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu18.text == "R"){
            bu18.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu18.text == "Y"){
            bu18.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu19 colors */
        if (bu19.text == "G"){
            bu19.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu19.text == "R"){
            bu19.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu19.text == "Y"){
            bu19.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu20 colors */
        if (bu20.text == "G"){
            bu20.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu20.text == "R"){
            bu20.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu20.text == "Y"){
            bu20.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu21 colors */
        if (bu21.text == "G"){
            bu21.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu21.text == "R"){
            bu21.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu21.text == "Y"){
            bu21.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu22 colors */
        if (bu22.text == "G"){
            bu22.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu22.text == "R"){
            bu22.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu22.text == "Y"){
            bu22.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu23 colors */
        if (bu23.text == "G"){
            bu23.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu23.text == "R"){
            bu23.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu23.text == "Y"){
            bu23.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu24 colors */
        if (bu24.text == "G"){
            bu24.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu24.text == "R"){
            bu24.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu24.text == "Y"){
            bu24.setBackgroundColor(Color.parseColor("#FFD12A"))
        }

        /** setting bu25 colors */
        if (bu25.text == "G"){
            bu25.setBackgroundColor(Color.parseColor("#A4C639"))
        }
        if (bu25.text == "R"){
            bu25.setBackgroundColor(Color.parseColor("#CC0000"))
        }
        if (bu25.text == "Y"){
            bu25.setBackgroundColor(Color.parseColor("#FFD12A"))
        }
    }

    private fun checkComplete(){
        var win = 1
        if (bu1.text == "G" && bu2.text == "G" && bu3.text == "G" && bu4.text == "G" &&
                bu5.text == "G" && bu6.text == "G" && bu8.text == "G" && bu10.text == "G" &&
                bu11.text == "G" && bu12.text == "G" && bu13.text == "G" && bu14.text == "G" &&
                bu15.text == "G" && bu16.text == "G" && bu18.text == "G" && bu20.text == "G" &&
                bu21.text == "G" && bu22.text == "G" && bu23.text == "G" && bu24.text == "G" &&
                bu25.text == "G"){
            win = 2
        }
        else if (bu1.text == "R" && bu2.text == "R" && bu3.text == "R" && bu4.text == "R" &&
                bu5.text == "R" && bu6.text == "R" && bu8.text == "R" && bu10.text == "R" &&
                bu11.text == "R" && bu12.text == "R" && bu13.text == "R" && bu14.text == "R" &&
                bu15.text == "R" && bu16.text == "R" && bu18.text == "R" && bu20.text == "R" &&
                bu21.text == "R" && bu22.text == "R" && bu23.text == "R" && bu24.text == "R" &&
                bu25.text == "R"){
            win = 2
        }
        else if (bu1.text == "Y" && bu2.text == "Y" && bu3.text == "Y" && bu4.text == "Y" &&
                bu5.text == "Y" && bu6.text == "Y" && bu8.text == "Y" && bu10.text == "Y" &&
                bu11.text == "Y" && bu12.text == "Y" && bu13.text == "Y" && bu14.text == "Y" &&
                bu15.text == "Y" && bu16.text == "Y" && bu18.text == "Y" && bu20.text == "Y" &&
                bu21.text == "Y" && bu22.text == "Y" && bu23.text == "Y" && bu24.text == "Y" &&
                bu25.text == "Y"){
            win = 2
        }
        if (win==2){

            if (sharedPreferences!!.getString(HIGH_SCORE, high_score) != "--") {
                val high_score_test = sharedPreferences!!.getString(HIGH_SCORE, high_score).toInt()

                if (move_count < high_score_test) {
                    val editor = sharedPreferences!!.edit()
                    editor.putString(HIGH_SCORE, move_count.toString())
                    editor.apply()
                }
            } else {
                val editor = sharedPreferences!!.edit()
                editor.putString(HIGH_SCORE, move_count.toString())
                editor.apply()
            }

            if (sharedPreferences!!.getInt(levelLockState, level_lock_initial) <= 49) {
                val editor = sharedPreferences!!.edit()
                editor.putInt(levelLockState, level_unlock)
                editor.apply()
            }

            // Initialize a new layout inflater instance
            val winnerInflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val winnerView = winnerInflater.inflate(R.layout.winner_popup,null)

            // Initialize a new instance of popup window
            val winnerPopup = PopupWindow(
                    winnerView, // Custom view to show in popup window
                    ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                    ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
            )

            winnerPopup.isFocusable = true
            winnerPopup.background

            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                winnerPopup.elevation = 10.0F
            }


            // If API level 23 or higher then execute the code
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                winnerPopup.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.TOP
                winnerPopup.exitTransition = slideOut
            }

            // Finally, show the popup window on app
            winnerPopup.showAtLocation(level_49, Gravity.CENTER, 0, 0)

            bu1.isEnabled = false
            bu2.isEnabled = false
            bu3.isEnabled = false
            bu4.isEnabled = false
            bu5.isEnabled = false
            bu6.isEnabled = false
            bu7.isEnabled = false
            bu8.isEnabled = false
            bu9.isEnabled = false

            winnerPopup.setOnDismissListener{

                // Initialize a new layout inflater instance
                val nextInflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                // Inflate a custom view using layout inflater
                val nextView = nextInflater.inflate(R.layout.next_popup,null)

                // Initialize a new instance of popup window
                val nextPopup = PopupWindow(
                        nextView, // Custom view to show in popup window
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                        ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
                )

                // Set an elevation for the popup window
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    nextPopup.elevation = 10.0F
                }


                // If API level 23 or higher then execute the code
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    // Create a new slide animation for popup window enter transition
                    val slideIn = Slide()
                    slideIn.slideEdge = Gravity.TOP
                    nextPopup.enterTransition = slideIn

                    // Slide animation for popup window exit transition
                    val slideOut = Slide()
                    slideOut.slideEdge = Gravity.TOP
                    nextPopup.exitTransition = slideOut
                }

                // Finally, show the popup window on app
                nextPopup.showAtLocation(level_49, Gravity.CENTER, 0, 0)

            }
        }
    }

}