package com.softxpert.moviesdb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softxpert.moviesdb.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_MoviesDB) // revert back to main theme
        setContentView(R.layout.activity_main)
    }
}
