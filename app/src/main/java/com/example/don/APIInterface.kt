package com.example.don

import com.example.don.bonus.Bonus
import com.example.don.buy.Buy
import com.example.don.login.Login
import com.example.don.register.Register
import com.example.don.magic.MagicString
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface APIInterface {
    @GET("shop/1")
    fun getMagicList(
        @Query("name")  localname:String,
        @Query("password") localpassword:String
    ): Call<MagicString>

    @POST("register")
    fun register(
        @Query("name") regName:String,
        @Query("password") regPassword:String,
        @Query("role") regRole:Int
    ):Call<Register>

    @POST("login")
    fun login(
        @Query("name") loginName:String,
        @Query("password") loginPassword:String
    ):Call<Login>

    @POST("magic")
    fun buy(
        @Query("name") buyName:String,
        @Query("password") buyPassword:String,
        @Query("shop_id") buysShop_id:Int,
        @Query("magic_id") buyMagic_id:Int
    ):Call<Buy>

    @PUT("bonus/")
    fun bonus(
        @Query("name") bonusName:String,
        @Query("password") bonusPassword:String
    ):Call<Bonus>

    @POST("register")
    fun register2(
        @Query("name") regName:String,
        @Query("password") regPassword:String,
        @Query("role") regRole:Int
    ):Call<Register>

}
