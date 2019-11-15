package com.example.don

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.don.shopadapter.Magic
import kotlinx.android.synthetic.main.activity_shop.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.don.buy.Buy
import com.example.don.magic.MagicString
import com.example.don.shopadapter.ShopAdapter
import kotlinx.android.synthetic.main.activity_shop.textViewMoney
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ShopActivity : AppCompatActivity() {

    var shopList = mutableListOf<Magic>()
    var fShopList = mutableListOf<Magic>()
    var money:Int = 0
    var page :Int = 1
    lateinit var shared :SharedPreferences

    lateinit var textPrice :TextView
    lateinit var imageView :ImageView
    lateinit var pos :TextView
    lateinit var neg :TextView
    lateinit var bye :TextView

    lateinit var context:Context

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

    fun callMoney(){
        val cash = shared.preference.getString("cash", "")
        if (cash.isNullOrEmpty()){
            money = 0
        }
        else{
            money = cash.toInt()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop)
        shared = SharedPreferences(this)
        // SQLite
//        var sqliteDB = SQLiteHelper(this).writableDatabase
//
//        fun addShopList(){
//            val DB = sqliteDB.rawQuery("SELECT * FROM donTable", null)
//            shopList.clear()
//            DB.moveToFirst()
//            for (i in 0 until DB.count){
//                shopList.add(i, Magic(i, DB.getInt(1), DB.getString(2), DB.getInt(3), DB.getInt(4), DB.getInt(5)))
//                DB.moveToNext()
//            }
//            DB.close()
//        }
//        addShopList()

        // getMoney
        callMoney()
        textViewMoney.text = money.toString()
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

                val shopResponse = response.body()
                if (shopResponse != null) {
                    for (i in 0 until shopResponse.magics.size){
                        shopList.add(i,
                            Magic(
                                shopResponse.magics[i]!!.id,
                                photoArray[i],
                                shopResponse.magics[i]!!.name,
                                shopResponse.magics[i]!!.price.toInt(),
                                shopResponse.magics[i]!!.level,
                                shopResponse.magics[i]!!.magic_id ?: 0
                            )
                        )
                    }

                    if (!shopList.isEmpty()){
                        recyclerView.setBackgroundResource(R.color.white)
                    }
                    // recyclerView layoutManager&Adapter
                    fShopList.addAll(shopList.filter { it.level == page })
                    var adapter =
                        ShopAdapter(fShopList, this@ShopActivity)
                    recyclerView.layoutManager = LinearLayoutManager(this@ShopActivity)
                    recyclerView.adapter = adapter

                    // interface

                    adapter.setclickedListener(object : ShopAdapter.clickedListener{

                        override fun showPurchase(img: Int, price: Int, position:Int) {

                            val inflater = this@ShopActivity.layoutInflater
                            val view = inflater.inflate(R.layout.alert_layout, null)
                            val builder = AlertDialog.Builder(adapter.context!!)
                                .setView(view)
                                .show()
                            imageView = view.findViewById(R.id.imagePurchase)
                            textPrice = view.findViewById(R.id.textPurchasePrice)
                            pos = view.findViewById(R.id.textPurchaseBuy)
                            neg = view.findViewById(R.id.textPurachseCancel)
                            bye = view.findViewById(R.id.textPurachseBye)
                            textPrice.text = "$"+"$price"
                            imageView.setImageResource(img)
                            pos.setOnClickListener {
                                if (money >= price){
//                                    var aftermoney:Int
//                                    aftermoney = money - price
//                                    money = aftermoney
//                                    shopList[position].buy = 1
//                                sqliteDB.delete("donTable", null, null)
//                                for (i in 0 until shopList.size){
//                                    sqliteDB.execSQL("INSERT INTO donTable(id, photo, name, price, level, magic_id) VALUES(?, ?, ?, ?, ?, ?)", arrayOf<Any?>(i, shopList[i].photo, shopList[i].name, shopList[i].price, shopList[i].level, shopList[i].buy))
//                                }
                                    val shopid = 1
                                    val retrofitBuy = Retrofit.Builder()
                                        .baseUrl("http://vegelephant.club/api/")
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build()
                                    val apiInterfaceBuy = retrofitBuy.create(APIInterface::class.java)
                                    val callBuy = apiInterfaceBuy.buy("$name", "$password", shopid, position)

                                    callBuy.enqueue(object :retrofit2.Callback<Buy>{
                                        override fun onFailure(call: Call<Buy>, t: Throwable) {
                                        }
                                        override fun onResponse(call: Call<Buy>, response: Response<Buy>) {
                                            val buydata = response.body()
                                            shared.setCash(buydata!!.user.balance.toString())
//                                            val preferenceBuy = getSharedPreferences("cash", Context.MODE_PRIVATE)
//                                            preferenceBuy.edit().putString("cash", buydata!!.user.balance.toString()).apply()
                                            money = buydata!!.user.balance
                                            textViewMoney.text = money.toString()
                                            val retrofitRe = Retrofit.Builder()
                                                .baseUrl("http://vegelephant.club/api/")
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build()
                                            val apiInterfaceRe = retrofitRe.create(APIInterface::class.java)
                                            val callRenew = apiInterfaceRe.getMagicList("$name", "$password")

                                            callRenew.enqueue(object :retrofit2.Callback<MagicString>{
                                                override fun onFailure(call: Call<MagicString>, t: Throwable) {
                                                }
                                                override fun onResponse(call: Call<MagicString>, response: Response<MagicString>) {
                                                    val list = response.body()
                                                    shopList.clear()
                                                    if (list != null) {
                                                        for (i in 0 until list.magics.size){
                                                            shopList.add(i,
                                                                Magic(
                                                                    list.magics[i]!!.id,
                                                                    photoArray[i],
                                                                    list.magics[i]!!.name,
                                                                    list.magics[i]!!.price.toInt(),
                                                                    list.magics[i]!!.level,
                                                                    list.magics[i]!!.magic_id ?: 0
                                                                )
                                                            )
                                                        }
                                                        filterLevel(adapter)
                                                    }
                                                }
                                            })
                                        }
                                    })

                                    adapter.notifyDataSetChanged()
                                    textViewMoney.text = money.toString()
                                    val preference2 = getSharedPreferences("cash", Context.MODE_PRIVATE)
                                    preference2.edit().putString("cash", money.toString()).apply()
                                    builder.dismiss()
                                }
                                else{
                                    val inflaterHaHa = this@ShopActivity.layoutInflater
                                    val viewHaHa = inflaterHaHa.inflate(R.layout.haha_layout, null)
                                    val builderHaHa = AlertDialog.Builder(adapter.context!!)
                                        .setView(viewHaHa)
                                        .show()
                                    val bye = viewHaHa.findViewById<TextView>(R.id.textBye)
                                    builder.dismiss()
                                    bye.setOnClickListener {
                                        builderHaHa.dismiss()
                                    }
                                }
                            }

                            neg.setOnClickListener {
                                builder.dismiss()
                            }

                            // 購買按鈕監聽事件
//                textPurchaseBuy.setOnClickListener {
//                    if (money >= price){
//                        var aftermoney:Int
//                        aftermoney = money - price
//                        money = aftermoney
//                        shopList[id].magic_id = 1
//                        sqliteDB.delete("donTable", null, null)
//                        for (i in 0 until shopList.size){
//                            sqliteDB.execSQL("INSERT INTO donTable(id, photo, name, price, level, magic_id) VALUES(?, ?, ?, ?, ?, ?)", arrayOf<Any?>(i, shopList[i].photo, shopList[i].name, shopList[i].price, shopList[i].level, shopList[i].magic_id))
//                        }
//                        adapter.notifyDataSetChanged()
//                        textViewMoney.text = money.toString()
//                        val preference2 = getSharedPreferences("cash", Context.MODE_PRIVATE)
//                        preference2.edit().putString("cash", money.toString()).apply()
//                        purchaseLayout.visibility = View.GONE
//                    }
//                    else{
//                        imagePurchase.setImageResource(R.drawable.eyes)
//                        textPurchasePrice.text = "您的錢有帶夠嗎?!"
//                        textPurchaseBuy.visibility = View.GONE
//                        textPurachseCancel.visibility = View.GONE
//                        textPurachseBye.visibility = View.VISIBLE
//                    }
//                }
//
//                // 取消按鈕監聽
//                textPurachseCancel.setOnClickListener {
//                    purchaseLayout.visibility = View.GONE
//                    recyclerView.isEnabled = true
//                }
//
//                // 掰按鈕監聽
//                textPurachseBye.setOnClickListener {
//                    textPurchaseBuy.visibility = View.VISIBLE
//                    textPurachseCancel.visibility = View.VISIBLE
//                    textPurachseBye.visibility = View.GONE
//                    purchaseLayout.visibility = View.GONE
//                }
                        }
                    })


                    textViewL1.setOnClickListener {
                        page = 1
                        filterLevel(adapter)
                    }

                    textViewL2.setOnClickListener {
                        page = 2
                        filterLevel(adapter)
                    }

                    textViewL3.setOnClickListener {
                        page = 3
                        filterLevel(adapter)
                    }

                    imageViewList1.setOnClickListener {
                        recyclerView.setBackgroundColor(Color.WHITE)
                        adapter.currentType = adapter.viewType_Linear
                        recyclerView.layoutManager = LinearLayoutManager(this@ShopActivity)
                        adapter.notifyDataSetChanged()
                    }

                    imageViewList2.setOnClickListener {
                        recyclerView.setBackgroundColor(Color.DKGRAY)
                        adapter.currentType = adapter.viewType_Grid
                        recyclerView.layoutManager = GridLayoutManager(this@ShopActivity, 4)
                        adapter.notifyDataSetChanged()
                    }

//                    imageViewm.setOnClickListener {
//                        money = money - 1000
//                        textViewMoney.text = money.toString()
//                        val preferenceMinus = getSharedPreferences("cash", Context.MODE_PRIVATE)
//                        preferenceMinus.edit().putString("cash", money.toString()).apply()
//                    }
                }
            }
        })
    }

    private fun filterLevel(adapter: ShopAdapter) {
        fShopList.clear()
        fShopList.addAll(shopList.filter { it.level == page })
        adapter.notifyDataSetChanged()
    }

    // 返回鍵
    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}
