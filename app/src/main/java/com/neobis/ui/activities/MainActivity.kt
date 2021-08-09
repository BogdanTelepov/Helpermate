package com.neobis.ui.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController

import com.neobis.R
import com.neobis.databinding.ActivityMainBinding
import com.neobis.ui.fragments.details.DetailsSleepActivity
import com.neobis.ui.fragments.details.DetailsSugarActivity
import com.neobis.ui.fragments.widgets.BottomSheetWidgetSleepFragment
import com.neobis.ui.fragments.widgets.TrackSleepFragment
import com.neobis.ui.fragments.widgets.TrackSugarFragment

import com.neobis.utils.openActivity
import com.neobis.utils.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val trackSleepFragment: TrackSleepFragment by lazy { TrackSleepFragment() }
    private val trackSugarFragment: TrackSugarFragment by lazy { TrackSugarFragment() }

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.menu.getItem(3).isEnabled = false

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
        binding.fabSleep.setOnClickListener {
            trackSleepFragment.show(supportFragmentManager, "TrackSleep")
        }

        binding.fabSugar.setOnClickListener {
            trackSugarFragment.show(supportFragmentManager, "TrackSugar")
        }


    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }


    fun loginActivityTransition() {
        openActivity(AuthorizationActivity::class.java)
        finish()
    }

    fun openDetailsSleepActivity() {
        openActivity(DetailsSleepActivity::class.java)
        finish()
    }

    fun openDetailsSugarActivity() {
        openActivity(DetailsSugarActivity::class.java)
        finish()
    }


    private fun setupBottomNavigationBar() {
        val bottomNavView = binding.bottomNavigationView
        val navGraphs = listOf(
            R.navigation.graph_home,
            R.navigation.graph_dairy,
            R.navigation.graph_profile
        )

        val controller = bottomNavView.setupWithNavController(
            navGraphs,
            supportFragmentManager,
            R.id.fragmentContainerViewMain,
            intent
        )
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean = currentNavController?.value?.navigateUp() ?: false

    override fun onBackPressed() {
        if (currentNavController?.value?.popBackStack() != true)
            super.onBackPressed()
    }
}