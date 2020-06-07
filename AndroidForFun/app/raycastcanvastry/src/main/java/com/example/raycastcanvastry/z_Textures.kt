package com.example.raycastcanvastry

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point

class z_Textures {
    var bm_size: Point

    val a_text: IntArray
    val b_text: IntArray
    val c_text: IntArray

    constructor(size: Point, context: Context) {
        this.bm_size = size

        a_text = IntArray(size.x * size.y)
        b_text = IntArray(size.x * size.y)
        c_text = IntArray(size.x * size.y)

        var a_icon = BitmapFactory.decodeResource(context?.getResources(), R.drawable.alice)
        var b_icon = BitmapFactory.decodeResource(context?.getResources(), R.drawable.staying_alive)
        var c_icon = BitmapFactory.decodeResource(context?.getResources(), R.drawable.scream)

        var a_bm = Bitmap.createScaledBitmap(a_icon, size.x,
            size.y, false)
        var b_bm = Bitmap.createScaledBitmap(b_icon, size.x,
            size.y, false)
        var c_bm = Bitmap.createScaledBitmap(c_icon, size.x,
            size.y, false)
        a_bm.getPixels(a_text, 0, size.x,
        0, 0, size.x, size.y)
        b_bm.getPixels(b_text, 0, size.x,
            0, 0, size.x, size.y)
        c_bm.getPixels(c_text, 0, size.x,
            0, 0, size.x, size.y)
    }
}
