package com.neobis.ui.fragments.on_boarding_fragments.screens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neobis.R
import com.neobis.databinding.FragmentThirdOnBoardingBinding
import com.neobis.ui.activities.AuthorizationActivity
import com.neobis.ui.activities.OnBoardingActivity
import com.neobis.utils.SessionManager


class ThirdOnBoardingFragment : Fragment() {

    private var _binding: FragmentThirdOnBoardingBinding? = null
    private val binding get() = _binding!!

    private val sessionManager: SessionManager by lazy { SessionManager(requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentThirdOnBoardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNextFragment.setOnClickListener {
            (activity as OnBoardingActivity).authActivityTransition()
            sessionManager.onBoardingFinished()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}