package com.example.githubuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.response.UsersItem
import com.example.githubuser.databinding.ItemRowUserBinding

class ListUserAdapter() : ListAdapter<UsersItem, ListUserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UsersItem) {
            binding.tvUsername.text = "${user.login}"
            Glide.with(binding.imgItemPhoto.context).load(user.avatarUrl).circleCrop().into(binding.imgItemPhoto)
            binding.root.setOnClickListener {
                val intentDetailActivity = Intent(binding.root.context,DetailUserActivity::class.java)
                intentDetailActivity.putExtra(DetailUserActivity.USERNAME, user.login)
                binding.root.context.startActivity(intentDetailActivity)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UsersItem>() {
            override fun areItemsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

