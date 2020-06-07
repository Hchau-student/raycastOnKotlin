package com.example.raycastcanvastry

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.widget.Button

fun set_less_visible(bm: Bitmap, x: Int, y: Int) {
    var bit_src: IntArray = IntArray(x * y)
    bm.getPixels(bit_src, 0, x,
        0, 0, x, y)
    for (i in 0..x - 1) {
        for (j in 0..y - 1) {
            if (bit_src[i + j * x] < 0x70000000) bit_src[i + j * x] = bit_src[i + j * x] - (0x70000000)
        }
    }
    bm.setPixels(bit_src, 0, x,
        0, 0, x, y)
}

class z_Preferences {
    var IsInversedRotation: Boolean = false
//    var button: Button
    var bm: Bitmap
    var callBM: Bitmap
    var call: Boolean
    var call_bm: IntArray
    var inverse: Int = 0
    var resolution: Int = 18
    var cameraHeight: Float = 0.5f
    constructor(context: Context, size: Point) {
        this.call = false
        call_bm = IntArray(size.x * size.y)
        this.callBM = BitmapFactory.decodeResource(context?.getResources(), R.drawable.pref2)
        this.bm = BitmapFactory.decodeResource(context?.getResources(), R.drawable.pref)
        this.bm = Bitmap.createScaledBitmap(bm, 100,
            100, false)
        this.callBM = Bitmap.createScaledBitmap(this.callBM, size.x,
            size.y, false)
        this.callBM.getPixels(this.call_bm, 0, size.x,
            0, 0, size.x, size.y)
//        set_less_visible(bm, 100, 100)    //how to manage visibility?
    }
}