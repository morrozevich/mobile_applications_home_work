package com.example.myapplicationnew

import android.app.Application

class CustomApplication : Application() {
    val credentialsManager: CredentialsManager by lazy { CredentialsManager.instance }
}
