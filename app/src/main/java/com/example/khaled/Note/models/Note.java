package com.example.khaled.Note.models;

import com.example.khaled.Note.R;

import java.util.Date;
import java.util.UUID;

/**
 * Created by khaled on 15/10/2017.
 */

public class Note {
    private String mTitle;
    private UUID mId;
    private Date mDate;
    private boolean mSolved;
    private String mContent;
    private String contactnumber;

    private String mFolder;
    private int TitleColor;
    private int mContentColor;
    private int mTitlSize;
    private int mContentSize;
    private int mNoteBackground;
    private int ToolbarListColor;
    private int ToolbarNoteColor;

    public int getContentColor() {
        return mContentColor;
    }

    public void setContentColor(int contentColor) {
        mContentColor = contentColor;
    }

    public int getTitlSize() {
        return mTitlSize;
    }

    public void setTitlSize(int titlSize) {
        mTitlSize = titlSize;
    }

    public int getContentSize() {
        return mContentSize;
    }

    public void setContentSize(int contentSize) {
        mContentSize = contentSize;
    }

    public int getNoteBackground() {
        return mNoteBackground;
    }

    public void setNoteBackground(int noteBackground) {
        mNoteBackground = noteBackground;
    }

    public int getToolbarListColor() {
        return ToolbarListColor;
    }

    public void setToolbarListColor(int toolbarListColor) {
        ToolbarListColor = toolbarListColor;
    }

    public int getToolbarNoteColor() {
        return ToolbarNoteColor;
    }

    public void setToolbarNoteColor(int toolbarNoteColor) {
        ToolbarNoteColor = toolbarNoteColor;
    }

    public String getFolder() {
        return mFolder;
    }

    public void setFolder(String folder) {
        mFolder = folder;
    }

    public int getTitleColor() {
        return TitleColor;
    }

    public void setTitleColor(int titleColor) {
        TitleColor = titleColor;
    }



    public Note(){
        mId = UUID.randomUUID();
       mDate = new Date();
    }

    public Note(UUID uuid){
        mId = uuid;
        mDate = new Date();
    }

    public String getPhotoFilename(){

        return "IMG_"+ getId().toString() +".jpg";

    }
    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
      /*  android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
       dateFormat.format("yyyy-MM-dd hh:mm:ss a", mDate);*/
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
