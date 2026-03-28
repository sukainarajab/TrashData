package com.example.trashdata.ui.files

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trashdata.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class FileListFragment : Fragment() {

    private lateinit var chipGroup: ChipGroup
    private lateinit var rvFiles: RecyclerView
    private lateinit var tvEmptyState: TextView
    private lateinit var tvSelectionSummary: TextView
    private lateinit var btnDeleteSelected: Button
    private lateinit var selectionBar: View

    // Member 1 (FileScanner) + Member 2 (RuleEngine) will provide this list
    private var fileList: List<FileDisplayItem> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_file_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chipGroup          = view.findViewById(R.id.chipGroupFilter)
        rvFiles            = view.findViewById(R.id.rvFiles)
        tvEmptyState       = view.findViewById(R.id.tvEmptyState)
        tvSelectionSummary = view.findViewById(R.id.tvSelectionSummary)
        btnDeleteSelected  = view.findViewById(R.id.btnDeleteSelected)
        selectionBar       = view.findViewById(R.id.selectionBar)

        // Set "All" chip selected by default
        view.findViewById<Chip>(R.id.chipAll).isChecked = true

        // Show empty state until real data arrives
        showEmptyState()

        setupRecyclerView()
        setupChips()

        btnDeleteSelected.setOnClickListener {
            // Member 1 will implement actual deletion
        }
    }

    private fun setupRecyclerView() {
        rvFiles.layoutManager = LinearLayoutManager(requireContext())
        rvFiles.adapter = FileAdapter(fileList) { selectedCount ->
            if (selectedCount > 0) {
                selectionBar.visibility    = View.VISIBLE
                tvSelectionSummary.text    = "$selectedCount selected"
            } else {
                selectionBar.visibility    = View.GONE
            }
        }
    }

    private fun setupChips() {
        chipGroup.setOnCheckedStateChangeListener { _, _ ->
            // Member 2 (RuleEngine) will filter by threshold here
            showEmptyState()
        }
    }

    private fun showEmptyState() {
        tvEmptyState.visibility = View.VISIBLE
        rvFiles.visibility      = View.GONE
        selectionBar.visibility = View.GONE
    }

    // Called by Member 1 to populate the list once scanning is done
    fun submitFiles(files: List<FileDisplayItem>) {
        fileList = files
        if (files.isEmpty()) {
            showEmptyState()
        } else {
            tvEmptyState.visibility = View.GONE
            rvFiles.visibility      = View.VISIBLE
            rvFiles.adapter = FileAdapter(files) { selectedCount ->
                if (selectedCount > 0) {
                    selectionBar.visibility    = View.VISIBLE
                    tvSelectionSummary.text    = "$selectedCount selected"
                } else {
                    selectionBar.visibility    = View.GONE
                }
            }
        }
    }
}