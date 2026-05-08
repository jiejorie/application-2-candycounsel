package com.example.secondapplication_candycounsel

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_results)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //================== SHOW RESULTS ==================

        // Receive string recommendation from Preferences Activity
        val candyRecString = intent.getStringExtra("candy_rec")

        // Initialize text and data to display candy recommendation
        val dreamSweets = findViewById<TextView>(R.id.dream_sweets)
        dreamSweets.text = candyRecString


        //================== RETURN TO HOME ==================

        // Return to Home
        val homeBtn = findViewById<Button>(R.id.button_return_home)
        homeBtn.setOnClickListener {
            // Dismiss Alert Dialog in Home Activity before return
            val alertDismissed = true
            // Send dismiss bool and string recommendation to Home Activity
            Intent(this@ResultsActivity, HomeActivity::class.java).also{
                it.putExtra("is_dismissed", alertDismissed)
                it.putExtra("candy_rec", candyRecString)
                startActivity(it)
            }

        }

        //================== GO BACK ==================

        // Return to Preferences
        val backBtn = findViewById<Button>(R.id.button_go_back)
        backBtn.setOnClickListener {
            val intent = Intent(this@ResultsActivity, PreferencesActivity::class.java)
            startActivity(intent)
        }
    }

}

