package com.example.khaled.Note.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.khaled.Note.NoteFragment;

import com.example.khaled.Note.R;
import com.example.khaled.Note.interfaces.InterfaceOnBackPressed;
import com.example.khaled.Note.interfaces.InterfaceOnSelectOptionMenuPager;
import com.example.khaled.Note.models.Note;
import com.example.khaled.Note.models.NoteLab;

import java.util.List;
import java.util.UUID;

public class ViewPagerActivity extends AppCompatActivity {
    ViewPager mViewPager;
    public static InterfaceOnBackPressed interfaceBack;
    public static final String TAG ="ViewPagerToast";
    static InterfaceOnSelectOptionMenuPager mInterfaceOnSelectOption;
    private List<Note> mNote;
    Toolbar mToolbar;
private static final String CRIMID_KEY ="com.example.khaled.crime.crimeIDViewPager";

    public static Intent newIntent(Context context, UUID crimeid){
        Intent intent = new Intent(context,ViewPagerActivity.class);
        intent.putExtra(CRIMID_KEY,crimeid);

        return intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
       /* mToolbar=(Toolbar)findViewById(R.id.ToolbarnorecontentID);
        setSupportActionBar(mToolbar);*/




        mViewPager=(ViewPager)findViewById(R.id.ViewPagerID);
        UUID crimeID =(UUID) getIntent().getSerializableExtra(CRIMID_KEY);

        mNote = NoteLab.get(this).getCrimes();
        FragmentManager fm = getSupportFragmentManager();
        //fragmentstatepager adapter diffrent the fragmentpageradapter
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Note note = mNote.get(position);
                return NoteFragment.newInstance(note.getId());
            }

            @Override
            public int getCount() {
                return mNote.size();
            }


        });

// to not start the viewpager from the bigaining
        for (int i = 0; i< mNote.size() ; i++){
            if (mNote.get(i).getId().equals(crimeID)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }




    }



    public static void setoptionmenu(InterfaceOnSelectOptionMenuPager interfaceOnSelectOption){
       mInterfaceOnSelectOption = interfaceOnSelectOption;
    }
// mInterfaceOnSelectOption.onSelectOptionMenu(item ,ViewPagerActivity.this);
public static void setOnBackPressed(InterfaceOnBackPressed interfaceback){
    interfaceBack =interfaceback;
}

    @Override
    public void onBackPressed() {

        if (interfaceBack!=null) {

            interfaceBack.InterfaceOnBackPressed();
        }
        super.onBackPressed();
    }
}
