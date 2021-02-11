package eu.hybase.henger.models

import android.os.Parcel
import android.os.Parcelable

data class AppointmentTime(
    val appointment_user_id : String = "",
    val appointment_date : Long = 0L,
    val appointment_period : Int = -1 //defeult period is daily 8 | one period 1.5 hours -> 8*1.5 = 12

) : Parcelable {
    constructor(parcel : Parcel): this(
        parcel.readString()!!,
        parcel.readLong()!!,
        parcel.readInt()!!
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

        dest.writeString(appointment_user_id)
        dest.writeLong(appointment_date)
        dest.writeInt(appointment_period)

    }

    companion object CREATOR : Parcelable.Creator<AppointmentTime> {
        override fun createFromParcel(parcel: Parcel): AppointmentTime {
            return AppointmentTime(parcel)
        }

        override fun newArray(size: Int): Array<AppointmentTime?> {
            return arrayOfNulls(size)
        }
    }

}