package com.neobis.ui.activities.forgottenPassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.neobis.R
import com.neobis.databinding.ActivityForgottenPasswordBinding
import com.neobis.utils.listenChanges


class ForgottenPasswordActivity : AppCompatActivity() {

    companion object {
        val TAG = ForgottenPasswordActivity::class.simpleName.toString()
    }

    private lateinit var binding: ActivityForgottenPasswordBinding

//    private val textWatcher: TextWatcher = object : SimpleTextWatcher() {
//        override fun afterTextChanged(s: Editable?) {
//            val input = s.toString().trim()
//            if (input.endsWith("@g")) {
//                val fullMail = "${input}mail.com"
//                setText(fullMail)
//            }
//        }
//    }


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
            if (binding.textInputEditTextEmail.text.isNullOrEmpty()) {
                binding.textInputLayoutEmail.error = "Поле не должно быть пустым"
                return@setOnClickListener
            } else {
                val intent = Intent(this, ConfirmResetCodeActivity::class.java)
                startActivity(intent)
                finish()
            }


        }
    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}