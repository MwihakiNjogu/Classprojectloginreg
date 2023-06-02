package com.example.nyumbakumi

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var edtEmail: EditText
    lateinit var edtPassword:EditText
    lateinit var btnLogin:Button
    lateinit var tvRegister: TextView
    lateinit var tvReset :TextView
    lateinit var progress: ProgressDialog
    lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        edtEmail = findViewById(R.id.mEdtEmail)
        edtPassword = findViewById(R.id.mEdtPassword)
        btnLogin = findViewById(R.id.mBtnLogin)
        tvRegister = findViewById(R.id.mTvRegister)
        tvReset= findViewById(R.id.mTvReset)
        mAuth = FirebaseAuth.getInstance()
        progress= ProgressDialog(this,)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")
        btnLogin.setOnClickListener {
            //Start by receiving data from the user
            var email= edtEmail.text.toString().trim()
            var password= edtPassword.text.toString().trim()

            //check if user is submitting empty fields
            if (email.isEmpty()){
                edtEmail.setError("Please fill this input")
                edtEmail.requestFocus()
            }else if (password.isEmpty()){
                edtPassword.setError("Please fill this input")
                edtPassword.requestFocus()
            }else{
                //Proceed to Login
                progress.show()
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                    progress.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }else{
                        displayMessage("Error",it.exception!!.message.toString())
                    }
                }

            }
        }
        tvRegister.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        tvReset.setOnClickListener {

        }
    }

    fun displayMessage(title:String, message: String){
        var alertDialog= AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Ok", null)
        alertDialog.create().show()
    }
}