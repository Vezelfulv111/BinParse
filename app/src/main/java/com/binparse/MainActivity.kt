package com.binparse

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import okhttp3.*
import org.json.JSONObject
import java.io.*



class MainActivity : AppCompatActivity() {
    var infoBin = ArrayList<InfoBin>()
    var listAdapter = ListAdapter(this@MainActivity,infoBin)
    private lateinit var listViewBin: ListView
    private val binFileName = "BinFile.dac"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)//для корректного применения темы
        listViewBin = findViewById(R.id.listview) // находим список

        readFromFile(binFileName)//функция чтения из файла
        listAdapter = ListAdapter(this@MainActivity,infoBin)//передаем аргументы в адаптер
        listViewBin.adapter = listAdapter

        val enterButton : Button = findViewById(R.id.enterButton)//кнопка для добавления элемента в спиоск
        enterButton.setOnClickListener {
            val inputBinNum: EditText = findViewById(R.id.inputBinNum)
            val num = inputBinNum.text.toString().toUInt()
            if (num.toString().length == 6) { //проверим, что число 6тизначное
                updateBin(num)
            }
            else{//если некоррекный номер карты выводится сообщение об этом
                val toast = Toast.makeText(applicationContext, "Число должно состоять из 6 цифр!", Toast.LENGTH_SHORT)
                toast.show()
            }
        }

        val delButton : ImageButton = findViewById(R.id.delButton)//кнопка для очищения списка
        delButton.setOnClickListener {
            infoBin.clear()
            listAdapter.notifyDataSetChanged()
            writetoFile(binFileName, infoBin)
            val toast = Toast.makeText(applicationContext, "История запросов очищена", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    //функция для обработки API запроса
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
                        val infobin2 = InfoBin()
                        infobin2.updateBin(jsonData)//парсим данные json
                        infoBin.add(infobin2)//добавляем данные в ArrayList
                        runOnUiThread {//запуск в основном потоке
                            listAdapter.notifyDataSetChanged()
                            writetoFile(binFileName, infoBin)
                        }
                    }
                }
            }
        })

    }
    private fun readFromFile(BinFileName: String) {
        val file = File(this.filesDir, BinFileName)
        if (file.exists()) {
            val fis: FileInputStream = openFileInput(BinFileName)
            val iss = ObjectInputStream(fis)
            infoBin = iss.readObject() as ArrayList<InfoBin>
            iss.close()
            fis.close()
        }
    }
    private fun writetoFile(BinFileName: String, infoBin: ArrayList<InfoBin>) {
        val fos: FileOutputStream = openFileOutput(BinFileName, Context.MODE_PRIVATE)
        val os = ObjectOutputStream(fos)
        os.writeObject(infoBin)
        os.close()
        fos.close()
    }

}

