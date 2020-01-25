package com.example.assistedreminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.ListView

import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.action_button.*








class MainActivity : AppCompatActivity() {
    private lateinit var listView : ListView;
    private var isClicked:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView = findViewById(R.id.reminderListView)

//        val show1 = AnimationUtils.loadAnimation(this,R.anim.show1);
//        val show2 = AnimationUtils.loadAnimation(this,R.anim.show2);
//        val hide1 = AnimationUtils.loadAnimation(this,R.anim.hide1);
//        val hide2 = AnimationUtils.loadAnimation(this,R.anim.hide2);


        fab_map.translationY = 0F
        fab_time.translationY = 0F


        fab.setOnClickListener{
            if(!isClicked) {
                fab_map.animate().translationY(-resources.getDimension(R.dimen.standard_66))
                fab_time.animate().translationY(-resources.getDimension(R.dimen.standard_116))
            }
            else{
                fab_map.animate().translationY(0F)
                fab_time.animate().translationY(0F)
            }
            isClicked = !isClicked

        }
        fab_time.setOnClickListener{
            val intent = Intent(this, TimeActivity::class.java).apply {
            }
            startActivity(intent)
        }
        fab_map.setOnClickListener{
            val intent = Intent(this, MapActivity::class.java).apply {
            }
            startActivity(intent)
        }
    }




}
