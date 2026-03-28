package com.example.trashdata.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trashdata.R

// Display model — Member 1 will map their FileItem into this
data class DashboardFileItem(
    val name: String,
    val size: String,
    val daysAgo: String,
    val badge: String
)

class DashboardAdapter(
    private val files: List<DashboardFileItem>
) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFileName:   TextView = itemView.findViewById(R.id.tvFileName)
        val tvDaysUnused: TextView = itemView.findViewById(R.id.tvDaysUnused)
        val tvFileSize:   TextView = itemView.findViewById(R.id.tvFileSize)
        val tvBadge:      TextView = itemView.findViewById(R.id.tvBadge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_dashboard_file, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val file = files[position]
        holder.tvFileName.text   = file.name
        holder.tvFileSize.text   = file.size
        holder.tvDaysUnused.text = file.daysAgo
        holder.tvBadge.text      = file.badge

        val badgeColor = when (file.badge) {
            "90d+" -> R.color.badge_red
            "60d+" -> R.color.badge_yellow
            else   -> R.color.badge_green
        }
        holder.tvBadge.setTextColor(holder.itemView.context.getColor(badgeColor))
    }

    override fun getItemCount(): Int = files.size
}