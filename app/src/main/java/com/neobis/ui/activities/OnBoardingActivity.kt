package com.neobis.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.neobis.adapters.ViewPagerAdapter
import com.neobis.databinding.ActivityOnBoardingBinding
import com.neobis.ui.fragments.on_boarding_fragments.screens.FirstOnBoardingFragment
import com.neobis.ui.fragments.on_boarding_fragments.screens.SecondOnBoardingFragment
import com.neobis.ui.fragments.on_boarding_fragments.screens.ThirdOnBoardingFragment

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentList = arrayListOf(
            FirstOnBoardingFragment(),
            SecondOnBoardingFragment(),
            ThirdOnBoardingFragment()
        )

        val adapter =
            ViewPagerAdapter(fragmentList, supportFragmentManager, lifecycle)
        binding.viewPager2.adapter = adapter

    }

    fun authActivityTransition() {
        val intent = Intent(this, AuthorizationActivity::class.java)
        startActivity(intent)
        finish()
    }


}