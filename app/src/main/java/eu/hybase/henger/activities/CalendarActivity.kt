package eu.hybase.henger.activities

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import eu.hybase.henger.adapters.CalendarAdapters
import eu.hybase.henger.constants.Constants
import eu.hybase.henger.databinding.ActivityCalendarBinding
import eu.hybase.henger.firestore.FireStoresConnect
import eu.hybase.henger.models.AppointmentTime
import kotlin.collections.ArrayList

class CalendarActivity : BaseActivity() {

    private lateinit var adapters : CalendarAdapters
    private lateinit var calendarBinding: ActivityCalendarBinding
    private var mSelectOptionalDay : Constants.OptionalDays = Constants.OptionalDays.TODAY
    private var mItemPosition = -1

    companion object {
        private const val TAG = "CalendarActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        calendarBinding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(calendarBinding.root)

        FireStoresConnect().getBookingDatedUsers(this,Constants.OptionalDays.TODAY)
        settingRadioGroupClickListener()


    }


    private fun alreadyOkAndSAVING() {
        var bookingDate = System.currentTimeMillis()
        if (mSelectOptionalDay == Constants.OptionalDays.TOMORROW)
            bookingDate += Constants.ONE_DAY_IN_LONG
        if (mSelectOptionalDay == Constants.OptionalDays.AFTER_TOMORROW)
            bookingDate += (Constants.ONE_DAY_IN_LONG) * 2

        FireStoresConnect().addAppointment(
                this,
                AppointmentTime(
                        appointment_user_id = FireStoresConnect().getCurrentUserId(),
                        appointment_date = bookingDate,
                        appointment_period = mItemPosition)
        )
        FireStoresConnect().getBookingDatedUsers(this,mSelectOptionalDay)
    }

    private fun showSelectDateDialog(position: Int) {
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle("Saving gym date")
        dialog.setMessage("Save your gym date to ${Constants.GYM_PERIOD[position]}")
        dialog.setPositiveButton("Save") { dialog, _ ->
            alreadyOkAndSAVING()
            dialog.dismiss()
        }
        dialog.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        dialog.show()
    }

    private fun saveUserGymTime(position: Int) {
        mItemPosition = position
        FireStoresConnect().checkBookedThisDayAlready(this,mSelectOptionalDay)

    }

    private fun settingRadioGroupClickListener() {

        calendarBinding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (calendarBinding.radioButtonToday.isChecked) {
                FireStoresConnect().getBookingDatedUsers(this,Constants.OptionalDays.TODAY)
                mSelectOptionalDay = Constants.OptionalDays.TODAY
            }
            if (calendarBinding.radioButtonTomorrow.isChecked) {
                FireStoresConnect().getBookingDatedUsers(this,Constants.OptionalDays.TOMORROW)
                mSelectOptionalDay = Constants.OptionalDays.TOMORROW
            }
            if (calendarBinding.radioButtonAfterTomorrow.isChecked) {
                FireStoresConnect().getBookingDatedUsers(this,Constants.OptionalDays.AFTER_TOMORROW)
                mSelectOptionalDay = Constants.OptionalDays.AFTER_TOMORROW
            }
        }
    }





    fun callCreateCalendarRecyclerView(list: ArrayList<AppointmentTime>) {
        adapters = CalendarAdapters(context = this, list)
        adapters.setOnClickListener(object : CalendarAdapters.OnClickListener{
            override fun onClick(position: Int) {
                saveUserGymTime(position)
            }
        })
        calendarBinding.calendarRecyclerView.layoutManager = LinearLayoutManager(this)
        calendarBinding.calendarRecyclerView.adapter = adapters

    }

    fun isSaveIsEnabled() {
        showSelectDateDialog(mItemPosition)
    }

    fun isSaveIsNotEnabled() {
        errorSnackBar("Your Booking date this time already!")
        Log.e(TAG, "isSaveIsNotEnabled: NOT ENABLED SAVING", )
    }
}