package com.example.calculateaverage_kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_splash.*

class ActivitySplash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        buttonMain.setOnClickListener{
            var intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }
}