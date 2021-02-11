package eu.hybase.henger.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import eu.hybase.henger.R
import eu.hybase.henger.databinding.ActivitySignUpBinding
import eu.hybase.henger.firestore.FireStoresConnect
import eu.hybase.henger.models.User
import java.util.concurrent.TimeUnit

class SignUpActivity : BaseActivity() {

    lateinit var signUpBinding : ActivitySignUpBinding
    companion object {
        private const val TAG = "SIGNUPACTIVITY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        signUpBinding.signUpLoginButton.setOnClickListener {
            if (validateRegistrationData()) {
                registerUserOrLogin()
            }
        }

        signUpBinding.signUpLoginText.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }


    }



    private fun registerUserOrLogin() {
        var mail = signUpBinding.signUpEmailText.text.toString()
        var pass = signUpBinding.signUpPwd1.text.toString()
        var fullName = signUpBinding.signUpFullName.text.toString()
        var phone = signUpBinding.signUpPhoneNumber.text.toString()

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(mail,pass).addOnCompleteListener {
            if (it.isSuccessful) {
                val firebaseUser : FirebaseUser = it.result!!.user!!
                val registeredMail = firebaseUser.email
                val user = User(firebaseUser.uid,phone,fullName,registeredMail.toString())
                FireStoresConnect().registerUser(this,user)
            }
            else {
                Log.e(TAG, "registerUserOrLogin: ${it.exception}", )
                errorSnackBar("SIGN UP IS ERROR ${it.exception?.message}")
            }
        }
    }

    private fun validateRegistrationData() : Boolean {
        if (signUpBinding.signUpFullName.text!!.isEmpty()) {
            signUpBinding.signUpFullName.error = getString(R.string.full_name_required)
            errorSnackBar(getString(R.string.full_name_required))
            return false
        }
        if (signUpBinding.signUpPhoneNumber.text!!.isEmpty()) {
            signUpBinding.signUpPhoneNumber.error = getString(R.string.phone_number_required)
            errorSnackBar(getString(R.string.phone_number_required))
            return false
        }
        if (signUpBinding.signUpPwd1.text!!.isEmpty()) {
            signUpBinding.signUpPwd1.error = getString(R.string.password_required)
            errorSnackBar(getString(R.string.password_required))
            return false
        }
        if (signUpBinding.signUpPwd1.text!!.length < 6) {
            signUpBinding.signUpPwd1.error = getString(R.string.password_less_then_six)
            errorSnackBar(getString(R.string.password_less_then_six))
            return false
        }
        if (signUpBinding.signUpEmailText.text!!.isEmpty()) {
            signUpBinding.signUpEmailText.error = getString(R.string.email_required)
            errorSnackBar(getString(R.string.email_required))
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(signUpBinding.signUpEmailText.text).matches()) {
            signUpBinding.signUpEmailText.error = getString(R.string.email_not_valid)
            errorSnackBar(getString(R.string.email_not_valid))
            return false
        }
        return true
    }



    fun userRegisterSuccess() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
        Log.e(TAG, "userRegisterSuccess: REGISTER IS SUCCESS" )
    }
}