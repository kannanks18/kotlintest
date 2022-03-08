package com.machine.testkotlin.ui
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.machine.testkotlin.databinding.EmployeelistRowBinding
import com.machine.testkotlin.room.model.Employee
import com.machine.testkotlin.utils.loadProfile
import java.util.*
import kotlin.collections.ArrayList

class EmployeeListAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<EmployeeListAdapter.EmployeeAdapter>(),
    Filterable {
    var dataList: ArrayList<Employee> = ArrayList()
    var dataListFiltered: ArrayList<Employee> = ArrayList()
    var row_index = -1
    fun setAdapter( list: List<Employee> ) {
        dataList = list as ArrayList<Employee>
        dataListFiltered = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeAdapter {
        val binding = EmployeelistRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeAdapter(binding)

    }

    override fun onBindViewHolder(holder: EmployeeAdapter, position: Int) {
        holder.bindList(dataListFiltered[position],position)
    }

    override fun getItemCount(): Int {
        return dataListFiltered.size
    }


    inner class EmployeeAdapter(private val binding: EmployeelistRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindList(employee: Employee?, position: Int) {
            binding.employeeName.text = "${employee?.name}"
            binding.companyName.text = "${employee?.company?.name}"
            binding.profilePic.loadProfile(employee?.profile_image)

            binding.employeeListlayout.setOnClickListener(View.OnClickListener {
                listener.onItemClick(employee)
                row_index=position
            })
        }

    }

    interface OnItemClickListener {
        public fun onItemClick(employee: Employee?)
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) dataListFiltered = dataList else {
                    val filteredList = ArrayList<Employee>()
                    dataList
                        .filter {
                            (it.name!!.contains(constraint!!)) or
                                    (it.email!!.contains(constraint))

                        }
                        .forEach { filteredList.add(it) }
                    dataListFiltered = filteredList

                }
                return FilterResults().apply { values = dataListFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                dataListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Employee>
                notifyDataSetChanged()
            }
        }
    }
//     Search Filter
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(charSequence: CharSequence): FilterResults? {
//                val charString = charSequence.toString()
//                if (charString.isEmpty()) {
//                    datumList = datumList as ArrayList<Employee>
//                } else {
//                    val filteredList: MutableList<Employee> = ArrayList()
//                    for (employee in datumList) {
//
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or email match
//                        val name = employee.name ?: ""
//                        val email = employee.email ?: ""
//                        if (name.toLowerCase(Locale.getDefault()).contains(charString.toLowerCase(
//                                Locale.getDefault()))
//                            || email.toLowerCase(Locale.getDefault()).contains(charSequence)) {
//                            filteredList.add(employee)
//                        }
//                    }
//                    datumList = filteredList as ArrayList<Employee>
//                }
//                val filterResults = FilterResults()
//                filterResults.values = datumList
//                return filterResults
//            }
//
//            override fun publishResults(charSequence: CharSequence?, results: FilterResults) {
//                datumList = if (results?.values == null)
//                    ArrayList()
//                else
//                    results.values as ArrayList<Employee>
//                notifyDataSetChanged()
////                if ((filterResults.values as ArrayList<Employee>).size!=null || (filterResults.values as ArrayList<Employee>).size>0) {
////                    datumList = (filterResults.values as? ArrayList<Employee>)!!
////                    notifyDataSetChanged()
////                }
//
//            }
//        }
//    }
}
