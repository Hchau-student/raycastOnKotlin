package com.example.myapplication

import android.content.pm.ActivityInfo
import android.graphics.Point
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView
import kotlin.system.exitProcess

class watchClip : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
        setContentView(R.layout.activity_watch_clip)

        //  initialasing video
        var videoClip: VideoView = findViewById(R.id.bee_gees)
        val uri = Uri.parse("android.resource://"
                + getPackageName() + "/" + R.raw.bee_gees_clip)
        videoClip.setVideoURI(uri)

        //  var mediaControll = MediaController(this)
        videoClip.setMediaController(MediaController(this))
        videoClip.start()

    }
}
