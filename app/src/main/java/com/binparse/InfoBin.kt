package com.binparse

import org.json.JSONObject

class InfoBin() {
    var scheme: String = ""
    var type: String = ""

    var brand: String = "";
    var prepaid: String = "";

    var length: String = "";
    var luhn: String = "";

    var coutryName: String = "";
    var coordinates: String = "";

    var name: String = "";
    var url: String = "";
    var phone: String = "";

    private var latitude ="";
    private var longitude ="";

    //функция для того чтобы распарсить массив
    fun updateBin(Data: JSONObject) {
        if (Data.has("scheme")) {
            scheme = Data.get("scheme").toString()
        }
        if (Data.has("type")) {
            type = Data.get("scheme").toString()
        }
        if (Data.has("brand")) {
            brand = Data.get("brand").toString()
        }
        if (Data.has("prepaid")) {
            prepaid = Data.get("prepaid").toString()
        }

        if (Data.has("bank"))  {
            val bankData: JSONObject = Data.get("bank") as JSONObject
            if (bankData.has("name"))  {
                name = bankData.get("name").toString()
            }
            if (bankData.has("url"))  {
                url = bankData.get("url").toString()
            }
            if (bankData.has("phone"))  {
                phone = bankData.get("phone").toString()
            }
        }

        if (Data.has("country"))  {
            val country: JSONObject = Data.get("country") as JSONObject
            if (country.has("name"))  {
                coutryName = country.get("name").toString()
            }
            if (country.has("latitude"))  {
                latitude = country.get("latitude").toString()
            }
            if (country.has("longitude"))  {
                longitude = country.get("longitude").toString()
            }
            coordinates = "latitude$latitude;longitude$longitude"
        }

        if (Data.has("number")) {
            val number: JSONObject = Data.get("number") as JSONObject
            if (number.has("length"))  {
                length = number.get("length").toString()
            }
            if (number.has("luhn"))  {
                luhn = number.get("luhn").toString()
            }
        }
    }


}