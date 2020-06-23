package com.killinsun.android.okazulogkt.screen.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.killinsun.android.okazulogkt.GoogleAuthController
import com.killinsun.android.okazulogkt.R
import com.killinsun.android.okazulogkt.databinding.ActivitySignInBinding

class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels()
    private lateinit var binding: ActivitySignInBinding

    private val googleAuthController: GoogleAuthController by lazy {
        GoogleAuthController(activity as AppCompatActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySignInBinding.inflate(layoutInflater)

        if(viewModel.isLogin) {
            navigateToOkazuLog()
        }

        binding.signInButton.setOnClickListener { onLogin() }
        binding.signOutButton.setOnClickListener { onLogout() }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun navigateToOkazuLog(){
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToOkazuLogFragment()
        )
    }

    private fun onLogin(){
        googleAuthController.startSignIn{
            viewModel.onLogin()
            Log.i("SignInFragment", "Logged In! email: " + viewModel.userEmail.value)
            navigateToOkazuLog()

        }
    }

    private fun onLogout() {
        googleAuthController.startSignOut()
    }

}