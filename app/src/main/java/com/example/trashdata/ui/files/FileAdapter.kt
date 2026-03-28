package com.example.trashdata.ui.files

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trashdata.R

// Display model — Member 1 will map their FileItem into this
data class FileDisplayItem(
    val name: String,
    val meta: String,   // e.g. "187 MB · 92d ago"
    val badge: String   // e.g. "90d+"
)

class FileAdapter(
    private val files: List<FileDisplayItem>,
    private val onSelectionChanged: (Int) -> Unit
) : RecyclerView.Adapter<FileAdapter.ViewHolder>() {

    private val selectedPositions = mutableSetOf<Int>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFileName: TextView = itemView.findViewById(R.id.tvFileName)
        val tvFileMeta: TextView = itemView.findViewById(R.id.tvFileMeta)
        val tvBadge:    TextView = itemView.findViewById(R.id.tvBadge)
        val cbSelect:   CheckBox = itemView.findViewById(R.id.cbSelect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_file, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = files[position]

        holder.tvFileName.text = file.name
        holder.tvFileMeta.text = file.meta
        holder.tvBadge.text    = file.badge

        val badgeColor = when (file.badge) {
            "90d+" -> R.color.badge_red
            "60d+" -> R.color.badge_yellow
            else   -> R.color.badge_green
        }
        holder.tvBadge.setTextColor(holder.itemView.context.getColor(badgeColor))

        holder.cbSelect.setOnCheckedChangeListener(null)
        holder.cbSelect.isChecked = selectedPositions.contains(position)

        val toggle = {
            if (selectedPositions.contains(position)) {
                selectedPositions.remove(position)
            } else {
                selectedPositions.add(position)
            }
            holder.cbSelect.isChecked = selectedPositions.contains(position)
            onSelectionChanged(selectedPositions.size)
        }

        holder.itemView.setOnClickListener { toggle() }
        holder.cbSelect.setOnCheckedChangeListener { _, _ -> toggle() }
    }

    override fun getItemCount(): Int = files.size
}