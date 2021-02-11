package eu.hybase.henger.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import eu.hybase.henger.R
import eu.hybase.henger.databinding.ActivityMainBinding
import eu.hybase.henger.firestore.FireStoresConnect

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var mainBinding : ActivityMainBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        mainBinding.mainActivityBookingButton.setOnClickListener {
            startActivity(Intent(this,CalendarActivity::class.java))
        }

        mainBinding.mainLogOutText.setOnClickListener {
            userLogOut()
        }


    }

    private fun userLogOut() {
        FireStoresConnect().logOutUser(this)
    }
}