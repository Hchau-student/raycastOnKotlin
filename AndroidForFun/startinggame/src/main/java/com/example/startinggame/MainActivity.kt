package com.example.startinggame

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    private var glSurfaceView: GLSurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            //а вдруг не поддерживает
        if (!supportES2()) {
            Toast.makeText(this, "OpenGl ES 2.0 is not supported", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        //подготовка компонента view
        glSurfaceView = GLSurfaceView(this)
        glSurfaceView!!.setEGLContextClientVersion(2)
//        glSurfaceView!!.setRenderer(y_OpenGLRender())
        glSurfaceView!!.setRenderer(y_OpenGLRenderer(this))
        setContentView(glSurfaceView)
    }

    override fun onPause() {
        //??
        super.onPause()
        glSurfaceView!!.onPause()
    }

    override fun onResume() {
        super.onResume()
        glSurfaceView!!.onResume()
    }

    private fun supportES2(): Boolean {
        //проверка поддерживается ли opengl 2
        val activityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        return configurationInfo.reqGlEsVersion >= 0x20000
    }
}


