package com.example.to_do.Registration

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import com.example.to_do.BossMainActivity
import com.example.to_do.EmployeeMainActivity
import com.example.to_do.databinding.ActivitySingInBinding
import com.example.to_do.databinding.ForgetPasswordDialogBinding
import com.example.to_do.utile.Users
import com.example.to_do.utile.utile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SingInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySingInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySingInBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            val email = binding.email.editText?.text.toString().trim()
            val password = binding.password.editText?.text.toString().trim()

            singInUser(email , password)
        }

        binding.tsing.setOnClickListener {

            startActivity(Intent(this,SingUpActivity::class.java))

        }

        // forget password dialog
        binding.forgetPass.setOnClickListener {

//            startActivity(Intent(this , test::class.java))
            showForgetpasswordDialog()
        }




    }
    // forget password dialog

    private fun showForgetpasswordDialog() {

        val dialog = ForgetPasswordDialogBinding.inflate(LayoutInflater.from(this@SingInActivity))
        val alertDialog = AlertDialog.Builder(this@SingInActivity)
            .setView(dialog.root)
            .create()
            alertDialog.show()
        dialog.Femail.requestFocus()

        dialog.FP.setOnClickListener {

            val email = dialog.Femail.editText?.text.toString().trim()
            alertDialog.dismiss()
            resetPassword(email)

        }

    }

    // forget password dialog

    private fun resetPassword(email: String) {

        lifecycleScope.launch {

            try {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
                utile.showToast(this@SingInActivity , "Please check your email and reset the password")

            }

            catch (e:Exception){
                utile.showToast(this@SingInActivity ,e.message.toString())

            }

        }


    }

    private fun singInUser(email: String, password: String) {

        utile.showDialog(this)
        val firebaseAuth = FirebaseAuth.getInstance()

        lifecycleScope.launch {

            try {

                val authResult = firebaseAuth.signInWithEmailAndPassword(email ,password).await()
                val currentUser = authResult.user?.uid          // get user uid

                // verify Email

                val verifyEmail = firebaseAuth.currentUser?.isEmailVerified
                if (verifyEmail == true){

                    if(currentUser != null){
                        // user . uid

                        FirebaseDatabase.getInstance().getReference("Users").child(currentUser).addValueEventListener(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val currentUserData = snapshot.getValue(Users::class.java)
                                when (currentUserData?.userType){
                                    "Boss" ->{
                                        // boss layout

                                        startActivity(Intent(this@SingInActivity , BossMainActivity::class.java))
                                        finish()


                                    }
                                    "Employee" ->{
                                        // Employee layout
                                        startActivity(Intent(this@SingInActivity , EmployeeMainActivity::class.java))
                                        finish()


                                    }
                                    else ->  {
                                        // Employee layout

                                        startActivity(Intent(this@SingInActivity , EmployeeMainActivity::class.java))
                                        finish()
                                    }

                                }

                            }

                            override fun onCancelled(error: DatabaseError) {
                                utile.hideDialog()
                                utile.showToast(this@SingInActivity , error.message)
                            }
                        })

                    }

                    else{
                        utile.hideDialog()
                        utile.showToast(this@SingInActivity , " User not found Please singUp first ")

                    }

                }
                else{
                    utile.hideDialog()
                    utile.showToast(this@SingInActivity , " Email is not verified ")

                }

            }
            catch (e:Exception){

                utile.hideDialog()
                utile.showToast(this@SingInActivity , e.message!!)

            }
        }

    }
}