package eu.hybase.henger.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import eu.hybase.henger.R
import eu.hybase.henger.constants.Constants
import eu.hybase.henger.databinding.ItemCalendarDayBinding
import eu.hybase.henger.models.AppointmentTime

class CalendarAdapters(private val context: Context,private val list: ArrayList<AppointmentTime>) :
 RecyclerView.Adapter<CalendarAdapters.ViewHolder>() {


    private var mOnClickListener : OnClickListener? = null

    companion object {
        private const val TAG = "CalendarAdapters"
    }

    interface OnClickListener {
        public fun onClick(position : Int)
    }

    inner class ViewHolder(itemView: ItemCalendarDayBinding) : RecyclerView.ViewHolder(itemView.root) {
        val progressBar = itemView.itemCalendarProgressBar
        val hour = itemView.itemCalendarHour
        val cardView = itemView.itemCalendarCardView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemCalendarDayBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getMembersNumberThisTime(position) <= Constants.MAX_MEMBERS) {
            //** cardView onclick
            holder.cardView.setOnClickListener { mOnClickListener?.onClick(position) }
        }
        else {
            holder.hour.setTextColor(context.resources.getColor(R.color.red))
        }

        holder.progressBar.max = Constants.MAX_MEMBERS
        holder.progressBar.progress = getMembersNumberThisTime(position)
        holder.hour.text = Constants.GYM_PERIOD[position]
    }

    fun getMembersNumberThisTime(timePeriod : Int) : Int {
        var membersSum = 0
        for (current in list) {
            if (current.appointment_period == timePeriod) {
                membersSum++
            }
        }
        Log.d(TAG, "getMembersThisTime: membersSUm: $membersSum")
        return membersSum
    }

    fun setOnClickListener(listener : OnClickListener) {
        this.mOnClickListener = listener
    }

    /**
     * nyitás 7:30, másfél órás turnusok
     * zárás 19:00, másfél óránként 7,5 kör fér bele!?
     */
    override fun getItemCount(): Int {
        return Constants.ONE_OF_PERIOD_OF_DAY
    }


}