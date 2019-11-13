package com.example.don

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.don.Magic.MagicL1Adapter
import com.example.don.Magic.MagicL2Adapter
import com.example.don.Magic.MagicL3Adapter
import com.example.don.Shop.Magic
import kotlinx.android.synthetic.main.activity_magic.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.don.Shop.MagicString
import kotlinx.android.synthetic.main.activity_welcome.*


class MagicActivity : AppCompatActivity() {

    var magicList = mutableListOf<Magic>()
    var inputL1List = mutableListOf<Magic>()
    var inputL2List = mutableListOf<Magic>()
    var inputL3List = mutableListOf<Magic>()

    var photoArray = arrayOf(
        R.drawable.m0,
        R.drawable.m1,
        R.drawable.m2,
        R.drawable.m3,
        R.drawable.m4,
        R.drawable.m5,
        R.drawable.m6,
        R.drawable.m7,
        R.drawable.m8,
        R.drawable.m9,
        R.drawable.m10,
        R.drawable.m11,
        R.drawable.m12,
        R.drawable.m0,
        R.drawable.m1,
        R.drawable.m2,
        R.drawable.m3,
        R.drawable.m4,
        R.drawable.m5,
        R.drawable.m6,
        R.drawable.m7,
        R.drawable.m8,
        R.drawable.m9,
        R.drawable.m10,
        R.drawable.m11,
        R.drawable.m12,
        R.drawable.m0,
        R.drawable.m1,
        R.drawable.m2,
        R.drawable.m3,
        R.drawable.m4,
        R.drawable.m5,
        R.drawable.m6,
        R.drawable.m7,
        R.drawable.m8,
        R.drawable.m9,
        R.drawable.m10,
        R.drawable.m11,
        R.drawable.m12
    )

    lateinit var check:String
    lateinit var name :String
    lateinit var password:String
    fun record(){
        val preferenceCheck = getSharedPreferences("check", Context.MODE_PRIVATE)
        check = preferenceCheck.getString("check","")?:0.toString()
        if (check == 1.toString()){
            val preferenceName = getSharedPreferences("name", Context.MODE_PRIVATE)
            name = preferenceName.getString("name", "")?:""
            val preferencePassword = getSharedPreferences("password", Context.MODE_PRIVATE)
            password = preferencePassword.getString("password", "")?:""
        }
        else{
            val preferenceName = getSharedPreferences("name2", Context.MODE_PRIVATE)
            name = preferenceName.getString("name2", "")?:""
            val preferencePassword = getSharedPreferences("password2", Context.MODE_PRIVATE)
            password = preferencePassword.getString("password2", "")?:""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magic)


       record()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://vegelephant.club/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface = retrofit.create(APIInterface::class.java)
        val call = apiInterface.getMagicList("$name", "$password")

        call.enqueue(object :retrofit2.Callback<MutableList<MagicString?>>{
            override fun onFailure(call: Call<MutableList<MagicString?>>, t: Throwable) {
            }

            override fun onResponse(call: Call<MutableList<MagicString?>>, response: Response<MutableList<MagicString?>>) {

                val list = response.body()
                if (list != null) {
                    for (i in 0 until list.size){
                        magicList.add(i, Magic(list[i]!!.id, photoArray[i], list[i]!!.name, list[i]!!.price.toInt(), list[i]!!.level, list[i]!!.magic_id ?:0))
                    }

                    inputL1List.addAll(magicList.filter { it.level == 1 })
                    inputL2List.addAll(magicList.filter { it.level == 2 })
                    inputL3List.addAll(magicList.filter { it.level == 3 })

                    recyclerL1.layoutManager = GridLayoutManager(this@MagicActivity, 4)
                    recyclerL2.layoutManager = GridLayoutManager(this@MagicActivity, 4)
                    recyclerL3.layoutManager = GridLayoutManager(this@MagicActivity, 4)

                    val l1Adapter = MagicL1Adapter(inputL1List, this@MagicActivity)
                    val l2Adapter = MagicL2Adapter(inputL2List, this@MagicActivity)
                    val l3Adapter = MagicL3Adapter(inputL3List, this@MagicActivity)

                    recyclerL1.adapter = l1Adapter
                    recyclerL2.adapter = l2Adapter
                    recyclerL3.adapter = l3Adapter

                }
            }
        })



//        var sqliteDB = SQLiteHelper(this).writableDatabase
//        val DB = sqliteDB.rawQuery("SELECT * FROM donTable", null)
//        fun addMagicList(){
//            magicList.clear()
//            DB.moveToFirst()
//            for (i in 0 until DB.count){
//                magicList.add(i, Magic(i, DB.getInt(1), DB.getString(2), DB.getInt(3), DB.getInt(4), DB.getInt(5)))
//                DB.moveToNext()
//            }
//        }

//        addMagicList()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}