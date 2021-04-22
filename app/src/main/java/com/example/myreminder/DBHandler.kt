package com.example.myreminder

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(context: Context?, factory: CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    /**
     * Creates table for reminder database
     * @param sqLiteDatabase reference to reminder db
     */
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        //create reminder table and store it as a string
        val query = "CREATE TABLE " + TABLE_REMINDERS + "(" +
                COLUMN_REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_REMINDER_NAME + " TEXT, " +
                COLUMN_REMINDER_TEXT + " TEXT, " +
                COLUMN_REMINDER_DATE + " TEXT, " +
                COLUMN_REMINDER_PRIORITY + " TEXT);"

        //statement execute
        sqLiteDatabase.execSQL(query)
    }

    /**
     * Creates a new version of the reminder database
     * @param sqLiteDatabase reference to reminder db
     * @param oldVersion old version of reminder db
     * @param newVersion new version of reminder db
     */
    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //define drop statement and store as a string
        val query = "DROP TABLE IF EXISTS " + TABLE_REMINDERS

        //statement execute
        sqLiteDatabase.execSQL(query)

        //call the method that creates
        onCreate(sqLiteDatabase)
    }

    /**
     * This method is called when the add button in the action bar of the createReminder activity is
     * clicked. It inserts a new row into the reminder table.
     * @param name reminder name
     * @param text reminder text
     * @param date reminder date
     */
    fun addAReminder(name: String?, text: String?, date: String?, priority: String?) {
        //get reference to reminder DB
        val db = writableDatabase

        //initialize contentvalues obj
        val values = ContentValues()

        //put data into contentvalues obj
        values.put(COLUMN_REMINDER_NAME, name)
        values.put(COLUMN_REMINDER_TEXT, text)
        values.put(COLUMN_REMINDER_DATE, date)
        values.put(COLUMN_REMINDER_PRIORITY, priority)

        //insert data in contentvalues obj into reminder table
        db.insert(TABLE_REMINDERS, null, values)

        //close db reference
        db.close()
    }//get reference to the reminder database

    //define select statement and store it in a string

    //execute the statement and return it as a cursor
    /**
     * This method gets called when the main activity is created. It will select and return
     * all of the data from the reminder table.
     *
     * @return Cursor that contains all of the data from the reminder table.
     */
    val reminders: Cursor
        get() {
            //get reference to the reminder database
            val db = writableDatabase

            //define select statement and store it in a string
            val query = "SELECT * FROM " + TABLE_REMINDERS

            //execute the statement and return it as a cursor
            return db.rawQuery(query, null)
        }

    /**
     * this method is going to get called when the viewreminder activity is started
     * @param reminderId database id of clicked reminder
     * @return Cursor that contains all of the data associated with the clicked reminder
     */
    fun getReminder(reminderId: Int): Cursor {
        //get reference to reminder db
        val db = writableDatabase

        //define select statement
        val query = "SELECT * FROM " + TABLE_REMINDERS +
                " WHERE " + COLUMN_REMINDER_ID + " = " + reminderId

        //execute select statement
        return db.rawQuery(query, null)
    }

    //get reference to reminder DB
    val highPriorityReminders: Cursor
        get() {
            //get reference to reminder DB
            val db = writableDatabase

            //define select statement and store it in a string
            val query = "SELECT * FROM " + TABLE_REMINDERS +
                    " WHERE " + COLUMN_REMINDER_PRIORITY + " = " + "'High'"

            //execute select statement
            return db.rawQuery(query, null)
        }

    //get reference to reminder DB
    val mediumPriorityReminders: Cursor
        get() {
            //get reference to reminder DB
            val db = writableDatabase

            //define select statement and store it in a string
            val query = "SELECT * FROM " + TABLE_REMINDERS +
                    " WHERE " + COLUMN_REMINDER_PRIORITY + " = " + "'Medium'"

            //execute select statement
            return db.rawQuery(query, null)
        }

    //get reference to reminder DB
    val lowPriorityReminders: Cursor
        get() {
            //get reference to reminder DB
            val db = writableDatabase

            //define select statement and store it in a string
            val query = "SELECT * FROM " + TABLE_REMINDERS +
                    " WHERE " + COLUMN_REMINDER_PRIORITY + " = " + "'Low'"

            //execute select statement
            return db.rawQuery(query, null)
        }

    /**
     * This method gets called when the delete button in the action bar of the view reminder activity gets clicked
     * @param reminderId database id of the reminder to be deleted
     */
    fun deleteReminder(reminderId: Int) {
        val db = writableDatabase

        //define a delete statement and store it in a string
        val query = "DELETE FROM " + TABLE_REMINDERS +
                " WHERE " + COLUMN_REMINDER_ID + " = " + reminderId

        //execute statement
        db.execSQL(query)

        //close db ref
        db.close()
    }

    /**
     * This method gets called when the delete button in the viewreminder activity is clicked.
     * @param priority priority of the reminder
     * @return returns a notification if all high priority reminders are deleted
     */
    fun getDeletedHighPriority(priority: String): Cursor {
        //get reference to database
        val db = writableDatabase

        //define select statement
        val query = "SELECT * FROM " + TABLE_REMINDERS +
                " WHERE " + COLUMN_REMINDER_PRIORITY + " = '" + priority + "'"


        // execute select statement and store it in a Cursor
        return db.rawQuery(query, null)
    }

    /**
     * This method gets called when the delete button in the viewreminder activity is clicked.
     * @param priority priority of the reminder
     * @return returns a notification if all medium priority reminders are deleted
     */
    fun getDeletedMediumPriority(priority: String): Cursor {
        //get reference to database
        val db = writableDatabase

        //define select statement
        val query = "SELECT * FROM " + TABLE_REMINDERS +
                " WHERE " + COLUMN_REMINDER_PRIORITY + " = '" + priority + "'"


        // execute select statement and store it in a Cursor
        return db.rawQuery(query, null)
    }

    /**
     * This method gets called when the delete button in the viewreminder activity is clicked.
     * @param priority priority of the reminder
     * @return returns a notification if all low priority reminders are deleted
     */
    fun getDeletedLowPriority(priority: String): Cursor {
        //get reference to database
        val db = writableDatabase

        //define select statement
        val query = "SELECT * FROM " + TABLE_REMINDERS +
                " WHERE " + COLUMN_REMINDER_PRIORITY + " = '" + priority + "'"


        // execute select statement and store it in a Cursor
        return db.rawQuery(query, null)
    }

    companion object {
        //initialize database's constants
        const val DATABASE_NAME = "reminder.db"
        const val DATABASE_VERSION = 4
        const val TABLE_REMINDERS = "reminders"
        const val COLUMN_REMINDER_ID = "_id"
        const val COLUMN_REMINDER_NAME = "name"
        const val COLUMN_REMINDER_TEXT = "text"
        const val COLUMN_REMINDER_DATE = "date"
        const val COLUMN_REMINDER_PRIORITY = "priority"
    }
}