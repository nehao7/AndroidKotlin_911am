package com.o7services.androidkotlin_9_11am.video_view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.o7services.androidkotlin_9_11am.R
import com.o7services.androidkotlin_9_11am.databinding.ActivityPickVideoBinding

class PickVideoActivity : AppCompatActivity() {
    private val binding:ActivityPickVideoBinding by lazy {
        ActivityPickVideoBinding.inflate(layoutInflater)
    }
    private val pickVideo = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val videoUri: Uri? = result.data?.data
            println("VideoUri : $videoUri")
            if (videoUri != null) {
                playVideo(videoUri)
            }else{
                Toast.makeText(this, "Video Uri is null", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnPickVideo.setOnClickListener {
            pickVideoFromGallery()
        }

    }
    private fun pickVideoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        pickVideo.launch(intent)
    }

    private fun playVideo(videoUri: Uri) {
        try{

        val mediaController = MediaController(this)
            binding.videoView.setMediaController(mediaController)
        mediaController.setAnchorView(binding.videoView)

            binding.videoView.setVideoURI(videoUri)
            binding.videoView.requestFocus()
            binding.videoView.start()

            binding.videoView.setOnErrorListener { mp, what, extra ->
            Toast.makeText(this, "Video playback error.", Toast.LENGTH_SHORT).show()
            true
        }

    } catch (e: Exception) {
        Toast.makeText(this, "Invalid video URI.", Toast.LENGTH_SHORT).show()
        e.printStackTrace()
    }


    }

}