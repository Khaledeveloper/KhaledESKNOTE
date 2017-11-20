package com.example.khaled.Note;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khaled.Note.activities.ViewPagerActivity;
import com.example.khaled.Note.adapters.NoteMListAdapter;
import com.example.khaled.Note.interfaces.InterfaceOnCreatePopUpMenuMain;
import com.example.khaled.Note.interfaces.InterfaceOnLongClick;
import com.example.khaled.Note.interfaces.InterfacePopupMenuMainRecycler;
import com.example.khaled.Note.models.Note;
import com.example.khaled.Note.models.NoteLab;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends Fragment implements InterfaceOnLongClick,InterfacePopupMenuMainRecycler, SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener ,InterfaceOnCreatePopUpMenuMain {
    public static final String TAG = "TAG";
    NoteMListAdapter mAdapter;
    Toolbar mToolbar;
    public String Folder ="MainList";
    FloatingActionButton mFAB;
    PopupMenu pop;
    private DrawerLayout mDrawerLayout;
    //private ActionBarDrawerToggle mActionBarDrawerToggle;
    static boolean isSelected = false;
    List<Note> notes;
    ArrayList<Note> SelectedItems = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
      //  mAdapter = new NoteMListAdapter();

       // mAdapter.setOnlongClick(this);

    }

    RecyclerView mRecyclerView;
    public NoteListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);



mRecyclerView =(RecyclerView)view.findViewById(R.id.mRecyclerviewID);
        mToolbar=(Toolbar)view.findViewById(R.id.ToolbarrecyclerviewID);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        mDrawerLayout=(DrawerLayout)view.findViewById(R.id.DrawerLayoutMainID);

        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,mToolbar,R.string.Enter_Content ,R.string.date_picker_title);

        if (((AppCompatActivity)getActivity()).getSupportActionBar()!=null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        }
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        NavigationView navigationView =(NavigationView)view.findViewById(R.id.NavigationMainID);
        navigationView.setNavigationItemSelectedListener(this);



        TextView textViewToolbar =(TextView)view.findViewById(R.id.TextviewToolbarRecyclerviewID);



        mFAB =(FloatingActionButton)view.findViewById(R.id.FABmainID);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewCrime();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerUpdate();

        if (!isSelected){
            textViewToolbar.setVisibility(View.GONE);

        }

        if (Folder.equals("Trash")){
            CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) mFAB.getLayoutParams();
            p.setAnchorId(View.NO_ID);
            mFAB.setLayoutParams(p);
            mFAB.setVisibility(View.GONE);
        }



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        /*getActivity().finish();
        startActivity(getActivity().getIntent());
         */

        RecyclerUpdate();
    }

    private void RecyclerUpdate(){
        NoteLab noteLab = NoteLab.get(getActivity());
         notes = noteLab.getCrimes(Folder);
        for (int i=0; i<notes.size(); i+=4){
            Note note = new Note();
            notes.add(i,note);
        }
        if (mAdapter == null) {
            mAdapter = new NoteMListAdapter(notes,this, this,this,getActivity());
            mRecyclerView.setAdapter(mAdapter);
        }else {
            mAdapter.setCrimes(notes);
            mAdapter.notifyDataSetChanged();
        } //the condation added in order to work with onResume to notify only


       // RecyclerAdmob();


       /* StoryModelM storyModelM1 = new StoryModelM();
        storyModelM1.setViewType(2);
        mModel.add(1 , storyModelM1);
        mModel.add(5 ,storyModelM1);*/

       /*
       public void recyclerviewAd(){

        for (int i = 0 ; i<mModel.size() ; i+=4){
            AdView adViewRecycler = new AdView(this);
            adViewRecycler.setAdSize(new AdSize(320 , 150));
            adViewRecycler.setAdUnitId("ca-app-pub-1156456518677227/5827654096");
            adViewRecycler.loadAd(new AdRequest.Builder().build());
            StoryModelM storyad = new StoryModelM(adViewRecycler);
            mModel.add(i,adViewRecycler);
        }

    }
        */






    }

    public void RecyclerAdmob(){

        /**
         * public void recyclerviewAd(){

         for (int i = 0 ; i<mModel.size() ; i+=4){
         AdView adViewRecycler = new AdView(this);
         adViewRecycler.setAdSize(new AdSize(320 , 150));
         adViewRecycler.setAdUnitId("ca-app-pub-1156456518677227/5827654096");
         adViewRecycler.loadAd(new AdRequest.Builder().build());
         StoryModelM storyad = new StoryModelM(adViewRecycler);
         mModel.add(i,adViewRecycler);
         }

         */

        for (int i = 0 ; i<notes.size() ; i+=10){
            notes.add(i,null);
        }



    }
    public void onBackPressed(){

    }

    @Override
    public void onLongClickInterface(View view, int position) {
        Note note = notes.get(position);
       // String Folder_Name = NoteLab.get(getActivity()).getCrime(note.getId()).getFolder();
         String Folder_Name = note.getFolder();
        Toast.makeText(getActivity(), Folder_Name, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClickPopUpMenuMainRecycler(MenuItem item, Context context, int Position , PopupMenu popupMenu) {
        pop = popupMenu;
        Note note = notes.get(Position);

        int id = item.getItemId();
        if (id == R.id.deletemenudotsmainID){
            NoteLab.get(getActivity()).deleteNote(note);
            RecyclerUpdate();

           /* mAdapter = new NoteMListAdapter(notes,this, this,getActivity());
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setCrimes(notes);
            mAdapter.notifyDataSetChanged();*/
            //Toast.makeText(getActivity(), R.string.Deleted , Toast.LENGTH_SHORT).show();
            Log.d(TAG,"menu interface done!!!............................"+ note.getId().toString()+ Position);
        }
        if (id ==R.id.removemenudotsmainID){
            note.setFolder("Trash");
            NoteLab.get(getActivity()).updateCrime(note);
            RecyclerUpdate();
        }

        if (id == R.id.favoritemenudotsmainID){

                note.setFolder("Favorite");
                NoteLab.get(getActivity()).updateCrime(note);

                RecyclerUpdate();



        }

        if (id ==R.id.removefavoritemenudotsmainID){
            note.setFolder("MainList");
            NoteLab.get(getActivity()).updateCrime(note);

            RecyclerUpdate();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        /*
         ArrayList<StoryModelM> newList = new ArrayList<>();
        for (StoryModelM storyModel : mModel) {
            String name = storyModel.getTitleModel();
            if (name.contains(newText))
                newList.add(storyModel);
        }
        adapter.setFilter(newList);

        return true;

         */

        ArrayList<Note>newList = new ArrayList<>();
        for (Note note : notes){
            String name =" ";
            String content =" ";
            if (note.getTitle()!=null) {
                 name = note.getTitle().toLowerCase();
            }

            if (note.getContent()!=null) {
                 content = note.getContent().toLowerCase();
            }
            if (name.contains(newText)|| content.contains(newText)){
                newList.add(note);
            }
            mAdapter.setFilter(newList);
        }
        return false;
    }


    //***********************************************************************

    //************************************************************************





    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_list, menu);
        MenuItem menuItem = menu.findItem(R.id.search_main_listID);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);

        super.onCreateOptionsMenu(menu, inflater);
    }
    public void AddNewCrime(){
        Note note = new Note();
        note.setFolder(Folder); //set folder before add crime
        NoteLab.get(getActivity()).addCrime(note);

        Intent intent = ViewPagerActivity.newIntent(getActivity(), note.getId(), Folder);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_item_new_crime:
               AddNewCrime();






                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

        int id = item.getItemId();

        if (id== R.id.TrashMainID){

            Folder= "Trash";
            RecyclerUpdate();
           // Toast.makeText(getActivity(), "donnnnnneeee!!!", Toast.LENGTH_SHORT).show();

        }
        if (id == R.id.MainListID){
            Folder ="MainList";
            RecyclerUpdate();

        }

        if (id == R.id.FavoriteMainID){

            if (item.getItemId()==R.id.favoritemenudotsmainID){

            }
            Folder ="Favorite";
            RecyclerUpdate();

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        mToolbar.setTitle(Folder);
        Toast.makeText(getActivity(), Folder, Toast.LENGTH_SHORT).show();


        return true;
    }

    @Override
    public void InterFaceonInflatePopUp(View view, PopupMenu popupMenu,  int position) {
        Note note= notes.get(position);

         if (Folder.equals("MainList")) {
             popupMenu.inflate(R.menu.menudotsmain);



         }else if (Folder.equals("Trash")){
             popupMenu.inflate(R.menu.menudotmain_trash);


         }else if (Folder.equals("Favorite")){
             popupMenu.inflate(R.menu.menudotmain_favorite);
         }

    }
}
