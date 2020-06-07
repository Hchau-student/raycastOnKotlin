package com.example.raycastcanvastry

import android.view.MotionEvent
import kotlin.math.abs

class t_TapScreen {
    var FirstTouch: Touch =
        Touch(0, false, FlVec2(0.0f, 0.0f))
    var SecondTouch: Touch =
        Touch(0, false, FlVec2(0.0f, 0.0f))

    class FlVec2 {
        var x: Float = 0.0f
        var y: Float = 0.0f

        constructor(_x: Float, _y: Float) {
            this.x = _x
            this.y = _y
        }
    }

    class Touch {
        var pointer: Int = 0
        var hold: Boolean = false
        var Begin: FlVec2 = FlVec2(0.0f, 0.0f)
        var End: FlVec2 = FlVec2(0.0f, 0.0f)
        var isMoving: Boolean = false

        constructor(_pointer: Int, _hold: Boolean, _Begin: FlVec2) {
            this.pointer = _pointer
            this.hold = _hold
            this.Begin = _Begin
            this.End = _Begin
        }
    }

    fun tupDown(ev: MotionEvent) {
        if (FirstTouch.hold == false) {
            FirstTouch = Touch(ev.actionIndex, true, FlVec2(ev.x, ev.y))
        } else if (SecondTouch.hold == false) {
            SecondTouch = Touch(ev.actionIndex, true, FlVec2(ev.x, ev.y))
        }
    }

    fun renew(ev: MotionEvent): Double {
        var res: Double = 0.0
        if (ev.actionIndex == FirstTouch.pointer) {
            if ((abs(FirstTouch.Begin.x - ev.x) <= 10.0) and (abs(FirstTouch.Begin.y - ev.y) <= 10.0))
                return (res)
            FirstTouch.End = FlVec2(ev.x, ev.y)
            FirstTouch.isMoving = true
            res = (FirstTouch.Begin.x - FirstTouch.End.x).toDouble()
            FirstTouch.Begin = FlVec2(ev.x, ev.y)
            return (res)
        } else if (ev.actionIndex == SecondTouch.pointer) {
            if ((abs(SecondTouch.Begin.x - ev.x) <= 10.0) and (abs(SecondTouch.Begin.y - ev.y) <= 10.0))
                return (res)
            SecondTouch.End = FlVec2(ev.x, ev.y)
            SecondTouch.isMoving = true
            res = (SecondTouch.Begin.x - SecondTouch.End.x).toDouble()
            SecondTouch.Begin =  FlVec2(ev.x, ev.y)
            return res
        }
        return (res)
    }

    fun endTouch(ev: MotionEvent) {
        if (ev.actionIndex == SecondTouch.pointer) {
//            if (SecondTouch.hold == true) {
                SecondTouch.hold = false
                SecondTouch.isMoving = false
//            }
        }
        if (ev.actionIndex == FirstTouch.pointer) {
            FirstTouch.hold = false
            FirstTouch.isMoving = false
        }
    }




    //проверка
    fun isShortTouch(id: Int): Boolean {
        if ((FirstTouch.isMoving == false) && (id == FirstTouch.pointer)
            && FirstTouch.hold == true) {
                return (true)
        }
        if ((SecondTouch.isMoving == false) && (id == SecondTouch.pointer)
            && SecondTouch.hold == true) {
            return (true)
        }
        return (false)
    }

    fun getColorLongTouch(): Float {
        if (FirstTouch.hold == true && FirstTouch.isMoving == true) {
            var res = ((FirstTouch.Begin.x - FirstTouch.End.x +
                    FirstTouch.Begin.y - FirstTouch.End.y))
            return (res)
        }
        if (SecondTouch.hold == true && SecondTouch.isMoving == true) {
            var res = ((SecondTouch.Begin.x - SecondTouch.End.x +
                    SecondTouch.Begin.y - SecondTouch.End.y))
            return (res)
        }
        return (0.0f)
    }
}
