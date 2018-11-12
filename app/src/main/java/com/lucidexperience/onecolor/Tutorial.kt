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
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tutorial.*
import kotlinx.android.synthetic.main.tutorial_begin.*
import java.util.*

class Tutorial : AppCompatActivity() {

    var move_count = 0
    val myPreferences = "myPrefs"
    var musicState = false
    val MUSICSTATE = "musicState"
    var music_state_initial = false
    val HIGH_SCORE = "high_score_tutorial"
    var high_score_init = "--"
    var sharedPreferences: SharedPreferences? = null
    var first_time = 1
    val btnStack = Stack<Button>()
    val hint1 = "First off, let's go over the basic layout of each level."
    val hint2 = "Above is the display for the current level you are on.\nCurrently, you are just on the Tutorial so the text says 'Tutorial'."
    val hint3 = "This RETRY button will refresh the screen so you can start over from the top."
    val hint4 = "This BACK button will all you to go straight back to the level select screen to try a different level."
    val hint5 = "Above is the moves counter. Every time you click on a square to change it's color the number of moves will increase."
    val hint6 = "Below is where your high score is displayed showing the lowest number of moves it took you to complete the puzzle."
    val hint7 = "Go ahead and click on one of the blocks."
    val hint8 = "This UNDO button below will allow you to undo one move when it's pressed. So don't worry about misclicking!"
    val hint9 = "If you look closely, the color of the block you touched and all blocks touching it changed color."
    val hint10 = "The colors go from Green to Red, Red to Yellow, and Yellow to Green."
    val hint11 = "In order to solve the puzzle you must get every block to show the same color: All Green, all Red, or all Yellow"
    val hint12 = "That's everything for now. Lets see how well you can solve this One Color."

    fun winnerSelect(v: View){
        val winnerBu = v as Button
        when(winnerBu) {
            retry_btn -> {val intent = Intent(this, Tutorial::class.java)
                startActivity(intent)}
            back_btn -> {val intent = Intent(this, LevelScreen::class.java)
                startActivity(intent)}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tutorial_begin)

        tutorial_begin_btn.setOnClickListener {

            setContentView(R.layout.activity_tutorial)

            sharedPreferences = this.getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
            val music_State = sharedPreferences!!.getBoolean(MUSICSTATE, music_state_initial)
            musicState = music_State

            if (high_score_num.text != null) {
                val highScore = sharedPreferences!!.getString(HIGH_SCORE, high_score_init)
                high_score_num.text = highScore
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
                            creditsPopup.showAtLocation(activity_tutorial, Gravity.CENTER, 0, 0)
                            true
                        }
                        else -> false
                    }
                }

                popupMenu.inflate(R.menu.options_menu)
                popupMenu.show()
            }

            // Initialize a new layout inflater instance
            val hint1Inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val hint1View = hint1Inflater.inflate(R.layout.tutorial_hints,null)
            val tv1 = hint1View.findViewById(R.id.tutorial_hint) as TextView
            tv1.text = hint1

            // Initialize a new instance of popup window
            val tutorialHint1 = PopupWindow(
                    hint1View, // Custom view to show in popup window
                    ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                    ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
            )

            tutorialHint1.isFocusable = true
            tutorialHint1.background

            // Finally, show the popup window on app
            tutorialHint1.showAtLocation(activity_tutorial, Gravity.CENTER, 0, 0)

            tutorialHint1.setOnDismissListener {

                // Initialize a new layout inflater instance
                val hint2Inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                // Inflate a custom view using layout inflater
                val hint2View = hint2Inflater.inflate(R.layout.tutorial_hints,null)
                val tv2 = hint2View.findViewById(R.id.tutorial_hint) as TextView
                tv2.text = hint2

                // Initialize a new instance of popup window
                val tutorialHint2 = PopupWindow(
                        hint2View, // Custom view to show in popup window
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                        ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
                )

                tutorialHint2.isFocusable = true
                tutorialHint2.background

                // Finally, show the popup window on app
                tutorialHint2.showAsDropDown(tutorial)

                tutorialHint2.setOnDismissListener {

                    // Initialize a new layout inflater instance
                    val hint3Inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                    // Inflate a custom view using layout inflater
                    val hint3View = hint3Inflater.inflate(R.layout.tutorial_hints,null)
                    val tv3 = hint3View.findViewById(R.id.tutorial_hint) as TextView
                    tv3.text = hint3

                    // Initialize a new instance of popup window
                    val tutorialHint3 = PopupWindow(
                            hint3View, // Custom view to show in popup window
                            ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                            ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
                    )

                    tutorialHint3.isFocusable = true
                    tutorialHint3.background

                    // Finally, show the popup window on app
                    tutorialHint3.showAsDropDown(retry_btn)

                    tutorialHint3.setOnDismissListener {

                        // Initialize a new layout inflater instance
                        val hint4Inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                        // Inflate a custom view using layout inflater
                        val hint4View = hint4Inflater.inflate(R.layout.tutorial_hints,null)
                        val tv4 = hint4View.findViewById(R.id.tutorial_hint) as TextView
                        tv4.text = hint4

                        // Initialize a new instance of popup window
                        val tutorialHint4 = PopupWindow(
                                hint4View, // Custom view to show in popup window
                                ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                                ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
                        )

                        tutorialHint4.isFocusable = true
                        tutorialHint4.background

                        // Finally, show the popup window on app
                        tutorialHint4.showAsDropDown(back_btn)

                        tutorialHint4.setOnDismissListener {

                            // Initialize a new layout inflater instance
                            val hint5Inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                            // Inflate a custom view using layout inflater
                            val hint5View = hint5Inflater.inflate(R.layout.tutorial_hints,null)
                            val tv5 = hint5View.findViewById(R.id.tutorial_hint) as TextView
                            tv5.text = hint5

                            // Initialize a new instance of popup window
                            val tutorialHint5 = PopupWindow(
                                    hint5View, // Custom view to show in popup window
                                    ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                                    ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
                            )

                            tutorialHint5.isFocusable = true
                            tutorialHint5.background

                            // Finally, show the popup window on app
                            tutorialHint5.showAsDropDown(moves, -25, 0)

                            tutorialHint5.setOnDismissListener {

                                // Initialize a new layout inflater instance
                                val hint6Inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                                // Inflate a custom view using layout inflater
                                val hint6View = hint6Inflater.inflate(R.layout.tutorial_hints,null)
                                val tv6 = hint6View.findViewById(R.id.tutorial_hint) as TextView
                                tv6.text = hint6

                                // Initialize a new instance of popup window
                                val tutorialHint6 = PopupWindow(
                                        hint6View, // Custom view to show in popup window
                                        ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                                        ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
                                )

                                tutorialHint6.isFocusable = true
                                tutorialHint6.background

                                // Finally, show the popup window on app
                                tutorialHint6.showAsDropDown(high_score, -290, -950)
                                tutorialHint6.isAboveAnchor

                                tutorialHint6.setOnDismissListener {

                                    // Initialize a new layout inflater instance
                                    val hint7Inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                                    // Inflate a custom view using layout inflater
                                    val hint7View = hint7Inflater.inflate(R.layout.tutorial_hints,null)
                                    val tv7 = hint7View.findViewById(R.id.tutorial_hint) as TextView
                                    tv7.text = hint7

                                    // Initialize a new instance of popup window
                                    val tutorialHint7 = PopupWindow(
                                            hint7View, // Custom view to show in popup window
                                            ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                                            ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
                                    )

                                    tutorialHint7.isFocusable = true
                                    tutorialHint7.background

                                    // Finally, show the popup window on app
                                    tutorialHint7.showAsDropDown(tutorial, -310, 0)
                                }
                            }

                        }

                    }

                }

            }


        }
    }

    fun buClick(view: View) {
        val buSelected = view as Button
        btnStack.push(buSelected)

        playGame(buSelected)
        if (move_count == 1 && first_time == 1){

            // Initialize a new layout inflater instance
            val hint8Inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val hint8View = hint8Inflater.inflate(R.layout.tutorial_hints,null)
            val tv8 = hint8View.findViewById(R.id.tutorial_hint) as TextView
            tv8.text = hint8

            // Initialize a new instance of popup window
            val tutorialHint8 = PopupWindow(
                    hint8View, // Custom view to show in popup window
                    ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                    ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
            )

            tutorialHint8.isFocusable = true
            tutorialHint8.background

            // Finally, show the popup window on app
            tutorialHint8.showAsDropDown(undo_btn, -310, -950)
            tutorialHint8.isAboveAnchor

            tutorialHint8.setOnDismissListener {

                // Initialize a new layout inflater instance
                val hint9Inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                // Inflate a custom view using layout inflater
                val hint9View = hint9Inflater.inflate(R.layout.tutorial_hints,null)
                val tv9 = hint9View.findViewById(R.id.tutorial_hint) as TextView
                tv9.text = hint9

                // Initialize a new instance of popup window
                val tutorialHint9 = PopupWindow(
                        hint9View, // Custom view to show in popup window
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                        ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
                )

                tutorialHint9.isFocusable = true
                tutorialHint9.background

                // Finally, show the popup window on app
                tutorialHint9.showAsDropDown(tutorial, -310, 0)

                tutorialHint9.setOnDismissListener {

                    // Initialize a new layout inflater instance
                    val hint10Inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                    // Inflate a custom view using layout inflater
                    val hint10View = hint10Inflater.inflate(R.layout.tutorial_hints,null)
                    val tv10 = hint10View.findViewById(R.id.tutorial_hint) as TextView
                    tv10.text = hint10

                    // Initialize a new instance of popup window
                    val tutorialHint10 = PopupWindow(
                            hint10View, // Custom view to show in popup window
                            ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                            ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
                    )

                    tutorialHint10.isFocusable = true
                    tutorialHint10.background

                    // Finally, show the popup window on app
                    tutorialHint10.showAsDropDown(tutorial, -310, 0)

                    tutorialHint10.setOnDismissListener {

                        // Initialize a new layout inflater instance
                        val hint11Inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                        // Inflate a custom view using layout inflater
                        val hint11View = hint11Inflater.inflate(R.layout.tutorial_hints,null)
                        val tv11 = hint11View.findViewById(R.id.tutorial_hint) as TextView
                        tv11.text = hint11

                        // Initialize a new instance of popup window
                        val tutorialHint11 = PopupWindow(
                                hint11View, // Custom view to show in popup window
                                ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                                ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
                        )

                        tutorialHint11.isFocusable = true
                        tutorialHint11.background

                        // Finally, show the popup window on app
                        tutorialHint11.showAsDropDown(tutorial, -310, 0)

                        tutorialHint11.setOnDismissListener {

                            // Initialize a new layout inflater instance
                            val hint12Inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                            // Inflate a custom view using layout inflater
                            val hint12View = hint12Inflater.inflate(R.layout.tutorial_hints,null)
                            val tv12 = hint12View.findViewById(R.id.tutorial_hint) as TextView
                            tv12.text = hint12

                            // Initialize a new instance of popup window
                            val tutorialHint12 = PopupWindow(
                                    hint12View, // Custom view to show in popup window
                                    ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                                    ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
                            )

                            tutorialHint12.isFocusable = true
                            tutorialHint12.background

                            // Finally, show the popup window on app
                            tutorialHint12.showAsDropDown(tutorial, -310, 0)

                            tutorialHint12.setOnDismissListener {
                                first_time = 2
                            }
                        }
                    }
                }
            }
        }
    }

    fun undoSelect(view: View){
        if (!btnStack.empty()){
            undoLastMove(btnStack.pop())
            move_count -= 1
            val moves = move_count.toString()
            move_counter_tutorial.text = moves
        } else {
            Toast.makeText(this, "There is nothing to undo.", Toast.LENGTH_LONG).show()
        }
        checkColor()
    }

    private fun playGame(buSelected: Button){
        move_count += 1
        val moves = move_count.toString()
        move_counter_tutorial.text = moves
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

            /** when the selected button is bu4 */
            if (buSelected.id == R.id.bu4) {
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

            /** when the selected button is bu5 */
            if (buSelected.id == R.id.bu5) {
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
            if (buSelected.id == R.id.bu5) {
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

            /** when the selected button is bu6 */
            if (buSelected.id == R.id.bu6) {
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
            if (buSelected.id == R.id.bu6) {
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
            if (buSelected.id == R.id.bu6) {
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

            /** when the selected button is bu7 */
            if (buSelected.id == R.id.bu7) {
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

            /** when the selected button is bu8 */
            if (buSelected.id == R.id.bu8) {
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

            /** when the selected button is bu9 */
            if (buSelected.id == R.id.bu9) {
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

            /** when the selected button is bu4 */
            if (buSelected.id == R.id.bu4) {
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

            /** when the selected button is bu5 */
            if (buSelected.id == R.id.bu5) {
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
            if (buSelected.id == R.id.bu5) {
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

            /** when the selected button is bu6 */
            if (buSelected.id == R.id.bu6) {
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
            if (buSelected.id == R.id.bu6) {
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
            if (buSelected.id == R.id.bu6) {
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

            /** when the selected button is bu7 */
            if (buSelected.id == R.id.bu7) {
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

            /** when the selected button is bu8 */
            if (buSelected.id == R.id.bu8) {
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

            /** when the selected button is bu9 */
            if (buSelected.id == R.id.bu9) {
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

            /** when the selected button is bu4 */
            if (buSelected.id == R.id.bu4) {
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

            /** when the selected button is bu5 */
            if (buSelected.id == R.id.bu5) {
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
            if (buSelected.id == R.id.bu5) {
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

            /** when the selected button is bu6 */
            if (buSelected.id == R.id.bu6) {
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
            if (buSelected.id == R.id.bu6) {
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
            if (buSelected.id == R.id.bu6) {
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

            /** when the selected button is bu7 */
            if (buSelected.id == R.id.bu7) {
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

            /** when the selected button is bu8 */
            if (buSelected.id == R.id.bu8) {
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

            /** when the selected button is bu9 */
            if (buSelected.id == R.id.bu9) {
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

            /** when the selected button is bu4 */
            if (buSelected.id == R.id.bu4) {
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

            /** when the selected button is bu5 */
            if (buSelected.id == R.id.bu5) {
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
            if (buSelected.id == R.id.bu5) {
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

            /** when the selected button is bu6 */
            if (buSelected.id == R.id.bu6) {
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
            if (buSelected.id == R.id.bu6) {
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
            if (buSelected.id == R.id.bu6) {
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

            /** when the selected button is bu7 */
            if (buSelected.id == R.id.bu7) {
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

            /** when the selected button is bu8 */
            if (buSelected.id == R.id.bu8) {
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

            /** when the selected button is bu9 */
            if (buSelected.id == R.id.bu9) {
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

            /** when the selected button is bu4 */
            if (buSelected.id == R.id.bu4) {
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

            /** when the selected button is bu5 */
            if (buSelected.id == R.id.bu5) {
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
            if (buSelected.id == R.id.bu5) {
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

            /** when the selected button is bu6 */
            if (buSelected.id == R.id.bu6) {
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
            if (buSelected.id == R.id.bu6) {
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
            if (buSelected.id == R.id.bu6) {
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

            /** when the selected button is bu7 */
            if (buSelected.id == R.id.bu7) {
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

            /** when the selected button is bu8 */
            if (buSelected.id == R.id.bu8) {
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

            /** when the selected button is bu9 */
            if (buSelected.id == R.id.bu9) {
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

            /** when the selected button is bu4 */
            if (buSelected.id == R.id.bu4) {
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

            /** when the selected button is bu5 */
            if (buSelected.id == R.id.bu5) {
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
            if (buSelected.id == R.id.bu5) {
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

            /** when the selected button is bu6 */
            if (buSelected.id == R.id.bu6) {
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
            if (buSelected.id == R.id.bu6) {
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
            if (buSelected.id == R.id.bu6) {
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

            /** when the selected button is bu7 */
            if (buSelected.id == R.id.bu7) {
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

            /** when the selected button is bu8 */
            if (buSelected.id == R.id.bu8) {
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

            /** when the selected button is bu9 */
            if (buSelected.id == R.id.bu9) {
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
    }

    private fun checkComplete(){
        var win = 1
        if (bu1.text == "G" && bu2.text == "G" && bu3.text == "G" && bu4.text == "G" &&
                bu5.text == "G" && bu6.text == "G" && bu7.text == "G" && bu8.text == "G" &&
                bu9.text == "G"){
            win = 2
        }
        else if (bu1.text == "R" && bu2.text == "R" && bu3.text == "R" && bu4.text == "R" &&
                bu5.text == "R" && bu6.text == "R" && bu7.text == "R" && bu8.text == "R" &&
                bu9.text == "R"){
            win = 2
        }
        else if (bu1.text == "Y" && bu2.text == "Y" && bu3.text == "Y" && bu4.text == "Y" &&
                bu5.text == "Y" && bu6.text == "Y" && bu7.text == "Y" && bu8.text == "Y" &&
                bu9.text == "Y"){
            win = 2
        }
        if (win==2){

            if (sharedPreferences!!.getString(HIGH_SCORE, high_score_init) != "--") {
                val high_score_test = sharedPreferences!!.getString(HIGH_SCORE, high_score_init).toInt()

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
            winnerPopup.showAtLocation(activity_tutorial, Gravity.CENTER, 0, 0)

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
                val nextView = nextInflater.inflate(R.layout.tutorial_finish,null)

                // Initialize a new instance of popup window
                val tutorialFinish = PopupWindow(
                        nextView, // Custom view to show in popup window
                        ConstraintLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                        ConstraintLayout.LayoutParams.WRAP_CONTENT // Window height
                )

                tutorialFinish.isFocusable = true

                // Set an elevation for the popup window
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    tutorialFinish.elevation = 10.0F
                }


                // If API level 23 or higher then execute the code
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    // Create a new slide animation for popup window enter transition
                    val slideIn = Slide()
                    slideIn.slideEdge = Gravity.TOP
                    tutorialFinish.enterTransition = slideIn

                    // Slide animation for popup window exit transition
                    val slideOut = Slide()
                    slideOut.slideEdge = Gravity.TOP
                    tutorialFinish.exitTransition = slideOut
                }

                // Finally, show the popup window on app
                tutorialFinish.showAtLocation(activity_tutorial, Gravity.CENTER, 0, 0)

                tutorialFinish.setOnDismissListener {
                    val intent = Intent(this, LevelScreen::class.java)
                    startActivity(intent)
                }

            }
        }
    }

}