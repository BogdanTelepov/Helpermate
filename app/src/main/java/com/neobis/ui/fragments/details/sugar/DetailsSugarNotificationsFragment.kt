package com.neobis.ui.fragments.details.sugar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neobis.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsSugarNotificationsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_sugar_notifications, container, false)
    }


}