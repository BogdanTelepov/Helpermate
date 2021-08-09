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
import com.neobis.databinding.ActivitySetNewPasswordBinding
import com.neobis.models.auth.RestorePasswordStepThreeRequest
import com.neobis.ui.fragments.DialogChangePasswordFragment
import com.neobis.utils.CustomLogger
import com.neobis.utils.NetworkResult
import com.neobis.utils.SessionManager
import com.neobis.viewModels.ForgottenPasswordActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetNewPasswordActivity : AppCompatActivity(), CustomLogger {

    override val TAG: String
        get() = SetNewPasswordActivity::class.java.simpleName


    private lateinit var binding: ActivitySetNewPasswordBinding
    private val dialogChangePasswordFragment by lazy { DialogChangePasswordFragment() }


    private val forgottenPasswordActivityViewModel: ForgottenPasswordActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        supportActionBar?.setTitle(R.string.forgotten_password)
        binding.toolbar.setTitleTextAppearance(this, R.style.body_text)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSavePassword.setOnClickListener {
            setNewPassword()
        }

    }

    private fun setNewPassword() {
        val newPassword = binding.textInputEditTextNewPassword.text.toString().trim()
        val confirmPassword = binding.textInputEditTextNewPasswordConfirm.text.toString().trim()

        if (newPassword != confirmPassword) {
            binding.textInputLayoutNewPasswordConfirm.error = "Пароли не совпадают"
            return
        }

        if (newPassword.isEmpty()) {
            binding.textInputLayoutNewPassword.error = "Поле не должно быть пустым"
            return
        }

        if (confirmPassword.isEmpty()) {
            binding.textInputLayoutNewPasswordConfirm.error = "Поле не должно быть пустым"
            return
        }

        val request: RestorePasswordStepThreeRequest by lazy {
            RestorePasswordStepThreeRequest(
                confirmPassword,
                newPassword
            )
        }

        forgottenPasswordActivityViewModel.restorePasswordStepThree(request)
        forgottenPasswordActivityViewModel.restorePasswordStepThreeResponse.observe(this) { response ->
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

        showDialog()


    }

    override fun showLog(message: String) {
        Log.d(TAG, message)
    }


    private fun showDialog() {
        dialogChangePasswordFragment.show(
            supportFragmentManager,
            "dialogChangePasswordFragment"
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, ConfirmResetCodeActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}