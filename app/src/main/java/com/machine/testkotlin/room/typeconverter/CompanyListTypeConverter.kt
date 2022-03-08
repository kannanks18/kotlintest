package com.machine.testkotlin.room.typeconverter
import androidx.room.TypeConverter
import com.machine.testkotlin.room.model.Company
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class CompanyListTypeConverter {

    @TypeConverter
    fun toJson(value: Company?): String {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<Company> = moshi.adapter(Company::class.java)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun fromJson(value: String): Company? {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<Company> = moshi.adapter(Company::class.java)
        return adapter.fromJson(value)
    }
}