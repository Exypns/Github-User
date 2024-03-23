package com.example.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.data.response.FollowersResponseItem
import com.example.githubuser.data.response.FollowingResponseItem
import com.example.githubuser.databinding.FragmentFollowBinding
import com.example.githubuser.ui.ViewModel.FollowViewModel

class FollowFragment : Fragment() {
    private var position: Int? = null
    private var username: String? = null

    private lateinit var _followBinding: FragmentFollowBinding
    private val followBinding get() = _followBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _followBinding = FragmentFollowBinding.inflate(inflater, container, false)
        val view = followBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowViewModel::class.java)

        followViewModel.listFollowers.observe(viewLifecycleOwner) { followers ->
            setFollowers(followers)
        }

        followViewModel.listFollowing.observe(viewLifecycleOwner) { following ->
            setFollowing(following)
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        followBinding.rvFollow.layoutManager = LinearLayoutManager(requireActivity())

        if (position == 1){
            username?.let { followViewModel.findFollowers(it) }
        } else {
            username?.let { followViewModel.findFollowing(it) }
        }
    }

    private fun setFollowers(users: List<FollowersResponseItem>) {
        val adapter = ListFollowersAdapter()
        adapter.submitList(users)
        followBinding.rvFollow.adapter = adapter
    }

    private fun setFollowing(users: List<FollowingResponseItem>) {
        val adapter = ListFollowingAdapter()
        adapter.submitList(users)
        followBinding.rvFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            followBinding.progressBar.visibility = View.VISIBLE
        } else {
            followBinding.progressBar.visibility = View.GONE
        }
    }

    companion object {
        const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "username"
    }
}