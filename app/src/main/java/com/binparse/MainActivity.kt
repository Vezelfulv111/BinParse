package com.binparse

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.Math.log

class MainActivity : AppCompatActivity() {

    var JsonData = JSONObject()
    var Infobin = InfoBin()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var enterButton : Button = findViewById(R.id.enterButton)
        enterButton.setOnClickListener {
            val inputBinNum: EditText = findViewById(R.id.inputBinNum);
            val num = inputBinNum.text.toString().toUInt();
            if (num.toString().length == 6) { //проверим, что число 6тизначное
                updateBin(num)
            }
            else{
                val toast = Toast.makeText(applicationContext, "Число должно состоять из 6 цифр!", Toast.LENGTH_SHORT)
                toast.show()
            }
        }
    }

    private fun updateBin(num: UInt) {
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            //.url("https://lookup.binlist.net/$num")
            .url("https://lookup.binlist.net/45332")
            .get()
            .addHeader("X-RapidAPI-Key", "SIGN-UP-FOR-KEY")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        throw IOException("Unexpected code $response")
                    }
                    else {
                        var str = response.body()!!.string()
                        //var Infobin = InfoBin();
                        JsonData = JSONObject(str)
                        Infobin.updateBin(JsonData)//получили 1 элемент списка
                    }
                }
            }
        })

    }

}

