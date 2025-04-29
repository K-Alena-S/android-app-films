package com.example.android_app_films.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android_app_films.R
import com.example.android_app_films.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(appModule)
        }

        setContentView(R.layout.activity_main)
    }
}
