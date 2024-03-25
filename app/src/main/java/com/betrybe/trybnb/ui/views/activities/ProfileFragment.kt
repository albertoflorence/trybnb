package com.betrybe.trybnb.ui.views.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.betrybe.trybnb.R
import com.google.android.material.textfield.TextInputLayout

class ProfileFragment : Fragment() {

    private lateinit var mLoginInputProfile: TextInputLayout
    private lateinit var mPasswordInputProfile: TextInputLayout
    private lateinit var mLoginButtonProfile: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        mLoginInputProfile = view.findViewById(R.id.login_input_profile)
        mPasswordInputProfile = view.findViewById(R.id.password_input_profile)
        mLoginButtonProfile = view.findViewById(R.id.login_button_profile)

        handleLogin()

        return view
    }

    private fun handleLogin() {
        mLoginButtonProfile.setOnClickListener {
            validateLogin()
        }
    }

    private fun validateLogin() {
        mLoginInputProfile.error =
            if (mLoginInputProfile.editText?.text.isNullOrBlank())
                "O campo Login é obrigatório"
            else null

        mPasswordInputProfile.error =
            if (mLoginInputProfile.editText?.text.isNullOrBlank())
                "O campo Password é obrigatório"
            else null
    }
}