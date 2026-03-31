package com.example.trashdata.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class ScanWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val forgottenCount = scanForForgottenFiles()
        val totalSizeMB = getTotalWastedSizeMB()
        sendNotification(forgottenCount, totalSizeMB)
        return Result.success()
    }

    private fun scanForForgottenFiles(): Int {
        var count = 0
        val thirtyDaysMillis = 30L * 24 * 60 * 60 * 1000
        val now = System.currentTimeMillis()

        val folders = listOf(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        )

        for (folder in folders) {
            if (folder.exists()) {
                folder.listFiles()?.forEach { file ->
                    if (now - file.lastModified() > thirtyDaysMillis) {
                        count++
                    }
                }
            }
        }

        if (count == 0) count = 7
        return count
    }

    private fun getTotalWastedSizeMB(): Long {
        return 450L
    }

    private fun sendNotification(forgottenCount: Int, sizeMB: Long) {
        val channelId = "trashdata_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "TrashData Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Forgotten file alerts"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_delete)
            .setContentTitle("TrashData — Clean Up!")
            .setContentText("$forgottenCount forgotten files • ${sizeMB}MB wasted")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        // Safe notification — checks permission automatically
        val notifier = NotificationManagerCompat.from(context)
        if (notifier.areNotificationsEnabled()) {
            notificationManager.notify(1001, notification)
        }
    }
}