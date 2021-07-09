package com.neobis.ui.activities.registration

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned

import android.text.method.LinkMovementMethod
import android.util.Log

import com.neobis.R
import com.neobis.databinding.ActivityCreateAccountBinding
import com.neobis.ui.activities.AuthorizationActivity
import com.neobis.ui.activities.PrivatePolicyActivity
import com.neobis.ui.activities.forgottenPassword.ForgottenPasswordActivity
import com.neobis.utils.MyClickableSpan
import com.neobis.utils.listenChanges
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCreateNewAccount.isEnabled = false
        binding.checkboxAgreeRules.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {

                binding.btnCreateNewAccount.setBackgroundColor(resources.getColor(R.color.dark_orange))
            } else {
                binding.btnCreateNewAccount.setBackgroundColor(resources.getColor(R.color.base_orange))
            }
            binding.btnCreateNewAccount.isEnabled = isChecked
        }


        binding.textInputEditTextEmail.listenChanges { text ->

            val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
            binding.textInputLayoutEmail.isErrorEnabled = !valid
            val error = if (valid) "" else getString(R.string.invalid_email_error)
            binding.textInputLayoutEmail.error = error


        }

        binding.btnCreateNewAccount.setOnClickListener {

            val email = binding.textInputEditTextEmail.text.toString().trim()
            val password = binding.textInputEditTextPassword.text.toString().trim()
            val confirmPassword = binding.textInputEditTextNewPasswordConfirm.text.toString().trim()

            if (email.isNullOrEmpty()) {
                binding.textInputLayoutEmail.error = "Поле не должно быть пустым"
                return@setOnClickListener
            }

            if (password.isNullOrEmpty()) {
                binding.textInputLayoutPassword.error = "Поле не должно быть пустым"
                return@setOnClickListener
            }

            if (confirmPassword.isNullOrEmpty()) {
                binding.textInputLayoutNewPasswordConfirm.error = "Поле не должно быть пустым"
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                binding.textInputLayoutNewPasswordConfirm.error = "Пароли не совпадают"
                return@setOnClickListener
            }


            val intent = Intent(this, ConfirmNewAccountActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, AuthorizationActivity::class.java)
            startActivity(intent)
            finish()
        }

        val fullText = getString(R.string.tv_agree_private_policy_full)
        val policy = getString(R.string.tv_agree_private_policy)
        val spannableString = SpannableString(fullText)
        val policyClickable = MyClickableSpan {
            val intent = Intent(this, PrivatePolicyActivity::class.java)
            startActivity(intent)
            finish()
        }
        spannableString.setSpan(
            policyClickable,
            fullText.indexOf(policy),
            fullText.indexOf(policy) + policy.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )


        binding.tvView3.run {
            text = spannableString
            movementMethod = LinkMovementMethod.getInstance()
            highlightColor = Color.TRANSPARENT
        }
    }
}