package com.example.raycastcanvastry

import android.graphics.*

fun DrawCanvas(canvas: Canvas, bm: Bitmap, pref: z_Preferences, rect: Rect, paint: Paint) {
    var rect2 = Rect(30, 30, 125, 125)
//    var callBM: Bitmap = BitmapFactory.decodeResource(context?.getResources(), R.drawable.gradient)
//    var rect = Rect(0, 0, size.x, size.y)
    canvas.translate(0F, 0F)
    canvas.save()
    canvas.translate(0f, 0f)
    canvas.drawBitmap(pref.callBM!!, null, rect!!, paint)
    canvas.drawBitmap(bm!!, null, rect!!, paint)
    canvas.drawBitmap(pref.bm!!, null, rect2!!, paint)
    canvas.restore()
}
