package com.example.trashdata.data.rules

import com.example.trashdata.data.model.FileItem
import java.util.concurrent.TimeUnit

object RuleEngine {

    // calculate how many days the file hasn't been used
    fun getDaysUnused(lastModified: Long): Int {
        val diff = System.currentTimeMillis() - lastModified
        return TimeUnit.MILLISECONDS.toDays(diff).toInt()
    }

    // classify file based on inactivity
    fun getForgottenLevel(days: Int): String {
        return when {
            days >= 90 -> "HIGH"
            days >= 60 -> "MEDIUM"
            days >= 30 -> "LOW"
            else -> "ACTIVE"
        }
    }

    // check if file is large
    fun isLargeFile(size: Long): Boolean {
        val sizeMB = size / (1024 * 1024)
        return sizeMB >= 50
    }

    // calculate storage used by forgotten files
    fun calculateForgottenStorage(files: List<FileItem>): Long {

        var total: Long = 0

        for (file in files) {
            if (file.isForgotten) {
                total += file.size
            }
        }

        return total
    }

    // calculate clutter score
    fun calculateClutterScore(files: List<FileItem>): Int {

        var forgottenCount = 0
        var largeCount = 0

        for (file in files) {

            if (file.isForgotten)
                forgottenCount++

            if (file.isLarge)
                largeCount++
        }

        val score = (forgottenCount * 2) + (largeCount * 3)

        return score
    }

}

