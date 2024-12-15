package com.example.myapplicationnew

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView

    private val credentialsManager = CredentialsManager.instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Initialize UI elements
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        emailInputLayout = view.findViewById(R.id.emailInputLayout)
        passwordInputLayout = view.findViewById(R.id.passwordInputLayout)
        loginButton = view.findViewById(R.id.loginButton)
        registerText = view.findViewById(R.id.register)

        // Set listeners
        loginButton.setOnClickListener { onLogin() }
        registerText.setOnClickListener { navigateToRegister() }

        return view
    }

    private fun onLogin() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Validate email and password format
        if (validateInput(email, password)) {
            if (credentialsManager.isUserAlreadyRegistered(email)) {
                val storedPassword = credentialsManager.getUsers()[email]
                if (storedPassword == password) {
                    Toast.makeText(requireContext(), "Login successful!", Toast.LENGTH_SHORT).show()
                    // Navigate to ListActivity
                    val intent = Intent(requireContext(), ListActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                } else {
                    passwordInputLayout.error = "Incorrect password"
                }
            } else {
                emailInputLayout.error = "User not registered"
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
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

        return isValid
    }

    // Navigate to RegisterFragment
    private fun navigateToRegister() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, RegisterFragment())
            .addToBackStack(null)  // Allow back navigation to this fragment
            .commit()
    }
}