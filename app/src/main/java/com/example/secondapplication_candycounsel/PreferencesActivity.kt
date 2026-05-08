package com.example.secondapplication_candycounsel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PreferencesActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    //List of Textures for Texture Spinner
    var listOfTextures = arrayOf(
        "Crunchy", "Chewy", "Creamy"
    )

    //List of Flavors for Flavor Spinner
    var listOfFlavors = arrayOf(
        "Sweet", "Sour", "Minty"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preferences)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //================== SPINNERS ==================

        // Get Spinners from activity_home.xml
        val spin = findViewById<Spinner>(R.id.texture_spinner)
        spin.onItemSelectedListener = this
        val spin2 = findViewById<Spinner>(R.id.flavor_spinner)
        spin2.onItemSelectedListener = this

        // Create the instances of ArrayAdapters
        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            R.layout.selected_spinner_item, listOfTextures
        )
        val ad2: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            R.layout.selected_spinner_item, listOfFlavors
        )

        // Set Spinner Layout
        ad.setDropDownViewResource(
            R.layout.spinner_items
        )
        ad2.setDropDownViewResource(
            R.layout.spinner_items
        )

        // Set the ArrayAdapters' data on the Spinners, which binds data to spinners
        spin.adapter = ad
        spin2.adapter = ad2

        // Initialize Shared Preferences and Editor
        val sharedPref = getSharedPreferences("myCandyPref", MODE_PRIVATE)
        val editor = sharedPref.edit()

        // Get Saved Preferences
        val savedTexture = sharedPref.getInt("saved_texture", 0)
        val savedFlavor = sharedPref.getInt("saved_flavor", 0)

        // Set spinner positions based on saved preferences
        spin.setSelection(savedTexture)
        spin2.setSelection(savedFlavor)

        //================== SUBMIT ==================

        val submitBtn = findViewById<Button>(R.id.button_submit)
        submitBtn.setOnClickListener {

            //  Toast to indicate submit click
            Toast.makeText(applicationContext, "Preferences Saved and Submitted!", Toast.LENGTH_LONG).show()

            // Get currently chosen item in spinner
            val texture = spin.selectedItemPosition
            val flavor = spin2.selectedItemPosition

            // Save Preferences
            editor.apply {
                putInt("saved_texture", texture)
                putInt("saved_flavor", flavor)
                apply()
            }

            //================== CANDY SUGGESTIONS ==================

            // Store Candy recommendation
            val candyRec: Any = when (spin.selectedItemPosition) {

                //================== CRUNCHY ==================

                // Sweet
                0 if spin2.selectedItemPosition == 0 -> {
                    "Dubai Chocolate Pistachio Kunafa"
                }
                // Sour
                0 if spin2.selectedItemPosition == 1 -> {
                    "Trolli Sour Gummi Crunchers"
                }
                // Minty
                0 if spin2.selectedItemPosition == 2 -> {
                    "Peppermint Candy Canes"
                }

                //================== CHEWY ==================

                // Sweet
                1 if spin2.selectedItemPosition == 0 -> {
                    "Sugar Coated jelly Candy Bites"
                }
                // Sour
                1 if spin2.selectedItemPosition == 1 -> {
                    "Sour Patches"
                }
                // Minty
                1 if spin2.selectedItemPosition == 2 -> {
                    "Peppermint Puffs"
                }

                //================== CREAMY ==================

                // Sweet
                2 if spin2.selectedItemPosition == 0 -> {
                    "Tiffany Milk Cream Eclairs"
                }
                // Sour
                2 if spin2.selectedItemPosition == 1 -> {
                    "Sweet and Sour Rainbow Ropes"
                }
                // Minty
                2 if spin2.selectedItemPosition == 2 -> {
                    "Peppermint Creams"
                }

                else -> {}
            }

            // Converts recommendation to string data type
            val candyRecString = candyRec.toString()

            // Send string recommendation to Results Activity
            Intent(this@PreferencesActivity, ResultsActivity::class.java).also {
                it.putExtra("candy_rec", candyRecString)
                startActivity(it)
            }
        }

    }

    //Show Toast on select
    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long)
    {
        // make toast of name of flavor and texture
        // which is selected in spinner
        when (parent.id) {
            R.id.texture_spinner -> Toast.makeText(applicationContext, listOfTextures[position], Toast.LENGTH_SHORT).show()
            R.id.flavor_spinner -> Toast.makeText(applicationContext, listOfFlavors[position], Toast.LENGTH_SHORT).show()
        }
    }

    //No Selection
    override fun onNothingSelected(parent: AdapterView<*>?) {}

}