package com.example.khaled.Note.AlarmManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import android.provider.Settings;

import com.example.khaled.Note.models.Note;
import com.example.khaled.Note.models.NoteLab;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by khaled on 21/11/2017.
 */

public class NoteReminder extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        mediaPlayer.start();


    }
    /*
     private void setAlarm(Long timemil) {
      AlarmManager  alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), NoteReminder.class);
     PendingIntent   pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);


        alarmManager.setRepeating(AlarmManager.RTC,timemil,AlarmManager.INTERVAL_DAY,pendingIntent);
    }
     */
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
