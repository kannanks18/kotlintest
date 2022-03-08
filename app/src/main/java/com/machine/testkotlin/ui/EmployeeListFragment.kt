package com.machine.testkotlin.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.machine.testkotlin.R
import com.machine.testkotlin.databinding.FragmentEmployeeListBinding
import com.machine.testkotlin.room.model.Employee
import com.machine.testkotlin.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeeListFragment : Fragment(R.layout.fragment_employee_list),
    EmployeeListAdapter.OnItemClickListener {
    private lateinit var empAdapter: EmployeeListAdapter
    lateinit var binding: FragmentEmployeeListBinding
    private val viewModel: EmployeeListViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEmployeeListBinding.bind(view)
        setUpRecyclerView()
        observeData()
        observeSearch()
        viewModel.getEmployeeDetailsList()
    }

    private fun observeSearch() {
//        binding.searchText.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                binding.lytPaymode.visibility=View.GONE
//                viewModel.paymodeDetails()
//            }
//        })

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager?
        binding.searchText.setSearchableInfo(searchManager?.getSearchableInfo(requireActivity().componentName))
        binding.searchText.maxWidth = Int.MAX_VALUE
        binding.searchText.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                empAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                empAdapter.filter.filter(query)
                return false
            }
        })
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.employeeList.asLiveData().observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Loading -> showProgress(true)

                        is Resource.Success -> {
                            showProgress(false)
                            if (it.value.isNotEmpty()) {
                                empAdapter.setAdapter(it.value)
                                empAdapter.notifyDataSetChanged()

                            } else {
                                viewModel.getEmployeeList()
                            }
                        }
                        is Resource.Error -> {
                            viewModel.getEmployeeList()
                            showProgress(false)
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.employeedetails.asLiveData().observe(viewLifecycleOwner) {
                    when (it) {
                        is Resource.Loading -> showProgress(true)

                        is Resource.Success -> {
                            showProgress(false)
                            viewModel.insertEmployeeData(it.value)
                        }
                        is Resource.Error -> {
                            showProgress(false)
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }


    private fun showProgress(b: Boolean) {
        binding.apply {
            progressBar.visibility = if (b) {
                View.VISIBLE
            } else {
                View.GONE
            }

        }

    }

    private fun setUpRecyclerView() {
        empAdapter = EmployeeListAdapter(this)
        binding.listEmployee.apply {
            setHasFixedSize(true)
            adapter = empAdapter
            layoutManager = LinearLayoutManager(requireContext())
//            empAdapter.filter.filter("")
        }
    }

    override fun onItemClick(employee: Employee?) {
        findNavController().navigate(
            EmployeeListFragmentDirections.actionEmployeeListFragmentToEmployeeDetailsFragment(
                employee!!
            )
        )
    }

}