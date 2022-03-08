package com.machine.testkotlin.ui
import com.machine.testkotlin.network.ApiService
import com.machine.testkotlin.room.dao.EmployeeListDao
import com.machine.testkotlin.room.model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class BaseRepository @Inject constructor(
    private val apiService: ApiService,private val employeeListDao: EmployeeListDao
) {

    fun empDetails(): Flow<Response<List<Employee>?>> {
        return flow {
            val response = apiService.getEmployeeData()
            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    fun getEmployeeList() = employeeListDao.getEmployees()


}