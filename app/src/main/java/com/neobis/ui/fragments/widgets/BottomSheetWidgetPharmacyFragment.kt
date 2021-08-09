package com.neobis.ui.fragments.widgets

import android.app.Dialog
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neobis.R
import com.neobis.adapters.PharmacyListAdapter
import com.neobis.databinding.FragmentBottomSheetWidgetPharmacyBinding
import com.neobis.models.widgetModels.MedicationsRequest
import com.neobis.models.widgetModels.PharmacyModel
import com.neobis.ui.activities.WidgetsActivity
import com.neobis.utils.*
import com.neobis.viewModels.BottomSheetWidgetPharmacyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetWidgetPharmacyFragment : BottomSheetDialogFragment(),
    PharmacyListAdapter.OnItemClickListener, CustomLogger {


    override val TAG: String
        get() = BottomSheetWidgetPharmacyFragment::class.java.simpleName


    private var _binding: FragmentBottomSheetWidgetPharmacyBinding? = null
    private val binding get() = _binding!!

    private val pharmacyListAdapter: PharmacyListAdapter by lazy { PharmacyListAdapter(this) }

    private val bottomSheetWidgetPharmacyViewModel: BottomSheetWidgetPharmacyViewModel by viewModels()

    private lateinit var listOfNames: ArrayList<String>

    private lateinit var bottomSheetActivityContractPharmacyWidget: BottomSheetActivityContractPharmacyWidget

    private var _mutableLiveData: MutableLiveData<String> = MutableLiveData()
    private val liveData: LiveData<String> get() = _mutableLiveData

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =
            FragmentBottomSheetWidgetPharmacyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listOfNames = ArrayList()

        bottomSheetActivityContractPharmacyWidget = (activity as WidgetsActivity)


        binding.etInputNamePharmacy.afterTextChanged { text ->
            if (text.isEmpty()) {
                binding.ivAddPharmacy.setImageResource(R.drawable.ic_search)
                binding.ivAddPharmacy.isEnabled = false
            } else {
                binding.ivAddPharmacy.setImageResource(R.drawable.ic_search_orange)
                binding.ivAddPharmacy.isEnabled = true

            }
        }

        binding.tvSave.setOnClickListener {
            if (listOfNames.isNotEmpty()) {
                val medicationsRequest: MedicationsRequest by lazy { MedicationsRequest(listOfNames) }
                bottomSheetWidgetPharmacyViewModel.addMedications(medicationsRequest)
                _mutableLiveData.value = "${listOfNames.size}"
                (bottomSheetActivityContractPharmacyWidget as WidgetsActivity).onPassDataFromPharmacyWidget(
                    liveData
                )
                bottomSheetWidgetPharmacyViewModel.medicationsResponse.observe(viewLifecycleOwner) { response ->
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
            } else {
                dismiss()
            }


        }

        binding.tvCancel.setOnClickListener {
            dismiss()
        }



        setupRv()

        binding.ivAddPharmacy.setOnClickListener {
            val pharmacyModel: PharmacyModel by lazy {
                PharmacyModel(
                    binding.etInputNamePharmacy.text.toString().trim()
                )
            }

            bottomSheetWidgetPharmacyViewModel.createListPharmacy(
                pharmacyModel
            )

            listOfNames.add(pharmacyModel.name.toString().trim())
            showLog(listOfNames.size.toString())

        }






        bottomSheetWidgetPharmacyViewModel.listOfPharmacy.observe(viewLifecycleOwner) {
            pharmacyListAdapter.differ.submitList(it.toList())


        }


    }


    private fun setupRv() {

        binding.rvPharmacyList.adapter = pharmacyListAdapter

        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_line)!!)
        binding.rvPharmacyList.addItemDecoration(divider)
    }


    override fun deleteItem(pharmacyModel: PharmacyModel) {
        bottomSheetWidgetPharmacyViewModel.deleteItemFromList(pharmacyModel)
        listOfNames.remove(pharmacyModel.name)
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


    override fun showLog(message: String) {
        Log.d(TAG, message)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}