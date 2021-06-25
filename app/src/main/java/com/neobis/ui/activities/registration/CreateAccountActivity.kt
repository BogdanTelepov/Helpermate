package com.neobis.ui.activities.registration

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned

import android.text.method.LinkMovementMethod

import com.neobis.R
import com.neobis.databinding.ActivityCreateAccountBinding
import com.neobis.ui.activities.AuthorizationActivity
import com.neobis.ui.activities.PrivatePolicyActivity
import com.neobis.utils.MyClickableSpan

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