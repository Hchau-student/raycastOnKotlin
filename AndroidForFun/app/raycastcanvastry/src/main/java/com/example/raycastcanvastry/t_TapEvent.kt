package com.example.raycastcanvastry

import android.graphics.Bitmap
import android.graphics.Point
import android.view.MotionEvent

fun TapEvent(event: MotionEvent, TouchScreen: t_TapScreen): Double {
    when (event.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
            TouchScreen.tupDown(event)
            return (0.0)
        }
        MotionEvent.ACTION_POINTER_DOWN -> { //новое нажатие
            TouchScreen.tupDown(event)
            return (0.0)
        }
        MotionEvent.ACTION_MOVE -> { //движение
            return TouchScreen.renew(event)
        }
    }
    return (0.0)
}

fun IsShortTouch(event: MotionEvent, TouchScreen: t_TapScreen): Boolean {
    when (event.actionMasked) {
        MotionEvent.ACTION_UP -> {
            return (TouchScreen.isShortTouch(event.actionIndex))
        }
        MotionEvent.ACTION_POINTER_UP -> {
            return (TouchScreen.isShortTouch(event.actionIndex))
        }
        MotionEvent.ACTION_CANCEL -> {
            return (TouchScreen.isShortTouch(event.actionIndex))
        }
        else ->
            return (false)
    }
    return (false)
}

fun EndTapEvent(event: MotionEvent, TouchScreen: t_TapScreen): Float {
    var res: Float = 0.0f
    when (event.actionMasked) {
        MotionEvent.ACTION_UP -> {
            res = if (TouchScreen.isShortTouch(event.actionIndex) == true) 0.5f else 0.0f
            TouchScreen.endTouch(event)
            return (res)
        }
        MotionEvent.ACTION_POINTER_UP -> {
            res = if (TouchScreen.isShortTouch(event.actionIndex) == true) 0.5f else 0.0f
            TouchScreen.endTouch(event)
            return (res)
        }
        MotionEvent.ACTION_CANCEL -> {
            res = if (TouchScreen.isShortTouch(event.actionIndex) == true) 0.5f else 0.0f
            TouchScreen.endTouch(event)
            return (res)
        }
    }
    return (res)
}

fun PrefTouch(ev: MotionEvent, pref: z_Preferences, touchScreen: t_TapScreen): Boolean {
    if (ev.actionIndex == touchScreen.FirstTouch.pointer) {
        if ((touchScreen.FirstTouch.End.x >= 30) and (touchScreen.FirstTouch.End.x <= 200)
            and (touchScreen.FirstTouch.End.y >= 30) and (touchScreen.FirstTouch.End.y <= 200)) {
            pref.call = !pref.call
            return true
        }
    } else if (ev.actionIndex == touchScreen.SecondTouch.pointer) {
        if ((touchScreen.SecondTouch.End.x >= 30) and (touchScreen.SecondTouch.End.x <= 200)
            and (touchScreen.SecondTouch.End.y >= 30) and (touchScreen.SecondTouch.End.y <= 200)) {
            pref.call = !pref.call
            return true
        }
    }
    return false
}

fun getPausedView(r: r_Raycast) {
    var src: IntArray = IntArray(r.bm_size.x * r.bm_size.y)
    r.bm.getPixels(src, 0, r.bm_size.x,
            0, 0, r.bm_size.x, r.bm_size.y)
    for (i in 0..r.bm_size.x - 1) {
        for (j in 0..r.bm_size.y - 1) {
            src[i + j * r.bm_size.x] = (src[i + j * r.bm_size.x] - 0xcc000000.toInt())
        }
    }
    r.bm.setPixels(src, 0, r.bm_size.x,
        0, 0, r.bm_size.x, r.bm_size.y)
}

fun PlayerMove(r: r_Raycast, ev: MotionEvent) {
    var TouchCoord: Float = 0.0f
    var shortTouch: Boolean = IsShortTouch(ev, r.TouchScreen)
    if (shortTouch == true) {
        if (PrefTouch(ev, r.pref, r.TouchScreen) == true) {
//            r.TouchScreen.FirstTouch.hold = false
//            r.TouchScreen.SecondTouch.hold = false
            EndTapEvent(ev, r.TouchScreen)
        }
    }
    if (r.pref.call == true) {
        getPausedView(r)
//        r.bm.setPixels(r.pref.call_bm, 0, r.bm_size.x,
//            0, 0, r.bm_size.x, r.bm_size.y)
        r.TouchScreen.FirstTouch.hold = false
        r.TouchScreen.SecondTouch.hold = false
        TapEvent(ev, r.TouchScreen)
        return
    }
     if (shortTouch == true) {
        r.player.go(r.map)
    } else {
        TouchCoord = TapEvent(ev, r.TouchScreen).toFloat()
        if (TouchCoord != 0.0f)
            r.player.rotate(TouchCoord, r.pref.inverse)
    }
    EndTapEvent(ev, r.TouchScreen)
    r_DrawWalls(r.bm_size, r, 0, r.bm_size.x)
}

