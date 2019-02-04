package com.example.goodman.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var nameAndSurnameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var submitBtn: Button
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var emailVerification: LinearLayout
    private lateinit var signUpContainer: LinearLayout
    private lateinit var firebaseAuth: FirebaseAuth


      override fun onCreate(savedInstanceState: Bundle?) {
          super.onCreate(savedInstanceState)
          setContentView(R.layout.activity_sign_up)
          emailEditText = sign_up_email
          submitBtn = sign_up_button
          nameAndSurnameEditText = name_and_surname
          passwordEditText = sign_up_password
          confirmPasswordEditText = confirm_password
          emailVerification = email_verification
          signUpContainer = sign_up_container

          firebaseAuth = FirebaseAuth.getInstance()

          passwordEditText.visibility = View.GONE
          emailVerification.visibility = View.GONE
          signUpContainer.visibility = View.VISIBLE

          confirmPasswordEditText.visibility = View.GONE
          submitBtn.text = getString(R.string.submit)

          submitBtn.setOnClickListener {
              Log.d("SubmitBtn", "email submitted")
              var user = firebaseAuth.currentUser
              firebaseAuth.sendPasswordResetEmail(emailEditText.text.toString()).addOnCompleteListener { task ->
                  if (task.isSuccessful) {
                      val user = firebaseAuth.currentUser
                  }
              }


          }
      }
}
