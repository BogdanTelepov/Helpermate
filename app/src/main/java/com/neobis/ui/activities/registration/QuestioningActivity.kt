package com.neobis.ui.activities.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.neobis.R

import com.neobis.databinding.ActivityQuestioningBinding
import com.neobis.models.auth.RegisterStepThreeRequest
import com.neobis.ui.activities.WidgetsActivity

import com.neobis.ui.fragments.DatePickerFragment
import com.neobis.utils.*

import com.neobis.viewModels.CreateAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class QuestioningActivity : AppCompatActivity(), CustomLogger {

    override val TAG: String
        get() = QuestioningActivity::class.java.simpleName


    private lateinit var binding: ActivityQuestioningBinding

    private val sessionManager: SessionManager by lazy { SessionManager(this) }

    private val createAccountViewModel: CreateAccountViewModel by viewModels()

    private var typePeople: Int? = null
    private lateinit var name: String
    private lateinit var birthDay: String
    private var genderId: Int? = null
    private lateinit var weight: String
    private lateinit var height: String
    private var typeOfDiabeticId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestioningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolbar()



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
            showDatePickerDialog()
        }



        binding.btnNextScreen.setOnClickListener {

            endRegister()

        }


    }


    private fun endRegister() {
        name = binding.etUserName.text.toString().trim()
        birthDay = binding.etUserBirthday.text.toString().trim()

        weight = binding.etUserWeight.text.toString()
        height = binding.etUserHeight.text.toString()



        if (binding.rbMale.isChecked) {
            genderId = 0
        }

        if (binding.rbFemale.isChecked) {
            genderId = 1
        }
        if (binding.rbFirstType.isChecked) {
            typeOfDiabeticId = 0
        }
        if (binding.rbSecondType.isChecked) {
            typeOfDiabeticId = 1
        }

        val registerStepThreeRequest: RegisterStepThreeRequest by lazy {
            RegisterStepThreeRequest(
                birthDay,
                typeOfDiabeticId!!,
                genderId!!,
                Integer.parseInt(height),
                name,
                Integer.parseInt(weight)
            )
        }

        createAccountViewModel.registerStepThree(registerStepThreeRequest)
        createAccountViewModel.registerStepThreeResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    openActivity(WidgetsActivity::class.java)
                    finish()
                }

                is NetworkResult.Error -> {
                    toast(response.message.toString())
                }
                is NetworkResult.Loading -> {

                }
            }
        }


    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        supportActionBar?.setTitle(R.string.questioning_activity)
        binding.toolbar.setTitleTextAppearance(this, R.style.body_text)
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year ->
            dateSelected(day, month, year)

        }
        datePicker.show(supportFragmentManager, "datePicker")
    }

    private fun dateSelected(day: Int, month: Int, year: Int) {
        val calendar: Calendar by lazy { Calendar.getInstance() }
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.MONTH, month)
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        binding.etUserBirthday.setText(format.format(calendar.time))
    }

    override fun showLog(message: String) {
        Log.d(TAG, message)
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