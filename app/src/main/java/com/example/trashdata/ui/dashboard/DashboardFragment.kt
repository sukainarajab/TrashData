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
    private lateinit var tvEmptyState: TextView

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
        tvEmptyState     = view.findViewById(R.id.tvEmptyState)

        // Placeholders — Member 2 (RuleEngine) will populate these
        tvClutterScore.text      = "--"
        tvScoreLabel.text        = "Not scanned yet"
        progressClutter.progress = 0
        tvForgottenCount.text    = "--"
        tvWastedSize.text        = "--"
        tvLargeCount.text        = "--"
        tvDuplicateCount.text    = "--"

        // Empty state shown until Member 1 provides real data
        tvEmptyState.visibility   = View.VISIBLE
        rvTopForgotten.visibility = View.GONE

        rvTopForgotten.layoutManager = LinearLayoutManager(requireContext())
        rvTopForgotten.isNestedScrollingEnabled = false
    }
}