package com.binparse

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class MainActivity : AppCompatActivity() {

    var InfoBin = ArrayList<InfoBin>();
    var listAdapter = ListAdapter(this@MainActivity,InfoBin)

    private lateinit var listViewBin: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);//для корректного применения темы

        val enterButton : Button = findViewById(R.id.enterButton)
        listViewBin = findViewById(R.id.listview) // находим список

        listAdapter = ListAdapter(this@MainActivity,InfoBin)//передаем аргументы в адаптер
        listViewBin.adapter = listAdapter


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
            .url("https://lookup.binlist.net/$num")
            //.url("https://lookup.binlist.net/45332")
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
                        val str = response.body()!!.string()
                        val jsonData = JSONObject(str)
                        val infobin = InfoBin();
                        infobin.updateBin(jsonData)
                        InfoBin.add(infobin)
                        runOnUiThread {
                            listAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }
        })

    }

}

