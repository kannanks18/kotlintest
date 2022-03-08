package com.machine.testkotlin.network
import com.machine.testkotlin.room.model.Employee
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("5d565297300000680030a986")
    suspend fun getEmployeeData(): Response<List<Employee>?>

}