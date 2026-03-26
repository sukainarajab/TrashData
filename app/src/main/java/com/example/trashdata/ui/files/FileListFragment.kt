package com.example.trashdata.ui.files

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trashdata.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class FileListFragment : Fragment() {

    private lateinit var chipGroup: ChipGroup
    private lateinit var rvFiles: RecyclerView
    private lateinit var tvSelectionSummary: TextView
    private lateinit var btnDeleteSelected: Button
    private lateinit var selectionBar: View

    // Dummy data — Member 1 (FileScanner) will replace this
    private val dummyFiles = listOf(
        FileDisplayItem("project_backup.zip", "187 MB · 92d ago", "90d+"),
        FileDisplayItem("old_resume.pdf",     "2.4 MB · 187d ago", "90d+"),
        FileDisplayItem("screenshot_43.png",  "4.1 MB · 72d ago",  "60d+"),
        FileDisplayItem("meeting_notes.docx", "0.8 MB · 65d ago",  "60d+"),
        FileDisplayItem("android_setup.exe",  "1.1 GB · 91d ago",  "90d+"),
        FileDisplayItem("dataset_raw.csv",    "44 MB · 34d ago",   "30d+"),
        FileDisplayItem("wallpaper_bg.jpg",   "8.7 MB · 62d ago",  "60d+"),
        FileDisplayItem("temp_export.pdf",    "1.2 MB · 90d ago",  "90d+"),
        FileDisplayItem("video_sample.mp4",   "340 MB · 31d ago",  "30d+")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_file_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chipGroup         = view.findViewById(R.id.chipGroupFilter)
        rvFiles           = view.findViewById(R.id.rvFiles)
        tvSelectionSummary = view.findViewById(R.id.tvSelectionSummary)
        btnDeleteSelected = view.findViewById(R.id.btnDeleteSelected)
        selectionBar      = view.findViewById(R.id.selectionBar)

        setupRecyclerView(dummyFiles)
        setupChips()

        btnDeleteSelected.setOnClickListener {
            Toast.makeText(requireContext(), "Delete tapped", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView(files: List<FileDisplayItem>) {
        rvFiles.layoutManager = LinearLayoutManager(requireContext())
        rvFiles.adapter = FileAdapter(files) { selectedCount ->
            if (selectedCount > 0) {
                selectionBar.visibility    = View.VISIBLE
                tvSelectionSummary.text    = "$selectedCount selected"
            } else {
                selectionBar.visibility    = View.GONE
            }
        }
    }

    private fun setupChips() {
        val chipAll = view?.findViewById<Chip>(R.id.chipAll)
        chipAll?.isChecked = true

        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            val filtered = when {
                checkedIds.contains(R.id.chip90d) -> dummyFiles.filter { it.badge == "90d+" }
                checkedIds.contains(R.id.chip60d) -> dummyFiles.filter { it.badge == "90d+" || it.badge == "60d+" }
                else -> dummyFiles
            }
            setupRecyclerView(filtered)
        }
    }
}