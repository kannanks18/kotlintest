package com.machine.testkotlin.room.typeconverter
import androidx.room.TypeConverter
import com.machine.testkotlin.room.model.Address
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class AddressListTypeConverter {

    @TypeConverter
    fun toJson(value: Address): String {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<Address> = moshi.adapter(Address::class.java)
        return adapter.toJson(value)
    }

    @TypeConverter
    fun fromJson(value: String): Address? {
        val moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<Address> = moshi.adapter(Address::class.java)
        return adapter.fromJson(value)
    }
}