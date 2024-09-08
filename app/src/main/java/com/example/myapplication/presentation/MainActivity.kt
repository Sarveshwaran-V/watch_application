package com.example.watchapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.watchapp.services.HealthDataService

class MainActivity : ComponentActivity() {

    companion object {
        private const val REQUEST_CODE = 123
    }

    private lateinit var heartRateInput: EditText
    private lateinit var spo2Input: EditText
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Use the generated R class here

        heartRateInput = findViewById(R.id.heartRateInput)
        spo2Input = findViewById(R.id.spo2Input)
        sendButton = findViewById(R.id.sendButton)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BODY_SENSORS), REQUEST_CODE)
        }

        sendButton.setOnClickListener {
            val heartRate = heartRateInput.text.toString().toInt()
            val spo2 = spo2Input.text.toString().toInt()

            val intent = Intent(this, HealthDataService::class.java).apply {
                putExtra("heartRate", heartRate)
                putExtra("spo2", spo2)
            }
            HealthDataService.enqueueWork(this, intent)
        }
    }
}
