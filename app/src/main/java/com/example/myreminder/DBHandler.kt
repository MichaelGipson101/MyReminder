package com.example.myreminder;

import android.content.ContentValues;
import android.database.Cursor;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    //initialize database's constants
    public static final String DATABASE_NAME = "reminder.db";
    public static final int DATABASE_VERSION = 4;

    public static final String TABLE_REMINDERS = "reminders";
    public static final String COLUMN_REMINDER_ID = "_id";
    public static final String COLUMN_REMINDER_NAME = "name";
    public static final String COLUMN_REMINDER_TEXT = "text";
    public static final String COLUMN_REMINDER_DATE = "date";
    public static final String COLUMN_REMINDER_PRIORITY = "priority";


    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super (context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Creates table for reminder database
     * @param sqLiteDatabase reference to reminder db
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create reminder table and store it as a string
        String query = "CREATE TABLE " + TABLE_REMINDERS + "(" +
                COLUMN_REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_REMINDER_NAME + " TEXT, " +
                COLUMN_REMINDER_TEXT + " TEXT, " +
                COLUMN_REMINDER_DATE + " TEXT, " +
                COLUMN_REMINDER_PRIORITY + " TEXT);";

        //statement execute
        sqLiteDatabase.execSQL(query);
    }

    /**
     * Creates a new version of the reminder database
     * @param sqLiteDatabase reference to reminder db
     * @param oldVersion old version of reminder db
     * @param newVersion new version of reminder db
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        //define drop statement and store as a string
        String query = "DROP TABLE IF EXISTS " + TABLE_REMINDERS;

        //statement execute
        sqLiteDatabase.execSQL(query);

        //call the method that creates
        onCreate(sqLiteDatabase);
    }

    /**
     * This method is called when the add button in the action bar of the createReminder activity is
     * clicked. It inserts a new row into the reminder table.
     * @param name reminder name
     * @param text reminder text
     * @param date reminder date
     */
    public void addAReminder(String name, String text, String date, String priority) {
        //get reference to reminder DB
        SQLiteDatabase db = getWritableDatabase();

        //initialize contentvalues obj
        ContentValues values = new ContentValues();

        //put data into contentvalues obj
        values.put(COLUMN_REMINDER_NAME, name);
        values.put(COLUMN_REMINDER_TEXT, text);
        values.put(COLUMN_REMINDER_DATE, date);
        values.put(COLUMN_REMINDER_PRIORITY, priority);

        //insert data in contentvalues obj into reminder table
        db.insert(TABLE_REMINDERS, null, values);

        //close db reference
        db.close();

    }

    /**
     * This method gets called when the main activity is created. It will select and return
     * all of the data from the reminder table.
     *
     * @return Cursor that contains all of the data from the reminder table.
     */
    public Cursor getReminders() {
        //get reference to the reminder database
        SQLiteDatabase db = getWritableDatabase();

        //define select statement and store it in a string
        String query = "SELECT * FROM " + TABLE_REMINDERS;

        //execute the statement and return it as a cursor
        return db.rawQuery(query, null);
    }

    /**
     * this method is going to get called when the viewreminder activity is started
     * @param reminderId database id of clicked reminder
     * @return Cursor that contains all of the data associated with the clicked reminder
     */
    public Cursor getReminder (Integer reminderId) {
        //get reference to reminder db
        SQLiteDatabase db = getWritableDatabase();

        //define select statement
        String query = "SELECT * FROM " + TABLE_REMINDERS +
                " WHERE " + COLUMN_REMINDER_ID + " = " + reminderId;

        //execute select statement
        return db.rawQuery(query, null);
    }

    public Cursor getHighPriorityReminders() {
        //get reference to reminder DB
        SQLiteDatabase db = getWritableDatabase();

        //define select statement and store it in a string
        String query = "SELECT * FROM " + TABLE_REMINDERS +
                " WHERE " + COLUMN_REMINDER_PRIORITY + " = " + "'High'";

        //execute select statement
        return db.rawQuery(query, null);
    }
    public Cursor getMediumPriorityReminders() {
        //get reference to reminder DB
        SQLiteDatabase db = getWritableDatabase();

        //define select statement and store it in a string
        String query = "SELECT * FROM " + TABLE_REMINDERS +
                " WHERE " + COLUMN_REMINDER_PRIORITY + " = " + "'Medium'";

        //execute select statement
        return db.rawQuery(query, null);
    }
    public Cursor getLowPriorityReminders() {
        //get reference to reminder DB
        SQLiteDatabase db = getWritableDatabase();

        //define select statement and store it in a string
        String query = "SELECT * FROM " + TABLE_REMINDERS +
                " WHERE " + COLUMN_REMINDER_PRIORITY + " = " + "'Low'";

        //execute select statement
        return db.rawQuery(query, null);
    }
    /**
     * This method gets called when the delete button in the action bar of the view reminder activity gets clicked
     * @param reminderId database id of the reminder to be deleted
     */
    public void deleteReminder(Integer reminderId) {
        SQLiteDatabase db = getWritableDatabase();

        //define a delete statement and store it in a string
        String query = "DELETE FROM " + TABLE_REMINDERS +
                " WHERE " + COLUMN_REMINDER_ID + " = " + reminderId;

        //execute statement
        db.execSQL(query);

        //close db ref
        db.close();
    }
    /**
     * This method gets called when the delete button in the viewreminder activity is clicked.
     * @param priority priority of the reminder
     * @return returns a notification if all high priority reminders are deleted
     */
    public Cursor getDeletedHighPriority(String priority) {
        //get reference to database
        SQLiteDatabase db = getWritableDatabase();

        //define select statement
        String query = "SELECT * FROM " + TABLE_REMINDERS +
                " WHERE " + COLUMN_REMINDER_PRIORITY + " = '" + priority + "'";


        // execute select statement and store it in a Cursor
        return db.rawQuery(query, null);
    }
    /**
     * This method gets called when the delete button in the viewreminder activity is clicked.
     * @param priority priority of the reminder
     * @return returns a notification if all medium priority reminders are deleted
     */
   public Cursor getDeletedMediumPriority(String priority) {
       //get reference to database
       SQLiteDatabase db = getWritableDatabase();

       //define select statement
       String query = "SELECT * FROM " + TABLE_REMINDERS +
               " WHERE " + COLUMN_REMINDER_PRIORITY + " = '" + priority + "'";


       // execute select statement and store it in a Cursor
       return db.rawQuery(query, null);
    }
    /**
     * This method gets called when the delete button in the viewreminder activity is clicked.
     * @param priority priority of the reminder
     * @return returns a notification if all low priority reminders are deleted
     */
     public Cursor getDeletedLowPriority(String priority) {
         //get reference to database
         SQLiteDatabase db = getWritableDatabase();

         //define select statement
         String query = "SELECT * FROM " + TABLE_REMINDERS +
                 " WHERE " + COLUMN_REMINDER_PRIORITY + " = '" + priority + "'";


         // execute select statement and store it in a Cursor
         return db.rawQuery(query, null);
     }
}
