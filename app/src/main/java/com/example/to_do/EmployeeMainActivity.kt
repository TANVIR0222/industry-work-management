package com.example.to_do

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.to_do.Registration.SingInActivity
import com.example.to_do.databinding.ActivityEmployeeMainBinding
import com.google.firebase.auth.FirebaseAuth

class EmployeeMainActivity : AppCompatActivity() {
    lateinit var binding : ActivityEmployeeMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityEmployeeMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)

        setContentView(binding.root)


        binding.tolber.setOnMenuItemClickListener{

            when(it.itemId){
                R.id.logOut_emp-> {
                    showLogOutDialog()
                    true
                }
                else -> false
            }
        }


    }

    private fun showLogOutDialog() {
        val builder = AlertDialog.Builder(this)
        val alertDialog = builder.show()
        builder.setTitle("Log Out")
            .setMessage("Your are sure you want to Logout ?")
            .setPositiveButton("yes"){ _,_->// lemda

                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this , SingInActivity::class.java))
                finish()

            }
            .setNegativeButton("No"){ _,_->
                alertDialog.dismiss()

            }

            .show()
            .setCancelable(false)

    }
}