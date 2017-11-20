package com.example.khaled.Note.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.khaled.Note.R;
import com.example.khaled.Note.activities.ViewPagerActivity;
import com.example.khaled.Note.interfaces.InterfaceOnCreatePopUpMenuMain;
import com.example.khaled.Note.interfaces.InterfaceOnLongClick;
import com.example.khaled.Note.interfaces.InterfacePopupMenuMainRecycler;
import com.example.khaled.Note.models.Note;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.api.BooleanResult;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class NoteMListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Note> mNotes;
    Context mContext;
    private static final int MENU_ITEM_VIEW_TYPE = 0;
    private static final int AD_VIEW_TYPE = 1;
    boolean getitemView=true;

    InterfaceOnLongClick mInterfaceOnLongClick;
    InterfacePopupMenuMainRecycler mInterfacePopupMenuMainRecycler;
    InterfaceOnCreatePopUpMenuMain interfaceOnCreatePopUpMenuMain;
    // NoteListActivity mCrimeListActivity;
    //   NoteListFragment mCrimeListFragment;


    public NoteMListAdapter(List<Note> notes, InterfaceOnLongClick interfaceOnlong, InterfacePopupMenuMainRecycler interfacePopupMenuMainRecycler, InterfaceOnCreatePopUpMenuMain interfaceOnCreatePopUpMenuMain, Context context) {
        // this.mContext = ctx;
        mNotes = notes;
        this.mInterfaceOnLongClick = interfaceOnlong;
        this.mInterfacePopupMenuMainRecycler = interfacePopupMenuMainRecycler;
        this.interfaceOnCreatePopUpMenuMain = interfaceOnCreatePopUpMenuMain;
        this.mContext = context;

        //   this.mCrimeListActivity =(NoteListActivity) mContext;

    }
    @Override
    public int getItemViewType(int position) {
        //every 8 item there will be an ad
        if (getitemView) {

            return (position % 4 == 0) ? AD_VIEW_TYPE : MENU_ITEM_VIEW_TYPE;
        }else {
            return super.getItemViewType(position);
        }
    }



    public void setOnlongClick(InterfaceOnLongClick interfaceOnLongClick){
        mInterfaceOnLongClick =interfaceOnLongClick;
    }

    public void setFilter(ArrayList<Note> newList){
        mNotes = new ArrayList<>();

        mNotes.addAll(newList);
        getitemView =false;
        notifyDataSetChanged();
    }




    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){

            case AD_VIEW_TYPE:
                View adbannerview = LayoutInflater.from(parent.getContext()).inflate(R.layout.admob_row_list, parent,false);
                return new AdViewHolder(adbannerview);
            case MENU_ITEM_VIEW_TYPE:
            default:

                View MeunItemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.crime_list_row, parent, false);
                return new Crimeholder(MeunItemview);


        }

    }

    /**
     *
     * NoteView viewHolder**************************************************************
     *
     *
     *
     *
     *
     */

    public class Crimeholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView mTitleCrime , mDateCrime;
        private TextView mContentNote;
        public CheckBox mCheckBoxList,checkdelete;
        private Note mNote;
        private Button menudots;
        CardView mCardView;


        public Crimeholder(final View itemView/*, NoteListActivity crimeListActivity*/) {
            super(itemView);



            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            //  itemView.setOnLongClickListener(this);

            //  checkdelete.setVisibility(View.GONE);




            mTitleCrime =(TextView) itemView.findViewById(R.id.CrimeTitle_listID);
            mDateCrime =(TextView)itemView.findViewById(R.id.CrimeDate_listID);
            mCheckBoxList =(CheckBox)itemView.findViewById(R.id.CheckBox_list_ctimeID);
            mContentNote=(TextView) itemView.findViewById(R.id.NoteContentRowID);
            checkdelete=(CheckBox)itemView.findViewById(R.id.checkTodeleteID);
            mCardView=(CardView)itemView.findViewById(R.id.cardviewRow);
            menudots=(Button)itemView.findViewById(R.id.menudotsmainID);

            //   mCardView.setOnLongClickListener(this);




           /* mCheckBoxList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                   //if (mCheckBoxList.isChecked()){
                     //   mNote.setSolved(false);
                  //  }else {
                        mNote.setSolved(true);

                   // }

                }
            });*/






        }




        public void Bindcrime(Note note){
            mNote = note;

            DateFormat df = new SimpleDateFormat("E, MMM d, yyyy");
            df.setTimeZone(TimeZone.getDefault());
            String date = df.format(mNote.getDate());

            mTitleCrime.setText(mNote.getTitle());
            mDateCrime.setText(date);
            mCheckBoxList.setChecked(mNote.isSolved());
            mContentNote.setText(mNote.getContent());

        }











        @Override
        public void onClick(View v) {
            Note note = new Note();
            //UUID CrimeID = note.getId();
            //changing the intent from Mainactivity to ViewPager
            //  Intent intent = MainActivity.newIntent(getActivity(),mNote.getId());
            Intent intent = ViewPagerActivity.newIntent(mContext, mNote.getId(),mNote.getFolder());
           mContext.startActivity(intent);

           /* Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);*/

        }








        @Override
        public boolean onLongClick(View v) {
            if (mInterfaceOnLongClick!=null) {

                mInterfaceOnLongClick.onLongClickInterface(itemView, getAdapterPosition());
            }
            // NoteListFragment.isSelected = true;
            //   Toast.makeText(itemView.getContext(), "hi", Toast.LENGTH_SHORT).show();

            return true;
        }
    }

    /**
     *
     * AdView viewHolder**************************************************************
     *
     *
     *


     *
     *
     */

    public class AdViewHolder extends RecyclerView.ViewHolder {
        public AdView mAdView;

        public AdViewHolder(View itemView) {
            super(itemView);
            mAdView =(AdView) itemView.findViewById(R.id.admobrowlist);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

        }
    }





//old before ad a Admob
        /*LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.crime_list_row , parent , false);
        //Crimeholder crimeholder= new Crimeholder(view, mCrimeListActivity);


        return new Crimeholder(view);*/
        //  return crimeholder;


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
   int viewType = getItemViewType(position);
       if (viewType == AD_VIEW_TYPE) {

       }else if (viewType ==MENU_ITEM_VIEW_TYPE){




                    Note note= mNotes.get(position);
                     final Crimeholder crimeholder =(Crimeholder)holder;
                    crimeholder.Bindcrime(note);

                    crimeholder.checkdelete.setVisibility(View.GONE);
                    crimeholder.mCheckBoxList.setVisibility(View.GONE);

                  crimeholder.menudots.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final int pos = holder.getAdapterPosition();

                            final PopupMenu popupMenu = new PopupMenu(mContext,crimeholder.menudots);

                            if (interfaceOnCreatePopUpMenuMain!=null){
                                interfaceOnCreatePopUpMenuMain.InterFaceonInflatePopUp(v , popupMenu, pos);
                            }

                            // popupMenu.inflate(R.menu.menudotsmain);
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    int pos = holder.getAdapterPosition();
                                    mInterfacePopupMenuMainRecycler.onClickPopUpMenuMainRecycler(item, mContext , pos , popupMenu);
                                    int id = item.getItemId();



                                    return false;
                                }
                            });

                            popupMenu.show();
                        }
                    });


        }




    }



       /* @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);
        }*/







    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    //added for database
    public void setCrimes(List<Note> notes) {
        mNotes = notes;
    }



}
