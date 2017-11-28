package com.example.khaled.Note.AlarmManager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.example.khaled.Note.NoteFragment;
import com.example.khaled.Note.R;
import com.example.khaled.Note.activities.NoteListActivity;
import com.example.khaled.Note.models.Note;
import com.example.khaled.Note.models.NoteLab;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by khaled on 21/11/2017.
 */

public class NoteReminder extends BroadcastReceiver {
    Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.start();
        AddNotification(context);



    }
    /*
     private void setAlarm(Long timemil) {
      AlarmManager  alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), NoteReminder.class);
     PendingIntent   pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);


        alarmManager.setRepeating(AlarmManager.RTC,timemil,AlarmManager.INTERVAL_DAY,pendingIntent);
    }
     */
    public void AddNotification(Context context){
      NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("MyNote Notification");
        builder.setSmallIcon(R.drawable.headernoteackground);
        builder.setContentText("its a reminder of your note!");

        Intent intent = new Intent(context, NoteFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 ,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager =(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());


    }

    public static void setAlarm(Long timemil, Context context, boolean OnOF) {
        boolean onof = true;
        onof = OnOF;
        List<Note> notes = new ArrayList<>();
        notes = NoteLab.get(context).getAllNotes();
        for (int i = 0; i < notes.size(); i++) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, NoteReminder.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i, intent, 0);
            if (OnOF == true) {
                alarmManager.setRepeating(AlarmManager.RTC, timemil, AlarmManager.INTERVAL_DAY, pendingIntent);
            } else {
                alarmManager.cancel(pendingIntent);
            }
        }

    }
}
