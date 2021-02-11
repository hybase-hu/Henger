package eu.hybase.henger.activities

import android.app.Dialog
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import com.google.android.material.snackbar.Snackbar
import eu.hybase.henger.R
import eu.hybase.henger.databinding.ActivityBaseBinding

open class BaseActivity : AppCompatActivity() {

    lateinit var baseBinding : ActivityBaseBinding
    lateinit var mProgressDialog : Dialog
    lateinit var view : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        baseBinding = ActivityBaseBinding.inflate(layoutInflater)
        view = baseBinding.root
        setContentView(view)
    }


    fun errorSnackBar(error : String) {
        Snackbar.make(findViewById(android.R.id.content),error,Snackbar.LENGTH_LONG).show()
    }


    fun showProgressBar() {
        if (!this::mProgressDialog.isInitialized) {
            mProgressDialog = Dialog(this, android.R.style.Theme_Black)
            mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            mProgressDialog.setContentView(view)
            mProgressDialog.getWindow()?.setBackgroundDrawableResource(
                R.color.darkTransparent
            )
        }
        if (!mProgressDialog.isShowing) {
            mProgressDialog.show()
        }

    }

    fun hideProgressBar() {
        if (this::mProgressDialog.isInitialized) {
            if (mProgressDialog.isShowing) {
                mProgressDialog.hide()
            }
        }
    }



}