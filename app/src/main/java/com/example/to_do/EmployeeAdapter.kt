package com.example.to_do

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.to_do.databinding.ItemRcvBinding
import com.example.to_do.utile.Users

class EmployeeAdapter(var postList: List<Users>) :RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> (){

    class  EmployeeViewHolder(val binding: ItemRcvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        return EmployeeViewHolder(ItemRcvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val post= postList[position]

        holder.binding.apply {
            Glide.with(holder.itemView).load(post.userImage).into(profileImage)
            nameEmployee.text = post.userName
        }



    }
}