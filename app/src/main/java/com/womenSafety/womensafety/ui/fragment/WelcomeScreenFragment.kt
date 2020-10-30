package com.womenSafety.womensafety.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import com.womenSafety.womensafety.R
import com.womenSafety.womensafety.ui.fragment.create_account.CreateAccountFragment1

class WelcomeScreenFragment : Fragment() {

    lateinit var loginButton: MaterialButton
    lateinit var registerButton: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_welcome_screen, container, false)
        // hooks
        hooks(view)

        loginButton.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.container, LoginFragment())
                .commit()
        }

        registerButton.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.container, CreateAccountFragment1())
                .commit()
        }

        return view
    }

    private fun hooks(view: View) {
        loginButton = view.findViewById(R.id.btnLoginWelcome)
        registerButton = view.findViewById(R.id.btnRegisterWelcome)
    }
}