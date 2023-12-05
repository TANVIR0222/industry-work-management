package com.example.to_do

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.to_do.Registration.SingInActivity
import com.example.to_do.databinding.FragmentEmployeeBinding
import com.example.to_do.utile.BaseFragment
import com.example.to_do.utile.Users
import com.example.to_do.utile.utile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EmployeeFragment :Fragment(){

    lateinit var binding: FragmentEmployeeBinding

    //  lateinit var adapter: EmployeeAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentEmployeeBinding.inflate(layoutInflater)

        binding.apply {
            tolber.setOnMenuItemClickListener{

                when(it.itemId){
                    R.id.logOut_emp-> {
                        showLogOutDialog()
                        true
                    }
                    else -> false
                }
            }
        }



//        setData()

        return binding.root
    }

//    private fun setData() {
//        FirebaseDatabase.getInstance().getReference("User").addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                val empList = arrayListOf<Users>()
//
//                for (sn in snapshot.children) {
//
//                    val current = sn.getValue(Users::class.java)
//
//                    if (current?.userType == "Employee"){
//
//                        empList.add(current)
//                    }
//                }
//
//                adapter = EmployeeAdapter(empList)
//                binding.Rcv.adapter = adapter
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//
//    }


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

