package com.machine.testkotlin.room.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.machine.testkotlin.room.model.Employee
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

@Dao
interface EmployeeListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(employee: List<Employee>)

    @Query("SELECT * FROM employees")
    fun getEmployees(): Flow<List<Employee>>

//

}