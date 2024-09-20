package com.example.myapplication.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    companion object {
        private const val REQUEST_CODE = 123
    }

    private lateinit var heartRateDisplay: TextView
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        heartRateDisplay = findViewById(R.id.heartRateDisplay)
        sendButton = findViewById(R.id.sendButton)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.BODY_SENSORS), REQUEST_CODE)
        }

        sendButton.setOnClickListener {
            fetchGoogleFitData()
        }
    }

    private fun fetchGoogleFitData() {
        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
            .build()

        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (account == null || !GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                this,
                REQUEST_CODE,
                account ?: GoogleSignIn.getAccountForExtension(this, fitnessOptions), // Provide a fallback account
                fitnessOptions
            )
        } else {
            val readRequest = DataReadRequest.Builder()
                .read(DataType.TYPE_HEART_RATE_BPM)
                .setTimeRange(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1), System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .build()

            Fitness.getHistoryClient(this, account)
                .readData(readRequest)
                .addOnSuccessListener { response ->
                    val heartRateData = response.getDataSet(DataType.TYPE_HEART_RATE_BPM)
                    // Extract heart rate data and update TextView
                    val heartRate = heartRateData.dataPoints.firstOrNull()?.getValue(Field.FIELD_BPM)?.toString() ?: "No data"
                    heartRateDisplay.text = heartRate
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }
}
