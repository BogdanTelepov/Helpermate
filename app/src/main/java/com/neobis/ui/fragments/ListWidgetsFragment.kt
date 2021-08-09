package com.neobis.ui.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels


import com.neobis.adapters.WidgetListAdapter
import com.neobis.databinding.FragmentListWidgetsBinding

import com.neobis.ui.activities.WidgetsActivity
import com.neobis.utils.NetworkResult
import com.neobis.utils.hideProgress
import com.neobis.utils.showProgress
import com.neobis.viewModels.ListWidgetsFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListWidgetsFragment : Fragment() {
    private var _binding: FragmentListWidgetsBinding? = null
    private val binding get() = _binding!!

    private val widgetListAdapter: WidgetListAdapter by lazy { WidgetListAdapter() }

    private val listWidgetsFragmentViewModel: ListWidgetsFragmentViewModel by viewModels()






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




        binding.rvWidgetList.adapter = widgetListAdapter





    }


    private fun fetchWidgetList() {
        listWidgetsFragmentViewModel.getListWidgets()
        listWidgetsFragmentViewModel.responseWidgetListItem.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.progressBar.hideProgress()
                    widgetListAdapter.differ.submitList(response.data?.toMutableList())
                }
                is NetworkResult.Error -> {
                    binding.progressBar.hideProgress()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.showProgress()
                }
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}