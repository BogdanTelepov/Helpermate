package com.neobis.ui.activities.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.neobis.R
import com.neobis.databinding.ActivityConfirmNewAccountBinding
import com.neobis.models.auth.RegisterStepTwoRequest
import com.neobis.utils.*
import com.neobis.viewModels.CreateAccountViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmNewAccountActivity : AppCompatActivity(), CustomLogger {

    override val TAG: String
        get() = ConfirmNewAccountActivity::class.java.simpleName


    private lateinit var binding: ActivityConfirmNewAccountBinding

    private lateinit var userEmail: String

    private val createAccountViewModel: CreateAccountViewModel by viewModels()

    private val sessionManager: SessionManager by lazy { SessionManager(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmNewAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)


        userEmail = intent.getStringExtra(Constants.USER_EMAIL).toString().trim()
        showLog(userEmail)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        supportActionBar?.setTitle(R.string.back)
        binding.toolbar.setTitleTextAppearance(this, R.style.body_text)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnConfirmCode.setOnClickListener {
            confirmCode()
        }
    }


    private fun confirmCode() {
        val confirmCode = binding.editTextCodeInput.text.toString().trim()
        if (confirmCode.isEmpty()) {
            return
        }

        val registerStepTwoRequest: RegisterStepTwoRequest by lazy {
            RegisterStepTwoRequest(
                userEmail,
                confirmCode
            )
        }

        createAccountViewModel.registerStepTwo(registerStepTwoRequest)
        createAccountViewModel.registerStepTwoResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.hideProgress()
                    sessionManager.saveToken(response.data?.jwtToken)
                    openActivity(ChooseStatusActivity::class.java)

                }

                is NetworkResult.Error -> {
                    binding.progressBar.hideProgress()
                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.showProgress()
                }
            }
        }


    }


    override fun showLog(message: String) {
        Log.d(TAG, message)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}