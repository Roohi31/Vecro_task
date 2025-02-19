package me.sabapro.vecros_task

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.rtsp.RtspMediaSource
import me.sabapro.vecros_task.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private var player : ExoPlayer? = null
    private var currentURL : String = ""
    private var playBackPosition : Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgPlayBtn.setOnClickListener {
            startStreaming()
        }


        binding.imgPauseBtn.setOnClickListener {
            pauseStreaming()
        }

        binding.imgStopBtn.setOnClickListener {
            stopStreaming()
        }

        binding.playerView.setOnClickListener {
            binding.buttonsLinearlayout.visibility = View.VISIBLE
        }

    }


    private fun startStreaming()
    {
        val url = intent.getStringExtra("URL") ?: ""
        binding.buttonsLinearlayout.visibility = View.GONE
        binding.playerView.visibility = View.VISIBLE

        if(url != currentURL)
        {
            currentURL = url
            playBackPosition = 0L
            player = ExoPlayer.Builder(this).build().also {
                    exoPlayer -> binding.playerView.player = exoPlayer

                val mediaItem = MediaItem.fromUri(url)
                exoPlayer?.setMediaItem(mediaItem)
                exoPlayer?.prepare()
                exoPlayer?.playWhenReady = true

            }

        }
        else
        {
            player?.seekTo(playBackPosition)
            player?.play()
        }
    }

    private fun pauseStreaming()
    {
        binding.buttonsLinearlayout.visibility = View.GONE
        player?.let {
            it.pause()
            playBackPosition = it.currentPosition
        }
        Toast.makeText(this,"Streaming Paused",Toast.LENGTH_SHORT).show()
    }

    private fun stopStreaming()
    {
        binding.buttonsLinearlayout.visibility = View.GONE
        player?.let {
            it.stop()
            it.seekTo(0)
            it.release()
            player = null
        }
        playBackPosition = 0L
        currentURL = ""
        Toast.makeText(this,"Streaming Stopped",Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        player?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }

}