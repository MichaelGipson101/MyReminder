package com.example.myreminder;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.myreminder.App.CHANNEL_REMINDER_ID;

public class ViewReminder extends AppCompatActivity {

    //delcare a bundle and a long used to get and store the data sent from
    //the viewlist activity
    Bundle bundle;
    long id;


    //declare a dbhandler
    DBHandler dbHandler;

    //declare an intent
    Intent intent;

    //declare edittexts
    EditText nameEditText;
    EditText textEditText;
    EditText priorityEditText;

    //declare strings to store the clicked shopping list item's name, price, quantity
    String name;
    String text;
    String priority;

    //declare notification manager used to show the notification
    NotificationManagerCompat notificationManagerCompat;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reminder);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize the bundle
        bundle = this.getIntent().getExtras();

        //use bundle to get id and store it in id field
        id = bundle.getLong("_id");

        //initialize dbhandler
        dbHandler = new DBHandler(this, null);

        //initialize edittexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        textEditText = (EditText) findViewById(R.id.textEditText);
        priorityEditText = (EditText) findViewById(R.id.priorityEditText);

        //disable edittexts
        nameEditText.setEnabled(false);
        textEditText.setEnabled(false);
        priorityEditText.setEnabled(false);

        //call the dbhandler method getreminder
        Cursor cursor = dbHandler.getReminder((int) id);

        cursor.moveToFirst();

        //get the name, text, and priority in the cursor and store it in strings
        name = cursor.getString(cursor.getColumnIndex( "name"));
        text = cursor.getString(cursor.getColumnIndex( "text"));
        priority = cursor.getString(cursor.getColumnIndex( "priority"));

        //set the name, text, and priority values in the edittexts
        nameEditText.setText(name);
        textEditText.setText(text);
        priorityEditText.setText(priority);

        //initialize the notification manager
        notificationManagerCompat = NotificationManagerCompat.from(this);
    }

    /**
     * this method further initializes the action bar of the activity.
     * it gets the code (xml) in the menu resource file and incorporates it into the
     * action bar.
     * @param menu menu resource file for the activity
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_reminder, menu);
        return true;
    }

    /**
     * This method gets called when a menu item in the overflow menu is
     * selected and it controls what happens when the menu item is selected
     * @param item selected menu item in the overflow menu
     * @return true if menu item is selected, else false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get the id of the menu item selected
        switch (item.getItemId()) {
            case R.id.action_home :
                // initialize an intent for the main activity and start it
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_reminder :
                // initialize an intent for the add reminder activity and start it
                intent = new Intent(this, AddReminder.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * This method gets called when the delete button in the action bar of the view reminder activity gets clicked
     * @param menuItem database id of the shopping list item to be deleted
     */
    public void deleteReminder(MenuItem menuItem) {
        //delete reminder from db
        dbHandler.deleteReminder((int) id);

        //display item deleted toast
        Toast.makeText(this, "Reminder Deleted!", Toast.LENGTH_LONG).show();

        //displays notification if all high priority reminders are deleted
        if((dbHandler.getDeletedHighPriority((String) this.priority)).getCount() == 0 && this.priority.equals("High")){
            // initialize Notification
            Notification notification = new NotificationCompat.Builder(this,
                    CHANNEL_REMINDER_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("MyReminder")
                    .setContentText("High priority reminders deleted!").build();

            // show notification
            notificationManagerCompat.notify(1, notification);

        }
        //displays notification if all medium priority reminders are deleted
        if((dbHandler.getDeletedMediumPriority((String) this.priority)).getCount() == 0 && this.priority.equals("Medium")){
            // initialize Notification
            Notification notification = new NotificationCompat.Builder(this,
                    CHANNEL_REMINDER_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("MyReminder")
                    .setContentText("Medium priority reminders deleted!").build();

            // show notification
            notificationManagerCompat.notify(1, notification);

        }
        //displays notification if all low priority reminders are deleted
        if((dbHandler.getDeletedLowPriority((String) this.priority)).getCount() == 0 && this.priority.equals("Low")){
            // initialize Notification
            Notification notification = new NotificationCompat.Builder(this,
                    CHANNEL_REMINDER_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("MyReminder")
                    .setContentText("Low priority reminders deleted!").build();

            // show notification
            notificationManagerCompat.notify(1, notification);

        }
    }
    }

