package eu.hybase.henger.models

import android.os.Parcel
import android.os.Parcelable

data class User(
    val id: String = "",
    val phone : String = "",
    val name : String = "",
    val email : String = "",
) : Parcelable {
    constructor(parcel : Parcel): this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(phone)
        dest.writeString(name)
        dest.writeString(email)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}