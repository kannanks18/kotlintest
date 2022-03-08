package com.machine.testkotlin.room.typeconverter

import androidx.room.TypeConverter
import com.machine.testkotlin.room.model.Geo
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class GeoTypeListConverter {

    @TypeConverter
    fun toJson(value: Geo): String {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<Geo> = moshi.adapter(Geo::class.java)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun fromJson(value: String): Geo? {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<Geo> = moshi.adapter(Geo::class.java)
        return adapter.fromJson(value)
    }
}