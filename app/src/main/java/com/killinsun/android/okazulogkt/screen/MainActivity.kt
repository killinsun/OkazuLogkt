package com.killinsun.android.okazulogkt.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.killinsun.android.okazulogkt.GoogleAuthController
import com.killinsun.android.okazulogkt.R
import com.killinsun.android.okazulogkt.databinding.ActivityMainBinding
import com.killinsun.android.okazulogkt.screen.signin.SignInFragment
import com.killinsun.android.okazulogkt.screen.signin.SignInFragmentDirections
import com.killinsun.android.okazulogkt.screen.signin.SignInViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        val navController = this.findNavController(R.id.myNavHostFragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.okazuLogFragment), binding.drawerLayout)
        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.v("MainActivity", "${item}")
        val navController = this.findNavController(R.id.myNavHostFragment)
        return item.onNavDestinationSelected(navController) ||
                super.onOptionsItemSelected(item)
    }
}
