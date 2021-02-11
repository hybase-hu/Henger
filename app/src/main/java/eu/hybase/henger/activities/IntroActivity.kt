package eu.hybase.henger.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import eu.hybase.henger.R
import eu.hybase.henger.firestore.FireStoresConnect

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        Handler().postDelayed({
            if (FireStoresConnect().getCurrentUserId().isNullOrEmpty()) {
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            }
            else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        },2000)
    }
}