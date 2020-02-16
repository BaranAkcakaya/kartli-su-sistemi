package com.example.KartliSuSistemi;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class giris_main extends AppCompatActivity {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor c;
    Bundle mybundle2;
    String abone_no2;
    Integer a=0 ;
    private ArrayList<Person> persons;
    private ListView listView;
    private CustomListViewAdapter listViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deneme);

        mybundle2=getIntent().getExtras();
        initialize();
        fillArrayList(persons);
    }
    private void initialize() {
        persons = new ArrayList<Person>();
        listView = (ListView) findViewById(R.id.person_list_view);
        listViewAdapter = new CustomListViewAdapter(giris_main.this,persons);
        listView.setAdapter(listViewAdapter);
    }

    private void fillArrayList(ArrayList<Person>persons){
        if(mybundle2 !=null) {
            abone_no2=mybundle2.getString("keytwo");
        }
        try{
            dbHelper = new DatabaseHelper(getApplicationContext(),"DATABASE.db");
            dbHelper.createDatabase("DATABASE.db");
            db = dbHelper.getReadableDatabase();
            c = db.rawQuery(" SELECT * FROM FATURA WHERE  ABONE_NO="+abone_no2 ,null);
        }catch(Exception e){
            Log.e("DB_LOG",e.getMessage());
            Log.e("DB_LOG","Veritabanı oluşturulamadı veya kopyalanamadı !");
            Toast.makeText(getApplicationContext(), "KULLANICI BULUNAMADI!", Toast.LENGTH_LONG).show();
        }
            while(c.moveToNext()){
                if (c.getInt(0) != 0){
                    Person person = new Person("Tarih:" + c.getInt(4),"Yukleme Tutarı:"+c.getInt(5)
                            , R.drawable.bill,"ODENDI");
                    persons.add(person);
                    a++;
                }
            }
            if(a==0){
                Toast.makeText(getApplicationContext(), "FATURA BULUNAMADI!", Toast.LENGTH_LONG).show();
                a=0;
        }

    }
}