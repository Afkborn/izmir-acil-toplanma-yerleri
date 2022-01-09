package com.bilgehankalay.izmirapi.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bilgehankalay.izmirapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding // viewbinding tanımlandı
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}