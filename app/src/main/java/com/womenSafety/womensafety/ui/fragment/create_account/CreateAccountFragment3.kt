package com.womenSafety.womensafety.ui.fragment.create_account

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hbb20.CountryCodePicker
import com.womenSafety.womensafety.R
import com.womenSafety.womensafety.ui.fragment.LoginFragment
import com.womenSafety.womensafety.util.Validations

class CreateAccountFragment3 : Fragment() {

    private lateinit var countryCodeHolder: CountryCodePicker
    private lateinit var btnNext: MaterialButton
    private lateinit var btnLogin: MaterialButton
    private lateinit var elMobile: TextInputLayout
    private lateinit var etMobile: TextInputEditText
    private lateinit var backButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_account3, container, false)

        hooks(view)

        val data = this.arguments
        val fullName = data?.getString("fullName")
        val userName = data?.getString("userName")
        val email = data?.getString("email")
        val password = data?.getString("password")
        val gender = data?.getString("gender")
        val dob = data?.getString("dob")

        val countryCodePicker = countryCodeHolder.fullNumber

        btnNext.setOnClickListener {
            val mobile: String = etMobile.text.toString().trim()

            val phoneNumber = "+$countryCodePicker$mobile"
            Log.d("TAG", "onCreateView: $phoneNumber")

            val frag = OtpFragment()
            val bundle = Bundle()
            bundle.putString("fullName", fullName)
            bundle.putString("userName", userName)
            bundle.putString("email", email)
            bundle.putString("password", password)
            bundle.putString("gender", gender)
            bundle.putString("dob", dob)
            bundle.putString("phone", phoneNumber)
            frag.arguments = bundle
            if (Validations.validateMobile(etMobile.text.toString().trim())) {
                elMobile.error = null
                parentFragmentManager.beginTransaction().replace(R.id.container, frag).commit()
            } else {
                elMobile.error = "Incorrect Mobile Number!!"
            }
        }
        btnLogin.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.container, LoginFragment())
                .commit()
        }

        backButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, CreateAccountFragment1()).commit()
        }
        return view
    }

    private fun hooks(view: View) {
        countryCodeHolder = view.findViewById(R.id.countryCodeHolder)
        btnNext = view.findViewById(R.id.btnNextTwo)
        btnLogin = view.findViewById(R.id.btnLoginTwo)
        elMobile = view.findViewById(R.id.elMobile)
        etMobile = view.findViewById(R.id.etMobileNumber)
        backButton = view.findViewById(R.id.create3_back_button)
    }
}