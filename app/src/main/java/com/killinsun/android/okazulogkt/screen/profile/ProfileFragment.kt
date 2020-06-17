package com.killinsun.android.okazulogkt.screen.profile

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.killinsun.android.okazulogkt.GoogleAuthController
import com.killinsun.android.okazulogkt.databinding.ProfileFragmentBinding
import com.killinsun.android.okazulogkt.screen.MainActivity
import com.killinsun.android.okazulogkt.screen.signin.SignInActivity
import com.squareup.picasso.Picasso
import io.wovn.wovnkt.Lang
import io.wovn.wovnkt.Wovn
import java.io.InputStream
import java.net.URL


class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding
    private lateinit var viewModel: ProfileViewModel

    private val googleAuthController: GoogleAuthController by lazy {
        GoogleAuthController(activity as AppCompatActivity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

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
        intentToSignIn()
    }

    private fun intentToSignIn() {
        val signInIntent = Intent(activity, SignInActivity::class.java)
        signInIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(signInIntent)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Log.v("OkazuLog", "${Wovn.getCurrentLang()}")
        Wovn.changeLang(Lang.english)
        view?.let { Wovn.translateView(it, "main") }
    }

}
