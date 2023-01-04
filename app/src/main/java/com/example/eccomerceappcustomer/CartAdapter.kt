package com.example.eccomerceappcustomer

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cart_layout.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdapter(val context: Context, val list: List<ProductModelRoom>): RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val img=itemView.cart_image
        val titles=itemView.cart_title
        val prices=itemView.cart_price
        val delete=itemView.btn_del
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         Glide.with(context).load(list[position].productImage).into(holder.img)
        holder.titles.text=list[position].productName
        holder.prices.text=list[position].productSp

        val dao=AppDatabase.getInstance(context).productDao()

        holder.itemView.setOnClickListener{
            val intent=Intent(context,ProductDetails::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }

        holder.delete.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO) {
                dao.deleteProduct(ProductModelRoom(list[position].productId,list[position].productName,list[position].productImage,list[position].productSp))
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }


}