package com.neobis.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.neobis.R
import com.neobis.databinding.FragmentPersonalInformationBinding
import com.neobis.utils.NetworkResult
import com.neobis.viewModels.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalInformationFragment : Fragment() {

    private var _binding: FragmentPersonalInformationBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val profileViewModel: ProfileViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPersonalInformationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.getCurrentUser()
        profileViewModel.userResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    binding.etUserName.setText(response.data?.name)
                    binding.etUserBirthday.setText(response.data?.birthDate)
                    binding.etUserHeight.setText(response.data?.height.toString())
                    binding.etUserWeight.setText(response.data?.weight.toString())
                    val gender = response.data?.gender
                    if (gender.equals("MALE")) {
                        binding.rbMale.isChecked = true
                    } else {
                        binding.rbFemale.isChecked = true
                    }
                }
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}