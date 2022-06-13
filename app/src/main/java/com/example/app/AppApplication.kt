package com.example.app

import android.app.Application
import com.example.app.data.AppDatabase

class AppApplication: Application() {

    val appDatabase: AppDatabase by lazy{
        AppDatabase.getInstance(this)
    }

}