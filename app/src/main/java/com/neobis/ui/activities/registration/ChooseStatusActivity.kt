package com.neobis.ui.activities.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neobis.databinding.ActivityChooseStatusBinding

import com.neobis.utils.SessionManager
import com.neobis.utils.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseStatusActivity : AppCompatActivity() {


    private lateinit var binding: ActivityChooseStatusBinding
    private val sessionManager: SessionManager by lazy { SessionManager(this) }
    private var typeOfPerson: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivNotDiabetic.setOnClickListener {
            typeOfPerson = 0
            sessionManager.saveTypeOfPerson(typeOfPerson)
            openActivity(QuestioningActivity::class.java)

            finish()
        }

        binding.ivDiabetic.setOnClickListener {
            typeOfPerson = 1
            sessionManager.saveTypeOfPerson(typeOfPerson)
            openActivity(QuestioningActivity::class.java)


            finish()
        }


    }
}