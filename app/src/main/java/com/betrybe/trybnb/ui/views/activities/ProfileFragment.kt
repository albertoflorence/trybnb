package com.betrybe.trybnb.ui.views.activities

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.betrybe.trybnb.R
import com.betrybe.trybnb.common.ApiIdlingResource
import com.betrybe.trybnb.data.api.Api
import com.betrybe.trybnb.data.models.AuthRequest
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileFragment : Fragment() {

    private lateinit var mLoginInputProfile: TextInputLayout
    private lateinit var mPasswordInputProfile: TextInputLayout
    private lateinit var mLoginButtonProfile: Button
    private lateinit var mLoginMessage: TextView

    private val api = Api.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        mLoginInputProfile = view.findViewById(R.id.login_input_profile)
        mPasswordInputProfile = view.findViewById(R.id.password_input_profile)
        mLoginButtonProfile = view.findViewById(R.id.login_button_profile)
        mLoginMessage = view.findViewById(R.id.login_message)

        handleLogin()

        return view
    }

    private fun handleLogin() {
        mLoginButtonProfile.setOnClickListener {
            if(!validateLogin()) return@setOnClickListener
            authentication()
        }
    }

    private fun validateLogin(): Boolean {
        mLoginInputProfile.error =
            if (mLoginInputProfile.editText?.text.isNullOrBlank())
                "O campo Login é obrigatório"
            else null

        mPasswordInputProfile.error =
            if (mLoginInputProfile.editText?.text.isNullOrBlank())
                "O campo Password é obrigatório"
            else null

        return mLoginInputProfile.error == null || mPasswordInputProfile.error == null
    }

    private fun authentication() {
        val login = mLoginInputProfile.editText?.text.toString()
        val password = mPasswordInputProfile.editText?.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                ApiIdlingResource.increment()
                withContext(Dispatchers.Main) {
                    val response = api.auth(AuthRequest(login, password))
                    if (response.isSuccessful) {
                        val data = response.body() ?: return@withContext
                        mLoginMessage.visibility = View.VISIBLE
                    }
                    ApiIdlingResource.decrement()
                }
            } catch (ex: Exception) {
                ApiIdlingResource.decrement()
                ex.printStackTrace()
            }
        }
    }
}