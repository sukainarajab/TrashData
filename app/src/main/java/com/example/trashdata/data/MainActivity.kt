package com.example.trashdata

import com.example.trashdata.data.model.FileItem
import com.example.trashdata.data.rules.RuleEngine
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.trashdata.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnScan = findViewById<Button>(R.id.btnScan)
        val btnForgotten = findViewById<Button>(R.id.btnForgotten)
        val txtResult = findViewById<TextView>(R.id.txtResult)

        btnScan.setOnClickListener {

            val files = mutableListOf<FileItem>()

            // test files
            files.add(
                FileItem(
                    "resume.pdf",
                    2_000_000,
                    System.currentTimeMillis() - (40L * 24 * 60 * 60 * 1000)
                )
            )

            files.add(
                FileItem(
                    "movie.mp4",
                    120_000_000,
                    System.currentTimeMillis() - (10L * 24 * 60 * 60 * 1000)
                )
            )

            files.add(
                FileItem(
                    "notes.pdf",
                    1_000_000,
                    System.currentTimeMillis() - (90L * 24 * 60 * 60 * 1000)
                )
            )

            // apply rules
            for (file in files) {

                val days = RuleEngine.getDaysUnused(file.lastModified)

                file.daysUnused = days

                if (days >= 30) {
                    file.isForgotten = true
                }

                if (RuleEngine.isLargeFile(file.size)) {
                    file.isLarge = true
                }
            }

            val clutterScore = RuleEngine.calculateClutterScore(files)
            val storage = RuleEngine.calculateForgottenStorage(files)

            txtResult.text =
                "Files scanned: ${files.size}\nClutter Score: $clutterScore\nForgotten Storage: $storage bytes"
        }

        btnForgotten.setOnClickListener {
            txtResult.text = "Showing forgotten files..."
        }
    }
}