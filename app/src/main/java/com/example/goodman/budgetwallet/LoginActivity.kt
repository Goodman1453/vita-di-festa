package com.example.goodman.budgetwallet

import android.app.LoaderManager.LoaderCallbacks
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.TextView

import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : Activity() {

    private lateinit var signUp : TextView
    private lateinit var forgotPassword : TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var loginBtn: Button

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
//    private var mAuthTask: UserLoginTask? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_login)
        signUp = sign_up
        forgotPassword = forgot_password
        loginBtn = login_button
        firebaseAuth = FirebaseAuth.getInstance()

        loginBtn.setOnClickListener {
            attemptLogin()
        }

        forgotPassword.setOnClickListener {
            var intent = Intent(applicationContext, com.example.goodman.budgetwallet.ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        signUp.setOnClickListener {
            var intent = Intent(applicationContext, com.example.goodman.budgetwallet.SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        Toast.makeText(baseContext, user.toString(),
                Toast.LENGTH_SHORT).show()
    }

    private fun attemptLogin() {
        if (firebaseAuth.currentUser != null) {
            firebaseAuth.signOut()
        }

        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {
            firebaseAuth.signInWithEmailAndPassword(emailStr,passwordStr)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            val user = firebaseAuth.currentUser
                            updateUI(user)
                        }else{

                            Toast.makeText(baseContext, task.toString(),
                                    Toast.LENGTH_SHORT).show()                    }
            }
        }

        }

//
    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.contains("@")
    }
//
    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }


}
