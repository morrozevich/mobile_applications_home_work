package com.example.myapplicationnew

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplicationnew.FragmentA
import com.example.myapplicationnew.FragmentB

class FragmentsActivity : AppCompatActivity() {

    private val switchFragmentButton: Button by lazy { findViewById(R.id.switchFragmentButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragments)

        // Set initial fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, FragmentA()) // Show FragmentA initially
                .commit()
        }

        // Button click to switch fragments
        switchFragmentButton.setOnClickListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            val newFragment = if (currentFragment is FragmentA) {
                FragmentB()
            } else {
                FragmentA()
            }

            // Switch to the new fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, newFragment)
                .commit()
        }
    }
}