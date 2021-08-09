package com.neobis.ui.fragments.details.sugar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.neobis.R
import com.neobis.adapters.HistorySugarListAdapter
import com.neobis.databinding.FragmentDetailsSugarHistoryBinding
import com.neobis.utils.NetworkResult
import com.neobis.viewModels.HistoryFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsSugarHistoryFragment : Fragment() {

    private var _binding: FragmentDetailsSugarHistoryBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val detailViewModel: HistoryFragmentViewModel by viewModels()
    private val sugarAdapter:HistorySugarListAdapter by lazy { HistorySugarListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsSugarHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvListItem.adapter= sugarAdapter
        detailViewModel.getSugarHistory()
        detailViewModel.historySugarResponse.observe(viewLifecycleOwner){response->
            when(response){
                is NetworkResult.Success->{
                    response.data?.let { sugarAdapter.setData(it) }
                }
                is NetworkResult.Error->{}
                is NetworkResult.Loading->{}
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}