package com.example.secondapplication_candycounsel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //================== POP UP MODAL ==================

        // Set up Pop Up Design
        val dialogBuilder = AlertDialog.Builder(this, R.style.CustomDialog)
        val dialogView = layoutInflater.inflate(R.layout.modal_layout, null)
        dialogBuilder.setView(dialogView)

        // Prevents clicks outside the Dialog pop up to dismiss
        dialogBuilder.setCancelable(false)

        // Create Dialog
        val instructionsDialog = dialogBuilder.create()

        // Results Activity prevents Dialog to open once returned to Home
        val alertDismissed = intent.getBooleanExtra("is_dismissed", false)
        if (!alertDismissed) {
            instructionsDialog.show()
        }

        // Dismiss Pop Up
        val proceedBtn: Button = dialogView.findViewById(R.id.button_proceed)
        proceedBtn.setOnClickListener {
            instructionsDialog.dismiss()
        }

        //================== CHANGING TEXTVIEW ==================

        // Initialize Shared Preferences and Editor
        val sharedPref = getSharedPreferences("myCandyPref", MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Get necessary data to display candy suggestions
        var homeCandyRecString = intent.getStringExtra("candy_rec")
        val savedCandyRec = sharedPref.getString("saved_candy_rec", homeCandyRecString)
        val homeDreamSweets = findViewById<TextView>(R.id.home_dream_sweet)

        // Check for previously saved preferences
        if (savedCandyRec != null ) {
            // Gets latest string recommendation from Results
            homeCandyRecString = intent.getStringExtra("candy_rec")

            // Save preferences
            editor.apply {
                putString("saved_candy_rec", homeCandyRecString)
                apply()
            }

            // Display recommendation text even after the app closes
            if (homeCandyRecString == null) {
                homeDreamSweets.text = "Willy Wonka advises you to eat some $savedCandyRec"
            }
            else {
                homeDreamSweets.text = "Willy Wonka advises you to eat some $homeCandyRecString"
            }
        }

        //================== MOVE TO PREFERENCES ACTIVITY ==================

        // Proceed to Preferences
        val setCandyPref = findViewById<Button>(R.id.set_candy_pref_button)
        setCandyPref.setOnClickListener {
            Intent(this@HomeActivity, PreferencesActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}