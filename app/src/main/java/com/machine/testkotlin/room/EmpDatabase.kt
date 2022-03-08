package com.machine.testkotlin.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.machine.testkotlin.room.dao.EmployeeListDao
import com.machine.testkotlin.room.model.Employee
import com.machine.testkotlin.room.typeconverter.AddressListTypeConverter
import com.machine.testkotlin.room.typeconverter.CompanyListTypeConverter
import com.machine.testkotlin.room.typeconverter.GeoTypeListConverter

@Database(entities = [Employee::class],version = 1, exportSchema = false)
@TypeConverters(
    AddressListTypeConverter::class, CompanyListTypeConverter::class, GeoTypeListConverter::class
)
abstract class EmpDatabase : RoomDatabase() {
    abstract fun empListDao(): EmployeeListDao
}