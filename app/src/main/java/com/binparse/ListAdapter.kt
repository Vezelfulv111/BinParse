package com.binparse

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*

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

    override fun getView(position: Int, convertView: View?, parent:ViewGroup?): View? {
        var convertView: View? = convertView
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_element, parent, false)
        }
        //базовая информация
        val scheme= convertView?.findViewById(R.id.Scheme_i) as TextView
        scheme.text = items[position].scheme
        val type = convertView.findViewById(R.id.Type_i) as TextView
        type.text = items[position].scheme
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

        return convertView
    }

}


