package com.neobis.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neobis.R

import com.neobis.databinding.ActivityAuthorizationBinding
import com.neobis.models.auth.UserLoginRequest
import com.neobis.ui.activities.forgottenPassword.ForgottenPasswordActivity
import com.neobis.ui.activities.registration.CreateAccountActivity
import com.neobis.utils.*

import com.neobis.viewModels.AuthorizationActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthorizationActivity : AppCompatActivity(), CustomLogger {

    override val TAG: String
        get() = AuthorizationActivity::class.java.simpleName


    private lateinit var binding: ActivityAuthorizationBinding

    private val authorizationActivityViewModel: AuthorizationActivityViewModel by viewModels()

    private val sessionManager: SessionManager by lazy { SessionManager(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (sessionManager.fetchUserToken() != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            finish()
        }

        binding.tvForgetPassword.setOnClickListener {
            val intent = Intent(this, ForgottenPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.tvRegistration.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)

        }



        binding.textInputEditTextEmail.listenChanges { text ->

            val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
            binding.textInputLayoutEmail.isErrorEnabled = !valid
            val error = if (valid) null else getString(R.string.invalid_email_error)
            binding.textInputLayoutEmail.error = error


        }


        binding.btnLogin.setOnClickListener {
            loginUser()


        }
    }


    private fun loginUser() {


        val email = binding.textInputEditTextEmail.text.toString().trim()
        val password = binding.textInputEditTextPassword.text.toString().trim()



        if (email.isEmpty()) {
            binding.textInputLayoutEmail.error = "Поле не должно быть пустым"
            return
        }
        if (password.isEmpty()) {
            binding.textInputLayoutPassword.error = "Поле не должно быть пустым"
            return
        }

        val userLoginRequest: UserLoginRequest by lazy { UserLoginRequest(email, password) }

        authorizationActivityViewModel.login(userLoginRequest)

        authorizationActivityViewModel.loginResponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    sessionManager.saveToken(response.data?.token)
                    showLog(response.data?.token.toString())

                    openActivity(MainActivity::class.java)
                    finish()


                }

                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, response.message.toString(), Toast.LENGTH_SHORT).show()
                }

            }
        }


    }


    override fun showLog(message: String) {
        Log.d(TAG, message)
    }


}