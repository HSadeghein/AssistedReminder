package com.example.assistedreminder


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.room.Room
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

class ReminderReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val text = intent.getStringExtra("message")

        Toast.makeText(context, text, 2).show()
        val uid = intent.getIntExtra("uid", 0)
        context.toast(text!!)
        MainActivity.showNotification(context,text!!)

        doAsync{
            val db = Room.databaseBuilder(context, AppDatabase::class.java, "reminders").build()
            db.reminderDao().delete(uid)
            db.close()
        }
    }
}