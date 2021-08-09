package com.neobis.ui.activities.forgottenPassword

import android.annotation.SuppressLint
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
import com.neobis.databinding.ActivityConfirmResetCodeBinding
import com.neobis.models.auth.RestorePasswordStepTwoRequest
import com.neobis.utils.Constants.USER_EMAIL
import com.neobis.utils.CustomLogger

import com.neobis.utils.NetworkResult
import com.neobis.utils.SessionManager
import com.neobis.utils.openActivity
import com.neobis.viewModels.ForgottenPasswordActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmResetCodeActivity : AppCompatActivity(), CustomLogger {

    override val TAG: String
        get() = ConfirmResetCodeActivity::class.java.simpleName


    private lateinit var userEmail: String

    private lateinit var binding: ActivityConfirmResetCodeBinding

    private val forgottenPasswordActivityViewModel: ForgottenPasswordActivityViewModel by viewModels()

    private val sessionManager: SessionManager by lazy { SessionManager(this) }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmResetCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userEmail = intent.getStringExtra(USER_EMAIL).toString().trim()
        binding.tvView2.text = "Введите код подтвержения отправленный на $userEmail"

        showLog(userEmail)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        supportActionBar?.setTitle(R.string.forgotten_password)
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

        val request: RestorePasswordStepTwoRequest by lazy {
            RestorePasswordStepTwoRequest(
                confirmCode,
                userEmail
            )
        }

        forgottenPasswordActivityViewModel.restorePasswordStepTwo(request)
        forgottenPasswordActivityViewModel.restorePasswordStepTwoResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    sessionManager.saveToken(response.data?.token)
                    openActivity(SetNewPasswordActivity::class.java)
                    finish()
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


    }

    override fun showLog(message: String) {
        Log.d(TAG, message)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, ForgottenPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}