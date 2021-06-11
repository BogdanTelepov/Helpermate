package com.neobis.ui.activities.forgottenPassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.neobis.R
import com.neobis.databinding.ActivityConfirmResetCodeBinding

class ConfirmResetCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfirmResetCodeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmResetCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        supportActionBar?.setTitle(R.string.forgotten_password)
        binding.toolbar.setTitleTextAppearance(this, R.style.body_text)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.btnConfirmCode.setOnClickListener {
            val intent = Intent(this, SetNewPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

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