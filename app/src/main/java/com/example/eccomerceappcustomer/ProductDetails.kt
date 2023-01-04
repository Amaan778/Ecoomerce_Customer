package com.example.eccomerceappcustomer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.activity_product_details.image_slider
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductDetails : AppCompatActivity() {
    private var db= Firebase.firestore
    private lateinit var database: DatabaseReference
    private lateinit var users: UserDetailConst

    private lateinit var usersdetail: UserDetailConst
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val b=intent.getStringExtra("id")
        val c=b.toString()

        val pname=intent.getStringExtra("name")
        val pnamed=pname.toString()
        db= FirebaseFirestore.getInstance()

        database= FirebaseDatabase.getInstance().getReference("slider")


        db.collection("products").document(c).get().addOnSuccessListener {

            if (it != null) {
                val na=it.data?.get("ProductName")?.toString()
                val ca=it.data?.get("category")?.toString()
                val pric=it.data?.get("price")?.toString()
                val des=it.data?.get("description").toString()
                val ids=it.data?.get("id").toString()
                p_title.setText(na)
                p_price.setText(pric)
                p_description.setText(des)
                val imgl=it.data?.get("img1")?.toString()
                val imge=it.data?.get("img2")?.toString()
                val imgf=it.data?.get("img3")?.toString()
                val imageList = ArrayList<SlideModel>() // Create image list

                imageList.add(SlideModel(imgl,ScaleTypes.FIT))
                imageList.add(SlideModel(imge,ScaleTypes.FIT))
                imageList.add(SlideModel(imgf,ScaleTypes.FIT))
                image_slider.setImageList(imageList)

                 cartAction(c, pric,na,it.data?.get("pic").toString())

            }else{
                Log.d("check", "DocumentSnapshot data: error")
            }

        }.addOnFailureListener {
            Toast.makeText(this, "error", Toast.LENGTH_LONG).show()
        }


    }

    private fun cartAction(c: String, pric: String?, na: String?, coverimg: String) {

        val productDao=AppDatabase.getInstance(this).productDao()

        if (productDao.isExit(c)!=null){
            clickbtn.text="Go To Cart"
        }else{
            clickbtn.text="Add to Cart"
        }

        clickbtn.setOnClickListener{

            Toast.makeText(this,"clicking 4 ",Toast.LENGTH_LONG).show()
            if (productDao.isExit(c)!=null){
                openCart()
            }else{
                addToCart(productDao,c,pric,coverimg,na)
            }

        }

    }

    private fun addToCart(productDao: ProductDao, c: String, pric: String?, coverimg: String, na: String?) {

        val data=ProductModelRoom(c,na,coverimg,pric)
        lifecycleScope.launch(Dispatchers.IO){
            productDao.insertProduct(data)
            clickbtn.text="Go To Cart"
        }

    }


    private fun openCart() {

        val prefrence =this.getSharedPreferences("info", MODE_PRIVATE)
        val editor=prefrence.edit()
        editor.putBoolean("isCart",true)
        editor.apply()

        startActivity(Intent(this,Cart::class.java))
        finish()

    }

    override fun onBackPressed() {
        startActivity(Intent(this,Fragments::class.java))
        finish()
    }
}
