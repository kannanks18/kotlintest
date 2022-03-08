package com.machine.testkotlin.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.machine.testkotlin.R
import com.machine.testkotlin.databinding.FragmentEmployeeDetailsBinding
import com.machine.testkotlin.databinding.FragmentEmployeeListBinding
import com.machine.testkotlin.utils.loadProfile
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmployeeDetailsFragment : Fragment(R.layout.fragment_employee_details) {
    private val args: EmployeeDetailsFragmentArgs by navArgs()

    lateinit var binding: FragmentEmployeeDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEmployeeDetailsBinding.bind(view)
        binding.employeeName.text = "${args.employee.name}"
        binding.companyName.text ="${args.employee.company?.name}, ${args.employee?.company?.catchPhrase} \n ${args.employee?.company?.bs}"
        binding.userName.text = "${args.employee.username}"
        binding.email.text = "${args.employee.email}"
        binding.mobileNumber.text = "${args.employee.phone}"
        binding.website.text = "${args.employee.website}"
        binding.address.text ="${args.employee.address.suite}, ${args.employee.address.street} \n ${args.employee.address.city}, ${args.employee.address.zipcode}"
        binding.profilePic.loadProfile(args.employee?.profile_image)

    }
}