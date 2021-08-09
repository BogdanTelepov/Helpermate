package com.neobis.ui.fragments.details.viewPagerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.neobis.R
import com.neobis.adapters.HistoryListAdapter
import com.neobis.databinding.FragmentDetailsHistoryBinding
import com.neobis.utils.NetworkResult
import com.neobis.viewModels.HistoryFragmentViewModel
import com.neobis.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsHistoryFragment : Fragment() {

    private var _binding: FragmentDetailsHistoryBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val detailViewModel: HistoryFragmentViewModel by viewModels()
    private val historyListAdapter: HistoryListAdapter by lazy { HistoryListAdapter() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvListItem.adapter = historyListAdapter
        detailViewModel.getSleepHistory()
        detailViewModel.historySleepResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.toList()?.let { historyListAdapter.setData(it) }
                }
                is NetworkResult.Error->{

                }
                is NetworkResult.Loading->{}
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}