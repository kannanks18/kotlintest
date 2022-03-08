package com.machine.testkotlin.ui
import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machine.testkotlin.room.dao.EmployeeListDao
import com.machine.testkotlin.room.model.Employee
import com.machine.testkotlin.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeListViewModel
@Inject constructor(private val baseRepository: BaseRepository,application: Application,private val employeeListDao: EmployeeListDao) : ViewModel() {
    private val _employeeListdetails : MutableStateFlow<Resource<List<Employee>>> =MutableStateFlow(Resource.Empty)
    val employeeList: StateFlow<Resource<List<Employee>>> = _employeeListdetails
    private val _employeedetails : MutableStateFlow<Resource<List<Employee>>> =MutableStateFlow(Resource.Empty)
    val employeedetails: StateFlow<Resource<List<Employee>>> = _employeedetails

    fun getEmployeeList() {
        viewModelScope.launch(Dispatchers.IO) {
            _employeeListdetails.emit(Resource.Loading)
            baseRepository.empDetails().catch { exception ->
                _employeeListdetails.emit(Resource.Error(error = exception.message!!))
            }.collect { response ->
                val body = response.body()
                if (response.isSuccessful && body != null) {
                        try {
                            _employeeListdetails.emit(Resource.Success(body))
                        } catch (e: Exception) {
                            _employeeListdetails.emit(Resource.Error(error = e.message!!))
                        }

                } else {
                    _employeeListdetails.emit(Resource.Error(error = response.message()))
                }

            }
        }
    }

    fun insertEmployeeData(listOfEmployees: List<Employee>) {
        viewModelScope.launch {
            employeeListDao.insert(listOfEmployees)
        }.invokeOnCompletion {
            getEmployeeDetailsList()        }
    }
     fun getEmployeeDetailsList(){
        viewModelScope.launch {
            viewModelScope.launch {
                baseRepository.getEmployeeList().catch { exception ->
                    _employeeListdetails.emit(Resource.Error(error = exception.message!!))
                }.collect {
                    _employeeListdetails.emit(Resource.Success(it))
                }
            }

        }
    }


}

