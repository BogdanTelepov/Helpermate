package com.neobis.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.neobis.adapters.ActiveWidgetsAdapter

import com.neobis.databinding.FragmentHomeBinding
import com.neobis.ui.activities.MainActivity
import com.neobis.ui.activities.WidgetsActivity
import com.neobis.utils.NetworkResult
import com.neobis.utils.SessionManager
import com.neobis.utils.toast
import com.neobis.viewModels.HomeViewModel
import com.neobis.viewModels.ListWidgetsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = checkNotNull(_binding)


    private val homeViewModel: HomeViewModel by viewModels()
    private val listWidgetsFragmentViewModel: ListWidgetsFragmentViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getSleepValues()
        homeViewModel.getSugarValues()
        homeViewModel.getSleepValuesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val duration = response.data?.sleep?.duration
                    val startTime = response.data?.sleep?.startTime
                    val endTime = response.data?.sleep?.endTime
                    if (duration != null) {
                        if (duration.isNotEmpty())
                            binding.tvLastActivitySleep.text = response.data.sleep.duration
                        binding.rootLayoutSleepWidget.strokeWidth = 3
                        binding.rootLayoutSleepWidget.strokeColor = Color.parseColor("#AF52DE")
                    } else {
                        binding.tvLastActivitySleep.text = ""
                        binding.rootLayoutSleepWidget.strokeWidth = 0
                    }
                    if (endTime != null && startTime != null) {
                        binding.itemDescriptionSleep.text =
                            "$endTime - $startTime"
                    } else {
                        binding.itemDescriptionSleep.text = ""
                    }


                }

                is NetworkResult.Error -> {
                }
                is NetworkResult.Loading -> {
                }
            }
        }

        homeViewModel.getSugarValuesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    val value = response.data?.sugar?.value

                    if (value != null) {
                        binding.tvLastActivitySugar.text = "$value ммоль/л"
                        binding.rootLayoutSugarWidget.strokeWidth = 3
                        binding.rootLayoutSugarWidget.strokeColor = Color.parseColor("#AF52DE")
                    } else{
                        binding.rootLayoutSugarWidget.strokeWidth = 0
                        binding.tvLastActivitySugar.text = ""
                    }


                }
                is NetworkResult.Error -> {
                }
                is NetworkResult.Loading -> {
                }
            }
        }



        homeViewModel.getCurrentUser()
        homeViewModel.userResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.tvUserName.text = response.data?.name
                }
                is NetworkResult.Error -> {
                    requireContext().toast(response.message.toString())
                }
            }
        }

        binding.rootLayoutSleepWidget.setOnClickListener {
            (activity as MainActivity).openDetailsSleepActivity()
        }
        binding.rootLayoutSugarWidget.setOnClickListener {
            (activity as MainActivity).openDetailsSugarActivity()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}