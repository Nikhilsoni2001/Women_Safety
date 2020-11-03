package com.womenSafety.womensafety.ui.fragment.create_account

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import com.womenSafety.womensafety.R
import com.womenSafety.womensafety.ui.fragment.LoginFragment

class CreateAccountFragment2 : Fragment() {

    lateinit var btnAgePicker: MaterialButton
    lateinit var rgGender: RadioGroup
    lateinit var rbMale: RadioButton
    lateinit var rbFemale: RadioButton
    lateinit var rbOther: RadioButton
    lateinit var gender: String
    lateinit var btnNext: MaterialButton
    lateinit var btnLogin: MaterialButton
    lateinit var backButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_account2, container, false)

        hooks(view)
        var dob = ""

        btnAgePicker.setOnClickListener {
            val materialDatePicker = buildMaterialDatePicker()
            materialDatePicker.show(parentFragmentManager, "Date Picker")
            materialDatePicker.addOnPositiveButtonClickListener {
                dob = materialDatePicker.headerText
            }
        }

        rgGender.setOnCheckedChangeListener { _, _ ->
            when {
                rbMale.isChecked -> {
                    gender = rbMale.text.toString()
                }
                rbFemale.isChecked -> {
                    gender = rbFemale.text.toString()
                }
                rbOther.isChecked -> {
                    gender = rbOther.text.toString()
                }
            }
        }

        val data = this.arguments
        val fullName = data?.getString("fullName")
        val userName = data?.getString("userName")
        val email = data?.getString("email")
        val password = data?.getString("password")

        btnNext.setOnClickListener {
            if (dob.trim().isNotBlank()) {
                if (rbMale.isChecked || rbFemale.isChecked || rbOther.isChecked) {
                    val frag = CreateAccountFragment3()
                    val bundle = Bundle()
                    bundle.putString("fullName", fullName)
                    bundle.putString("userName", userName)
                    bundle.putString("email", email)
                    bundle.putString("password", password)
                    bundle.putString("gender", gender)
                    bundle.putString("dob", dob)
                    frag.arguments = bundle

                    parentFragmentManager.beginTransaction().replace(R.id.container, frag).commit()
                } else {
                    Toast.makeText(context, "Please Pick your Gender!!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "Please Pick your dob!!", Toast.LENGTH_LONG).show()
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

    private fun buildMaterialDatePicker(): MaterialDatePicker<Long> {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Select Your DOB : ")
        return builder.build()
    }

    private fun hooks(view: View) {
        btnAgePicker = view.findViewById(R.id.agePicker)
        rgGender = view.findViewById(R.id.rgGender)
        rbMale = view.findViewById(R.id.radioMale)
        rbFemale = view.findViewById(R.id.radioFemale)
        rbOther = view.findViewById(R.id.radioOther)
        btnNext = view.findViewById(R.id.btnNextThree)
        btnLogin = view.findViewById(R.id.btnLoginThree)
        backButton = view.findViewById(R.id.create2_back_button)
    }
}