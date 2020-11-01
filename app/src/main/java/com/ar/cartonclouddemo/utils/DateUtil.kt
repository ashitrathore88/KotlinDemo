package com.ar.cartonclouddemo.utils

import java.text.SimpleDateFormat


class DateUtil {

    companion object{
        fun formatDateToTime(date: String): String{
            val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("HH")
            val formattedDate = formatter.format(parser.parse(date))
            return formattedDate;
        }

        fun formatDateToDay(date: String): String{
            val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd/MM")
            val formattedDate = formatter.format(parser.parse(date))
            return formattedDate;
        }
    }
}