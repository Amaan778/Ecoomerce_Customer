package com.example.eccomerceappcustomer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.name

class Checkout : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var users: UserDetailConst
    private lateinit var uid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        auth= FirebaseAuth.getInstance()
        uid=auth.currentUser?.uid.toString()


        database= FirebaseDatabase.getInstance().getReference("usrs")
        if (uid.isNotEmpty()){

            database.child(uid).addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    users=snapshot.getValue(UserDetailConst::class.java)!!
                    name.setText(users.names)
//                    add.setText(users.addr)
                    number.setText(users.number)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext,"Error",Toast.LENGTH_LONG).show()
                }

            })

        }

        p_checkout.setOnClickListener(View.OnClickListener {

            val na=name.text.toString().trim()
            val nu=number.text.toString().trim()
            val vill=village.text.toString().trim()
            val cit=city.text.toString().trim()
            val sta=state.text.toString().trim()
            val pin=pincode.text.toString().trim()

            if (na.isEmpty()){
                name.error="Required"
            }else if (nu.isEmpty()){
                number.error="Required"
            }else if (vill.isEmpty()){
                village.error="Required"
            }else if (cit.isEmpty()){
                city.error="Required"
            }else if (sta.isEmpty()){
                state.error="Required"
            }else if (pin.isEmpty()){
                pincode.error="Required"
            }else{
                Toast.makeText(this,"Clicking",Toast.LENGTH_LONG).show()
            }

        })

    }
}