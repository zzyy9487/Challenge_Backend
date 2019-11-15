package com.example.don

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.don.magicadapter.MagicL1Adapter
import com.example.don.magicadapter.MagicL2Adapter
import com.example.don.magicadapter.MagicL3Adapter
import com.example.don.shopadapter.Magic
import kotlinx.android.synthetic.main.activity_magic.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.don.magic.MagicString

class MagicActivity : AppCompatActivity() {

    var magicList = mutableListOf<Magic>()
    var inputL1List = mutableListOf<Magic>()
    var inputL2List = mutableListOf<Magic>()
    var inputL3List = mutableListOf<Magic>()
    lateinit var shared :SharedPreferences

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
        check = shared.preference.getString("check", "")?:0.toString()
        if (check == 1.toString()){
            name = shared.preference.getString("name", "")?:""
            password =shared.preference.getString("password", "")?:""
        }
        else{
            name = shared.preference.getString("name2", "")?:""
            password =shared.preference.getString("password2", "")?:""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magic)
        shared = SharedPreferences(this)
        record()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://vegelephant.club/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiInterface = retrofit.create(APIInterface::class.java)
        val call = apiInterface.getMagicList("$name", "$password")

        call.enqueue(object :retrofit2.Callback<MagicString>{
            override fun onFailure(call: Call<MagicString>, t: Throwable) {
            }
            override fun onResponse(call: Call<MagicString>, response: Response<MagicString>) {
                val magicResponse = response.body()
                if (magicResponse != null) {
                    for (i in 0 until magicResponse.magics.size){
                        magicList.add(i,
                            Magic(
                                magicResponse.magics[i]!!.id,
                                photoArray[i],
                                magicResponse.magics[i]!!.name,
                                magicResponse.magics[i]!!.price.toInt(),
                                magicResponse.magics[i]!!.level,
                                magicResponse.magics[i]!!.magic_id ?: 0
                            )
                        )
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
