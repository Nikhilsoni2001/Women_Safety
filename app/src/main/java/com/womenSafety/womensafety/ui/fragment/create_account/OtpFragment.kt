package com.womenSafety.womensafety.ui.fragment.create_account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.chaos.view.PinView
import com.google.android.material.button.MaterialButton
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.womenSafety.womensafety.R
import com.womenSafety.womensafety.ui.activity.HomeActivity
import java.util.concurrent.TimeUnit

class OtpFragment : Fragment() {

    private lateinit var exitButton: ImageView
    private var fullName: String? = null
    private var userName: String? = null
    private var email: String? = null
    private var password: String? = null
    private var gender: String? = null
    private var dob: String? = null
    private var phone: String? = null
    private var whatToDo: String? = null
    private val pinView = activity?.findViewById<PinView>(R.id.pin_view)
    private lateinit var btnVerify: MaterialButton

    private lateinit var codeBySystem: String
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_otp, container, false)

        hooks(view)

        val data = arguments
        fullName = data?.getString("fullName")
        userName = data?.getString("userName")
        email = data?.getString("email")
        password = data?.getString("password")
        gender = data?.getString("gender")
        dob = data?.getString("dob")
        phone = data?.getString("phone")
        whatToDo = data?.getString("whatToDo")
        Log.d("TAG", "onCreateView: $fullName $userName $email $password $gender $dob $phone $whatToDo")

        if (phone != null) {
            sendOtpToUser("$phone")
        }

        btnVerify.setOnClickListener {
            callNextScreenFromOtp()
        }

        exitButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.container, CreateAccountFragment1()).commit()
        }
        return view
    }

    private fun sendOtpToUser(phone: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(requireActivity())                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {

            val code: String? = credential.smsCode

            if (code != null) {
                pinView?.setText("$code")
                verifyCode(code)
            }
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.
            Log.w("TAG", "onVerificationFailed", p0)

            if (p0 is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Toast.makeText(requireContext(), "Invalid Request", Toast.LENGTH_SHORT).show()
            } else if (p0 is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Toast.makeText(requireContext(), "Limit Exceeded!!", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, token)
            Log.d("TAG", "onCodeSent:$verificationId")

            codeBySystem = verificationId
        }
        // Force reCAPTCHA flow
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(codeBySystem, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    if (whatToDo.equals("updateData()")) {
                        //updateOldUserData()
                    } else {
                        storeNewUserData()
                        activity?.let {
                            startActivity(Intent(it, HomeActivity::class.java))
                        }
                    }
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(
                            context,
                            "Verification not Completed!!, Please Try Again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    private fun storeNewUserData() {
//        val
    }

    private fun callNextScreenFromOtp() {
        val code: String = pinView?.text.toString()
        if (code.trim().isNotEmpty()) {
            verifyCode(code)
        }
    }

    private fun hooks(view: View) {
        exitButton = view.findViewById(R.id.otp_exit)
        btnVerify = view.findViewById(R.id.btnVerifyCode)
    }
}