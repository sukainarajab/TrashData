package com.example.trashdata.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trashdata.R

class DashboardFragment : Fragment() {

    private lateinit var tvClutterScore: TextView
    private lateinit var tvScoreLabel: TextView
    private lateinit var progressClutter: ProgressBar
    private lateinit var tvForgottenCount: TextView
    private lateinit var tvWastedSize: TextView
    private lateinit var tvLargeCount: TextView
    private lateinit var tvDuplicateCount: TextView
    private lateinit var rvTopForgotten: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvClutterScore   = view.findViewById(R.id.tvClutterScore)
        tvScoreLabel     = view.findViewById(R.id.tvScoreLabel)
        progressClutter  = view.findViewById(R.id.progressClutter)
        tvForgottenCount = view.findViewById(R.id.tvForgottenCount)
        tvWastedSize     = view.findViewById(R.id.tvWastedSize)
        tvLargeCount     = view.findViewById(R.id.tvLargeCount)
        tvDuplicateCount = view.findViewById(R.id.tvDuplicateCount)
        rvTopForgotten   = view.findViewById(R.id.rvTopForgotten)

        // Dummy data — Member 2 (RuleEngine) will replace these values
        tvClutterScore.text   = "47"
        tvScoreLabel.text     = "Moderate"
        progressClutter.progress = 47
        tvForgottenCount.text = "23"
        tvWastedSize.text     = "1.2 GB"
        tvLargeCount.text     = "8"
        tvDuplicateCount.text = "3"

        // Dummy file list — Member 1 (FileScanner) will replace this
        val dummyFiles = listOf(
            DashboardFileItem("project_backup.zip", "187 MB", "92d ago", "90d+"),
            DashboardFileItem("old_resume.pdf",     "2.4 MB", "187d ago", "90d+"),
            DashboardFileItem("screenshot_43.png",  "4.1 MB", "72d ago",  "60d+"),
            DashboardFileItem("meeting_notes.docx", "0.8 MB", "65d ago",  "60d+"),
            DashboardFileItem("dataset_raw.csv",    "44 MB",  "34d ago",  "30d+")
        )

        rvTopForgotten.layoutManager = LinearLayoutManager(requireContext())
        rvTopForgotten.isNestedScrollingEnabled = false
        rvTopForgotten.adapter = DashboardAdapter(dummyFiles)
    }
}