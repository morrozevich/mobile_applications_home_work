package com.example.myapplicationnew

import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals

class CredentialsManagerTest {


    @Test
    fun whenEmailIsEmpty_thenValidationFails() {
        val credentialsManager = CredentialsManager()

        val result = credentialsManager.isValidEmail("")

        assertEquals(false, result)
    }


    @Test
    fun whenEmailFormatIsIncorrect_thenValidationFails() {
        val credentialsManager = CredentialsManager()
        val email = "invalid-email-format.com"
        val result = credentialsManager.isValidEmail(email)

        assertEquals(false, result)
    }


    @Test
    fun whenEmailFormatIsCorrect_thenValidationPasses() {
        val credentialsManager = CredentialsManager()

        val result = credentialsManager.isValidEmail("newuser@example.org")

        assertEquals(true, result)
    }


    @Test
    fun whenPasswordIsEmpty_thenValidationFails() {
        val credentialsManager = CredentialsManager()

        val result = credentialsManager.isValidPassword("")

        assertEquals(false, result)
    }


    @Test
    fun whenPasswordIsValid_thenValidationPasses() {
        val credentialsManager = CredentialsManager()

        val result = credentialsManager.isValidPassword("newPassword456")

        assertEquals(true, result)
    }


    @Test
    fun whenPasswordIsTooShort_thenValidationFails() {
        val credentialsManager = CredentialsManager()

        val result = credentialsManager.isValidPassword("123")

        assertEquals(false, result)
    }

    // Specific test for email: test@te.st
    @Test
    fun whenEmailIsTestDotTeDotSt_thenValidationPasses() {
        val credentialsManager = CredentialsManager()

        val result = credentialsManager.isValidEmail("test@te.st")

        assertEquals(true, result)
    }

    // Specific test for password: 1234
    @Test
    fun whenPasswordIs1234_thenValidationPasses() {
        val credentialsManager = CredentialsManager()

        val result = credentialsManager.isValidPassword("1234")

        assertEquals(true, result)
    }
}
