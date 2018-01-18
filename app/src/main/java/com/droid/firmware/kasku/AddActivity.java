package com.droid.firmware.kasku;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.droid.firmware.kasku.helper.sqlitehelper;

public class AddActivity extends AppCompatActivity {

    RadioButton radio_status1,radio_status2;
    EditText edit_jumlah, edit_keterangan;
    Button btn_simpan;
    String status;
    RippleView rip_simpan;
    sqlitehelper Mysqlitehelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Mysqlitehelper = new sqlitehelper( getBaseContext());
        radio_status1 = findViewById(R.id.radio_masuk);
        radio_status2 = findViewById(R.id.radio_keluar);
        edit_jumlah = findViewById(R.id.edit_jumlah);
        edit_keterangan = findViewById(R.id.edit_keterangan);
        btn_simpan = findViewById(R.id.btn_simpan);
        rip_simpan = findViewById(R.id.rip_simpan);
        status = "";

        rip_simpan.setOnRippleCompleteListener( new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                if (edit_jumlah.getText().toString().equals("") || edit_keterangan.getText().toString().equals("") ){
                    //toast
                    Toast.makeText(AddActivity.this, "Masukan Data dengan Benar",
                            Toast.LENGTH_LONG).show();
                } else {
                    if(radio_status1.isChecked()){
                        status = "MASUK";
                    }else if(radio_status2.isChecked()){
                        status = "KELUAR";
                    }

                    //Mendapatkan Repository dengan Mode Menulis
                    SQLiteDatabase create = Mysqlitehelper.getWritableDatabase();

                    //Membuat Map Baru, Yang Berisi Nama Kolom dan Data Yang Ingin Dimasukan
                    ContentValues values = new ContentValues();
                    values.put(sqlitehelper.MyColumns.Status, status);
                    values.put(sqlitehelper.MyColumns.Jumlah, edit_jumlah.getText().toString());
                    values.put(sqlitehelper.MyColumns.Keterangan, edit_keterangan.getText().toString());

                    //Menambahkan Baris Baru, Berupa Data Yang Sudah Diinputkan pada Kolom didalam Database
                    create.insert(sqlitehelper.MyColumns.NamaTabel, null, values);

                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(AddActivity.this, "Data Transaksi Berhasil disimpan",
                            Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        } );

        //ganti nama actionbar
        getSupportActionBar().setTitle("Tambah Baru");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
