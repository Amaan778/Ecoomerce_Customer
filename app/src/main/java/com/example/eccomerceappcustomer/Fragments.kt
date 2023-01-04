package com.example.eccomerceappcustomer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class Fragments : AppCompatActivity() {
    private lateinit var user:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragments)

        replace(Home())

        user= FirebaseAuth.getInstance()

        if (user.currentUser!=null){
            user.currentUser?.let {

            }
        }

        bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home->replace(Home())
                R.id.cart->replace(Cart())
                R.id.setting->replace(Setting())
                else->{

                }

            }
            true

        }
    }

    private fun replace(fragment: Fragment){

        val fragmentManager=supportFragmentManager
        val fragmenttransaction=fragmentManager.beginTransaction()
        fragmenttransaction.replace(R.id.frame,fragment)
        fragmenttransaction.commit()

    }

}