package com.neobis.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neobis.R
import com.neobis.adapters.WidgetListAdapter
import com.neobis.databinding.FragmentListWidgetsBinding
import com.neobis.models.WidgetModel
import com.neobis.ui.activities.WidgetsActivity
import com.neobis.ui.fragments.widgets.BottomSheetWidgetPharmacyFragment
import com.neobis.ui.fragments.widgets.BottomSheetWidgetSleepFragment


class ListWidgetsFragment : Fragment() {
    private var _binding: FragmentListWidgetsBinding? = null
    private val binding get() = _binding!!

    private val widgetListAdapter: WidgetListAdapter by lazy { WidgetListAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListWidgetsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnConfirmChoose.setOnClickListener {
            (activity as WidgetsActivity).mainActivityTransition()
        }


        val listOfWidgets: List<WidgetModel> = arrayListOf(
            WidgetModel(
                1,
                "Сон",
                R.drawable.ic_widget_sleep,
                resources.getString(R.color.widget_sleep_color),
                null
            ),
            WidgetModel(
                2,
                "Еда",
                R.drawable.ic_widget_foods,
                resources.getString(R.color.widget_food_color),
                null
            ),
            WidgetModel(
                3,
                "Давление",
                R.drawable.ic_widget_pressure,
                resources.getString(R.color.widget_pressure_color),
                null
            ),
            WidgetModel(
                4,
                "Инсулин",
                R.drawable.ic_widget_insulin,
                resources.getString(R.color.widget_insulin_color),
                null
            ),
            WidgetModel(
                5,
                "Активность",
                R.drawable.ic_widget_activities,
                resources.getString(R.color.widget_activities_color),
                null
            ),
            WidgetModel(
                6,
                "Вода",
                R.drawable.ic_widget_water,
                resources.getString(R.color.widget_water_color),
                null
            ),
            WidgetModel(
                7,
                "Лекарства",
                R.drawable.ic_widget_pharmacy,
                resources.getString(R.color.widget_pharmacy_color),
                null
            ),
            WidgetModel(
                8,
                "Сахар",
                R.drawable.ic_widget_sugar,
                resources.getString(R.color.widget_sugar_color),
                null
            )
        )

        binding.rvWidgetList.adapter = widgetListAdapter
        widgetListAdapter.differ.submitList(listOfWidgets.toList())
        widgetListAdapter.setOnClick {

            when (it.id) {
                1 -> {
                    val bottomSheet: BottomSheetWidgetSleepFragment by lazy { BottomSheetWidgetSleepFragment() }
                    bottomSheet.show(childFragmentManager, "bottomSheet")
                }

                7 -> {
                    val bottomSheetWidgetPharmacyFragment: BottomSheetWidgetPharmacyFragment by lazy { BottomSheetWidgetPharmacyFragment() }
                    bottomSheetWidgetPharmacyFragment.show(childFragmentManager, "bottomSheetPharm")
                }
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}