package com.example.myapplicationnew

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CredentialsManager public constructor() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun login() {
        _isLoggedIn.value = true
        Log.d("CredentialsManager", "User logged in: ${_isLoggedIn.value}")

    }

    fun logout() {
        _isLoggedIn.value = false
    }
    private val existingUsers = mutableMapOf<String, String>()

    // Singleton instance
    companion object {
        val instance: CredentialsManager by lazy { CredentialsManager() }
    }

    init {
        existingUsers["user111@gmail.com"] = "password123"
        existingUsers["userNew@tt.com"] = "password456"
        existingUsers["test@test.com"] = "password789"
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.isNotEmpty() && Regex(emailRegex).matches(email)
    }

    fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 8
    }

    fun isUserAlreadyRegistered(email: String): Boolean {
        return existingUsers.containsKey(email)
    }

    fun register(email: String, password: String): String {
        if (isUserAlreadyRegistered(email)) {
            return "Error: Email is already registered."
        }
        existingUsers[email] = password
        return "Registration successful."
    }

    fun getUsers(): Map<String, String> = existingUsers
}