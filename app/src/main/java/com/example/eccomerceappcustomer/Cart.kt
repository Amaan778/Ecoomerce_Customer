package com.example.eccomerceappcustomer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.ColumnInfo
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.recycler

class Cart : Fragment() {
    private val list=ArrayList<HomeConstructor>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val prefrence=requireContext().getSharedPreferences("info",AppCompatActivity.MODE_PRIVATE)
        val editor = prefrence.edit()
        editor.putBoolean("iscart",false)
        editor.apply()


        recycler.layoutManager= LinearLayoutManager(context)


        val dao =AppDatabase.getInstance(requireContext()).productDao()

        dao.getAllProducts().observe(requireActivity()){

            recycler.adapter= CartAdapter(requireContext(),it)

            totalCost(it)

        }



    }

    private fun totalCost(data: List<ProductModelRoom>?) {

        var total=0
        for (item in data!!){
            total+=item.productSp!!.toInt()
        }

        cart_item.text="Total item in cart is ${data.size}"
        cart_total.text="Total Cost : $total"

        checkout.setOnClickListener{

            val intent=Intent(context,Checkout::class.java)
            intent.putExtra("totalcost",total)
            startActivity(intent)

        }

    }
}