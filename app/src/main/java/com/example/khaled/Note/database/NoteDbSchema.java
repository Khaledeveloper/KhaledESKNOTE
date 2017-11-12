package com.example.khaled.Note.database;

/**
 * Created by khaled on 22/10/2017.
 */

public class NoteDbSchema {

    public static final class NoteTable {
        public static final String NAME ="crimestab";

        public static final class Cols {

            public static final String UUID ="uuid";
            public static final String TITLE ="title";
            public static final String CONTENT="content";
            public static final String  DATE ="date";
            public static final String  SOLVED ="solved";
            public static final String CONTACTNUMBER ="CONTACTNUMBER";

            public static final String FOLDER ="Folder";
            public static final String TITLECOLOR="titlecolor";
            public static final String CONTENTCOLOR="contentcolor";
            public static final String TITLESIZE="titlesize";
            public static final String CONTENTSIZE="contentsize";
            public static final String NOTEBACKGROUND="notebackground";
            public static final String TOOLBARLISTCOLOR="toolbarlistcolor";
            public static final String TOOLBARNOTECOLOR="toolbarnotecolor";



        }






    }
}
