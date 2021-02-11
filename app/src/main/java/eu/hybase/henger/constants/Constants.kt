package eu.hybase.henger.constants

import android.util.Log
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Constants {
    /**
     * USER CLASS CONSTANST
     */
    const val USER = "user"
    const val PHONE = "phone"
    const val NAME = "name"

    /**
     * APPOINTMENT CONST
     * THIS IS BOOKING FROM USERS
     */
    const val APPOINTMENT = "appointment"
    const val APPOINTMENT_USER_ID = "appointment_user_id"
    const val APPOINTMENT_PERIOD = "appointment_period"
    const val APPOINTMENT_DATE = "appointment_date"

    enum class OptionalDays  {
        TODAY,TOMORROW,AFTER_TOMORROW
    }


    const val ONE_DAY_IN_LONG : Long = 1000 * 60 * 60 * 24

    const val ONE_OF_PERIOD_OF_DAY = 8
    const val GYM_OPEN_HOUR = 7.5f
    const val MAX_MEMBERS = 4

    val GYM_PERIOD : ArrayList<String> = arrayListOf<String>("7:30","9:00","10:30","12:00","13:30","15:00","16:30","18:00")


    fun getTodayDateToLong(optionalDay : Constants.OptionalDays) : Long {
        var calendar = Calendar.getInstance()
        var date : Date? = null


        when (optionalDay) {
            Constants.OptionalDays.TODAY -> {
                date = Date(System.currentTimeMillis())
            }
            Constants.OptionalDays.TOMORROW -> {
                date = Date(System.currentTimeMillis() + ONE_DAY_IN_LONG)
            }
            Constants.OptionalDays.AFTER_TOMORROW -> {
                date = Date(System.currentTimeMillis() + ( 2* ONE_DAY_IN_LONG) )
            }
            else -> {
                date = Date(System.currentTimeMillis())
            }
        }
//        calendar.set(Calendar.YEAR,date.year)
        calendar.set(Calendar.MONTH,date.month)
        calendar.set(Calendar.DAY_OF_WEEK,date.day+1)
        calendar.set(Calendar.HOUR_OF_DAY,0)
        calendar.set(Calendar.MINUTE,1)
        var format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
        Log.d("TAG", "getTodayDateToLong: ${format.format(Date(calendar.timeInMillis))}")
        return calendar.timeInMillis
    }

}