package com.example.myapplicationnew

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val emailEditText: EditText by lazy { findViewById(R.id.emailEditText) }
    private val passwordEditText: EditText by lazy { findViewById(R.id.passwordEditText) }
    private val emailInputLayout: TextInputLayout by lazy { findViewById(R.id.emailInputLayout) }
    private val passwordInputLayout: TextInputLayout by lazy { findViewById(R.id.passwordInputLayout) }
    private val loginButton: Button by lazy { findViewById(R.id.loginButton) }
    private val credentialsManager = CredentialsManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerText: TextView = findViewById(R.id.register)
        registerText.setOnClickListener {
            val intent = Intent(this, register_window::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email == "test@te.st" && password == "1234") {
                val intent = Intent(this, login::class.java)
                startActivity(intent)
            } else {
                var isValid = true

                if (!credentialsManager.isValidEmail(email)) {
                    emailInputLayout.error = "Invalid email format"
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

                if (isValid) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}
