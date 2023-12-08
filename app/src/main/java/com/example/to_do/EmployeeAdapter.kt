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

class EmployeeAdapter  :RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> (){
    class  EmployeeViewHolder(val binding: ItemRcvBinding):ViewHolder(binding.root)

    val diffUtil  = object :DiffUtil.ItemCallback<Users>(){
        override fun areItemsTheSame(oldItem: Users, newItem: Users): Boolean {

            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(oldItem: Users, newItem: Users): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer (this,diffUtil)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {

        return EmployeeViewHolder(ItemRcvBinding.inflate(LayoutInflater.from(parent.context),parent,false))


    }

    override fun getItemCount(): Int {

        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val currentData = differ.currentList[position]
        holder.binding.apply {
            Glide.with(holder.itemView).load(currentData.userImage).into(profileImage)
            nameEmployee.text=currentData.userName
        }

    }
}