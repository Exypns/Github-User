package com.example.githubuser.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.databinding.ActivityDetailUserBinding
import com.example.githubuser.ui.ViewModel.DetailUserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var DetailUserBinding: ActivityDetailUserBinding

    companion object {
        const val USERNAME = "username"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }

    private val detailUserViewModel by viewModels<DetailUserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DetailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(DetailUserBinding.root)

        val username = intent.getStringExtra(USERNAME)

        if (username != null) {
                detailUserViewModel.findDetailUser(username)
        }

        detailUserViewModel.userDetail.observe(this) { user ->
                setUserDetailData(user)
        }

        detailUserViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        if (username != null) {
            sectionPagerAdapter.username = username
        }
        val viewPager: ViewPager2 = DetailUserBinding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    private fun setUserDetailData(user: DetailUserResponse) {
            DetailUserBinding.tvUsername.text = user.login
            DetailUserBinding.tvName.text = user.name
            DetailUserBinding.tvFollowers.text = "${user.followers.toString()} Followers"
            DetailUserBinding.tvFollowing.text = "${user.following.toString()} Following"
            Glide.with(this@DetailUserActivity).load(user.avatarUrl).circleCrop().into(DetailUserBinding.imgPhoto)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            DetailUserBinding.progressBar.visibility = View.VISIBLE
        } else {
            DetailUserBinding.progressBar.visibility = View.GONE
        }
    }
}