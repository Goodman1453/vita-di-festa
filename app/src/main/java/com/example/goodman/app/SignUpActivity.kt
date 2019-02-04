package com.example.goodman.app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.widget.Toast
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import android.support.v4.app.FragmentActivity
import com.google.android.gms.tasks.OnCompleteListener



class SignUpActivity : AppCompatActivity() {
    private lateinit var usernameAndSurname: TextView
    private lateinit var signUpbtn: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var verifyButton: Button
    private lateinit var verifyEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        firebaseAuth = FirebaseAuth.getInstance()
        signUpbtn = sign_up_button
        verifyButton = verification_button
        verifyEmail = sign_up_email_verification
        signUpbtn.setOnClickListener {
            attemptSignUp()
        }

        verifyButton.setOnClickListener {
        attemptSignUp()
        }
    }

    private fun attemptSignUp() {

        sign_up_email_verification.error = null
        sign_up_password.error = null

        // Store values at the time of the login attempt.
        val emailStr = sign_up_email_verification.text.toString()
        val passwordStr = sign_up_password.text.toString()
        val confirmPasswordStr = confirm_password.text.toString()

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
            sign_up_email_verification.error = getString(R.string.error_field_required)
            focusView = sign_up_email_verification
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            sign_up_email_verification.error = getString(R.string.error_invalid_email)
            focusView = sign_up_email_verification
            cancel = true
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {
            firebaseAuth.createUserWithEmailAndPassword(emailStr, passwordStr)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            val user = firebaseAuth.currentUser
                            updateUI(user)
                        }
                    }
        }

    }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.contains("@")
    }
    //
    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4 && sign_up_password.text.toString() == confirm_password.text.toString()
    }

    private fun updateUI(user: FirebaseUser?) {
        Log.d("x", "trial1")
    }
}
