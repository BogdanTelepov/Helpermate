package com.neobis.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neobis.R

import com.neobis.databinding.ActivityAuthorizationBinding
import com.neobis.ui.activities.forgottenPassword.ForgottenPasswordActivity
import com.neobis.ui.activities.registration.CreateAccountActivity
import com.neobis.utils.listenChanges

class AuthorizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthorizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthorizationBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
            val error = if (valid) "" else getString(R.string.invalid_email_error)
            binding.textInputLayoutEmail.error = error


        }


        binding.btnLogin.setOnClickListener {

            val email = binding.textInputEditTextEmail.text.toString().trim()
            val password = binding.textInputEditTextPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.textInputLayoutEmail.error = "Поле не должно быть пустым"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.textInputLayoutPassword.error = "Поле не должно быть пустым"
                return@setOnClickListener
            }
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}