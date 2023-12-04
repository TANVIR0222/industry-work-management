package com.example.to_do.Registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.example.to_do.BossMainActivity
import com.example.to_do.EmployeeMainActivity
import com.example.to_do.R
import com.example.to_do.utile.Users
import com.example.to_do.utile.utile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({

            val currentUser = FirebaseAuth.getInstance().currentUser?.uid

            if (currentUser != null){

                lifecycleScope.launch {

                    try {

                        FirebaseDatabase.getInstance().getReference("Users").child(currentUser).addValueEventListener(object :
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val currentUserData = snapshot.getValue(Users::class.java)
                                when (currentUserData?.userType){
                                    "Boss" ->{
                                        // boss layout

                                        startActivity(Intent(this@SplashActivity , BossMainActivity::class.java))
                                        finish()


                                    }
                                    "Employee" ->{
                                        // Employee layout
                                        startActivity(Intent(this@SplashActivity , EmployeeMainActivity::class.java))
                                        finish()


                                    }
                                    else ->  {
                                        // Employee layout

                                        startActivity(Intent(this@SplashActivity , EmployeeMainActivity::class.java))
                                        finish()
                                    }

                                }

                            }

                            override fun onCancelled(error: DatabaseError) {
                                utile.hideDialog()
                                utile.showToast(this@SplashActivity , error.message)
                            }
                        })


                    }
                    catch (e:Exception){
                        utile.showToast(this@SplashActivity , e.message!!)

                    }
                }

            }else{
                startActivity(Intent(this , SingInActivity::class.java))
                finish()
            }


        },2000)
    }
}