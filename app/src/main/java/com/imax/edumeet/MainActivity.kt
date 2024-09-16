package com.imax.edumeet

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.imax.edumeet.presentation.screens.SplashScreen
import com.imax.navigation.NavigationExtensions.addFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.addFragment(R.id.activity_main_container, SplashScreen())

    }
}
