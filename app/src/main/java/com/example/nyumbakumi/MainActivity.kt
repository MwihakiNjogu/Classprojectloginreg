package com.example.nyumbakumi

import android.app.ProgressDialog
import android.content.Intent
import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var edtEmail: EditText
    lateinit var edtPassword: EditText
    lateinit var btnRegister: Button
    lateinit var tvLogin: TextView
    lateinit var progress: ProgressDialog
    lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtEmail=findViewById(R.id.mEdtEmail)
        edtPassword=findViewById(R.id.mEdtPassword)
        btnRegister=findViewById(R.id.mBtnReg)
        tvLogin=findViewById(R.id.mTvLogin)
        mAuth = FirebaseAuth.getInstance()
        progress= ProgressDialog(this,)
        progress.setTitle("Loading")
        progress.setMessage("Please wait...")

        btnRegister.setOnClickListener {
        //Start  by receiving data from user
            var email= edtEmail.text.toString().trim()
            var password= edtPassword.text.toString().trim()

            //check if user is submiting empty fields
            if (email.isEmpty()){
                edtEmail.setError("Please fill this input")
                edtEmail.requestFocus()
            }else if (password.isEmpty()){
                edtPassword.setError("Please fill this input")
                edtPassword.requestFocus()
            }else if(password.length<6){
                edtPassword.setError("Password is too short")
                edtPassword.requestFocus()

            }else{
                //Proceed to register user
                progress.show()
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    progress.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                        mAuth.signOut()
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish()
                    }else{
                        displayMessage("Error",it.exception!!.message.toString())
                    }
                }
            }
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }

    fun displayMessage(title:String, message: String){
        var alertDialog=AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Ok", null)
        alertDialog.create().show()
    }
}