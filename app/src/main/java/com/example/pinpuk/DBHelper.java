package com.example.pinpuk;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "PinPuk.db";
    public static final String PINPUK_TABLE_NAME = "pinpuk";
    public static final String PINPUK_COLUMN_ID = "id";
    public static final String PINPUK_COLUMN_NR_KARTY = "Nr_karty";
    public static final String PINPUK_COLUMN_PIN = "Pin";
    public static final String PINPUK_COLUMN_PUK = "Puk";
    public static final String PINPUK_COLUMN_NR_TEL = "Nr_tel";
    public static final String PINPUK_COLUMN_MGPO = "MGPO";
    public static final String PINPUK_COLUMN_KONWOJ = "KONWOJ";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE IF NOT EXISTS " + PINPUK_TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Nr_karty BIGINT," +
                "Pin INTEGER," +
                "Puk BIGINT," +
                "Nr_tel VARCHAR," +
                "MGPO BOOL," +
                "KONWOJ BOOL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS pinpuk");
        onCreate(db);
    }

    public boolean insertCard (BigInteger nr_karty, Integer pin, BigInteger puk, String nr_tel, Boolean mgpo, Boolean konwoj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nr_karty", nr_karty.toByteArray());
        contentValues.put("pin", pin);
        contentValues.put("puk", puk.toByteArray());
        contentValues.put("nr_tel", nr_tel);
        contentValues.put("mgpo", mgpo);
        contentValues.put("konwoj", konwoj);
        db.insert("pinpuk", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from pinpuk where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PINPUK_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, BigInteger nr_karty, Integer pin, BigInteger puk, String nr_tel, Boolean mgpo, Boolean konwoj) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nr_karty", nr_karty.toByteArray());
        contentValues.put("pin", pin);
        contentValues.put("puk", puk.toByteArray());
        contentValues.put("nr_tel", nr_tel);
        contentValues.put("mgpo", mgpo);
        contentValues.put("konwoj", konwoj);
        db.update("pinpuk", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("pinpuk",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    @SuppressLint("Range")
    public ArrayList<String> getAllCotacts() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from pinpuk", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            //BigInteger g = new BigInteger(PINPUK_COLUMN_NR_KARTY.toString());
            array_list.add(res.getString(res.getColumnIndex(PINPUK_COLUMN_NR_TEL)));
            res.moveToNext();
        }
        return array_list;
    }
}
