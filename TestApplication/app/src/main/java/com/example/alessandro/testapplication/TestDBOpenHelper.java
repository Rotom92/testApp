package com.example.alessandro.testapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alessandro on 30/10/2014.
 */
public class TestDBOpenHelper extends SQLiteOpenHelper {

    private final static String DB_NAME="test";
    private final static Integer DB_VERSION=2;
    private final static String TABLE_NAME="User";
    private final static String UID="id";
    private final static String NAME="name";
    private final static String SURNAME="surname";
    private final static String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" ("+UID+" Integer PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255),"+SURNAME+" VARCHAR(255));";


    TestDBOpenHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION); //the null parameter is a custom cursor object
    }

    //questo metodo lo chiamo quando creo per la prima volta il database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);
        //questo metodo lo chiamo quando in corso d'opera devo aggiungere table modificare lo schema
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
