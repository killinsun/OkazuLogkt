package com.killinsun.android.okazulogkt.screen.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.killinsun.android.okazulogkt.GoogleAuthController
import com.killinsun.android.okazulogkt.databinding.ActivitySignInBinding

class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels()
    private lateinit var binding: ActivitySignInBinding

    private val googleAuthController: GoogleAuthController by lazy {
        GoogleAuthController(activity as AppCompatActivity, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivitySignInBinding.inflate(layoutInflater)

        viewModel.onLogin()
        viewModel.user.observe(viewLifecycleOwner, Observer {
            navigateToOkazuLog(viewModel.user.value?.id, viewModel.user.value?.gId)
        })

        binding.signInButton.setOnClickListener { onLogin() }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleAuthController.onActivityResult(requestCode, resultCode, data)
    }

    fun navigateToOkazuLog( id: String?, gId: String?){
        if(id == null || gId == null) return
        Log.v("OkazuLog", "navigate ${id} ${gId}")
        findNavController().navigate(
            SignInFragmentDirections.actionSignInFragmentToOkazuLogFragment(
                id,
                gId
            )
        )
        Log.v("OkazuLog", "navigate end")
    }

    private fun onLogin(){
        googleAuthController.startSignIn{
            viewModel.onLogin()
        }
    }
}