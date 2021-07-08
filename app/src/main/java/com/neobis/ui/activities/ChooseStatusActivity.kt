package com.neobis.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neobis.databinding.ActivityChooseStatusBinding
import com.neobis.utils.Constants.TYPE_PEOPLE
import com.neobis.utils.SessionManager

class ChooseStatusActivity : AppCompatActivity() {


    private lateinit var binding: ActivityChooseStatusBinding
    private val sessionManager: SessionManager by lazy { SessionManager(this) }
    private var typeOfPerson: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivNotDiabetic.setOnClickListener {

            val intent = Intent(this, QuestioningActivity::class.java)
            typeOfPerson = 0
            sessionManager.saveTypeOfPerson(typeOfPerson)

            startActivity(intent)
            finish()
        }

        binding.ivDiabetic.setOnClickListener {
            typeOfPerson = 1
            val intent = Intent(this, QuestioningActivity::class.java)
            sessionManager.saveTypeOfPerson(typeOfPerson)

            startActivity(intent)
        }


    }
}