package com.example.don

import android.content.Context

class SharedPreferences(context: Context) {
    val preference = context.getSharedPreferences("challenge", Context.MODE_PRIVATE)

    fun setName(name:String){
        preference.edit().putString("name", name).apply()
    }

    fun setName2(name2:String){
        preference.edit().putString("name2", name2).apply()
    }

    fun setPassword(password:String){
        preference.edit().putString("password", password).apply()
    }

    fun setPassword2(password2:String){
        preference.edit().putString("password2", password2).apply()
    }

    fun setCheck(check:String){
        preference.edit().putString("check", check).apply()
    }

    fun setCash(cash:String){
        preference.edit().putString("cash", cash).apply()
    }
}