package com.example.don

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.don.login.Login
import com.example.don.register.Register
import kotlinx.android.synthetic.main.activity_welcome.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class WelcomeActivity : AppCompatActivity() {

    var check :String = "0"
    lateinit var shared :SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        shared = SharedPreferences(this)
        if(shared.preference.getString("name", "").isNullOrEmpty()){
        }
        else{
            editAccount.setText(shared.preference.getString("name", ""))
            editPassword.setText(shared.preference.getString("password", ""))
        }

        fun getCheckStatus(){
            if (shared.preference.getString("check", "").isNullOrEmpty()){
                check = "0"
                checkRecord.isChecked = false
            }
            else if (shared.preference.getString("check", "") == 0.toString()){
                check = "0"
                checkRecord.isChecked = false
            }
            else{
                checkRecord.isChecked = true
            }
        }

        getCheckStatus()

        checkRecord.setOnClickListener{
            if (checkRecord.isChecked){
                check = "1"
                shared.setCheck("1")
                shared.setName("")
                shared.setPassword("")
            }
            else{
                checkRecord.isChecked = false
                check = "0"
                shared.setCheck("0")
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
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://vegelephant.club/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                val apiInterface = retrofit.create(APIInterface::class.java)
                val call = apiInterface.register("${editAccount.text}", "${editPassword.text}", 0)

                call.enqueue(object :retrofit2.Callback<Register>{
                    override fun onFailure(call: Call<Register>, t: Throwable) {
                        Toast.makeText(this@WelcomeActivity, "$t", Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(call: Call<Register>, response: Response<Register>) {
                        if(response.isSuccessful){
                                val regdata = response.body()
                                Toast.makeText(this@WelcomeActivity, "您已成功註冊帳號： ${regdata!!.user.name} " , Toast.LENGTH_LONG).show()
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
//                    .create<APIInterface>().login("${editAccount.text}", "${editPassword.text}")
                var apiInterface = retrofit.create(APIInterface::class.java)
                var call = apiInterface.login("${editAccount.text}", "${editPassword.text}")

                call.enqueue(object :retrofit2.Callback<Login>{
                    override fun onFailure(call: Call<Login>, t: Throwable) {
                        Toast.makeText(this@WelcomeActivity, "你是不是沒有連網路阿！@@~", Toast.LENGTH_LONG).show()
                    }
                    override fun onResponse(call: Call<Login>, response: Response<Login>) {
                        if (response.isSuccessful){
                            var logindata = response.body()
                            shared.setCash(logindata!!.user.balance.toString())
                            if (check=="1"){
                                shared.setName(editAccount.text.toString())
                                shared.setPassword(editPassword.text.toString())
                                editAccount.setText("")
                                editPassword.setText("")
                                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                            else{
                                shared.setName2(editAccount.text.toString())
                                shared.setPassword2(editPassword.text.toString())
                                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                                startActivity(intent)
                            }
                        }
                        else{
                            if (response.code() == 401){
                            Toast.makeText(this@WelcomeActivity, "name or password error.", Toast.LENGTH_LONG).show()
                            }
                            else if(response.code() == 406){
                                Toast.makeText(this@WelcomeActivity, "The password field is required.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                })
            }
        }
    }
}
