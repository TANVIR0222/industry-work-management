package com.example.to_do

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.to_do.Registration.SingInActivity
import com.example.to_do.databinding.FragmentEmployeeBinding
import com.example.to_do.utile.BaseFragment
import com.google.firebase.auth.FirebaseAuth

class EmployeeFragment : BaseFragment <FragmentEmployeeBinding> (FragmentEmployeeBinding::inflate){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.next.setOnClickListener {
            findNavController().navigate(R.id.action_employeeFragment_to_workFragment)
        }

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
        val builder = AlertDialog.Builder(requireContext())
        val alertDialog = builder.show()
        builder.setTitle("Log Out")
            .setMessage("Your are sure you want to Logout ?")
            .setPositiveButton("yes"){ _,_->// lemda

                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(requireContext() , SingInActivity::class.java))
                findNavController().popBackStack()

            }
            .setNegativeButton("No"){ _,_->
                alertDialog.dismiss()

            }

            .show()
            .setCancelable(false)


    }


}