package com.binparse

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

    var JsonData = JSONObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var enterButton : Button = findViewById(R.id.enterButton)
        enterButton.setOnClickListener {
            val inputBinNum: EditText = findViewById(R.id.inputBinNum);
            val num = inputBinNum.text.toString().toUInt();
            updateBin(num);//функция получения данных с сервера
        }
    }

    private fun updateBin(num: UInt) {
        val client = OkHttpClient()
        val request: Request = Request.Builder()
            .url("https://lookup.binlist.net/45717360")
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
                        JsonData = JSONObject(str)
                    }
                }
            }
        })

    }

}

