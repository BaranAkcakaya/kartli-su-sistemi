package com.example.KartliSuSistemi.Yonetici;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.KartliSuSistemi.CustomListViewAdapter;
import com.example.KartliSuSistemi.DatabaseHelper;
import com.example.KartliSuSistemi.Person;
import com.example.KartliSuSistemi.R;

import java.util.ArrayList;

public class yonetici_arama extends AppCompatActivity {
    EditText editText;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor c;
    Integer a=0 ;
    private ArrayList<Person> persons;
    private ListView listView;
    private CustomListViewAdapter listViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yonetici_arama);

        initialize();
        fillArrayList(persons);
    }
    public void yonetici_arama(View view){
        editText=(EditText)findViewById(R.id.editText9) ;
        String abone_no=editText.getText().toString();
        if(abone_no.length() ==0){
            this.finish();
            this.startActivity(getIntent());
        }else {
            try {
                persons.clear();
                dbHelper = new DatabaseHelper(getApplicationContext(), "DATABASE.db");
                dbHelper.createDatabase("DATABASE.db");
                db = dbHelper.getReadableDatabase();
                c = db.rawQuery(" SELECT * FROM ABONE WHERE Abone_No=" + abone_no, null);
                while (c.moveToNext()) {
                    if (c.getInt(0) != 0) {
                        Person person = new Person("Abone No:" + c.getString(0),
                                "Kullanıcı No:" + c.getString(1)
                                , R.drawable.business, "Adres No:" + c.getString(2));
                        persons.add(person);
                        a++;
                    }
                }
            } catch (Exception e) {
                Log.e("DB_LOG", e.getMessage());
                Log.e("DB_LOG", "Veritabanı oluşturulamadı veya kopyalanamadı !");
            }
        }
    }
    public void yonetici_sil(View vi){
        editText=(EditText)findViewById(R.id.editText9) ;
        String abone_no=editText.getText().toString();
        if(abone_no.length() ==0){
            this.finish();
            this.startActivity(getIntent());
        }else {
            try {
                persons.clear();
                dbHelper = new DatabaseHelper(getApplicationContext(), "DATABASE.db");
                dbHelper.createDatabase("DATABASE.db");
                db = dbHelper.getWritableDatabase();
                c = db.rawQuery(" SELECT * FROM ABONE WHERE Abone_No=" + abone_no, null);
                while (c.moveToNext()) {
                    db.delete("ABONE", "Abone_No="+abone_no ,null);
                    db.close();
                }
            } catch (Exception e) {
                Log.e("DB_LOG", e.getMessage());
                Log.e("DB_LOG", "Veritabanı oluşturulamadı veya kopyalanamadı !");
            }
            this.finish();
            this.startActivity(getIntent());
        }
    }
    private void initialize() {
        persons = new ArrayList<Person>();
        listView = (ListView) findViewById(R.id.person_list_view);
        listViewAdapter = new CustomListViewAdapter(yonetici_arama.this,persons);
        listView.setAdapter(listViewAdapter);
    }
    private void fillArrayList(ArrayList<Person>persons){
        try{
            dbHelper = new DatabaseHelper(getApplicationContext(),"DATABASE.db");
            dbHelper.createDatabase("DATABASE.db");
            db = dbHelper.getReadableDatabase();
            c = db.rawQuery(" SELECT * FROM ABONE",null);
        }catch(Exception e){
            Log.e("DB_LOG",e.getMessage());
            Log.e("DB_LOG","Veritabanı oluşturulamadı veya kopyalanamadı !");
            Toast.makeText(getApplicationContext(), "KULLANICI BULUNAMADI!", Toast.LENGTH_LONG).show();
        }
        while(c.moveToNext()){
            if (c.getInt(0) != 0){
                Person person = new Person("Abone No:" + c.getString(0),
                        "Kullanıcı No:"+c.getString(1)
                        , R.drawable.users,"Adres No:"+c.getString(2));
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
