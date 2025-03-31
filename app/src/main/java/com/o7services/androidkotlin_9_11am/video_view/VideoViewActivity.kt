package com.o7services.androidkotlin_9_11am.video_view

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.o7services.androidkotlin_9_11am.R
import com.o7services.androidkotlin_9_11am.databinding.ActivityVideoViewBinding

class VideoViewActivity : AppCompatActivity() {
    private val binding: ActivityVideoViewBinding by lazy {
        ActivityVideoViewBinding.inflate(layoutInflater)
    }
    var Videoview: VideoView? = null
    lateinit var mediaControls: MediaController
    val videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       Videoview = findViewById<VideoView>(R.id.videoView) as VideoView

        if (mediaControls == null) {
            mediaControls = MediaController(this)

            mediaControls!!.setAnchorView(this.Videoview)
        }

        Videoview!!.setMediaController(mediaControls)
        Videoview!!.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.bigbuckbunny))
        Videoview!!.requestFocus()
        Videoview!!.start()

        setVideoViewToLoop(Videoview!!)

        // assigning id of VideoView from
        // activity_main.xml layout file
//        simpleVideoView = findViewById<View>(R.id.simpleVideoView) as VideoView


//        if (!::mediaControls.isInitialized) {
//            // creating an object of media controller class
//            mediaControls = MediaController(this)
//
//            // set the anchor view for the video view
//            mediaControls.setAnchorView(this.binding.videoView)
//        }
//
//        binding.videoView.apply {
//
//            try {
//
//            val videoUri: Uri = Uri.parse("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
//
//            // Set the video URI to the VideoView
//           setVideoURI(videoUri)
//
//          start()
//
//            // Optional: Set up listeners if needed (e.g., for completion or error handling)
//            setOnCompletionListener {
//                // Handle video completion here if necessary
//            }
//
//          setOnErrorListener { mp, what, extra ->
//                // Handle error here if necessary
//                false
//            }
//            // set the media controller for video view
//            setMediaController(mediaControls)
//
//            // set the absolute path of the video file which is going to be played
//          setVideoURI(
//                Uri.parse(
//                    "android.resource://"
//                            + packageName + "/" + R.raw.bigbuckbunny
//                )
//            )
//            setVideoURI(Uri.parse(videoUrl))
//
//           requestFocus()
//
//            // starting the video
//            start()
//
//            // display a toast message
//            // after the video is completed
//
//           setOnCompletionListener {
//                Toast.makeText(
//                    applicationContext, "Video completed",
//                    Toast.LENGTH_LONG
//                ).show()
//                true
//            }
//
//            // display a toast message if any
//            // error occurs while playing the video
//           setOnErrorListener { mp, what, extra ->
//                Toast.makeText(
//                    applicationContext, "An Error Occurred While Playing Video !!!", Toast.LENGTH_LONG
//                ).show()
//                false
//            }
//
//            }catch (e : Exception){
//                Toast.makeText(
//                    applicationContext, "${e.localizedMessage}", Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//        // on below line we are initializing our variables.
//
//
//        // Uri object to refer the
//        // resource from the videoUrl
//        val uri = Uri.parse(videoUrl)
//
//        // sets the resource from the
//        // videoUrl to the videoView
//        binding.videoView.setVideoURI(uri)
//
//        // creating object of
//        // media controller class
//        val mediaController = MediaController(this)
//
//        // sets the anchor view
//        // anchor view for the videoView
//        mediaController.setAnchorView(binding.videoView)
//
//        // sets the media player to the videoView
//        mediaController.setMediaPlayer(binding.videoView)
//
//        // sets the media controller to the videoView
//        binding.videoView.setMediaController(mediaController);
//
//        // starts the video
//        binding.videoView.start();
    }

    private fun setVideoViewToLoop(Videoview : VideoView){
        Videoview.setOnCompletionListener {
            Videoview.start()
        }
    }
}