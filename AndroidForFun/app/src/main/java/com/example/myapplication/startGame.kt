package com.example.myapplication

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class startGame : AppCompatActivity() {

    var currBackground: String = "Menu"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // fixing screen orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_start_game)

        var background: View = findViewById(R.id.background)
        var buttonStartGame: Button = findViewById(R.id.buttonNewGame)
        var buttonContinue: Button = findViewById(R.id.buttonContinue)

        background.setBackgroundResource(R.drawable.start_game_view_background)
        buttonStartGame.setBackgroundResource(R.drawable.start_game_button_newgame)
        buttonContinue.setBackgroundResource(R.drawable.start_game_button_continue)

    }

    fun tapEvent(v: View?) {
        var background: View = findViewById(R.id.background)
        if (currBackground.equals("Menu")) {
            background.setBackgroundResource(R.drawable.start_game_slonik)
            currBackground = "Slonik"
        } else {
            background.setBackgroundResource(R.drawable.start_game_view_background)
            currBackground = "Menu"
        }
    }

    fun tapNewGame(v: View?) {
        var intent: Intent = Intent(this, b_Raycast::class.java)
        startActivity(intent)
    }

    fun tapContinue(v: View?) {

    }
}
