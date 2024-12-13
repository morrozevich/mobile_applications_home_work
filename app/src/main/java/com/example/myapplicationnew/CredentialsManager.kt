package com.example.myapplicationnew
class CredentialsManager public constructor() {


    fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.isNotEmpty() && Regex(emailRegex).matches(email)
    }

    fun isValidPassword(password: String): Boolean {
        return password.isNotEmpty() && password.length >= 8
    }


}
