package com.killinsun.android.okazulogkt.screen.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.killinsun.android.okazulogkt.GoogleAuthController
import com.killinsun.android.okazulogkt.databinding.ProfileFragmentBinding
import com.killinsun.android.okazulogkt.screen.editor.RecipieEditorFragmentDirections
import com.killinsun.android.okazulogkt.screen.editor.RecipieEditorViewModel
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding
    private val viewModel: ProfileViewModel by viewModels()

    private val googleAuthController: GoogleAuthController by lazy {
        GoogleAuthController(activity as AppCompatActivity, this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.signOutButton.setOnClickListener { onLogout() }

        Picasso.get()
            .load(viewModel.photoUrl)
            .into(binding.profileImage)

        return binding.root
    }

    private fun onLogout(){
        googleAuthController.startSignOut()
        navigateToSignIn()
    }

    private fun navigateToSignIn() {
        findNavController().navigate(
            ProfileFragmentDirections.actionProfileFragmentToSignInFragment()
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
