package com.binparse

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity


class ListAdapter(var context: Context, var items: ArrayList<InfoBin>) : BaseAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent:ViewGroup?): View {
        var convertView: View? = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_element, parent, false)
        }
        //базовая информация
        val scheme= convertView?.findViewById(R.id.Scheme_i) as TextView
        scheme.text = items[position].scheme
        val type = convertView.findViewById(R.id.Type_i) as TextView
        type.text = items[position].type
        val brand = convertView.findViewById(R.id.Brand_i) as TextView
        brand.text = items[position].brand
        val prepaid = convertView.findViewById(R.id.Prepaired_i) as TextView
        prepaid.text = items[position].prepaid

        //параметры карты
        val length = convertView.findViewById(R.id.Length_i) as TextView
        length.text = items[position].length
        val luhn = convertView.findViewById(R.id.Luhn_i) as TextView
        luhn.text = items[position].luhn

        //страна координаты
        val coordinates = convertView.findViewById(R.id.Coordinates) as TextView
        coordinates.text = items[position].coordinates
        val country = convertView.findViewById(R.id.Country_i) as TextView
        country.text = items[position].coutryName

        //банк
        val bank = convertView.findViewById(R.id.Bank_i) as TextView
        bank.text = items[position].bankname
        val link = convertView.findViewById(R.id.Link) as TextView
        link.text = items[position].url
        val phone = convertView.findViewById(R.id.Phone) as TextView
        phone.text = items[position].phone

        //переход в "звонилку"
        phone.setOnClickListener() {
            if (items[position].phone != "?" && items[position].phone.isNotEmpty()) {
                val number = items[position].phone
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$number")
                context.startActivity(intent)
            }
        }

        //переход на сайт банка
        link.setOnClickListener() {
            if (items[position].url != "?" && items[position].url.isNotEmpty()) {
                var url =  items[position].url
                if (!url.startsWith("https://") && !url.startsWith("http://")) {//проверка на тип ссылки
                    url = "http://$url";
                }
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                context.startActivity(intent)
            }
        }

        //переход на карты
        coordinates.setOnClickListener() {
            if (items[position].coordinatesURL.isNotEmpty()) {
                val gmmIntentUri = Uri.parse(items[position].coordinatesURL)
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                //мой телефон не поддерживает гугл сервисы, в том числе гугл карты
                //поэтому в качестве package я выбрал яндекс карты
                mapIntent.setPackage("ru.yandex.yandexmaps")
                mapIntent.resolveActivity(context.packageManager)?.let {
                    context.startActivity(mapIntent)
                }
            }
        }

        return convertView
    }

}


