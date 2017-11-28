package com.example.khaled.Note.AlarmManager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.example.khaled.Note.NoteFragment;
import com.example.khaled.Note.R;
import com.example.khaled.Note.activities.NoteListActivity;
import com.example.khaled.Note.activities.ViewPagerActivity;
import com.example.khaled.Note.models.Note;
import com.example.khaled.Note.models.NoteLab;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.UUID;

/**
 * Created by khaled on 21/11/2017.
 */

public class NoteReminder extends BroadcastReceiver {
    public static final String TITLE_OF_NOTEALARM = "TITLEOFNOTEALARM";
    public static final String CONTENT_OF_NOTALARM = "CONTENTOFNOTALARM";
    public static final String NOTE_IDKEY = "NOTEIDKEY";
    String mTitle, mContent;
   public static UUID mNoteID;
    Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        mTitle = intent.getStringExtra(TITLE_OF_NOTEALARM);
        mContent = intent.getStringExtra(CONTENT_OF_NOTALARM);


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
        builder.setContentTitle(mTitle);
        builder.setSmallIcon(R.drawable.ic_note_add_white_24dp);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.headernoteackground);

        builder.setLargeIcon(bitmap);

        builder.setContentText(mContent);

        Intent intent = new Intent(context, ViewPagerActivity.class);
        intent.putExtra(NOTE_IDKEY, mNoteID);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 ,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager =(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0,builder.build());


    }

    public static void setAlarm(Long timemil, Context context, boolean OnOF, String Title , String Content, UUID NoteID) {
        mNoteID = NoteID;
        boolean onof = true;
        onof = OnOF;
        List<Note> notes = new ArrayList<>();
        notes = NoteLab.get(context).getAllNotes();
       // for (int i = 0; i < notes.size(); i++) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, NoteReminder.class);
            intent.putExtra(TITLE_OF_NOTEALARM,Title);
            intent.putExtra(CONTENT_OF_NOTALARM,Content);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
            if (OnOF == true) {
                alarmManager.set(AlarmManager.RTC, timemil, pendingIntent);
            } else {
                alarmManager.cancel(pendingIntent);
            }
      //  }

    }
}
