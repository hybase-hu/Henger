package eu.hybase.henger.firestore

import android.app.Activity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import eu.hybase.henger.activities.CalendarActivity
import eu.hybase.henger.activities.MainActivity
import eu.hybase.henger.activities.SignUpActivity
import eu.hybase.henger.constants.Constants
import eu.hybase.henger.models.AppointmentTime
import eu.hybase.henger.models.User
import java.util.*
import kotlin.collections.ArrayList

class FireStoresConnect {

    private val mFireStore = FirebaseFirestore.getInstance()
    private var mCurrentUser : FirebaseUser? = null
    private val mAuth: FirebaseAuth? = FirebaseAuth.getInstance()

    companion object {
        private const val TAG = "FireStoresConnect"
    }

    fun getCurrentUserId(): String {
        var currentUserId = ""
        if (mAuth == null) {
            return currentUserId
        }
        else {
            mCurrentUser = mAuth.currentUser
            if (mCurrentUser != null) {
                currentUserId = mCurrentUser!!.uid
            }
        }
        return currentUserId
    }


    fun registerUser(activity: SignUpActivity,userInfo : User) {
        mFireStore.collection(Constants.USER)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisterSuccess()
            }.addOnFailureListener{
                e->
                Log.e(activity.javaClass.simpleName,"Error writing documents" )
            }
    }

    fun logOutUser(activity: MainActivity) {
        if (mAuth != null) {
            mAuth.signOut()
            activity.finish()
        }
    }


    fun addAppointment(activity: CalendarActivity,appointmentTime: AppointmentTime) {
        mFireStore.collection(Constants.APPOINTMENT)
                .document()
                .set(appointmentTime, SetOptions.merge())
                .addOnFailureListener {
                    Log.e(TAG, "addAppointment: FAILER ${it.message}")
                }
                .addOnSuccessListener {
                    Log.d(TAG, "addAppointment: SUCCESS" )
                }
    }

    fun checkBookedThisDayAlready(activity: CalendarActivity,optionalDay : Constants.OptionalDays)  {
         mFireStore.collection(Constants.APPOINTMENT)
                .whereGreaterThan(Constants.APPOINTMENT_DATE,Constants.getTodayDateToLong(optionalDay))
                .whereLessThan(Constants.APPOINTMENT_DATE,Constants.getTodayDateToLong(optionalDay) + Constants.ONE_DAY_IN_LONG)
                .whereEqualTo(Constants.APPOINTMENT_USER_ID,getCurrentUserId())
                .get()
                 .addOnSuccessListener {
                     Log.e(TAG, "checkBookedThisDayAlready: SUCCESS", )
                     if (it.documents.size == 0) {
                         Log.e(TAG, "checkBookedThisDayAlready: document size is 0", )
                         activity.isSaveIsEnabled()
                     }
                     else {
                         Log.e(TAG, "checkBookedThisDayAlready: document size is ${it.documents.size}", )
                         activity.isSaveIsNotEnabled()
                     }
                 }
                 .addOnFailureListener {
                     Log.e(TAG, "checkBookedThisDayAlready: ERROR ${it.message}", )
                 }
    }


    fun getBookingDatedUsers(activity: CalendarActivity, optionalDay : Constants.OptionalDays) {
        //today
        activity.showProgressBar()
        Log.d(TAG, "getBookingDatedUsers: ${Constants.getTodayDateToLong(optionalDay)}")

        mFireStore.collection(Constants.APPOINTMENT)
                .whereGreaterThan(Constants.APPOINTMENT_DATE,Constants.getTodayDateToLong(optionalDay))
                .whereLessThan(Constants.APPOINTMENT_DATE,Constants.getTodayDateToLong(optionalDay) + Constants.ONE_DAY_IN_LONG)
                .get()
                .addOnSuccessListener {
                    activity.hideProgressBar()
                    val appointmentList : ArrayList<AppointmentTime> = ArrayList()
                    for (i in it.documents) {
                        appointmentList.add(i.toObject(AppointmentTime::class.java)!!)
                    }
                    activity.callCreateCalendarRecyclerView(appointmentList)
                }
                .addOnFailureListener {
                    activity.hideProgressBar()
                }
    }

}