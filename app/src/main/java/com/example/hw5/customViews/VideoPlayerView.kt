package com.example.hw5.customViews

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.hw5.R
import kotlinx.android.synthetic.main.view_video_player.view.*


class VideoPlayerView
@JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private var isSeekBarVisible = false
    private var isPlayed = false
    private var isPaused = false
    private var previewVisible = 0
    private lateinit var runnable: Runnable
    private var currentPos = 0
    private var isVideoTouched = false
    private var isMin = false
    private val handlerView: Handler = Handler()


    init {
        inflate(context, R.layout.view_video_player, this)
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.VideoPlayerView, defStyleAttr, 0)
            isSeekBarVisible = typedArray.getBoolean(
                R.styleable.VideoPlayerView_isSeekBarVisible,
                isSeekBarVisible
            )
            previewVisible = typedArray.getInteger(
                R.styleable.VideoPlayerView_previewVisible,
                previewVisible
            )
            seekBar.isVisible = isSeekBarVisible
            videoView.setBackgroundResource(R.mipmap.preview)
            typedArray.recycle()
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                videoView.seekTo(seekBar?.progress ?: 0)
                videoView.start()
            }
        })
        videoView.setOnClickListener {
            if (!isVideoTouched) {
                isVideoTouched = true
                controlImageView.visibility = View.GONE
                seekBar.visibility = View.GONE
                maxTimeTextView.visibility = View.GONE
                currentTimeTextView.visibility = View.GONE
            } else if (isVideoTouched) {
                isVideoTouched = false
                controlImageView.visibility = View.VISIBLE
                seekBar.visibility = View.VISIBLE
                maxTimeTextView.visibility = View.VISIBLE
                currentTimeTextView.visibility = View.VISIBLE
            }
        }
        controlImageView.setOnClickListener {
            if (isPlayed && !isPaused) {
                controlImageView.setImageResource(R.drawable.ic_play)
                isPaused = true
                currentPos = videoView.currentPosition
                videoView.pause()
            } else if (!isPlayed && !isPaused) {
                controlImageView.setImageResource(R.drawable.ic_pause)
                isPlayed = true
                videoView.setVideoPath("http://docs.google.com/uc?export=download&id=129IEVOJOvCvR-7Jqg5_bZrTKdegaFUnU")
                videoView.setOnErrorListener { mediaPlayer, i, i2 ->
                    mediaPlayer
                    false
                }
                currentTimeTextView.visibility = View.VISIBLE
                maxTimeTextView.visibility = View.VISIBLE
                currentTimeTextView.text = "00:00 - "
                maxTimeTextView.text = "00:00"
                videoView.setBackgroundResource(0)
                previewTextView.visibility = View.GONE
                seekBar.isVisible = true
                videoView.start()
                videoView.setOnPreparedListener {
                    val totalTime = creatingTimeLabel(it.duration)
                    maxTimeTextView.text = totalTime
                    seekBar.max = it.duration
                    listenPlayerTrack()
                }
            } else if (isPlayed && isPaused) {
                controlImageView.setImageResource(R.drawable.ic_pause)
                isPaused = false
                videoView.seekTo(currentPos)
                videoView.start()
            }
        }

    }

    fun play(url: String) {
        videoView.setVideoPath(url)
        videoView.setOnErrorListener { mediaPlayer, i, i2 ->
            mediaPlayer
            false
        }
        videoView.setBackgroundResource(0)
        seekBar.isVisible = true
        videoView.start()
        videoView.setOnPreparedListener {
            seekBar.max = it.duration
            listenPlayerTrack()
        }
    }

    fun pause() {
        currentPos = videoView.currentPosition
        videoView.pause()
    }

    fun resume() {
        videoView.seekTo(currentPos)
        videoView.start()
    }

    private fun listenPlayerTrack() {
        isMin = true
        runnable = Runnable {
            seekBar.progress = videoView.currentPosition
            currentTimeTextView.text = creatingTimeLabel(seekBar.progress)
            handlerView.postDelayed(runnable, 100)
        }
        handlerView.postDelayed(runnable, 100)
    }

    private fun creatingTimeLabel(duration: Int): String {
        var timeLabel = ""
        val min = duration / 1000 / 60
        val sec = duration / 1000 % 60
        if (isMin) {
            timeLabel += if (min < 10) {
                "0$min:"
            } else {
                "$min:"
            }
            timeLabel += if (sec < 10) {
                "0$sec -"
            } else {
                "$sec -"
            }
            return timeLabel
        } else {
            timeLabel += if (min < 10) {
                "0$min:"
            } else {
                "$min:"
            }
            timeLabel += if (sec < 10) {
                "0$sec"
            } else {
                "$sec"
            }
            return timeLabel
        }
    }

    fun destroy() {
        handlerView.removeCallbacks(runnable)
    }
}