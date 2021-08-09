package com.neobis.ui.fragments.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.neobis.R
import com.neobis.adapters.DetailsPagerAdapter
import com.neobis.databinding.FragmentDetailsWidgetFoodBinding
import com.neobis.ui.fragments.details.viewPagerFragments.DetailsHistoryFragment
import com.neobis.ui.fragments.details.viewPagerFragments.DetailsNotificationsFragment
import com.neobis.ui.fragments.details.viewPagerFragments.DetailsStatisticsFragment
import kotlinx.coroutines.channels.ticker


class DetailsWidgetFoodFragment : Fragment() {

    private var _binding: FragmentDetailsWidgetFoodBinding? = null
    private val binding get() = checkNotNull(_binding)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsWidgetFoodBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragments = ArrayList<Fragment>()
        fragments.add(DetailsHistoryFragment())
        fragments.add(DetailsNotificationsFragment())
        fragments.add(DetailsStatisticsFragment())

        val titles = ArrayList<String>()
        titles.add("История")
        titles.add("Напоминания")
        titles.add("Статистика")

        val detailsPagerAdapter = DetailsPagerAdapter(fragments, requireActivity())
        binding.viewPager2.apply {
            adapter = detailsPagerAdapter
        }

        TabLayoutMediator(binding.tabsLayout, binding.viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}