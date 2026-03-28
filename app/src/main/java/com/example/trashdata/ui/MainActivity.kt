package com.example.trashdata.ui

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.trashdata.R
import com.example.trashdata.ui.dashboard.DashboardFragment
import com.example.trashdata.ui.files.FileListFragment
import com.example.trashdata.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            // Member 1 (FileScanner) will act on the result here
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        prefs = getSharedPreferences("trashdata_prefs", 0)

        // Show splash for 2.5 seconds then load main UI
        Handler(Looper.getMainLooper()).postDelayed({
            setContentView(R.layout.activity_main)
            setupNav()

            // Show permission dialog only on first ever launch
            val isFirstLaunch = prefs.getBoolean("first_launch", true)
            if (isFirstLaunch) {
                showPermissionDialog()
                prefs.edit().putBoolean("first_launch", false).apply()
            }
        }, 2500)
    }

    private fun setupNav() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        loadFragment(DashboardFragment())

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> loadFragment(DashboardFragment())
                R.id.nav_files     -> loadFragment(FileListFragment())
                R.id.nav_settings  -> loadFragment(SettingsFragment())
            }
            true
        }
    }

    private fun showPermissionDialog() {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_permission, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogView.findViewById<Button>(R.id.btnAllow).setOnClickListener {
            requestStoragePermission()
            dialog.dismiss()
        }

        dialogView.findViewById<Button>(R.id.btnDeny).setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun requestStoragePermission() {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.READ_MEDIA_AUDIO
            )
        } else {
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        val allGranted = permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }

        if (!allGranted) {
            requestPermissionLauncher.launch(permissions)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}