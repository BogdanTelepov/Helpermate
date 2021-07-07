package com.neobis.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.neobis.R

import com.neobis.databinding.ActivityQuestioningBinding
import com.neobis.ui.activities.registration.CreateAccountActivity
import com.neobis.utils.Constants.TYPE_PEOPLE
import com.neobis.utils.SessionManager

class QuestioningActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestioningBinding
    private val sessionManager: SessionManager by lazy { SessionManager(this) }

    private var typePeople: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestioningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        supportActionBar?.setTitle(R.string.questioning_activity)
        binding.toolbar.setTitleTextAppearance(this, R.style.body_text)


        typePeople = sessionManager.getTypeOfPerson()

        when (typePeople) {
            0 -> {
                binding.radioButtonGroupTypeDiabet.visibility = View.GONE
                binding.tvView7.visibility = View.GONE

            }
            1 -> {
                binding.radioButtonGroupTypeDiabet.visibility = View.VISIBLE
                binding.tvView7.visibility = View.VISIBLE
            }
        }

        binding.textInputLayoutUserBirthday.setEndIconOnClickListener {
            // TODO: 25.06.2021  
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, ChooseStatusActivity::class.java)
            sessionManager.deleteTypeOfPerson()
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}