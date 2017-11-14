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
import com.example.khaled.Note.interfaces.InterfaceOnLongClick;
import com.example.khaled.Note.interfaces.InterfacePopupMenuMainRecycler;
import com.example.khaled.Note.models.Note;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class NoteMListAdapter extends RecyclerView.Adapter<NoteMListAdapter.Crimeholder>{
    private List<Note> mNotes;
    Context mContext;
    InterfaceOnLongClick mInterfaceOnLongClick;
    InterfacePopupMenuMainRecycler mInterfacePopupMenuMainRecycler;
    // NoteListActivity mCrimeListActivity;
    //   NoteListFragment mCrimeListFragment;


    public NoteMListAdapter(List<Note> notes, InterfaceOnLongClick interfaceOnlong, InterfacePopupMenuMainRecycler interfacePopupMenuMainRecycler, Context context) {
        // this.mContext = ctx;
        mNotes = notes;
        this.mInterfaceOnLongClick = interfaceOnlong;
        this.mInterfacePopupMenuMainRecycler = interfacePopupMenuMainRecycler;
        this.mContext = context;

        //   this.mCrimeListActivity =(NoteListActivity) mContext;

    }


    public void setOnlongClick(InterfaceOnLongClick interfaceOnLongClick){
        mInterfaceOnLongClick =interfaceOnLongClick;
    }

    public void setFilter(ArrayList<Note> newList){
        mNotes = new ArrayList<>();
        mNotes.addAll(newList);
        notifyDataSetChanged();
    }

    public class Crimeholder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private TextView mTitleCrime , mDateCrime;
        private TextView mContentNote;
        public CheckBox mCheckBoxList,checkdelete;
        private Note mNote;
        private Button menudots;
        CardView mCardView;


        public Crimeholder(View itemView/*, NoteListActivity crimeListActivity*/) {
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

    @Override
    public Crimeholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.crime_list_row , parent , false);
        //Crimeholder crimeholder= new Crimeholder(view, mCrimeListActivity);


        return new Crimeholder(view);
        //  return crimeholder;
    }

    @Override
    public void onBindViewHolder(final Crimeholder holder, final int position) {

        Note note = mNotes.get(position);
        holder.Bindcrime(note);

            /*if (!NoteListFragment.isSelected) {


                holder.checkdelete.setVisibility(View.GONE);
            }else {
                holder.checkdelete.setVisibility(View.VISIBLE);
            }*/


        holder.checkdelete.setVisibility(View.GONE);
        holder.mCheckBoxList.setVisibility(View.GONE);

        holder.menudots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(mContext,holder.menudots);
                popupMenu.inflate(R.menu.menudotsmain);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mInterfacePopupMenuMainRecycler.onClickPopUpMenuMainRecycler(item, mContext , position , popupMenu);
                        int id = item.getItemId();



                        return false;
                    }
                });

                popupMenu.show();
            }
        });



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
