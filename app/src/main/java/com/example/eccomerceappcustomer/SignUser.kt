package com.example.eccomerceappcustomer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_user.*

class SignUser : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_user)

        register.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,RegisterUser::class.java))
            finish()
        })

        auth = Firebase.auth

        if (auth.currentUser!=null){
            startActivity(Intent(this,Fragments::class.java))
            finish()
        }

        sigin.setOnClickListener(View.OnClickListener {

            val eml=email.text.toString().trim()
            val pa=password.text.toString().trim()

            auth.signInWithEmailAndPassword(eml, pa)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this,"signin",Toast.LENGTH_LONG).show()
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }


        })

    }

    private fun updateUI(user: FirebaseUser?) {

        startActivity(Intent(this,Fragments::class.java))
        finish()
    }

}