package com.example.trashdata.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.trashdata.R

class SettingsFragment : Fragment() {

    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = requireActivity().getSharedPreferences("trashdata_prefs", 0)

        // Notifications toggle
        val tvNotifVal = view.findViewById<TextView>(R.id.tvNotificationsVal)
        tvNotifVal.text = if (prefs.getBoolean("notifications", true)) "On" else "Off"

        view.findViewById<View>(R.id.rowNotifications).setOnClickListener {
            val current = prefs.getBoolean("notifications", true)
            prefs.edit().putBoolean("notifications", !current).apply()
            tvNotifVal.text = if (!current) "On" else "Off"
        }
    }
}