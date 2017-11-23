package com.example.khaled.Note;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.khaled.Note.AlarmManager.NoteReminder;
import com.example.khaled.Note.activities.NoteListActivity;
import com.example.khaled.Note.activities.ViewPagerActivity;
import com.example.khaled.Note.interfaces.InterfaceOnBackPressed;
import com.example.khaled.Note.models.Note;
import com.example.khaled.Note.models.NoteLab;
import com.example.khaled.Note.utils.PicUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment implements InterfaceOnBackPressed /*implementsInterfaceOnSelectOptionMenuPager*/ {


    public static final int REQUEST_TIMEPICKER = 5;
    EditText mEditText, mContentText;
    public Toolbar mToolbar;
    Button mDateButtn ,ChooseContactbtn;
    CheckBox mCheckBox;
    private ImageView IMGview, IMGviewGallery;
    private  boolean canTakePic;
    public Note mNote;
    private Button TakepicBtn;
    private File mPicFile, mPicGalleryFile;
    public  String Folder;
  public static final String FOLDER_ARGS ="FOLDERARGS";
    public static final String TAG ="crimeFragment";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_CONTACT =1;
    private static final int REQUEST_PIC =2;
    public static final int REQUEST_PICGALLERY = 3;

    Intent IntentPickPicFromGallery;
    Uri uriDataGallery;
    InputStream inputStreamGallery;
    Intent cameraintent;
    Long TIMEMIL;
    //AlarmManager alarmManager;
    //PendingIntent pendingIntent;

   // Intent mDataGallery;


    Date mDate;
    private static final String ARG_CRIME_ID = "crime_id";

    private static final String DIALOG_DATE = "DialogDate";

    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment newInstance(UUID crimeIDARG,String Folder){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID,crimeIDARG);
        args.putString(FOLDER_ARGS,Folder);
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // ViewPagerActivity.setoptionmenu(this);


        Log.d(ViewPagerActivity.TAG,"OnCreateFragment.........");


        /*

        Arguments

        send the data from Activty be to activity c by Intent
        then get the data from the activty c throw Arguments
        and send it to the Fragment of activity c by agrement

        Intent
        getExtra
        argument
        put
        argument
        get


         */


       /* this was for intent UUID CrimeID =(UUID) getActivity().getIntent()
                .getSerializableExtra(MainActivity.Crime_ID_KEY);*/
    Folder = getArguments().getString(FOLDER_ARGS);
       UUID CrimeID =(UUID)getArguments().getSerializable(ARG_CRIME_ID);
        mNote = NoteLab.get(getActivity()).getCrime(CrimeID);

        //requere premission
        mPicFile= NoteLab.get(getActivity()).getPhotoFile(mNote);
      /* alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), NoteReminder.class);
       pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);*/
    }



//add for database
    @Override
    public void onPause() {
        super.onPause();

        Log.d(ViewPagerActivity.TAG,"onPaused Fragment");

        NoteLab.get(getActivity()).updateCrime(mNote);
    }



    public  void deleteEmptyNote(){
        if (mNote.getTitle()==null && mNote.getContent()== null){
            NoteLab.get(getActivity()).deleteNote(mNote);
        }
    }

    @Override
    public void onStop() {
        Log.d(ViewPagerActivity.TAG,"onStoop............");
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        setHasOptionsMenu(true);
        Log.d(ViewPagerActivity.TAG,"onCreateView....");
      //  mNote.setFolder(Folder);

  mToolbar =(Toolbar)v.findViewById(R.id.ToolbarnorecontentID);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
       // mToolbar.setTitle("Khaled");
        mToolbar.inflateMenu(R.menu.menu_note_content);
        /*if (((AppCompatActivity)getActivity()).getSupportActionBar()!=null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }*/

        mToolbar.setNavigationIcon(R.drawable.ic_done_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();
                deleteEmptyNote();



            }
        });
        TakepicBtn =(Button)v.findViewById(R.id.takepicbtnID);
       TakepicBtn.setVisibility(View.GONE);



        IMGview =(ImageView)v.findViewById(R.id.IMGviewID);
        IMGview.setVisibility(View.GONE);

        IMGviewGallery =(ImageView)v.findViewById(R.id.IMGviewGalleryID);
        IMGviewGallery.setVisibility(View.GONE);





        ChooseContactbtn=(Button)v.findViewById(R.id.choosecontactbtnID);
        if (mNote.getContactnumber()!=null) {
            ChooseContactbtn.setText(mNote.getContactnumber());
        }
        final Intent intentcontact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        ChooseContactbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult(intentcontact , REQUEST_CONTACT);


            }
        });


        PackageManager packageManager = getActivity().getPackageManager();
        if (packageManager.resolveActivity(intentcontact ,PackageManager.MATCH_DEFAULT_ONLY)==null){
            ChooseContactbtn.setEnabled(false);
        }

         cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);



        canTakePic = mPicFile!= null && cameraintent.resolveActivity(packageManager)!=null;

   TakepicBtn.setEnabled(canTakePic);
        if (canTakePic){
            Uri uri = Uri.fromFile(mPicFile);
            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }

        TakepicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(cameraintent, REQUEST_PIC);
            }
        });







        mCheckBox=(CheckBox)v.findViewById(R.id.crime_solvedCheckID);
       // mCheckBox.setChecked(mNote.isSolved());
        mDateButtn=(Button)v.findViewById(R.id.crime_dateBtnID);
        mEditText =(EditText)v.findViewById(R.id.EditTextFragmentID);
        mEditText.setText(mNote.getTitle());


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                     mNote.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
       // mEditText.setTextColor(mNote.getTitleColor());

       // mDateButtn.setText(mNote.getDate().toString());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getDefault());
        String date = df.format(mNote.getDate());
        mDateButtn.setText(date);
        //mDateButtn.setEnabled(false);
        mCheckBox.setChecked(mNote.isSolved());

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mNote.setSolved(true);

            }
        });

        mDateButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDate = mNote.getDate();
                FragmentManager fragmentManager = getFragmentManager();
                DialogPickerFragment dialogPickerFragment = DialogPickerFragment.newInstace(mDate);
                dialogPickerFragment.setTargetFragment(NoteFragment.this, REQUEST_DATE);
                dialogPickerFragment.show(fragmentManager ,DIALOG_DATE );

            }
        });

        mContentText =(EditText)v.findViewById(R.id.NoteContentID);
        mContentText.setText(mNote.getContent());
        mContentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                   mNote.setContent(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        PicUpdate();
       // PicGalleryUpdate(mDataGallery);



        mDateButtn.setVisibility(View.GONE);
        ChooseContactbtn.setVisibility(View.GONE);
        mCheckBox.setVisibility(View.GONE);
        ViewPagerActivity.setOnBackPressed(this);




        return v;



    }

   /* public void DeleteNote(){
        String Title = mEditText.toString();
        String Content = mContentText.toString();

        if (Title.length()<1 && Content.length()<1){
            NoteLab.get(getActivity()).deleteNote(mNote);
        }else if ((Title.length()>0 || Content.length()>0)){
            Toast.makeText(getActivity(), R.string.saved, Toast.LENGTH_SHORT).show();
        }
    }*/
    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d(ViewPagerActivity.TAG,"OnAttach.....");
    }

    @Override
    public void onStart() {
        Log.d(ViewPagerActivity.TAG,"onStart...........");
        super.onStart();
    }

    @Override
    public void onDestroy() {
        deleteEmptyNote();

        super.onDestroy();
    }

    @Override
    public void onDetach() {
        deleteEmptyNote();

        Toast.makeText(getActivity(), R.string.saved, Toast.LENGTH_SHORT).show();
        super.onDetach();
    }




//////////////OnAvtivityResult*************************************

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!= Activity.RESULT_OK){
            return;
        }
        if (requestCode == REQUEST_DATE){
            Date date =(Date)data
                    .getSerializableExtra(DialogPickerFragment.DATE_KEY_BACK);
            mNote.setDate(date);
            DateUpdate();
        }else if (requestCode == REQUEST_CONTACT && data!=null){
            Uri contactUri = data.getData();
            String[] query = new String[]{
                    ContactsContract.Contacts.DISPLAY_NAME
            };

            Cursor c = getActivity().getContentResolver().query(contactUri, query,null,null,null);

            try {
                if (c.getCount()==0){
                    return;
                }

                c.moveToFirst();
                String contact = c.getString(0);
                mNote.setContactnumber(contact);
                ChooseContactbtn.setText(contact);
            }finally {
                c.close();
            }

        }else if (requestCode ==REQUEST_PIC){
            PicUpdate();

        }else if (requestCode ==REQUEST_PICGALLERY){
         /*   Uri uriDataGallery = data.getData();
            InputStream inputStreamGallery;*/


           PicGalleryUpdate(data);




         /*  try {
                inputStreamGallery = getActivity().getContentResolver().openInputStream(uriDataGallery);
                Bitmap bitmapImageFromGallery = BitmapFactory.decodeStream(inputStreamGallery);

                IMGviewGallery.setImageBitmap(bitmapImageFromGallery);
              IMGviewGallery.setVisibility(View.VISIBLE);




            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

        } else if (requestCode==REQUEST_TIMEPICKER){
            /*
            Date date =(Date)data
                    .getSerializableExtra(DialogPickerFragment.DATE_KEY_BACK);
             */
            TIMEMIL =data.getLongExtra(TimePickerFragment.TIMEMILL_BACK,0);
            Toast.makeText(getActivity(), ""+TIMEMIL, Toast.LENGTH_SHORT).show();

            //setAlarm(TIMEMIL);
        }


    }


    private void setAlarm(Long timemil) {
      AlarmManager  alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), NoteReminder.class);
     PendingIntent   pendingIntent = PendingIntent.getBroadcast(getActivity(),0,intent,0);


        alarmManager.setRepeating(AlarmManager.RTC,timemil,AlarmManager.INTERVAL_DAY,pendingIntent);
    }
    /*private void AlarmCancel(){
        alarmManager.cancel(pendingIntent);
    }*/


    //dateupdate
    private void DateUpdate(){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setTimeZone(TimeZone.getDefault());
        String date = df.format(mNote.getDate());
       // mDateButtn.setText(mNote.getDate().toString());

        mDateButtn.setText(date);
    }


   /* @Override
    public void onSelectOptionMenu(MenuItem item, ViewPagerActivity viewPagerActivity) {

            int id = item.getItemId();
            if (id== R.id.sharenotefragmentID)
                Toast.makeText(getActivity(), "share", Toast.LENGTH_SHORT).show();



    }*/

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_note_content, menu);

        MenuItem camerabtn = (MenuItem) menu.findItem(R.id.IMGviewID);
        //menu.findItem(R.id.cameratoolbarID).setEnabled(!camerabtn);
        super.onCreateOptionsMenu(menu, inflater);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
      if (id==R.id.GalleryImgID){
          IntentPickPicFromGallery = new Intent(Intent.ACTION_PICK);

          //file to find the pic
          mPicGalleryFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
          //the string text of path
          String mPickGalleryFilePath = mPicGalleryFile.getPath();
          //get the uri of it
          Uri uri = Uri.parse(mPickGalleryFilePath);

          //the type to set all image type *

          IntentPickPicFromGallery.setDataAndType(uri,"image/*");

          //start activity

          startActivityForResult(IntentPickPicFromGallery,REQUEST_PICGALLERY);


      }
        if (id== R.id.sharenotefragmentID){
           /* Toast.makeText(getActivity(), "viewpager", Toast.LENGTH_SHORT).show();
            Log.d(ViewPagerActivity.TAG,"share pressed");

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT , getNoteContent());
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.crime_report_subject));
            intent = Intent.createChooser( intent , getString(R.string.send_report));
            startActivity(intent);*/

            Toast.makeText(getActivity(), mNote.getFolder(), Toast.LENGTH_SHORT).show();

        }

        if (id== R.id.cameratoolbarID){
            mNote.setTitleColor(R.color.midbrown);
            mEditText.setTextColor(mNote.getTitleColor());

          /*  cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri uri = Uri.fromFile(mPicFile);
            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, uri);


            startActivityForResult(cameraintent, REQUEST_PIC);*/




        }
        if (id==R.id.AlarmNoteContectID){
            /*
                mDate = mNote.getDate();
                FragmentManager fragmentManager = getFragmentManager();
                DialogPickerFragment dialogPickerFragment = DialogPickerFragment.newInstace(mDate);
                dialogPickerFragment.setTargetFragment(NoteFragment.this, REQUEST_DATE);
                dialogPickerFragment.show(fragmentManager ,DIALOG_DATE );
             */

            FragmentManager fm = getFragmentManager();
            TimePickerFragment timePickerFragment =  TimePickerFragment.newInstace();
            timePickerFragment.setTargetFragment(NoteFragment.this, REQUEST_TIMEPICKER);
            timePickerFragment.show(fm,null);
        }

        if (id==R.id.TurnOffAlarmID){
            //AlarmCancel();
        }

        return super.onOptionsItemSelected(item);
    }


 private String getNoteContent(){
     String checksolved = null;
     if (mNote.isSolved()){
         checksolved= getString(R.string.crime_report_solved);
     }else{
         checksolved=getString(R.string.crime_report_unsolved);
     }

     String dateFormat = "EEE, MMM dd - yyyy";
     String dateString = (String) android.text.format.DateFormat.format( dateFormat, mNote.getDate());

     String Contactnumber= mNote.getContactnumber();
     if (Contactnumber == null){
         Contactnumber= getString(R.string.crime_report_no_suspect);
     }else {
         Contactnumber = getString(R.string.crime_report_suspect , Contactnumber);
     }

     String sharecontent = getString(R.string.share_note, mNote.getTitle(), mNote.getContent(), dateString ,checksolved, Contactnumber);

     return sharecontent;
 }

 /*


    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
  */

 public void PicUpdate(){
     if (mPicFile==null|| !mPicFile.exists()){
         IMGview.setImageDrawable(null);
     }else {
         Bitmap bitmap = PicUtils.getScaledPic(mPicFile.getPath(), getActivity());
         IMGview.setVisibility(View.VISIBLE);
         IMGview.setImageBitmap(bitmap);
     }


 }

public void PicGalleryUpdate(Intent data) {
        if (mPicGalleryFile==null || !mPicGalleryFile.exists()){
            IMGviewGallery.setImageDrawable(null);
        }else {
            uriDataGallery = data.getData();
            try {
                inputStreamGallery = getActivity().getContentResolver().openInputStream(uriDataGallery);
                Bitmap bitmapImageFromGallery = BitmapFactory.decodeStream(inputStreamGallery);

                IMGviewGallery.setImageBitmap(bitmapImageFromGallery);
                Log.d(TAG,"PicGalleryUpdate");





            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            IMGviewGallery.setVisibility(View.VISIBLE);
        }





    }


    @Override
    public void InterfaceOnBackPressed() {
        getActivity().finish();

        deleteEmptyNote();
       /* Intent intent = new Intent(getActivity(), NoteListActivity.class);
        startActivity(intent);*/



       // getActivity().getSupportFragmentManager().popBackStack();





      Log.d(TAG,"###################################onBackPressed.......................................");




    }
}
