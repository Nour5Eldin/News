package com.noureldin.news.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.noureldin.news.databinding.ActivityMainBinding
import com.noureldin.news.ui.categories.CategoriesFragment
import com.noureldin.news.ui.search.SearchFragment
import com.noureldin.news.ui.settings.SettingsFragment
import com.noureldin.news.util.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var binding: ActivityMainBinding

    var currentFragment = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        binding.navView.setNavigationItemSelectedListener(this)
        enableHamburgerButton()
        currentFragment = "categories"
        supportFragmentManager.beginTransaction().replace(
            com.noureldin.news.R.id.fragment_container,
            CategoriesFragment(),
            currentFragment
        ).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.noureldin.news.R.menu.main, menu)
        val searchItem = menu.findItem(com.noureldin.news.R.id.action_search)
        val searchView = searchItem.actionView as SearchView?

        searchItem.setOnMenuItemClickListener {
            navigateToSearchFragment()
            true
        }


        // Configure the searchView, such as adding a listener to handle search queries

        // Configure the searchView, such as adding a listener to handle search queries
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Handle search query submission here
                // Navigate to your Search Fragment and pass the query
                navigateToSearchFragment(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Handle query text changes here (if needed)
                return true
            }
        })
        return true
    }

    private fun navigateToSearchFragment(query: String? = null) {
        val searchFragment = SearchFragment()
        val bundle = Bundle()
        bundle.putString(Constants.QUERY, query)
        searchFragment.arguments = bundle
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(com.noureldin.news.R.id.fragment_container, searchFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun enableHamburgerButton() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            com.noureldin.news.R.string.navigation_drawer_open,
            com.noureldin.news.R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            com.noureldin.news.R.id.nav_categories -> {
                currentFragment = "categories"
                supportFragmentManager.beginTransaction().replace(
                    com.noureldin.news.R.id.fragment_container,
                    CategoriesFragment(),
                    currentFragment
                ).addToBackStack("").commit()

            }

            com.noureldin.news.R.id.nav_settings -> {
                currentFragment = "settings"
                supportFragmentManager.beginTransaction().replace(
                    com.noureldin.news.R.id.fragment_container,
                    SettingsFragment(),
                    currentFragment
                ).addToBackStack("").commit()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {

        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)

        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentFragment", currentFragment)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
//        Log.e("tt", savedInstanceState.getString("currentFragment") ?: "")
        when (savedInstanceState.getString("currentFragment")) {
            "categories" -> Toast.makeText(this, "cat", Toast.LENGTH_SHORT).show()
            "settings" -> {
                currentFragment = "settings"
                supportFragmentManager.beginTransaction().replace(
                    com.noureldin.news.R.id.fragment_container,
                    SettingsFragment(),
                    currentFragment
                ).addToBackStack("").commit()
            }
        }
    }

}