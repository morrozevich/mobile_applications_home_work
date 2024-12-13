package com.example.myapplicationnew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class register_window : AppCompatActivity() {
    // Declare EditText and other views
    private val emailEditText: EditText by lazy { findViewById(R.id.emailEditText) }
    private val passwordEditText: EditText by lazy { findViewById(R.id.passwordEditText2) }
    private val registerButton: Button by lazy { findViewById(R.id.loginButton) }
    private val emailInputLayout: TextInputLayout by lazy { findViewById(R.id.emailInputLayout) }
    private val passwordInputLayout: TextInputLayout by lazy { findViewById(R.id.passwordInputLayout) }
    private val loginText: TextView by lazy { findViewById(R.id.register) }

    private val credentialsManager = CredentialsManager.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_window)


        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Handle the "Login" link click event
        loginText.setOnClickListener {
            navigateToLogin() // Navigate to login screen
        }

        // Handle the Register button click event
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (validateInput(email, password)) {
                val result = credentialsManager.register(email, password)
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show()  // Show result message
                if (result == "Registration successful.") {

                    println("Updated Users Map: ${credentialsManager.getUsers()}")
                    navigateToLogin()
                }
            }
        }
    }


    private fun validateInput(email: String, password: String): Boolean {
        var isValid = true


        if (!credentialsManager.isValidEmail(email)) {
            emailInputLayout.error = "Invalid email format"
            isValid = false
        } else if (credentialsManager.isUserAlreadyRegistered(email)) {
            emailInputLayout.error = "Email already used"
            isValid = false
        } else {
            emailInputLayout.error = null
        }

        if (!credentialsManager.isValidPassword(password)) {
            passwordInputLayout.error = "Password must be at least 8 characters"
            isValid = false
        } else {
            passwordInputLayout.error = null
        }

        return isValid
    }

    private fun navigateToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}