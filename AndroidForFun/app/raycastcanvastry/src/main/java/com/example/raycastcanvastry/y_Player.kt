package com.example.raycastcanvastry

import android.R
import android.content.SharedPreferences
import android.widget.EditText
import java.lang.Math.cos
import java.lang.Math.sin
import kotlin.math.cos
import kotlin.math.sin


class y_Player {
    val APP_PREFERENCES = "mysettings"
    val APP_PREFERENCES_COUNTER = "counter"
    lateinit var pref: SharedPreferences

    //добавить FOV
    var moveSpeed: Float = 0.5f
    var rotationSpeed: Float = 0.5f

    var FOV: FVec2 = FVec2(1.0f, 0.66f) //расстояние между
        //Direction && Plane
    var Pos: FVec2 = FVec2(22.0f, 12.0f) //player position
    var Dir: FVec2 = FVec2(-1.0f, 0.0f) //in
    // possible FOV circle camera direction
    var CameraPlane: FVec2 = FVec2(0.0f, 0.66f) // масштаб
    // угла обзора в possible FOV circle

    var time = 0.0 //time of current frame
    var oldTime = 0.0 //time of previous frame

    class FVec2 {
        var x: Float = 0.0f
        var y: Float = 0.0f
        constructor(x: Float, y: Float) {
            this.x = x
            this.y = y
        }

    }

    fun go(map: z_Map) {
    var nextStep: Float = Pos.x + Dir.x * (moveSpeed * 2)
        val newPosx: Float = Pos.x + Dir.x * moveSpeed
        if ((nextStep.toInt() < map.With) and (nextStep.toInt() >= 0)) {
            if ((map.worldMap[nextStep.toInt() + (Pos.y.toInt() * map.With).toInt()] == 0))// and
               Pos.x = newPosx
        }
    nextStep = Pos.y + Dir.y * (moveSpeed * 2)
        val newPosy: Float = Pos.y + Dir.y * moveSpeed
        if ((nextStep.toInt() < map.Height) and (nextStep.toInt() >= 0)) {
                if ((map.worldMap[Pos.x.toInt() + (nextStep.toInt() * map.With).toInt()] == 0))//\\\ and
            {
                    Pos.y = newPosy
            }
        }
    }
    fun rotate(touch: Float, inverse: Int) {
//        var inverse1: Int = if (inverse == 1) 1 else -1
        val oldDirX: Float = Dir.x
        rotationSpeed = (touch) / 100 * inverse
        Dir.x = (Dir.x * cos(rotationSpeed) - Dir.y * sin(rotationSpeed))
        Dir.y = oldDirX * sin(rotationSpeed) + Dir.y * cos(rotationSpeed)
        val oldPlaneX: Float = CameraPlane.x
        CameraPlane.x = CameraPlane.x * cos(rotationSpeed) - CameraPlane.y * sin(rotationSpeed)
        CameraPlane.y = oldPlaneX * sin(rotationSpeed) + CameraPlane.y * cos(rotationSpeed)
    }
}