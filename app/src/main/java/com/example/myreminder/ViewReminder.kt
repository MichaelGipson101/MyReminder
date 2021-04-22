package com.example.myreminder

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ViewReminder : AppCompatActivity() {
    //delcare a bundle and a long used to get and store the data sent from
    //the viewlist activity
    var bundle: Bundle? = null
    var id: Long = 0

    //declare a dbhandler
    var dbHandler: DBHandler? = null

    //declare an intent
    //var intent: Intent? = null

    //declare edittexts
    var nameEditText: EditText? = null
    var textEditText: EditText? = null
    var priorityEditText: EditText? = null

    //declare strings to store the clicked shopping list item's name, price, quantity
    var name: String? = null
    var text: String? = null
    var priority: String? = null

    //declare notification manager used to show the notification
    var notificationManagerCompat: NotificationManagerCompat? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reminder)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //initialize the bundle
        bundle = getIntent().extras

        //use bundle to get id and store it in id field
        id = bundle!!.getLong("_id")

        //initialize dbhandler
        dbHandler = DBHandler(this, null)

        //initialize edittexts
        nameEditText = findViewById<View>(R.id.nameEditText) as EditText
        textEditText = findViewById<View>(R.id.textEditText) as EditText
        priorityEditText = findViewById<View>(R.id.priorityEditText) as EditText

        //disable edittexts
        nameEditText!!.isEnabled = false
        textEditText!!.isEnabled = false
        priorityEditText!!.isEnabled = false

        //call the dbhandler method getreminder
        val cursor = dbHandler!!.getReminder(id.toInt())
        cursor.moveToFirst()

        //get the name, text, and priority in the cursor and store it in strings
        name = cursor.getString(cursor.getColumnIndex("name"))
        text = cursor.getString(cursor.getColumnIndex("text"))
        priority = cursor.getString(cursor.getColumnIndex("priority"))

        //set the name, text, and priority values in the edittexts
        nameEditText!!.setText(name)
        textEditText!!.setText(text)
        priorityEditText!!.setText(priority)

        //initialize the notification manager
        notificationManagerCompat = NotificationManagerCompat.from(this)
    }

    /**
     * this method further initializes the action bar of the activity.
     * it gets the code (xml) in the menu resource file and incorporates it into the
     * action bar.
     * @param menu menu resource file for the activity
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_view_reminder, menu)
        return true
    }

    /**
     * This method gets called when a menu item in the overflow menu is
     * selected and it controls what happens when the menu item is selected
     * @param item selected menu item in the overflow menu
     * @return true if menu item is selected, else false
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //get the id of the menu item selected
        return when (item.itemId) {
            R.id.action_home -> {
                // initialize an intent for the main activity and start it
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_add_reminder -> {
                // initialize an intent for the add reminder activity and start it
                intent = Intent(this, AddReminder::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method gets called when the delete button in the action bar of the view reminder activity gets clicked
     * @param menuItem database id of the shopping list item to be deleted
     */
    fun deleteReminder(menuItem: MenuItem?) {
        //delete reminder from db
        dbHandler!!.deleteReminder(id.toInt())

        //display item deleted toast
        Toast.makeText(this, "Reminder Deleted!", Toast.LENGTH_LONG).show()

        //displays notification if all high priority reminders are deleted
        if (dbHandler!!.getDeletedHighPriority(priority!!).count == 0 && priority == "High") {
            // initialize Notification
            val notification = NotificationCompat.Builder(this,
                    App.CHANNEL_REMINDER_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("MyReminder")
                    .setContentText("High priority reminders deleted!").build()

            // show notification
            notificationManagerCompat!!.notify(1, notification)
        }
        //displays notification if all medium priority reminders are deleted
        if (dbHandler!!.getDeletedMediumPriority(priority!!).count == 0 && priority == "Medium") {
            // initialize Notification
            val notification = NotificationCompat.Builder(this,
                    App.CHANNEL_REMINDER_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("MyReminder")
                    .setContentText("Medium priority reminders deleted!").build()

            // show notification
            notificationManagerCompat!!.notify(1, notification)
        }
        //displays notification if all low priority reminders are deleted
        if (dbHandler!!.getDeletedLowPriority(priority!!).count == 0 && priority == "Low") {
            // initialize Notification
            val notification = NotificationCompat.Builder(this,
                    App.CHANNEL_REMINDER_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("MyReminder")
                    .setContentText("Low priority reminders deleted!").build()

            // show notification
            notificationManagerCompat!!.notify(1, notification)
        }
    }
}