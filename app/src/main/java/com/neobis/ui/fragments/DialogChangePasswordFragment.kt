package com.neobis.ui.fragments

import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.neobis.databinding.FragmentDialogChangePasswordBinding
import com.neobis.ui.activities.AuthorizationActivity
import com.neobis.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogChangePasswordFragment : DialogFragment() {

    private var _binding: FragmentDialogChangePasswordBinding? = null
    private val binding get() = _binding!!

    private val sessionManager: SessionManager by lazy { SessionManager(requireContext()) }


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
            sessionManager.clearTokens()

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