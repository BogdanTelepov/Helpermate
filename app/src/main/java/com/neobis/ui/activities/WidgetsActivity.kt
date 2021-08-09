package com.neobis.ui.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData

import com.neobis.R
import com.neobis.databinding.ActivityWidgetsBinding
import com.neobis.ui.activities.registration.QuestioningActivity
import com.neobis.ui.fragments.widgets.BottomSheetWidgetPharmacyFragment
import com.neobis.ui.fragments.widgets.BottomSheetWidgetSleepFragment
import com.neobis.ui.fragments.widgets.BottomSheetWidgetSugarFragment
import com.neobis.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WidgetsActivity : AppCompatActivity(), BottomSheetActivityContractSleepWidget,
    BottomSheetActivityContractPharmacyWidget, BottomSheetActivityContractSugarWidget {


    private lateinit var binding: ActivityWidgetsBinding

    private val listIds: ArrayList<Int> = ArrayList()


    private val bottomSheetWidgetSleepFragment: BottomSheetWidgetSleepFragment by lazy { BottomSheetWidgetSleepFragment() }
    private val bottomSheetWidgetPharmacyFragment: BottomSheetWidgetPharmacyFragment by lazy { BottomSheetWidgetPharmacyFragment() }
    private val bottomSheetWidgetSugarFragment: BottomSheetWidgetSugarFragment by lazy { BottomSheetWidgetSugarFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWidgetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        supportActionBar?.title = "Показатели"
        binding.toolbar.setTitleTextAppearance(this, R.style.body_text)

        binding.btnConfirmChoose.setOnClickListener {
            Log.d("WidgetActivity", "${listIds.size}")
            openActivity(MainActivity::class.java)
            finish()
        }

        binding.rootLayoutSleepWidget.setOnClickListener {
            binding.rootLayoutSleepWidget.isChecked = !binding.rootLayoutSleepWidget.isChecked


            if (binding.rootLayoutSleepWidget.isChecked) {
                listIds.add(EnumWidgetId.SLEEP.id)
                Log.d("WidgetActivity", "add to List")
                bottomSheetWidgetSleepFragment.show(supportFragmentManager, "SleepBottom")
                binding.rootLayoutSleepWidget.strokeWidth = 3
                binding.rootLayoutSleepWidget.strokeColor =
                    Color.parseColor("#AF52DE")


            } else {
                binding.rootLayoutSleepWidget.strokeWidth = 0
                listIds.remove(EnumWidgetId.SLEEP.id)
                binding.itemDescriptionSleep.text = ""
                Log.d("WidgetActivity", "remove from List")

            }
        }

        binding.rootLayoutPharmacyWidget.setOnClickListener {
            binding.rootLayoutPharmacyWidget.isChecked = !binding.rootLayoutPharmacyWidget.isChecked

            if (binding.rootLayoutPharmacyWidget.isChecked) {
                Log.d("WidgetActivity", "add to List")
                listIds.add(EnumWidgetId.PHARMACY.id)
                bottomSheetWidgetPharmacyFragment.show(supportFragmentManager, "PharmacyBottom")
                binding.rootLayoutPharmacyWidget.strokeWidth = 3
                binding.rootLayoutPharmacyWidget.strokeColor =
                    Color.parseColor("#AF52DE")
            } else {

                binding.rootLayoutPharmacyWidget.strokeWidth = 0
                listIds.remove(EnumWidgetId.PHARMACY.id)
                binding.itemDescriptionPharmacy.text = ""
                Log.d("WidgetActivity", "remove from List")

            }
        }

        binding.rootLayoutSugarWidget.setOnClickListener {
            binding.rootLayoutSugarWidget.isChecked = !binding.rootLayoutSugarWidget.isChecked
            if (binding.rootLayoutSugarWidget.isChecked) {
                listIds.add(EnumWidgetId.SUGAR.id)
                bottomSheetWidgetSugarFragment.show(supportFragmentManager, "sugarBottom")
                binding.rootLayoutSugarWidget.strokeWidth = 3
                binding.rootLayoutSugarWidget.strokeColor =
                    Color.parseColor("#AF52DE")
            } else {
                binding.rootLayoutSugarWidget.strokeWidth = 0
                listIds.remove(EnumWidgetId.SUGAR.id)
                binding.itemDescriptionSugar.text = ""
            }
        }


    }

    // https://stackoverflow.com/questions/56390372/how-to-pass-data-from-bottomsheetfragmentdialog

    override fun onPassDataRequested(data: LiveData<String>?) {
        data?.observe(this) {
            binding.itemDescriptionSleep.text = it
        }

    }

    override fun onPassDataFromPharmacyWidget(data: LiveData<String>?) {
        data?.observe(this) {
            binding.itemDescriptionPharmacy.text = "$it шт."
        }
    }


    override fun onPassDataFromSugarWidget(data: LiveData<String>?) {
        data?.observe(this) {
            binding.itemDescriptionSugar.text = "$it ммоль/л"
        }
    }

    fun mainActivityTransition() {

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this, QuestioningActivity::class.java)

            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }





}