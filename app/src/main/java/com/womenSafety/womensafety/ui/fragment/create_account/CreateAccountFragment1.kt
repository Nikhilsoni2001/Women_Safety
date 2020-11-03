package com.womenSafety.womensafety.ui.fragment.create_account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.womenSafety.womensafety.R
import com.womenSafety.womensafety.ui.fragment.LoginFragment
import com.womenSafety.womensafety.ui.fragment.WelcomeScreenFragment
import com.womenSafety.womensafety.util.Validations

class CreateAccountFragment1 : Fragment() {

    private lateinit var elFullName: TextInputLayout
    private lateinit var etFullName: TextInputEditText
    private lateinit var elUsername: TextInputLayout
    private lateinit var etUsername: TextInputEditText
    private lateinit var elEmail: TextInputLayout
    private lateinit var etEmail: TextInputEditText
    private lateinit var elPassword: TextInputLayout
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnNext: MaterialButton
    private lateinit var btnLogin: MaterialButton
    private lateinit var backButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_account1, container, false)

        // hooks
        hooks(view)

        btnNext.setOnClickListener {
            callNextScreen()
        }

        btnLogin.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment()).commit()
        }

        backButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, WelcomeScreenFragment())
                .commit()
        }
        return view
    }

    private fun hooks(view: View) {
        elFullName = view.findViewById(R.id.elFullName)
        etFullName = view.findViewById(R.id.etFullName)
        elUsername = view.findViewById(R.id.elUsername)
        etUsername = view.findViewById(R.id.etUsername)
        elEmail = view.findViewById(R.id.elEmail)
        etEmail = view.findViewById(R.id.etEmail)
        elPassword = view.findViewById(R.id.elPassword)
        etPassword = view.findViewById(R.id.etPassword)
        btnNext = view.findViewById(R.id.btnNextOne)
        btnLogin = view.findViewById(R.id.btnLoginOne)
        backButton = view.findViewById(R.id.create1_back_button)
    }

    private fun callNextScreen() {
        if (Validations.validateFullNameLength(etFullName.text.toString().trim())) {
            elFullName.error = null
            if (Validations.validateUserNameLength(etUsername.text.toString().trim())) {
                elUsername.error = null
                if (Validations.validateEmail(etEmail.text.toString().trim())) {
                    elEmail.error = null
                    if (Validations.validatePasswordLength(etPassword.text.toString().trim())) {
                        elPassword.error = null

                        val fragment = CreateAccountFragment2()
                        val bundle = Bundle()
                        bundle.putString("fullName", etFullName.text.toString().trim())
                        bundle.putString("userName", etUsername.text.toString().trim())
                        bundle.putString("email", etEmail.text.toString().trim())
                        bundle.putString("password", etPassword.text.toString().trim())
                        fragment.arguments = bundle

                        parentFragmentManager.beginTransaction().replace(R.id.container, fragment)
                            .commit()
                    } else {
                        elPassword.error = "Password is too Short!!"
                    }
                } else {
                    elEmail.error = "Email is Invalid!!"
                }
            } else {
                elUsername.error = "Username is too Short!!"
            }
        } else {
            elFullName.error = "Name is too Short!!"
        }
    }
}