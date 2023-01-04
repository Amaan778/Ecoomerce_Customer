package com.example.eccomerceappcustomer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var onBoardingAdapter: ViewPagerAdapter?=null
    var tablayout:TabLayout?=null
    var onBoardingViewPager:ViewPager?=null
    var next:TextView?=null
    var sharedPrefrence:SharedPreferences?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (restorePrefData()){
            val i= Intent(applicationContext,Fragments::class.java)
            startActivity(i)
            finish()
        }

        setContentView(R.layout.activity_main)
        var position:Int

        tablayout=findViewById(R.id.tab)
        next=findViewById(R.id.btn)

        val onBoarding:MutableList<Boarding> = ArrayList()
        onBoarding.add(Boarding("Purchase Online","",R.drawable.product))
        onBoarding.add(Boarding("Pay","",R.drawable.pay))
        onBoarding.add(Boarding("Delivery","",R.drawable.deleivery))

        setonBoardingViewPagerAdapter(onBoarding)

        position=onBoardingViewPager!!.currentItem
        next?.setOnClickListener(View.OnClickListener {
            if (position<onBoarding.size){
                position++
                onBoardingViewPager!!.currentItem=position
            }
            if (position==onBoarding.size){
                savedData()
                val i= Intent(applicationContext,SignUser::class.java)
                startActivity(i)
                finish()
            }
        })


        tablayout!!.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position=tab!!.position
                if (tab.position==onBoarding.size -1){
                    next!!.text="Get Started"
                }else{
                    next!!.text="Next"
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })


    }
    private fun setonBoardingViewPagerAdapter(onBoarding:List<Boarding>){
        onBoardingViewPager=findViewById(R.id.screenPager)
        onBoardingAdapter= ViewPagerAdapter(this,onBoarding)
        onBoardingViewPager!!.adapter=onBoardingAdapter
        tablayout?.setupWithViewPager(onBoardingViewPager)

    }

    private fun savedData(){
        sharedPrefrence =applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor=sharedPrefrence!!.edit()
        editor.putBoolean("isFirstTimeRun",true)
        editor.apply()
    }

    private fun restorePrefData():Boolean{
        sharedPrefrence =applicationContext.getSharedPreferences("pref", Context.MODE_PRIVATE)
        return sharedPrefrence!!.getBoolean("isFirstTimeRun",false)
    }

}