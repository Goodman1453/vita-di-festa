package com.example.goodman.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_sign_up.*

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var nameAndSurnameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var submitBtn: Button
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText


      override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
          emailEditText = sign_up_email
          submitBtn = sign_up_button
          nameAndSurnameEditText = name_and_surname
          passwordEditText = sign_up_password
          confirmPasswordEditText = confirm_password

          passwordEditText.visibility = View.GONE
          confirmPasswordEditText.visibility = View.GONE
          submitBtn.text = getString(R.string.submit)

          submitBtn.setOnClickListener {
              Log.d("SubmitBtn", "email submitted")
          }

      }
}
