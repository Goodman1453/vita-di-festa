package com.example.goodman.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : Activity() {

    private lateinit var signUp : TextView
    private lateinit var forgotPassword : TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var loginBtn: Button


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
            var intent = Intent(applicationContext, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        signUp.setOnClickListener {
            var intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateUI(user: FirebaseUser?) {

        if(user != null){
            intent = Intent(applicationContext, LandingActivity::class.java)
            startActivity(intent)
        }else {
            Toast.makeText(baseContext, user.toString(),
                    Toast.LENGTH_SHORT).show()
        }
    }

    private fun attemptLogin() {


        email.error = null
        password.error = null

        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

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
                            Toast.makeText(baseContext, task.exception!!.message,
                                    Toast.LENGTH_SHORT).show()
                        }
            }
        }

        }


    private fun isEmailValid(email: String): Boolean {
        return email.contains("@")
    }
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 4
    }


}
