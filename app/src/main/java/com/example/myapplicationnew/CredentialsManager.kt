package com.example.myapplicationnew
import android.util.Patterns

class CredentialsManager {

    fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && email.contains("@") && email.contains(".")
    }

    fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 4
    }

}