package com.example.to_do.View

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.to_do.R
import com.example.to_do.databinding.FragmentWorkBinding
import com.example.to_do.utile.BaseFragment

class WorkFragment : BaseFragment<FragmentWorkBinding>(FragmentWorkBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.next.setOnClickListener {
//            findNavController().navigate(R.id.action_workFragment_to_assingWorkFragment)
//        }
    }

}