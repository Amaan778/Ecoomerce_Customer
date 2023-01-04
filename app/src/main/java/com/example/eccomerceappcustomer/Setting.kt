package com.example.eccomerceappcustomer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_setting.*

class Setting : Fragment() {
    private lateinit var auth:FirebaseAuth
    private lateinit var database:DatabaseReference
    private lateinit var users: UserDetailConst
    private lateinit var uid:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        auth= FirebaseAuth.getInstance()
        uid=auth.currentUser?.uid.toString()

        database=FirebaseDatabase.getInstance().getReference("usrs")
        if (uid.isNotEmpty()){

            database.child(uid).addValueEventListener(object :ValueEventListener{

                override fun onDataChange(snapshot: DataSnapshot) {
                    users=snapshot.getValue(UserDetailConst::class.java)!!
                    name.setText(users.names)
//                    add.setText(users.addr)
                    num.setText(users.number)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                }

            })

        }

    }

}