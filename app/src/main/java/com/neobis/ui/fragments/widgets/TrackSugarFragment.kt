package com.neobis.ui.fragments.widgets

import android.app.Dialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.neobis.R
import com.neobis.databinding.FragmentTrackSugarBinding
import com.neobis.models.widgetModels.UserSugar
import com.neobis.utils.NetworkResult
import com.neobis.utils.afterTextChanged
import com.neobis.viewModels.BottomSheetWidgetSugarViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class TrackSugarFragment : BottomSheetDialogFragment() {


    private var _binding: FragmentTrackSugarBinding? = null
    private val binding get() = checkNotNull(_binding)

    private var startTime: String? = ""
    private var selectedHour: Int? = null
    private var selectedMinute: Int? = null

    private val sugarViewModel: BottomSheetWidgetSugarViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTrackSugarBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etTrackTime.afterTextChanged { text ->
            binding.btnAdd.isEnabled = text.isNotEmpty()
        }
        binding.etSugarVal.afterTextChanged { text ->
            if (text.isEmpty()) {
                binding.btnAdd.isEnabled = false
            } else {
                binding.btnAdd.isEnabled = true
                binding.btnAdd.setBackgroundColor(Color.parseColor("#FF8753"))
            }
        }

        binding.textInputLayoutTrackTime.setEndIconOnClickListener {
            openTimePickerStart()
        }

        binding.btnAdd.setOnClickListener {
            val time = binding.etTrackTime.text.toString().trim()
            val value = binding.etSugarVal.text.toString().trim()
            val userSugar: UserSugar by lazy { UserSugar(time, Integer.parseInt(value)) }

            sugarViewModel.trackSugar(userSugar)
            sugarViewModel.trackSugarResponse.observe(viewLifecycleOwner) { respons ->
                when (respons) {
                    is NetworkResult.Success -> {
                        dismiss()
                    }
                    is NetworkResult.Error -> {
                    }
                    is NetworkResult.Loading -> {
                    }
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openTimePickerStart() {
        val hour = selectedHour ?: LocalDateTime.now().hour
        val minute = selectedMinute ?: LocalDateTime.now().minute

        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(hour)
            .setMinute(minute)
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    onTimeSelectedForStartTime(
                        this.hour,
                        this.minute
                    )
                }
            }.show(childFragmentManager, MaterialTimePicker::class.java.canonicalName)

    }

    private fun onTimeSelectedForStartTime(hour: Int, minute: Int) {
        selectedHour = hour
        selectedMinute = minute
        val hourAsText = if (hour < 10) "0$hour" else hour
        val minuteAsText = if (minute < 10) "0$minute" else minute

        "$hourAsText:$minuteAsText".also { binding.etTrackTime.setText(it) }
        startTime = "$hourAsText:$minuteAsText"

    }


    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
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