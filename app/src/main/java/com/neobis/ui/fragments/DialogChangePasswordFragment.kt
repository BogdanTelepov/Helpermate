package com.neobis.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.neobis.R
import com.neobis.databinding.FragmentDialogChangePasswordBinding
import com.neobis.ui.activities.AuthorizationActivity


class DialogChangePasswordFragment : DialogFragment() {

    private var _binding: FragmentDialogChangePasswordBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogChangePasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            val intent = Intent(
                requireContext(),
                AuthorizationActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}