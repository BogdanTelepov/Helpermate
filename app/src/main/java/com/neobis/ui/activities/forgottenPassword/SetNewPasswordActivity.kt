package com.neobis.ui.activities.forgottenPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neobis.databinding.ActivitySetNewPasswordBinding
import com.neobis.ui.fragments.DialogChangePasswordFragment

class SetNewPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySetNewPasswordBinding
    private val dialogChangePasswordFragment by lazy { DialogChangePasswordFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetNewPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSavePassword.setOnClickListener {
            dialogChangePasswordFragment.show(
                supportFragmentManager,
                "dialogChangePasswordFragment"
            )
        }

    }
}