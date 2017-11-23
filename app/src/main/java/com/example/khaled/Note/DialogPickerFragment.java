package com.example.khaled.Note;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by khaled on 18/10/2017.
 */

public class DialogPickerFragment extends DialogFragment {
    DatePicker mDatePicker;
    private static final String NewInstace_Tage_date ="com.example.khaled.crime.date";
    public static final String DATE_KEY_BACK =
            "com.khaled.android.criminalintent.date";


    public static DialogPickerFragment newInstace(Date date){
        Bundle bundle = new Bundle();
        bundle.putSerializable(NewInstace_Tage_date,date);
        DialogPickerFragment dialogPickerFragment = new DialogPickerFragment();
        dialogPickerFragment.setArguments(bundle);

        return dialogPickerFragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Date mDate = (Date)getArguments().getSerializable(NewInstace_Tage_date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);




        View view = LayoutInflater.from(getActivity()).inflate(R.layout.date_picker, null);

        mDatePicker =(DatePicker)view.findViewById(R.id.DatePickerID);
        mDatePicker.init(year,month,day,null);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //to send the date to crimefragment
                        int year = mDatePicker.getYear();
                        int day = mDatePicker.getDayOfMonth();
                        int month = mDatePicker.getMonth();
                        Date date = new GregorianCalendar(year, month , day).getTime();
        sendResult(Activity.RESULT_OK, date);



                        FragmentManager fm = getFragmentManager();
                        TimePickerFragment timePickerFragment = new TimePickerFragment();
                        timePickerFragment.show(fm, null);
                        /*

                        FragmentManager fragmentManager = getFragmentManager();
                DialogPickerFragment dialogPickerFragment = DialogPickerFragment.newInstace(mDate);
                dialogPickerFragment.setTargetFragment(NoteFragment.this, REQUEST_DATE);
                dialogPickerFragment.show(fragmentManager ,DIALOG_DATE );
                         */
                    }
                })
                .create();





    }

    private void sendResult(int ResultCode , Date date){
        if (getTargetFragment()==null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(DATE_KEY_BACK , date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),ResultCode,intent);


    }
}
