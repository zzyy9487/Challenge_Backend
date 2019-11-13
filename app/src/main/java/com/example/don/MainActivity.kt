package com.example.don

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.don.Bonus.Bonus
import com.example.don.Bonus.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.textViewMoney
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

//    lateinit var sqliteDB:SQLiteDatabase
//    var initList = mutableListOf<Magic>()
    var money :Int = 0

//    var photoArray = arrayOf(
//        R.drawable.m0,
//        R.drawable.m1,
//        R.drawable.m2,
//        R.drawable.m3,
//        R.drawable.m4,
//        R.drawable.m5,
//        R.drawable.m6,
//        R.drawable.m7,
//        R.drawable.m8,
//        R.drawable.m9,
//        R.drawable.m10,
//        R.drawable.m11,
//        R.drawable.m12,
//        R.drawable.m0,
//        R.drawable.m1,
//        R.drawable.m2,
//        R.drawable.m3,
//        R.drawable.m4,
//        R.drawable.m5,
//        R.drawable.m6,
//        R.drawable.m7,
//        R.drawable.m8,
//        R.drawable.m9,
//        R.drawable.m10,
//        R.drawable.m11,
//        R.drawable.m12,
//        R.drawable.m0,
//        R.drawable.m1,
//        R.drawable.m2,
//        R.drawable.m3,
//        R.drawable.m4,
//        R.drawable.m5,
//        R.drawable.m6,
//        R.drawable.m7,
//        R.drawable.m8,
//        R.drawable.m9,
//        R.drawable.m10,
//        R.drawable.m11,
//        R.drawable.m12
//        )

//    var nameArray = arrayOf(
//        "Wind (L1)",
//        "Bolt (L1)",
//        "DePosion (L1)",
//        "DeWeight (L1)",
//        "Detetion (L1)",
//        "FireArrow (L1)",
//        "Heal (L1)",
//        "HollyWeapen (L1)",
//        "IceDagger (L1)",
//        "Light (L1)",
//        "Shield (L1)",
//        "Teleport (L1)",
//        "Touch (L1)",
//        "Wind (L2)",
//        "Bolt (L2)",
//        "DePosion (L2)",
//        "DeWeight (L2)",
//        "Detetion (L2)",
//        "FireArrow (L2)",
//        "Heal (L2)",
//        "HollyWeapen (L2)",
//        "IceDagger (L2)",
//        "Light (L2)",
//        "Shield (L2)",
//        "Teleport (L2)",
//        "Touch (L2)",
//        "Wind (L3)",
//        "Bolt (L3)",
//        "DePosion (L3)",
//        "DeWeight (L3)",
//        "Detetion (L3)",
//        "FireArrow (L3)",
//        "Heal (L3)",
//        "HollyWeapen (L3)",
//        "IceDagger (L3)",
//        "Light (L3)",
//        "Shield (L3)",
//        "Teleport (L3)",
//        "Touch (L3)"
//    )

//    var priceArray = arrayOf(
//        100,
//        100,
//        100,
//        100,
//        100,
//        100,
//        100,
//        100,
//        100,
//        100,
//        100,
//        100,
//        100,
//        200,
//        200,
//        200,
//        200,
//        200,
//        200,
//        200,
//        200,
//        200,
//        200,
//        200,
//        200,
//        200,
//        500,
//        500,
//        500,
//        500,
//        500,
//        500,
//        500,
//        500,
//        500,
//        500,
//        500,
//        500,
//        500
//    )

//    var levelArray = arrayOf(
//        1,
//        1,
//        1,
//        1,
//        1,
//        1,
//        1,
//        1,
//        1,
//        1,
//        1,
//        1,
//        1,
//        2,
//        2,
//        2,
//        2,
//        2,
//        2,
//        2,
//        2,
//        2,
//        2,
//        2,
//        2,
//        2,
//        3,
//        3,
//        3,
//        3,
//        3,
//        3,
//        3,
//        3,
//        3,
//        3,
//        3,
//        3,
//        3
//    )

//    fun makeMagicList(){
//        for (i in 0..38){
//        initList.add(i,Magic(i, photoArray[i], nameArray[i], priceArray[i], levelArray[i]))
//        }
//    }

//    fun addInitList(){
//        var db = sqliteDB.rawQuery("SELECT * FROM donTable", null)
//        if (db.count == 0){
//            // 初始資料建立
//            makeMagicList()
//            for (i in 0 until initList.size){
//                sqliteDB.execSQL("INSERT INTO donTable(id, photo, name, price, level, magic_id) VALUES(?, ?, ?, ?, ?, ?)", arrayOf<Any?>(i, initList[i].photo, initList[i].name, initList[i].price, initList[i].level, initList[i].buy))
//            }
//            db.close()
//        }
//        else{
//            initList.clear()
//            db.moveToFirst()
//            for (i in 0..38){
//                initList.add(i, Magic(i, db.getInt(1), db.getString(2), db.getInt(3), db.getInt(4), db.getInt(5)))
//                db.moveToNext()
//            }
//            db.close()
//        }
//    }

    fun callMoney(){
//        val db = sqliteDB.rawQuery("SELECT * FROM donMoney", null)
//        db.moveToFirst()
//        if (db.count == 0){
//            db.close()
//        }
//        else {
//            money = db.getInt(1)
//            db.close()
//        }

//          local Money
        val preference = getSharedPreferences("cash", Context.MODE_PRIVATE)
        val cash = preference.getString("cash", "")
        if (cash.isNullOrEmpty()){
            money = 0
        }
        else{
            money = cash.toInt()
        }
    }


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
        setContentView(R.layout.activity_main)

        // SQLite初始化
//        sqliteDB = SQLiteHelper(this).writableDatabase
        // 判斷SQLite裡是否有之前資料
//        addInitList()
        // callMoney
        callMoney()
        // 初始金額
        textViewMoney.text = money.toString()
        // 金額寫入 SharePreference
//        val preference = getSharedPreferences("cash", Context.MODE_PRIVATE)
//        preference.edit().putString("cash", money.toString()).apply()

        // 金額寫入SQLite
//        sqliteDB.delete("donMoney", null, null)
//        sqliteDB.execSQL("INSERT INTO donMoney(number, cash) VALUES(?,?)", arrayOf<Any?>(0, money))

        // 島主登入加名字
        record()
        textViewName.text = "島主"+"$name"

        imageViewName.setOnClickListener {
            val preference1 = getSharedPreferences("cash", Context.MODE_PRIVATE)
            preference1.edit().putString("cash", "0").apply()
            val preference2 = getSharedPreferences("name", Context.MODE_PRIVATE)
            preference2.edit().putString("name", "").apply()
            val preference3 = getSharedPreferences("password", Context.MODE_PRIVATE)
            preference3.edit().putString("password", "").apply()
            val preference4 = getSharedPreferences("check", Context.MODE_PRIVATE)
            preference4.edit().putString("check", "0").apply()
            val preference5 = getSharedPreferences("name2", Context.MODE_PRIVATE)
            preference5.edit().putString("name2", "").apply()
            val preference6 = getSharedPreferences("password2", Context.MODE_PRIVATE)
            preference6.edit().putString("password2", "").apply()
            val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
            startActivity(intent)
            this@MainActivity.finish()
        }

        // 魔法商店按鈕監聽
        buttonShop.setOnClickListener {
            val intent = Intent(this, ShopActivity::class.java)
            startActivity(intent)
//            this@MainActivity.finish()
        }
        // reset
//        textViewH.setOnClickListener {
//            val preferenceReset = getSharedPreferences("cash", Context.MODE_PRIVATE)
//            money = 2000
//            preferenceReset.edit().putString("cash", money.toString()).apply()
//            textViewMoney.text = money.toString()
//            initList.forEach { it.buy = 0 }
//            var db = sqliteDB.rawQuery("SELECT * FROM donTable", null)
//            sqliteDB.delete("donTable", null, null)
//            for (i in 0 until initList.size){
//                sqliteDB.execSQL("INSERT INTO donTable(id, photo, name, price, level, magic_id) VALUES(?, ?, ?, ?, ?, ?)", arrayOf<Any?>(i, initList[i].photo, initList[i].name, initList[i].price, initList[i].level, initList[i].buy))
//            }
//            db.close()
//        }
        // +$
//        imageViewMoney.setOnClickListener {
//            money = money + 100
//            textViewMoney.text = money.toString()
//            val preferencePlus = getSharedPreferences("cash", Context.MODE_PRIVATE)
//            preferencePlus.edit().putString("cash", money.toString()).apply()
//        }
        // 魔法列表按鈕監聽
        buttonMagicTotalList.setOnClickListener {
            val intent = Intent(this, MagicActivity::class.java)
            startActivity(intent)
        }

        var inputword : String = ""

        imageBug.setOnClickListener {
            inputword = "$inputword" + "1"
            if(inputword.length > 9){
                var input = inputword.get(3).toString() + inputword.get(4).toString() + inputword.get(5).toString() + inputword.get(6).toString() + inputword.get(7).toString() + inputword.get(8).toString() + inputword.get(9).toString()
                inputword = input
            }
        }

        imageKill.setOnClickListener {
            inputword = "$inputword" + "2"
            if (inputword.contains("1121122")){
//                Toast.makeText(this, "按這麼久才加100...QQ...", Toast.LENGTH_LONG).show()
                var retrofitBonus = Retrofit.Builder()
                    .baseUrl("http://vegelephant.club/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var apiInterface = retrofitBonus.create(APIInterface::class.java)
                var callBonus = apiInterface.bonus(name, password)

                callBonus.enqueue(object :retrofit2.Callback<Bonus>{
                    override fun onFailure(call: Call<Bonus>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "不要登入後偷偷關網路齁！", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Bonus>, response: Response<Bonus>) {
                        val bonusdata = response.body()
                        money = bonusdata!!.result.balance
                        val preferenceBonus = getSharedPreferences("cash", Context.MODE_PRIVATE)
                        preferenceBonus.edit().putString("cash", money.toString()).apply()
                        textViewMoney.text = money.toString()
                    }
                })
//                inputword = ""
//                money = money + 100
//                textViewMoney.text = money.toString()
//                val preferenceBonus = getSharedPreferences("cash", Context.MODE_PRIVATE)
//                preferenceBonus.edit().putString("cash", money.toString()).apply()
            }
            else if (inputword.length > 9){
                var input = inputword.get(3).toString() + inputword.get(4).toString() + inputword.get(5).toString() + inputword.get(6).toString() + inputword.get(7).toString() + inputword.get(8).toString() + inputword.get(9).toString()
                inputword = input
            }
        }



//        object ApiClient {
//            var BASE_URL:String="http://8a932029.ngrok.io/api/"
//            val getClient: APIInterface
//                get() {
//                    val retrofit = Retrofit.Builder()
//                        .baseUrl(BASE_URL)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build()
//                    return retrofit.create(APIInterface::class.java)
//
//                }
//        }


    }

    override fun onResume() {
        super.onResume()
        val preference = getSharedPreferences("cash", Context.MODE_PRIVATE)
        val cash = preference.getString("cash", "")
        if (cash.isNullOrEmpty()){
            money = 0
        }
        else{
            money = cash.toInt()
        }
        textViewMoney.text = money.toString()
        record()
        textViewName.text = "島主"+"$name"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}
