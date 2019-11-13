package com.example.don

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.don.Login.Login
import com.example.don.Register.Register
import kotlinx.android.synthetic.main.activity_welcome.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WelcomeActivity : AppCompatActivity() {

    var check :String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val preferenceInit = getSharedPreferences("name", Context.MODE_PRIVATE)
        if (preferenceInit.getString("name","").isNullOrEmpty()){
        }
        else{
            Thread{
                Thread.sleep(0)
                runOnUiThread {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }.start()
        }

        fun getCheckStatus(){
            val preferenceCheck = getSharedPreferences("check", Context.MODE_PRIVATE)
            if (preferenceCheck.getString("check", "").isNullOrEmpty()){
                check = "0"
                checkRecord.isChecked = false
            }
            else if (preferenceCheck.getString("check", "") == 0.toString()){
                check = "0"
                checkRecord.isChecked = false
            }
            else{
                checkRecord.isChecked = true
            }
        }

        getCheckStatus()

        checkRecord.setOnClickListener{
            val preferenceCheck = getSharedPreferences("check", Context.MODE_PRIVATE)
            if (checkRecord.isChecked){
                check = "1"
                preferenceCheck.edit().putString("check", "1").apply()
                val preferenceName = getSharedPreferences("name", Context.MODE_PRIVATE)
                preferenceName.edit().putString("name", "").apply()
                val preferencePassword = getSharedPreferences("password", Context.MODE_PRIVATE)
                preferencePassword.edit().putString("password", "").apply()
            }
            else{
                checkRecord.isChecked = false
                check = "0"
                preferenceCheck.edit().putString("check", "0").apply()
            }
        }

        btn_forgot.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btn_registered.setOnClickListener {
            if (editAccount.text.isEmpty() or editPassword.text.isEmpty()){
                Toast.makeText(this, "帳號密碼都要輸入！", Toast.LENGTH_SHORT).show()
            }
            else{
                var retrofit = Retrofit.Builder()
                    .baseUrl("http://vegelephant.club/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                var apiInterface = retrofit.create(APIInterface::class.java)
                var call = apiInterface.register("${editAccount.text}", "${editPassword.text}", 0)

                call.enqueue(object :retrofit2.Callback<Register>{

                    override fun onFailure(call: Call<Register>, t: Throwable) {
                        Toast.makeText(this@WelcomeActivity, "註冊失敗耶！@@~", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Register>, response: Response<Register>) {
                        if(response.isSuccessful){
                                var regdata = response.body()
                                Toast.makeText(this@WelcomeActivity, "註冊名字是${regdata!!.name}", Toast.LENGTH_LONG).show()
                                editAccount.setText("")
                                editPassword.setText("")
                        }
                        else{
                            Toast.makeText(this@WelcomeActivity, "註冊失敗耶！@@~", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }

        btn_login.setOnClickListener {
            if (editAccount.text.isEmpty() or editPassword.text.isEmpty()){
                Toast.makeText(this, "帳號密碼都要輸入！！！", Toast.LENGTH_SHORT).show()
            }
            else{
                var retrofit = Retrofit.Builder()
                    .baseUrl("http://vegelephant.club/api/")
                    .addConverterFactory(GsonConverterFactory.create())

                    .build()
                var apiInterface = retrofit.create(APIInterface::class.java)
                var call = apiInterface.login("${editAccount.text}", "${editPassword.text}")

                call.enqueue(object :retrofit2.Callback<Login>{

                    override fun onFailure(call: Call<Login>, t: Throwable) {
                        Toast.makeText(this@WelcomeActivity, "登入失敗耶！@@~", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<Login>, response: Response<Login>) {
                        if (response.isSuccessful){
                            var logindata = response.body()
//                            Toast.makeText(this@WelcomeActivity, "餘額：${logindata!!.balance}", Toast.LENGTH_LONG).show()
                            val preferenceCash = getSharedPreferences("cash", Context.MODE_PRIVATE)
                            preferenceCash.edit().putString("cash", logindata!!.balance.toString()).apply()
                            if (check=="1"){
                                val preferenceName = getSharedPreferences("name", Context.MODE_PRIVATE)
                                preferenceName.edit().putString("name", editAccount.text.toString()).apply()
                                val preferencePassword = getSharedPreferences("password", Context.MODE_PRIVATE)
                                preferencePassword.edit().putString("password", editPassword.text.toString()).apply()
                                editAccount.setText("")
                                editPassword.setText("")
                                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                            else{
                                val preferenceName = getSharedPreferences("name2", Context.MODE_PRIVATE)
                                preferenceName.edit().putString("name2", editAccount.text.toString()).apply()
                                val preferencePassword = getSharedPreferences("password2", Context.MODE_PRIVATE)
                                preferencePassword.edit().putString("password2", editPassword.text.toString()).apply()
                                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                                startActivity(intent)
                            }

                        }
                        else{
                            Toast.makeText(this@WelcomeActivity, "登入失敗耶！@@~", Toast.LENGTH_LONG).show()
                        }

                    }
                })
            }
        }

    }
}
