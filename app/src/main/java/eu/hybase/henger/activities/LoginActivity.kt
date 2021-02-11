package eu.hybase.henger.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import eu.hybase.henger.R
import eu.hybase.henger.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.loginButton.setOnClickListener {
            if(validateRegistrationData()) {
                loginUser()
                showProgressBar()
            }
        }

    }

    private fun loginUser() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            loginBinding.loginEmailText.text.toString()
            ,loginBinding.loginUpPwd1.text.toString())
            .addOnSuccessListener {
                hideProgressBar()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                hideProgressBar()
                errorSnackBar(it.message.toString())
            }
    }




    private fun validateRegistrationData() : Boolean {
        if (loginBinding.loginEmailText.text!!.isEmpty()) {
            loginBinding.loginEmailText.error = getString(R.string.email_required)
            errorSnackBar(getString(R.string.email_required))
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(loginBinding.loginEmailText.text).matches()) {
            loginBinding.loginEmailText.error = getString(R.string.email_not_valid)
            errorSnackBar(getString(R.string.email_not_valid))
            return false
        }
        if (loginBinding.loginUpPwd1.text!!.isEmpty()) {
            loginBinding.loginUpPwd1.error = getString(R.string.password_required)
            errorSnackBar(getString(R.string.password_required))
            return false
        }
        return true
    }

}