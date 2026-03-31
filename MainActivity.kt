package com.example.trashdata

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import com.example.trashdata.worker.ScanWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        askNotificationPermission()
        scheduleDailyScan()

        setContent {
            var status by remember { mutableStateOf("Press the button to test!") }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🗑️ TrashData", fontSize = 32.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Member 4 — Notifications", fontSize = 16.sp)
                Spacer(modifier = Modifier.height(40.dp))

                Button(onClick = {
                    val testRequest = OneTimeWorkRequestBuilder<ScanWorker>().build()
                    WorkManager.getInstance(this@MainActivity).enqueue(testRequest)
                    status = "✅ Done! Swipe down from top to see notification"
                }) {
                    Text("Test Notification Now")
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(status, fontSize = 14.sp)
            }
        }
    }

    private fun scheduleDailyScan() {
        val dailyRequest = PeriodicWorkRequestBuilder<ScanWorker>(
            24, TimeUnit.HOURS
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "daily_trash_scan",
            ExistingPeriodicWorkPolicy.KEEP,
            dailyRequest
        )
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    100
                )
            }
        }
    }
}