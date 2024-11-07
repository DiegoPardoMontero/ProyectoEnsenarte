package com.puj.proyectoensenarte.dictionary

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.MediaController
import android.widget.VideoView
import com.puj.proyectoensenarte.R

class CustomVideoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val videoView: VideoView
    private val mediaController: MediaController
    private val controllerContainer: FrameLayout
    private val handler = Handler(Looper.getMainLooper())
    private val hideRunnable = Runnable { hideController() }
    private val AUTO_HIDE_DELAY_MS = 3000L // 3 segundos para auto-ocultar

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_video_view, this, true)

        videoView = findViewById(R.id.videoView)
        controllerContainer = findViewById(R.id.controllerContainer)

        mediaController = object : MediaController(context) {
            override fun show() {
                super.show()
                visibility = View.VISIBLE
                scheduleHiding()
            }

            override fun hide() {
                super.hide()
                visibility = View.GONE
            }

            override fun dispatchTouchEvent(event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        // Cancelar el ocultamiento programado al tocar
                        handler.removeCallbacks(hideRunnable)
                    }
                    MotionEvent.ACTION_UP -> {
                        // Programar ocultamiento después de soltar
                        scheduleHiding()
                    }
                }
                return super.dispatchTouchEvent(event)
            }
        }

        // Configurar el listener de toque para el VideoView
        videoView.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    toggleControllerVisibility()
                    true
                }
                else -> false
            }
        }
    }

    private fun scheduleHiding() {
        handler.removeCallbacks(hideRunnable)
        handler.postDelayed(hideRunnable, AUTO_HIDE_DELAY_MS)
    }

    private fun hideController() {
        mediaController.visibility = View.GONE
    }

    private fun toggleControllerVisibility() {
        if (mediaController.visibility == View.VISIBLE) {
            hideController()
            handler.removeCallbacks(hideRunnable)
        } else {
            mediaController.show()
            scheduleHiding()
        }
    }

    fun setupVideo(url: String?) {
        if (url != null) {
            videoView.setVideoURI(Uri.parse(url))
            videoView.setMediaController(mediaController)
            mediaController.setAnchorView(controllerContainer)
            videoView.setOnPreparedListener { mp ->
                mp.isLooping = true
                videoView.pause()
                videoView.seekTo(1)
                addMediaControllerToContainer()
                // Ocultar los controles inicialmente después de un breve momento
                handler.postDelayed(hideRunnable, 1000)
            }
        }
    }

    private fun addMediaControllerToContainer() {
        controllerContainer.removeAllViews()
        mediaController.visibility = View.VISIBLE

        val parent = mediaController.parent as? ViewGroup
        parent?.removeView(mediaController)

        val lp = FrameLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT,
            Gravity.BOTTOM
        )
        controllerContainer.addView(mediaController, lp)
        scheduleHiding()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        handler.removeCallbacks(hideRunnable)
    }
}