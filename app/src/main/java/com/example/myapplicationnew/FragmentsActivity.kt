package com.example.myapplicationnew

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class FragmentsActivity : AppCompatActivity() {

    private val switchFragmentButton: Button by lazy { findViewById(R.id.switchFragmentButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragments)

        // Start with the LoginFragment when the activity is first created
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LoginFragment())
                .commit()
        }

        // Switch between fragments when the button is clicked
        switchFragmentButton.setOnClickListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
            val newFragment: Fragment = if (currentFragment is LoginFragment) {
                RegisterFragment()
            } else {
                LoginFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, newFragment)
                .commit()
        }
    }
}