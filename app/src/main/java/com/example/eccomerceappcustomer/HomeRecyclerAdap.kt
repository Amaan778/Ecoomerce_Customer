package com.example.eccomerceappcustomer

import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.boarding_screen.view.*
import kotlinx.android.synthetic.main.homerecyclerlayout.view.*

class HomeRecyclerAdap (private var  context: Context, val aaraylist:ArrayList<HomeConstructor>):RecyclerView.Adapter<HomeRecyclerAdap.ViewHolder>(){

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val img=itemView.imageproduct
        val title=itemView.titleproduct
        val category=itemView.category
        val totolprice=itemView.price
        val price=itemView.price_product

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.homerecyclerlayout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(aaraylist[position].pic).into(holder.img)
        holder.title.text=aaraylist[position].ProductName
        holder.category.text=aaraylist[position].category
        holder.totolprice.text=aaraylist[position].sellingprice
        holder.price.text=aaraylist[position].price

        holder.itemView.setOnClickListener(View.OnClickListener {
            Toast.makeText(context,"clicking",Toast.LENGTH_LONG).show()
            val intent=Intent(context,ProductDetails::class.java)
            intent.putExtra("id",aaraylist[position].id)
            intent.putExtra("name",aaraylist[position].ProductName)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return aaraylist.size
    }
}
