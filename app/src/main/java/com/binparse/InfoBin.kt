package com.binparse

import org.json.JSONObject
import java.io.Serializable

//Сериализация добавлена для записи и чтения из файла
class InfoBin() : Serializable {
    var scheme: String = "?"
    var type: String = "?"

    var brand: String = "?"
    var prepaid: String = "?"

    var length: String = "?"
    var luhn: String = "?"

    var coutryName: String = "?"
    var coordinates: String = "?"

    var bankname: String = "?"
    var url: String = "?"
    var phone: String = "?"

    private var latitude =""
    private var longitude =""

    //функция для того чтобы распарсить массив
    fun updateBin(Data: JSONObject) {
        if (Data.has("scheme") && !Data.isNull("scheme")) {
            scheme = Data.get("scheme").toString()
        }
        if (Data.has("type") && !Data.isNull("type")) {
            type = Data.get("type").toString()
        }
        if (Data.has("brand") && !Data.isNull("brand")) {
            brand = Data.get("brand").toString()
        }
        if (Data.has("prepaid") && !Data.isNull("prepaid")) {
            prepaid = Data.get("prepaid").toString()
        }

        if (Data.has("bank") && !Data.isNull("bank"))  {
            val bankData: JSONObject = Data.get("bank") as JSONObject
            if (bankData.has("name") && !bankData.isNull("name"))  {
                bankname = bankData.get("name").toString()
            }
            if (bankData.has("url") && !bankData.isNull("phone"))  {
                url = bankData.get("url").toString()
            }
            if (bankData.has("phone") && !bankData.isNull("phone"))  {
                phone = bankData.get("phone").toString()
            }
        }

        if (Data.has("country") && !Data.isNull("country"))  {
            val country: JSONObject = Data.get("country") as JSONObject
            if (country.has("name") && !country.isNull("name")) {
                coutryName = country.get("name").toString()
            }
            if (country.has("latitude") && !country.isNull("latitude")) {
                latitude = country.get("latitude").toString()
            }
            if (country.has("longitude") && !country.isNull("longitude")) {
                longitude = country.get("longitude").toString()
            }
            coordinates = "latitude$latitude;longitude$longitude"
        }

        if (Data.has("number") && !Data.isNull("number")) {
            val number: JSONObject = Data.get("number") as JSONObject
            if (number.has("length") && !number.isNull("length"))  {
                length = number.get("length").toString()
            }
            if (number.has("luhn")  && !number.isNull("luhn"))  {
                luhn = number.get("luhn").toString()
            }
        }
    }


}