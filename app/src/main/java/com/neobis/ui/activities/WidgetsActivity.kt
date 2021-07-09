package com.neobis.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neobis.databinding.ActivityWidgetsBinding

class WidgetsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWidgetsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWidgetsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}