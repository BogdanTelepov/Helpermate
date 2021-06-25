package com.neobis.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.neobis.R
import com.neobis.databinding.ActivityPrivatePolicyBinding
import com.neobis.ui.activities.registration.CreateAccountActivity

class PrivatePolicyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPrivatePolicyBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrivatePolicyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        supportActionBar?.title = "Назад"
        binding.toolbar.setTitleTextAppearance(this, R.style.body_text)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnAgreePrivatePolicy.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}