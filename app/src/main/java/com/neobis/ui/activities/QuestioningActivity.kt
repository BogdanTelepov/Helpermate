package com.neobis.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.neobis.databinding.ActivityQuestioningBinding

class QuestioningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestioningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestioningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textInputLayoutUserBirthday.setEndIconOnClickListener {
            // TODO: 25.06.2021  
        }
    }
}