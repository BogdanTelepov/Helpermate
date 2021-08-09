package com.neobis.ui.fragments.details

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.neobis.R
import com.neobis.adapters.DetailsPagerAdapter
import com.neobis.databinding.ActivityDetailsSleepBinding
import com.neobis.ui.activities.MainActivity
import com.neobis.ui.fragments.details.viewPagerFragments.DetailsHistoryFragment
import com.neobis.ui.fragments.details.viewPagerFragments.DetailsNotificationsFragment
import com.neobis.ui.fragments.details.viewPagerFragments.DetailsStatisticsFragment
import com.neobis.utils.openActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsSleepActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsSleepBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsSleepBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.setBackgroundColor(Color.parseColor("#FFFFFFFF"))

        supportActionBar?.setTitle(R.string.back)
        binding.toolbar.setTitleTextAppearance(this, R.style.body_text)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val titles = ArrayList<String>()
        titles.add("История")
        titles.add("Напоминания")
        titles.add("Статистика")

        val fragments = ArrayList<Fragment>()
        fragments.add(DetailsHistoryFragment())
        fragments.add(DetailsNotificationsFragment())
        fragments.add(DetailsStatisticsFragment())

        val pagerAdapter = DetailsPagerAdapter(fragments, this)
        binding.viewPager2.apply {
            adapter = pagerAdapter
        }

        TabLayoutMediator(binding.tabsLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            openActivity(MainActivity::class.java)
            finish()
        }
        return true
    }
}