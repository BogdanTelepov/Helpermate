package com.neobis.ui.activities.forgottenPassword


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.neobis.R
import com.neobis.databinding.ActivityForgottenPasswordBinding
import com.neobis.models.auth.RestorePasswordStepOneRequest
import com.neobis.utils.Constants.USER_EMAIL
import com.neobis.utils.CustomLogger

import com.neobis.utils.NetworkResult
import com.neobis.utils.listenChanges
import com.neobis.viewModels.ForgottenPasswordActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgottenPasswordActivity : AppCompatActivity(), CustomLogger {

    override val TAG: String
        get() = ForgottenPasswordActivity::class.java.simpleName


    private lateinit var binding: ActivityForgottenPasswordBinding

    private val forgottenPasswordActivityViewModel: ForgottenPasswordActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgottenPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        supportActionBar?.setTitle(R.string.forgotten_password)
        binding.toolbar.setTitleTextAppearance(this, R.style.body_text)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.textInputEditTextEmail.listenChanges { text ->
            Log.d(TAG, text)

            val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
            binding.textInputLayoutEmail.isErrorEnabled = !valid
            val error = if (valid) "" else getString(R.string.invalid_email_error)
            binding.textInputLayoutEmail.error = error


        }

        binding.btnSendCode.setOnClickListener {
            sendCode()

        }
    }

    private fun sendCode() {
        val email = binding.textInputEditTextEmail.text.toString().trim()

        if (email.isEmpty()) {
            binding.textInputLayoutEmail.error = "Поле не должно быть пустым"
            return
        }

        val restorePasswordStepOneRequest: RestorePasswordStepOneRequest by lazy {
            RestorePasswordStepOneRequest(
                email
            )
        }

        forgottenPasswordActivityViewModel.restorePasswordStepOne(restorePasswordStepOneRequest)
        forgottenPasswordActivityViewModel.restorePasswordStepOneResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE


                }
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_SHORT).show()


                }

                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

        val intent = Intent(this, ConfirmResetCodeActivity::class.java)
        intent.putExtras(Bundle().apply {
            putString(USER_EMAIL, email)
        })
        startActivity(intent)
        finish()


    }


    override fun showLog(message: String) {
        Log.d(TAG, message)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}