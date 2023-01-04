package com.example.eccomerceappcustomer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register_user.*

class RegisterUser : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_user)


        auth = Firebase.auth
        database=FirebaseDatabase.getInstance().getReference("usrs")
        login.setOnClickListener(View.OnClickListener {

            val mail=email.text.toString().trim()
            val pass=password.text.toString().trim()
            val nam=name.text.toString().trim()
            val num=number.text.toString().trim()
            val add=address.text.toString().trim()


            auth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(this,"Register user",Toast.LENGTH_LONG).show()
                        val user = auth.currentUser

                        val uid=auth.currentUser?.uid
                        val users=UserDetailConst(nam,num,add)

                        if (uid!=null){

                            database.child(uid).setValue(users).addOnSuccessListener {

                            }.addOnFailureListener{
                                Toast.makeText(this,"error in realtime",Toast.LENGTH_LONG).show()
                            }

                        }

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