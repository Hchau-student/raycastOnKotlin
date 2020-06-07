package com.example.raycastcanvastry

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.lang.Thread.sleep


class MainActivity : AppCompatActivity() {
    init {//библиотека компилится CMake'ом, лежит в директории приложухи;
        //если захочешь дописать свои файлы - прописывай их
        //в том же мейкфайле
        System.loadLibrary("native-lib")
    }//ф-ия написана в файле Trryy.cpp
    external fun stringFromJNI(x: Int, y: Int,
                               a_text: IntArray, b_text: IntArray, c_text: IntArray,
    pDirX: Float, pDirY: Float, pCameraX: Float, pCameraY: Float,
    PosX: Float, PosY: Float, WorldMap: IntArray, wm_w: Int, wm_h: Int): IntArray
    //не знаю, как передавать структуры в c++ файл

    val APP_PREFERENCES = "mysettings"
    val APP_PREFERENCES_COUNTER2 = "counter2"
    val APP_PREFERENCES_COUNTER = "counter"
    val APP_RESOLUTION = "resolution"
    val APP_CAMERA_HEIGHT = "camera"

    var variable_progress: Int = 0
    lateinit var pref: SharedPreferences
    var my_val: Float = 1.0f
    var resolution: Int = 18
    var cameraHeight: Float = 0.5f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE)

        val display = getWindowManager().getDefaultDisplay()
        val size = Point()

        display.getSize(size)
        var r: r_Raycast = r_Raycast(size, this)

        setContentView(DrawView(this, size, r))
    }

    internal inner class DrawView(context: Context?, size: Point, ray: r_Raycast) :
        View(context) {
        var raycast = ray
        var paint = Paint(Paint.ANTI_ALIAS_FLAG)
        var rect = Rect(0, 0, size.x, size.y)

        override fun onDraw(canvas: Canvas) {
            raycast.wallHeight = my_val

            if (raycast.pref.call == true) {
                val intent = Intent(this.context, Preferences::class.java)
                startActivity(intent)
                DrawCanvas(canvas, raycast.bm, raycast.pref, rect, paint)
            }
            DrawCanvas(canvas, raycast.bm, raycast.pref, rect, paint)
            raycast.pref.call = false
        }
        override fun onTouchEvent(event: MotionEvent): Boolean {
            if (pref.contains(APP_PREFERENCES_COUNTER)) {
                variable_progress = pref.getInt(APP_PREFERENCES_COUNTER, 0)
            }
            if (pref.contains(APP_RESOLUTION)) {
                resolution = pref.getInt(APP_RESOLUTION, 0)
            }
            if (pref.contains(APP_PREFERENCES_COUNTER2)) {
                my_val = (pref.getInt(APP_PREFERENCES_COUNTER2, 0) + 3).toFloat() * 0.20f
            }
            if (pref.contains(APP_CAMERA_HEIGHT)) {
                cameraHeight = (pref.getInt(APP_CAMERA_HEIGHT, 0) + 1).toFloat() * 0.10f
            }
            raycast.pref.inverse = if (variable_progress == 0) 1 else -1
            raycast.pref.resolution = resolution
            raycast.wallHeight = my_val
            raycast.pref.cameraHeight = cameraHeight
            PlayerMove(raycast, event)
            invalidate()
            return true
        }
    }
}
