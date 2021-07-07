package com.neobis.ui.activities.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.neobis.R
import com.neobis.databinding.ActivityConfirmNewAccountBinding
import com.neobis.ui.activities.ChooseStatusActivity
import com.neobis.ui.activities.forgottenPassword.ForgottenPasswordActivity

class ConfirmNewAccountActivity : AppCompatActivity() {


    private lateinit var binding: ActivityConfirmNewAccountBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmNewAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        supportActionBar?.setTitle(R.string.back)
        binding.toolbar.setTitleTextAppearance(this, R.style.body_text)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnConfirmCode.setOnClickListener {
            val intent = Intent(this, ChooseStatusActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}