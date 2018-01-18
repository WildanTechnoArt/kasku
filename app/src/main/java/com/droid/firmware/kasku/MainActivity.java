package com.droid.firmware.kasku;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.droid.firmware.kasku.helper.sqlitehelper;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView text_Masuk, text_Keluar, text_Saldo;
    String query_kas, query_total;
    sqlitehelper Mysqlitehelper;
    Cursor cursor;
    ListView list_kas;
    ArrayList <HashMap<String, String>> aruskas = new ArrayList<>( );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Mysqlitehelper = new sqlitehelper( getBaseContext());
        text_Masuk = findViewById(R.id.text_masuk );
        text_Keluar = findViewById(R.id.text_keluar );
        text_Saldo = findViewById(R.id.text_saldo );
        list_kas = findViewById(R.id.list_kas );
        query_kas =
                "SELECT *, strftime('%d/%m/%y', "+ sqlitehelper.MyColumns.Tanggal+") AS tgl FROM "
                        + sqlitehelper.MyColumns.NamaTabel+" ORDER BY "+ sqlitehelper.MyColumns.ID+" DESC";
        query_total =
                "SELECT SUM("+ sqlitehelper.MyColumns.Jumlah+") AS total, " +
                        "(SELECT SUM("+ sqlitehelper.MyColumns.Jumlah+") FROM "+
                        sqlitehelper.MyColumns.NamaTabel+" WHERE "+ sqlitehelper.MyColumns.Status+" = 'KELUAR') AS keluar, " +
                        "(SELECT SUM("+ sqlitehelper.MyColumns.Jumlah+") FROM "+
                        sqlitehelper.MyColumns.NamaTabel+" WHERE "+ sqlitehelper.MyColumns.Status+" = 'MASUK') AS keluar " +
                        "FROM "+ sqlitehelper.MyColumns.NamaTabel;

        KasAdapter();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
//                Snackbar.make(view, "Firmwaredroid", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });
    }

    private void KasAdapter (){

        SQLiteDatabase database = Mysqlitehelper.getReadableDatabase();
        cursor = database.rawQuery( query_kas, null );
        cursor.moveToFirst();

        for (int i=0 ; i<cursor.getCount(); i++){
            cursor.moveToPosition(i);

            HashMap <String, String> map = new HashMap<>();
            map.put(sqlitehelper.MyColumns.ID, cursor.getString(0 ));
            map.put(sqlitehelper.MyColumns.Status, cursor.getString(1 ));
            map.put(sqlitehelper.MyColumns.Jumlah, cursor.getString(2 ));
            map.put(sqlitehelper.MyColumns.Keterangan, cursor.getString(3 ));
            map.put(sqlitehelper.MyColumns.Tanggal, cursor.getString(4));
//            map.put( "tanggal", cursor.getString(5));

            aruskas.add( map );
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter( this, aruskas, R.layout.list_kas,
                new String[]{sqlitehelper.MyColumns.ID, sqlitehelper.MyColumns.Status, sqlitehelper.MyColumns.Jumlah,
                        sqlitehelper.MyColumns.Keterangan, sqlitehelper.MyColumns.Tanggal},
                new int[]{R.id.text_transaksi_id, R.id.text_status, R.id.text_jumlah, R.id.text_keterangan, R.id.text_tanggal});

        list_kas.setAdapter( simpleAdapter );
        KasTotal();

    }
    private void KasTotal(){
        NumberFormat rupiah = NumberFormat.getInstance( Locale.GERMANY);
        SQLiteDatabase database = Mysqlitehelper.getReadableDatabase();
        cursor = database.rawQuery( query_total, null );
        cursor.moveToFirst();

        text_Masuk.setText( rupiah.format(cursor.getDouble(2)) );
//        text_Saldo.setText( rupiah.format(cursor.getDouble( 1 ) - cursor.getDouble( 2 )));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
