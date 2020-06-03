package com.killinsun.android.okazulogkt.screen.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.killinsun.android.okazulogkt.GoogleAuthController
import com.killinsun.android.okazulogkt.databinding.ActivitySignInBinding
import com.killinsun.android.okazulogkt.screen.MainActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var viewModel: SignInViewModel
    private lateinit var binding: ActivitySignInBinding

    private val googleAuthController: GoogleAuthController by lazy {
        GoogleAuthController(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SignInViewModel::class.java)
        binding = ActivitySignInBinding.inflate(layoutInflater)

        if(viewModel.isLogin) {
            intentToOkazuLog()
        }

        binding.signInButton.setOnClickListener { onLogin() }
        binding.signOutButton.setOnClickListener { onLogout() }
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)
    }

    private fun onLogin(){
        googleAuthController.startSignIn{
            viewModel.onLogin()
            Log.i("SignInActivity", "Logged In! email: " + viewModel.userEmail.value)
            intentToOkazuLog()

        }
    }

    private fun onLogout() {
        googleAuthController.startSignOut()
    }

    private fun intentToOkazuLog() {
        val okazuLogIntent = Intent(this, MainActivity::class.java)
        okazuLogIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(okazuLogIntent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.v("SignInActivity", "resultCode: $resultCode")

        googleAuthController.onActivityResult(requestCode, resultCode, data)
    }

}
