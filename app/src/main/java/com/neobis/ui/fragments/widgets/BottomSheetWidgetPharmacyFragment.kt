package com.neobis.ui.fragments.widgets

import android.app.Dialog
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neobis.R
import com.neobis.adapters.PharmacyListAdapter
import com.neobis.databinding.FragmentBottomSheetWidgetPharmacyBinding
import com.neobis.models.PharmacyModel
import com.neobis.utils.afterTextChanged


class BottomSheetWidgetPharmacyFragment : BottomSheetDialogFragment(),
    PharmacyListAdapter.OnItemClickListener {

    private var _binding: FragmentBottomSheetWidgetPharmacyBinding? = null
    private val binding get() = _binding!!

    private val pharmacyListAdapter: PharmacyListAdapter by lazy { PharmacyListAdapter(this) }

    private val listOfPharmacy: ArrayList<PharmacyModel> by lazy { ArrayList() }


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



        binding.etInputNamePharmacy.afterTextChanged { text ->
            if (text.isEmpty()) {
                binding.ivSearch.setImageResource(R.drawable.ic_search)
                binding.ivSearch.isEnabled = false
            } else {
                binding.ivSearch.setImageResource(R.drawable.ic_search_orange)
                binding.ivSearch.isEnabled = true

            }
        }

        binding.tvSave.setOnClickListener {
            dismiss()
        }

        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.ivSearch.setOnClickListener {
            listOfPharmacy.add(PharmacyModel(binding.etInputNamePharmacy.text.toString().trim()))
            pharmacyListAdapter.differ.submitList(listOfPharmacy.toList())

        }



        binding.rvPharmacyList.adapter = pharmacyListAdapter

        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_line)!!)
        binding.rvPharmacyList.addItemDecoration(divider)


        pharmacyListAdapter.differ.submitList(listOfPharmacy.toList())

        Log.d("Pharm", listOfPharmacy.size.toString())

    }


    override fun deleteItem(pharmacyModel: PharmacyModel) {
        listOfPharmacy.remove(pharmacyModel)
        pharmacyListAdapter.differ.submitList(listOfPharmacy.toList())
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