package com.example.egtimer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var initialTimer=6000L
    lateinit var tv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var btn=findViewById<Button>(R.id.button)
        tv=findViewById(R.id.tv)
        btn.setOnClickListener {

            countTimer()


        }
    }
    fun countTimer(){

            object: CountDownTimer(initialTimer, 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    initialTimer=millisUntilFinished
                    tv.text="${initialTimer / 1000}"
                }

                override fun onFinish() {
                    //title="Finish!"
                    tv.text="Your Eggs Is Ready!!"
                    initialTimer=0L
                     notification()

                }
            }.start()


    }
    fun notification(){
        val channelId = "myapp.notifications"
        val description = "Notification App Example"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, MainActivity2::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel = NotificationChannel(channelId,description,
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
            var builder = Notification.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
                // .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.imgnotcopy))
                .setContentIntent(pendingIntent)
                .setContentTitle("Cooking Notification")
                .setContentText("Your Egg Is Ready")
            notificationManager.notify(1234, builder.build())

        } else {
            var builder = Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .setContentTitle("Cooking Notification")
                .setContentText("Your Egg Is Ready")
            notificationManager.notify(1234, builder.build())

        }
    }
}