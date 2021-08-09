package com.neobis.ui.fragments.widgets

import android.app.Dialog
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
import com.neobis.databinding.FragmentTrackSleepBinding
import com.neobis.models.widgetModels.UserSleep
import com.neobis.ui.activities.WidgetsActivity
import com.neobis.utils.NetworkResult
import com.neobis.utils.toast
import com.neobis.viewModels.BottomSheetWidgetSleepViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime

@AndroidEntryPoint
class TrackSleepFragment : BottomSheetDialogFragment() {


    private var _binding: FragmentTrackSleepBinding? = null
    private val binding get() = checkNotNull(_binding)

    private var selectedHour: Int? = null
    private var selectedMinute: Int? = null

    private var endTime: String? = ""
    private var startTime: String? = ""

    private val bottomSheetWidgetSleepViewModel: BottomSheetWidgetSleepViewModel by viewModels()


    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTrackSleepBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.tvSave.setOnClickListener {
            val endTime = binding.etGoToBedTime.text.toString().trim()
            val startTime = binding.etWakeUpTime.text.toString().trim()

            if (endTime.isEmpty()) {
                return@setOnClickListener
            }

            if (startTime.isEmpty()) {
                return@setOnClickListener
            }

            val userSleep: UserSleep by lazy { UserSleep(endTime, startTime) }
            bottomSheetWidgetSleepViewModel.trackSleep(userSleep)
            bottomSheetWidgetSleepViewModel.userSleepResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        dismiss()
                    }
                    is NetworkResult.Error -> {
                        requireContext().toast(response.message.toString())
                    }
                    is NetworkResult.Loading -> {
                    }
                }
            }


        }

        binding.textInputLayoutGoToBedTime.setEndIconOnClickListener {
            openTimePickerForEnd()
        }
        binding.textInputLayoutWakeUpTime.setEndIconOnClickListener {
            openTimePickerStart()
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun openTimePickerForEnd() {
        val hour = selectedHour ?: LocalDateTime.now().hour
        val minute = selectedMinute ?: LocalDateTime.now().minute

        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(hour)
            .setMinute(minute)
            .build()
            .apply {
                addOnPositiveButtonClickListener {
                    onTimeSelectedForEndTime(
                        this.hour,
                        this.minute
                    )
                }
            }.show(childFragmentManager, MaterialTimePicker::class.java.canonicalName)


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

        "$hourAsText:$minuteAsText".also { binding.etWakeUpTime.setText(it) }
        startTime = "$hourAsText:$minuteAsText"
        Log.d("SleepBottom", startTime.toString())
    }

    private fun onTimeSelectedForEndTime(hour: Int, minute: Int) {
        selectedHour = hour
        selectedMinute = minute
        val hourAsText = if (hour < 10) "0$hour" else hour
        val minuteAsText = if (minute < 10) "0$minute" else minute

        "$hourAsText:$minuteAsText".also { binding.etGoToBedTime.setText(it) }
        endTime = "$hourAsText:$minuteAsText"
        Log.d("SleepBottom", endTime.toString())
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