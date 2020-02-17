package com.example.assistedreminder

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView

import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room

import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

//import kotlinx.android.synthetic.main.action_button.*








class MainActivity : AppCompatActivity() {
    private lateinit var listView : ListView;
    private var isClicked:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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
        list_view.onItemClickListener = AdapterView.OnItemClickListener{_,_, position, _->
            val selected =list_view.adapter.getItem(position) as Reminder

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Delete reminder?")
                .setMessage(selected.message)
                .setPositiveButton("Delete" ){_,_->

                    if(selected.time != null) {
                        val manager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                        val intent = Intent(this, ReminderReceiver::class.java)
                        val pending = PendingIntent.getBroadcast(this, selected.uid!!, intent, PendingIntent.FLAG_ONE_SHOT)
                        manager.cancel(pending)
                    }
                    doAsync {
                        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "reminders").build()
                        db.close()
                        refreshList()
                    }
                }
                .setNegativeButton("Cancel"){dialog,_->
                    dialog.dismiss()
                }
                .show()
        }
    }
    override fun onResume() {
        super.onResume()

        refreshList()
    }
    private fun refreshList() {
        doAsync {
            val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "reminders").build()
            val reminders = db.reminderDao().getReminders()
            db.close()

            uiThread {
                if(reminders.isNotEmpty()) {
                    val adapter = CustomAdapter(applicationContext, reminders)
                    list_view.adapter = adapter
                } else {
                    Toast.makeText(applicationContext, "No Reminder", 2).show()
                }
            }
        }
    }




}
