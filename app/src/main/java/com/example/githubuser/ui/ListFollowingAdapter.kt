package com.example.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.response.FollowingResponseItem
import com.example.githubuser.databinding.ItemRowUserBinding

class ListFollowingAdapter : ListAdapter<FollowingResponseItem, ListFollowingAdapter.MyViewHolder>(
    DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class MyViewHolder(val binding: ItemRowUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FollowingResponseItem) {
            binding.tvUsername.text = "${user.login}"
            Glide.with(binding.imgItemPhoto.context).load(user.avatarUrl).circleCrop().into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowingResponseItem>() {
            override fun areItemsTheSame(oldItem: FollowingResponseItem, newItem: FollowingResponseItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: FollowingResponseItem, newItem: FollowingResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}