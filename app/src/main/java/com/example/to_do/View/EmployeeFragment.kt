package com.example.to_do.View

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.to_do.R
import com.example.to_do.Registration.SingInActivity
import com.example.to_do.adapter.EmployeeAdapter
import com.example.to_do.databinding.FragmentEmployeeBinding
import com.example.to_do.utile.Users
import com.example.to_do.utile.utile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EmployeeFragment : Fragment(){

    lateinit var binding: FragmentEmployeeBinding

      private lateinit var employeeAdapter: EmployeeAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentEmployeeBinding.inflate(layoutInflater)

        binding.apply {
            tolber.setOnMenuItemClickListener{

                when(it.itemId){
                    R.id.logOut_emp -> {
                        showLogOutDialog()
                        true
                    }
                    else -> false
                }
            }
        }

        seeEmployee()

        showAllEmployee()
        return binding.root
    }

    private fun showAllEmployee() {
        utile.showDialog(requireContext())
        FirebaseDatabase.getInstance()
            .getReference("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val employeeList = arrayListOf<Users>()
                for (sn in snapshot.children){
                    val currentEmployee = sn.getValue(Users::class.java)
                    if (currentEmployee?.userType =="Employee"){
                        employeeList.add(currentEmployee)

                    }

                }
                employeeAdapter.differ.submitList(employeeList)
                utile.hideDialog()
            }

            override fun onCancelled(error: DatabaseError) {
                utile.apply {
                    hideDialog()
                    showToast(requireContext(), error.message)
                }
            }
        })
    }

    private fun seeEmployee() {
        employeeAdapter = EmployeeAdapter()
        binding.Rcv.apply {

            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            adapter = employeeAdapter

        }
    }


    private fun showLogOutDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val alertDialog = builder.show()
        builder.setTitle("Log Out")
            .setMessage("Your are sure you want to Logout ?")
            .setPositiveButton("yes"){ _,_->// lemda

                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(requireContext(), SingInActivity::class.java))
                findNavController().popBackStack()

            }
            .setNegativeButton("No"){ _,_->
                alertDialog.dismiss()

            }

            .show()
            .setCancelable(false)

    }


}