package com.neobis.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.neobis.databinding.ActivityAuthorizationBinding
import com.neobis.ui.activities.forgottenPassword.ForgottenPasswordActivity
import com.neobis.ui.activities.registration.CreateAccountActivity

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
            val intent = Intent(this,CreateAccountActivity::class.java)
            startActivity(intent)

        }
    }
}