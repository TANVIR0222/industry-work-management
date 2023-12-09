package com.example.to_do.utile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<vB : ViewBinding>(
    private val bindingInflater: (inflater: LayoutInflater) -> vB


) : Fragment() {

    private var _binding: vB? = null

    val binding: vB
        get() = _binding as vB

//    lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = bindingInflater.invoke(inflater)

//        database = FirebaseDatabase.getInstance().reference

        return binding.root

    }

}
