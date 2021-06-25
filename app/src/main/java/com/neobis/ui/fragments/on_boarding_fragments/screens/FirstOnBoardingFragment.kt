package com.neobis.ui.fragments.on_boarding_fragments.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.neobis.R
import com.neobis.databinding.FragmentFirstOnBoardingBinding
import com.neobis.ui.activities.OnBoardingActivity


class FirstOnBoardingFragment : Fragment() {

    private var _binding: FragmentFirstOnBoardingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFirstOnBoardingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager2)
        binding.btnNextFragment.setOnClickListener {
            viewPager?.currentItem = 1
        }

        binding.tvSkipFragments.setOnClickListener {
            (activity as OnBoardingActivity).authActivityTransition()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}