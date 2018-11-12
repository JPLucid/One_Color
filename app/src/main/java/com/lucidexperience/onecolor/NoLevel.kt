package com.lucidexperience.onecolor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.no_level.*

class NoLevel : AppCompatActivity(){

    fun winnerSelect(v: View){
        val winnerBu = v as Button
        when(winnerBu) {
            level_select_btn -> {val intent = Intent(this, LevelScreen::class.java)
                startActivity(intent)}
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.no_level)

    }
}