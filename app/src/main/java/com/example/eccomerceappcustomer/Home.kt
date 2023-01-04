package com.example.eccomerceappcustomer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*

class Home : Fragment() {
    val arrContact= ArrayList<HomeConstructor>()
    private var db= Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //imageslider
        val imageList = ArrayList<SlideModel>() // Create image list

        imageList.add(SlideModel(R.drawable.img1,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.img2,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.img3,ScaleTypes.FIT))
        image_slider.setImageList(imageList)

        //recycler products

        recycler.layoutManager= LinearLayoutManager(context)
        val adap= context?.let { HomeRecyclerAdap(it,arrContact) }
        recycler.adapter=adap

        db= FirebaseFirestore.getInstance()
        db.collection("products").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                if (error!=null){
                    Log.e("firestore", error.message.toString())
                }

                for (dc : DocumentChange in value?.documentChanges!!){

                    if(dc.type == DocumentChange.Type.ADDED){
                        arrContact.add(dc.document.toObject(HomeConstructor::class.java))
                    }

                }

                adap?.notifyDataSetChanged()

            }
        })


    }
}