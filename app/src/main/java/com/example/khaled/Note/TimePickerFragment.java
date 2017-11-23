package com.example.khaled.Note;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.khaled.Note.AlarmManager.NoteReminder;
import com.example.khaled.Note.interfaces.InterfaceOnLongClick;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimePickerFragment extends DialogFragment {

    public static final String TIMEMILL_BACK ="TIMEMILLBACK";
    TimePicker mTimePicker;

    public static TimePickerFragment newInstace(){

        TimePickerFragment timePickerFragment = new TimePickerFragment();


        return timePickerFragment;


    }


    public TimePickerFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_time_picker, null);
mTimePicker =(TimePicker)view.findViewById(R.id.TimePickerID);
        Calendar calendar = Calendar.getInstance();
       // int hour= calendar.get(calendar.ge)
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.title_of_timePicker)
                .setView(view)
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
 Calendar calendar1 = Calendar.getInstance();

                        if (Build.VERSION.SDK_INT>=23){
                        calendar1.set(
                                calendar1.get(Calendar.YEAR),
                            calendar1.get(Calendar.MONTH),
                            calendar1.get(Calendar.DAY_OF_MONTH),
                            mTimePicker.getHour(),
                            mTimePicker.getMinute(),
                                0

                               );
                        }else {
                            calendar1.set(
                                    calendar1.get(Calendar.YEAR),
                            calendar1.get(Calendar.MONTH),
                            calendar1.get(Calendar.DAY_OF_MONTH),
                            mTimePicker.getCurrentHour(),
                            mTimePicker.getCurrentMinute(),
                                    0

                               );
                        }
                        int Hr = mTimePicker.getCurrentHour();
                        int Min = mTimePicker.getCurrentMinute();

                       setAlarm(calendar1.getTimeInMillis());
long mTimeInMillis =  calendar1.getTimeInMillis();
                        setResult(Activity.RESULT_OK,mTimeInMillis);
                        Toast.makeText(getActivity(), "its"+Hr +" "+Min, Toast.LENGTH_SHORT).show();


                    }
                }).create();
    }

    private void setResult(int resultOk, long mTimeInMillis) {


        if (getTargetFragment()==null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(TIMEMILL_BACK,mTimeInMillis);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultOk,intent);

    }

    private void setAlarm(long timeInMillis) {
        AlarmManager alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), NoteReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC,timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent);


    }


}
