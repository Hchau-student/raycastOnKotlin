package com.example.raycastcanvastry

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.preferences_control_game.*


class Preferences : AppCompatActivity() {

    val APP_PREFERENCES = "mysettings"
    val APP_PREFERENCES_COUNTER = "counter"
    val APP_PREFERENCES_COUNTER2 = "counter2"
    val APP_RESOLUTION = "resolution"
    val APP_CAMERA_HEIGHT = "camera"
    lateinit var pref: SharedPreferences
    var variable_progress: Int = 0
    var variable_progress2: Int = 0
    var resolution_progress: Int = 17
    var cameraHeightProgress: Int = 5

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)
        mainMenu()
        super.onCreate(savedInstanceState)
    }
    override fun onPause() {
        super.onPause()

        // Запоминаем данные
        val editor = pref.edit()
        editor.putInt(APP_PREFERENCES_COUNTER, variable_progress)
        editor.putInt(APP_PREFERENCES_COUNTER2, variable_progress2)
        editor.putInt(APP_RESOLUTION, resolution_progress)
        editor.putInt(APP_CAMERA_HEIGHT, cameraHeightProgress)
        editor.apply()
    }
    override fun onResume() {
        super.onResume()

        if (pref.contains(APP_PREFERENCES_COUNTER)) {
            variable_progress = pref.getInt(APP_PREFERENCES_COUNTER, 0)
        }
        if (pref.contains(APP_PREFERENCES_COUNTER2)) {
            variable_progress2 = pref.getInt(APP_PREFERENCES_COUNTER2, 0)
        }
        if (pref.contains(APP_RESOLUTION)) {
            resolution_progress = pref.getInt(APP_RESOLUTION, 0)
        }
        if (pref.contains(APP_CAMERA_HEIGHT)) {
            cameraHeightProgress = pref.getInt(APP_CAMERA_HEIGHT, 0)
        }
    }

    fun mainMenu() {
        setContentView(R.layout.game_preferences)
        var background: View = findViewById(R.id.background)
        background.setBackgroundResource(R.drawable.gradient)
        var game_Control: Button = findViewById(R.id.button1)
        game_Control.getBackground().setColorFilter(0, PorterDuff.Mode.MULTIPLY)
        val game_Control2: Button = findViewById(R.id.button2)
        game_Control2.getBackground().setColorFilter(0, PorterDuff.Mode.MULTIPLY)
        val game_Control3: Button = findViewById(R.id.button3)
        game_Control3.getBackground().setColorFilter(0, PorterDuff.Mode.MULTIPLY)
        val game_Control4: Button = findViewById(R.id.button4)
        game_Control4.getBackground().setColorFilter(0, PorterDuff.Mode.MULTIPLY)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun tapEventGameControl(v: View?) {

        setContentView(R.layout.preferences_control_game)
        var background: View = findViewById(R.id.background)
        background.setBackgroundResource(R.drawable.gradient)

        var inverse: SeekBar = findViewById(R.id.inverse)
        var wallHeight: SeekBar = findViewById(R.id.seekBar2)
        var resolution: SeekBar = findViewById(R.id.resolutionBar)
        var resolutionText: TextView = findViewById(R.id.resolutionText)
        var inverseText: TextView = findViewById(R.id.textView)
        var wallsHeightText: TextView = findViewById(R.id.textView2)
        var cameraH: SeekBar = findViewById(R.id.cameraHeightBar)
        var cameraHText: TextView = findViewById(R.id.cameraHeightText)

        inverse.setProgress(variable_progress)
        wallHeight.setProgress(variable_progress2)
        resolution.setProgress(resolution_progress)
        cameraH.setProgress(cameraHeightProgress)

        resolutionText.setText("image resolution = " + resolution_progress)
        inverseText.setText("inverse rotation " + if (variable_progress == 0) "off" else "on")
        wallsHeightText.setText("Walls height = " + "%.1f".format((variable_progress2 + 3) * 0.20f))
        cameraHText.setText("camera height = " + "%.1f".format((cameraHeightProgress + 1) * 0.10f))

        inverse.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                var str_progress = getString(R.string.inverse)
                variable_progress = i
                inverseText.setText("inverse rotation " + if (variable_progress == 0) "off" else "on")
                var sharedPref = getSharedPreferences(str_progress, Context.MODE_PRIVATE)
                val editor: Editor = sharedPref.edit()
                editor.commit()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
        wallHeight.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                var str_progress = getString(R.string.inverse)
                variable_progress2 = i
                var sharedPref = getSharedPreferences(str_progress, Context.MODE_PRIVATE)
                val editor: Editor = sharedPref.edit()
                var print = ((variable_progress2 + 3) * 0.20f)
//                if ((print >= 1.7f) and (print <= 1.9f)) print = 1.8f
                wallsHeightText.setText("Walls height = " + "%.1f".format(print))
                editor.commit()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
    resolution.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
            var str_progress = getString(R.string.inverse)
            resolution_progress = i
            var sharedPref = getSharedPreferences(str_progress, Context.MODE_PRIVATE)
            val editor: Editor = sharedPref.edit()
            editor.commit()
            resolutionText.setText("image resolution = " + resolution_progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
        }
    })

        cameraH.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                var str_progress = getString(R.string.inverse)
                cameraHeightProgress = i
                var sharedPref = getSharedPreferences(str_progress, Context.MODE_PRIVATE)
                val editor: Editor = sharedPref.edit()
                val print = (cameraHeightProgress + 1) * 0.10f
                cameraHText.setText("camera height = " + "%.1f".format(print))
                editor.commit()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })
}



    fun tapEventBack(v: View?) {
        mainMenu()
    }

    fun tapEventGame(v: View?) {
        finish()
    }

    fun tapEventSeek(v: View?) {

    }
}
