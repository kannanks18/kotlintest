package com.machine.testkotlin.internet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.machine.testkotlin.R
import com.machine.testkotlin.databinding.FragmentInternetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InternetFragment : Fragment(R.layout.fragment_internet) {
    lateinit var binding: FragmentInternetBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInternetBinding.bind(view)
    }


}