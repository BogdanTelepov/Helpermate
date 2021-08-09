package com.neobis.ui.fragments.widgets

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neobis.R
import com.neobis.databinding.FragmentBottomSheetWidgetPressureBinding
import com.neobis.models.widgetModels.UserPressure
import com.neobis.utils.ConfigManager
import com.neobis.utils.Constants.USER_PRESSURE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetWidgetPressureFragment : BottomSheetDialogFragment() {


    private var _binding: FragmentBottomSheetWidgetPressureBinding? = null
    private val binding get() = checkNotNull(_binding)

    private val configManager: ConfigManager by lazy { ConfigManager(requireContext()) }


    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =
            FragmentBottomSheetWidgetPressureBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSave.setOnClickListener {
            val systolicVal = binding.etTopPressure.text.toString().trim()
            val dystolicVal = binding.etBottomPressure.text.toString().trim()


            if (systolicVal.isEmpty()) {
                binding.textInputLayoutTopPressure.error = "Поле должно быть заполнено"
                return@setOnClickListener
            }

            if (dystolicVal.isEmpty()) {
                binding.textInputLayoutBottomPressure.error = "Поле должно быть заполнено"
                return@setOnClickListener
            }

            val userPressure: UserPressure by lazy {
                UserPressure(
                    Integer.parseInt(dystolicVal),
                    Integer.parseInt(systolicVal)
                )
            }

            val bundle = Bundle()
            bundle.putSerializable(USER_PRESSURE, userPressure)
            arguments?.putBundle(USER_PRESSURE, bundle)


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