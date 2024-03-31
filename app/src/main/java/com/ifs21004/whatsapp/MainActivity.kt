package com.ifs21004.whatsapp

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ifs21004.whatsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
    }

    private fun setupView() {
        binding.navView.setCheckedItem(R.id.nav_kotakmasuk)
        binding.inAppBar.toolbar.overflowIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_more_vert)
        loadFragment(FLAG_FRAGMENT_HOME)
    }
    private fun setupAction() {
        binding.inAppBar.toolbar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.inAppBar.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.profile -> {
                    loadFragment(FLAG_FRAGMENT_HOME, "Memilih menu Profile!")
                    true
                }
                R.id.action_settings -> {
                    loadFragment(FLAG_FRAGMENT_HOME, "Memilih menu Settings!")
                    true
                }
                R.id.action_logout -> {
                    loadFragment(FLAG_FRAGMENT_HOME, "Memilih menu Logout!")
                    true
                }
                else -> true
            }
        }
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_kotakmasuk -> {
                    loadFragment(FLAG_FRAGMENT_HOME)
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_utama -> {
                    loadFragment(FLAG_FRAGMENT_HOME, "Memilih menu Utama!")
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_promosi -> {
                    loadFragment(FLAG_FRAGMENT_HOME, "Memilih menu Promosi!")
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_sosial -> {
                    loadFragment(FLAG_FRAGMENT_HOME, "Memilih menu Sosial!")
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_star -> {
                    loadFragment(FLAG_FRAGMENT_HOME, "Memilih menu Berbintang!")
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> true
            }
        }
        binding.inAppBar.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_email -> {
                    loadFragment(FLAG_FRAGMENT_HOME)
                    true
                }

                R.id.navigation_meet -> {
                    loadFragment(FLAG_FRAGMENT_MEET)
                    true
                }
                else -> true
            }
        }
    }
    private fun loadFragment(flag: String, message: String? = null) {
        val fragmentManager = supportFragmentManager
        val fragmentContainerId =
            binding.inAppBar.inContentMain.frameContainer.id
        when (flag) {
            FLAG_FRAGMENT_HOME -> {
                binding.inAppBar.bottomNavView
                    .menu
                    .findItem(R.id.navigation_email)
                    .setChecked(true)
                val homeFragment = HomeFragment()
                val bundle = Bundle().apply {
                    this.putString(
                        HomeFragment.EXTRA_MESSAGE,
                        message ?: "Belum ada menu yang dipilih!"
                    )
                }
                homeFragment.arguments = bundle
                fragmentManager
                    .beginTransaction()
                    .replace(
                        fragmentContainerId,
                        homeFragment,
                        homeFragment::class.java.simpleName
                    )
                    .commit()
            }
            FLAG_FRAGMENT_MEET -> {
                val meetFragment = MeetFragment()
                val fragment = fragmentManager
                    .findFragmentByTag(MeetFragment::class.java.simpleName)
                if (fragment !is MeetFragment) {
                    fragmentManager
                        .beginTransaction()
                        .replace(
                            fragmentContainerId,
                            meetFragment,
                            MeetFragment::class.java.simpleName
                        )
                        .commit()
                }
            }
        }
    }
    companion object {
        const val FLAG_FRAGMENT_HOME = "home_fragment"
        const val FLAG_FRAGMENT_MEET = "fragment_meet"
    }
}