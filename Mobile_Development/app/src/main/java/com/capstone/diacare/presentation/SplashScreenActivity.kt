package com.capstone.diacare.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.capstone.diacare.presentation.home.MainActivity
import com.capstone.diacare.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            // Intent untuk berpindah ke Activity berikutnya
            val intent = Intent(this, MainActivity::class.java) // Ganti dengan activity utama Anda
            startActivity(intent)
            finish() // Menutup SplashScreenActivity agar tidak kembali saat tombol back ditekan
        }, 3000) // Delay 3000ms = 3 detik
    }
}