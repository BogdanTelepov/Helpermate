package com.neobis.ui.activities.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neobis.databinding.ActivityCreateAccountBinding
import com.neobis.ui.activities.AuthorizationActivity

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCreateNewAccount.setOnClickListener {
            val intent = Intent(this, ConfirmNewAccountActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.tvLogin.setOnClickListener {
            val intent = Intent(this,AuthorizationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}