package com.droid.firmware.kasku.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class sqlitehelper  extends SQLiteOpenHelper{

    //InnerClass, untuk mengatur artibut seperti Nama Tabel, nama-nama kolom dan Query
    public static abstract class MyColumns implements BaseColumns{
        //Menentukan Nama Table dan Kolom
        public static final String NamaTabel = "transaksi";
        public static final String ID = "transaksi_id";
        public static final String Status  = "status";
        public static final String Jumlah = "jumlah";
        public static final String Keterangan  = "keterangan";
        public static final String Tanggal  = "tanggal";
    }

    private static final String NamaDatabse = "uangKas.db";
    private static final int VersiDatabase = 2;

    //Query yang digunakan untuk membuat Tabel
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+MyColumns.NamaTabel+
            "("+MyColumns.ID+" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+MyColumns.Status+" TEXT, "+MyColumns.Jumlah+
            " DOUBLE, "+MyColumns.Keterangan+" TEXT, "+MyColumns.Tanggal+
            " DATE DEFAULT CURRENT_DATE)";

    //Query yang digunakan untuk mengupgrade Tabel
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+MyColumns.NamaTabel;

    public sqlitehelper(Context context) {
        super(context, NamaDatabse, null, VersiDatabase);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}