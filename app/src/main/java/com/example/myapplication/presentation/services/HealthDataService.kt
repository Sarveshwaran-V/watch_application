package com.example.watchapp.services

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.JobIntentService

class HealthDataService : JobIntentService() {

    companion object {
        private const val JOB_ID = 1000

        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, HealthDataService::class.java, JOB_ID, work)
        }
    }

    override fun onHandleWork(intent: Intent) {
        val heartRate = intent.getIntExtra("heartRate", -1)
        val spo2 = intent.getIntExtra("spo2", -1)

        // Here you would send the data to the mobile application
        // This can be done using an API call, Bluetooth, etc.
    }
}
