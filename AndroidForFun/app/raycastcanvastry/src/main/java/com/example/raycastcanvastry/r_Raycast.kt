package com.example.raycastcanvastry

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point

class r_Raycast {
    var TouchScreen: t_TapScreen
    val bm_size: Point
    var bm: Bitmap
    val map: z_Map = z_Map()
    var player: y_Player = y_Player()
    var textures: z_Textures
    var pref: z_Preferences
    var wallHeight: Float = 1.0f
    val wallH: Float

    constructor(size: Point, context: Context) {
        this.pref = z_Preferences(context, size)
        this.bm_size = size
        this.wallH = size.y.toFloat() / size.x.toFloat()
        this.bm =  Bitmap.createBitmap(size.x, size.y,
        Bitmap.Config.ARGB_8888)
        this.TouchScreen = t_TapScreen()
        this.textures = z_Textures(size, context)
    }
}