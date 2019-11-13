package com.example.don

import android.content.Context

class Shared(context: Context) {
    private val preference = context.getSharedPreferences("Don", Context.MODE_PRIVATE)

    fun set(key:String, value:String){
        preference.edit().putString(key, value).apply()
    }

    fun setName(name:String){
        preference.edit().putString("name", name).apply()
    }
}