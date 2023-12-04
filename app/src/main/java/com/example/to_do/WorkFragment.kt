package com.example.to_do

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.to_do.databinding.FragmentWorkBinding
import com.example.to_do.utile.BaseFragment

class WorkFragment : BaseFragment<FragmentWorkBinding>(FragmentWorkBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.next.setOnClickListener {
            findNavController().navigate(R.id.action_workFragment_to_assingWorkFragment)
        }
    }

}