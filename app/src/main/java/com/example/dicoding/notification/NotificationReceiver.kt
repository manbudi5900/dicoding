package com.example.dicoding.notification

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.dicoding.R
import com.example.dicoding.Utama_Nav
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class NotificationReceiver(applicationContext: Context? = null) :
    BroadcastReceiver() {

    private val EXTRA_TYPE = "type"
    private val TYPE_DAILY = "daily_reminder"
    private val TYPE_RELEASE = "release_reminder"
    private val ID_DAILY_REMINDER = 1000
    private val ID_RELEASE_TODAY = 1001

    private var context: Context? = applicationContext

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        if (type == TYPE_DAILY) {
            showDailyReminder(context)
        } else if (type == TYPE_RELEASE) {
            getReleaseToday(context)
        }
    }

    private fun getReminderTime(type: String): Calendar {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(
            Calendar.HOUR_OF_DAY,
            if (type == TYPE_DAILY) 7 else 8
        )
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }
        return calendar
    }

    private fun getReminderIntent(type: String): Intent {
        val intent = Intent(context, NotificationReceiver::class.java)
        intent.putExtra(EXTRA_TYPE, type)
        return intent
    }

    fun setReleaseTodayReminder() {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_RELEASE_TODAY,
            getReminderIntent(TYPE_RELEASE),
            0
        )

        val alarmManager =
            context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            getReminderTime(TYPE_RELEASE).timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun setDailyReminder() {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            ID_DAILY_REMINDER,
            getReminderIntent(TYPE_DAILY),
            0
        )

        val alarmManager =
            context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            getReminderTime(TYPE_DAILY).timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun getReleaseToday(context: Context) {
        val client = AsyncHttpClient()
        val currentDate = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateFormatted = dateFormat.format(currentDate.time)
        val url =
            "https://api.themoviedb.org/3/discover/movie?api_key=7b3ad7574fb018c27ba6b1ec6da5c6e0&primary_release_date.gte=$dateFormatted&primary_release_date.lte=$dateFormatted"

        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {

                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")
                    Log.d("ini", list.toString())

                    for (i in 0 until list.length()) {
                        val movie = list.getJSONObject(i)

                        val title: String = movie.getString("original_title")
                        val id: String = movie.getString("id")
                        val desc = movie.getString("overview")
                        showReleaseToday(context, title, desc, id.toInt())
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })

    }

    private fun showReleaseToday(
        context: Context,
        title: String,
        desc: String,
        id: Int
    ) {
        val channelId = "Channel_2"
        val channelName = "Today release channel"
        val intent = Intent(context, Utama_Nav::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, id, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val uriRingtone: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.ic_notifications_black_24dp
                )
            )
            .setContentTitle(title)
            .setContentText(desc)
            .setSubText(context.getString(R.string.release_reminder_message))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(uriRingtone)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notification: Notification = mBuilder.build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mBuilder.setChannelId(channelId)
            mNotificationManager.createNotificationChannel(channel)
        }
        mNotificationManager.notify(id, notification)
    }

    private fun showDailyReminder(context: Context) {
        val notificationId = 1
        val channelId = "Channel_1"
        val channelName = "Daily Reminder channel"
        val intent = Intent(context, Utama_Nav::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, notificationId, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val uriRingtone: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.ic_notifications_black_24dp
                )
            )
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(context.resources.getString(R.string.daily_reminder_message))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setSound(uriRingtone)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notification: Notification = mBuilder.build()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            mBuilder.setChannelId(channelId)
            mNotificationManager.createNotificationChannel(channel)
        }
        mNotificationManager.notify(notificationId, notification)
    }

    private fun cancelReminder(context: Context, type: String) {
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val requestCode = if (type.equals(
                TYPE_DAILY,
                ignoreCase = true
            )
        ) ID_DAILY_REMINDER else ID_RELEASE_TODAY
        val pendingIntent =
            PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    fun cancelDailyReminder(context: Context?) {
        if (context != null) {
            cancelReminder(context, TYPE_DAILY)
        }
    }

    fun cancelReleaseToday(context: Context?) {
        if (context != null) {
            cancelReminder(context, TYPE_RELEASE)
        }
    }

}