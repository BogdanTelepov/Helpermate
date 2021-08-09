package com.neobis.ui.fragments.widgets

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neobis.R
import com.neobis.databinding.FragmentBottomSheetWidgetSugarBinding
import com.neobis.models.widgetModels.SugarRequest
import com.neobis.ui.activities.MainActivity
import com.neobis.ui.activities.WidgetsActivity
import com.neobis.utils.BottomSheetActivityContractSugarWidget
import com.neobis.utils.NetworkResult
import com.neobis.utils.toast
import com.neobis.viewModels.BottomSheetWidgetSugarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetWidgetSugarFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetWidgetSugarBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val sugarViewModel: BottomSheetWidgetSugarViewModel by viewModels()

    private lateinit var bottomSheetActivityContractSugarWidget: BottomSheetActivityContractSugarWidget

    private var _mutableLiveData: MutableLiveData<String> = MutableLiveData()
    private val liveData: LiveData<String> get() = _mutableLiveData


    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBottomSheetWidgetSugarBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetActivityContractSugarWidget = (activity as WidgetsActivity)


        binding.tvSave.setOnClickListener {

            val sugarVal = binding.etSugarVal.text.toString().trim()
            if (sugarVal.isEmpty()) {
                binding.textInputLayoutSugarValue.error = "Заполните поле"
                return@setOnClickListener
            }
            _mutableLiveData.value = sugarVal
            bottomSheetActivityContractSugarWidget.onPassDataFromSugarWidget(liveData)
            val sugarRequest: SugarRequest by lazy { SugarRequest(Integer.parseInt(sugarVal)) }
            sugarViewModel.setSugarValue(sugarRequest)
            sugarViewModel.sugarResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        dismiss()

                    }

                    is NetworkResult.Error -> {
                        requireActivity().toast(response.message.toString())
                    }
                    is NetworkResult.Loading -> {

                    }
                }
            }

        }
        binding.tvCancel.setOnClickListener {
            dismiss()
        }


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behavior = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}