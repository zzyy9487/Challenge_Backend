package com.example.don

import com.example.don.Bonus.Bonus
import com.example.don.Bonus.Result
import com.example.don.Buy.Buy
import com.example.don.Login.Login
import com.example.don.Register.Register
import com.example.don.Shop.MagicString
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface APIInterface {
    @GET("shop/1")
    fun getMagicList(
    @Query("name")  localname:String
    ,@Query("password") localpassword:String
    ): Call<MutableList<MagicString?>>

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

}
