package com.example.myreminder

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

/**
 * This class creates a notification channel for myreminder. Notification channels became
 * necessary starting with android oreo to be able to show notifications. This notification channel
 * for myreminder will be created as soon as the application starts and will be available for all other
 * activity classes to use.
 */
class App : Application() {
    //override the oncreate method
    override fun onCreate() {
        super.onCreate()

        //call method that creates notification channel for myreminder
        createNotificationChannel()
    }

    /**
     * This method creates the notification channel for myreminder
     */
    protected fun createNotificationChannel() {
        //check if android oreo (API 26) or higher because notification channels were not available
        //on lower versions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //initialize notification channel - must give it an ID, name, and importance
            val channelreminder = NotificationChannel(
                    CHANNEL_REMINDER_ID,
                    "Channel Reminder",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            //customize the notification channel - set its description
            channelreminder.description = "This is the myreminder channel."

            //initialize a notification manager
            val manager = getSystemService(NotificationManager::class.java)

            //create shopper notification channel
            manager.createNotificationChannel(channelreminder)
        }
    }

    companion object {
        //delcare and initialize a channel ID
        const val CHANNEL_REMINDER_ID = "channelreminder"
    }
}