package com.faris.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.Toast
import com.faris.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    //viewBinding
    private lateinit var binding: ActivityMainBinding

    // Keys for saving the state
    val IS_RUNNING_KEY = "isRunning"
    val OFFSET_KEY = "offset"
    val BASE_KEY = "base"

//    lateinit var stopwatch : Chronometer // The stopwatch
    var isRunning = false // Is it running
    var offset : Long = 0 // Offset for the stopwatch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        stopwatch = findViewById(R.id.stopwatch)

        // Restoring the previous state
        if (savedInstanceState !=null ){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            isRunning = savedInstanceState.getBoolean(IS_RUNNING_KEY)
            if (isRunning){
                binding.stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                binding.stopwatch.start()
            }
            else {
                setBaseTime()
            }
            //toastText("States restored!")
        }

//        val startButton = findViewById<Button>(R.id.start_button)
//        val pauseButton = findViewById<Button>(R.id.puse_button)
//        val resetButton = findViewById<Button>(R.id.reset_button)

        binding.startButton.setOnClickListener {
            if (!isRunning){
                setBaseTime()
                binding.stopwatch.start()
                isRunning = true
            }
           toastText("Stopwatch started!")
        }

        binding.puseButton.setOnClickListener {
            if (isRunning){
                saveOffset()
                binding.stopwatch.stop()
                isRunning = false
            }
            toastText("Stopwatch Paused!")
        }

        binding.resetButton.setOnClickListener {
            offset = 0
            setBaseTime()
        }
    }

//    override fun onStop() {
//        super.onStop()
//        // Stopping the stopwatch when the activity isn't visible
//        if (isRunning){
//            saveOffset()
//            stopwatch.stop()
//        }
//        toastText("OnStop!")
//    }

//    override fun onRestart() {
//        super.onRestart()
//        // If the stopwatch was running make it run again when its visible
//        if (isRunning){
//            setBaseTime()
//            stopwatch.start()
//            offset = 0
//        }
//        toastText("OnRestart!")
//    }

    override fun onPause() {
        super.onPause()
        if (isRunning){
            saveOffset()
            binding.stopwatch.stop()
        }
        toastText("OnPause!")
    }

        override fun onResume() {
        super.onResume()
        // If the stopwatch was running make it run again when its visible
        if (isRunning){
            setBaseTime()
            binding.stopwatch.start()
            offset = 0
        }
        toastText("OnResume!")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(IS_RUNNING_KEY, isRunning)
        outState.putLong(OFFSET_KEY, offset)
        outState.putLong(BASE_KEY, binding.stopwatch.base)
        //toastText("States saved!")
        super.onSaveInstanceState(outState)
    }

    private fun saveOffset() {
        offset = SystemClock.elapsedRealtime() -  binding.stopwatch.base
    }

    private fun setBaseTime() {
        //Stopwatch.base is the number of milliseconds since the device was booted
        binding.stopwatch.base = SystemClock.elapsedRealtime() - offset
    }

    private fun toastText(text: Any){
        Toast.makeText(this, text as String, Toast.LENGTH_SHORT).show()
    }
}