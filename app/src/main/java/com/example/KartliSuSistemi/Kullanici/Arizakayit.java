package com.example.KartliSuSistemi.Kullanici;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.KartliSuSistemi.CustomListViewAdapter;
import com.example.KartliSuSistemi.DatabaseHelper;
import com.example.KartliSuSistemi.Person;
import com.example.KartliSuSistemi.R;

import java.util.ArrayList;

public class Arizakayit extends AppCompatActivity {
    Bundle mybundle2;
    String abone_no2;
    private ArrayList<Person> persons;
    private ListView listView;
    private CustomListViewAdapter listViewAdapter;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    SQLiteDatabase db1;
    Cursor c;
    Cursor d;
    Integer a=0 ;
    String elemanno;
    String adresno;
    TextView textView;
    Button button;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText7;
    EditText editText8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arizakayit);
        mybundle2=getIntent().getExtras();
        button = (Button)findViewById(R.id.button);
        initialize();
        fillArrayList(persons);

    }

    private void initialize() {

        persons = new ArrayList<Person>();
        listView = (ListView) findViewById(R.id.person_list_view);
        listViewAdapter = new CustomListViewAdapter(this,persons);
        listView.setAdapter(listViewAdapter);
    }

    private void fillArrayList(ArrayList<Person>persons) {
        if (mybundle2 != null) {
            abone_no2 = mybundle2.getString("keyariza");
        }
        try {
            dbHelper = new DatabaseHelper(getApplicationContext(), "DATABASE.db");
            dbHelper.createDatabase("DATABASE.db");
            db = dbHelper.getReadableDatabase();
            c = db.rawQuery(" SELECT * FROM ISLEM ", null);
            System.out.println("İslem Adet "+c.getCount());
        } catch (Exception e) {
            Log.e("DB_LOG", e.getMessage());
            Log.e("DB_LOG", "Veritabanı oluşturulamadı veya kopyalanamadı !");
            Toast.makeText(getApplicationContext(), "KULLANICI BULUNAMADI!", Toast.LENGTH_LONG).show();
        }
        while (c.moveToNext()) {
            adres_getir();
            a++;
        }
        if (a == 0) {
            Toast.makeText(getApplicationContext(), "FATURA BULUNAMADI!", Toast.LENGTH_LONG).show();
            a = 0;
        }
        db.close();
        db1.close();
    }
    public void adres_getir(){
        try {
            dbHelper = new DatabaseHelper(getApplicationContext(),"DATABASE.db");
            dbHelper.createDatabase("DATABASE.db");
            db1 = dbHelper.getReadableDatabase();
            d = db1.rawQuery(" SELECT * FROM ADRES WHERE  ADRES_NO="+c.getString(3), null);
            System.out.println("**** dKactane: "+d.getCount());
        } catch (Exception e) {
            Log.e("DB_LOG", e.getMessage());
            Log.e("DB_LOG", "Veritabanı oluşturulamadı veya kopyalanamadı !");
        }

        while (d.moveToNext()) {

            Person person = new Person("Sokak: "+d.getString(2)+"Mahalle: "+
                    d.getString(3)
                    , "Tur:" + " ATM"
                    , R.drawable.tools, "ARIZALI");
            persons.add(person);

        }
    }
    public void arizaekle(View view){
        setContentView(R.layout.activity_ariza__kayit);
    }
    public void arizakaydet(View view){
        editText3=(EditText)findViewById(R.id.editText3);
        editText4=(EditText)findViewById(R.id.editText4);
        editText5=(EditText)findViewById(R.id.editText5);
        editText6=(EditText)findViewById(R.id.editText6);
        editText7=(EditText)findViewById(R.id.editText7);
        editText8=(EditText)findViewById(R.id.editText8);

        String dosya3=editText3.getText().toString();
        String dosya4=editText4.getText().toString();
        String dosya5=editText5.getText().toString();
        String dosya6=editText6.getText().toString();
        String dosya7=editText7.getText().toString();
        String dosya8=editText8.getText().toString();

        Integer adres_bilgi=adres_olustur();
        Integer eleman_no=eleman_bul();
        eleman_no++;


        ContentValues cv = new ContentValues();
        cv.put("Dosya_No",dosya3);
        cv.put("Eleman_No","");  //252
        cv.put("Abone_No",dosya4);
        cv.put("Adres_No",adres_bilgi); //6
        cv.put("Tarih",dosya5);
        cv.put("Tur",dosya6);

        db.insert("ISLEM", null, cv);
        Toast.makeText(getApplicationContext(), "ARIZA KAYDI BAŞARILIYLA EKLENDİ!", Toast.LENGTH_LONG).show();
        this.finish();
    }
    public int eleman_bul(){

        try{
            dbHelper = new DatabaseHelper(getApplicationContext(),"DATABASE.db");
            dbHelper.createDatabase("DATABASE.db");
            db = dbHelper.getReadableDatabase();
            c = db.rawQuery(" SELECT * FROM ELEMAN ",null);
            while(c.moveToNext()){
                Log.i("DB_LOG"," "+c.getString(0));
                elemanno=c.getString(0);
            }
        }catch(Exception e){
            Log.e("DB_LOG",e.getMessage());
            Log.e("DB_LOG","Veritabanı oluşturulamadı veya kopyalanamadı !");
        }
        return Integer.parseInt(elemanno);

    }
    public int adres_olustur(){
        editText7=(EditText)findViewById(R.id.editText7);
        editText8=(EditText)findViewById(R.id.editText8);
        String dosya7=editText7.getText().toString();
        String dosya8=editText8.getText().toString();
        try{
            dbHelper = new DatabaseHelper(getApplicationContext(),"DATABASE.db");
            dbHelper.createDatabase("DATABASE.db");
            db = dbHelper.getReadableDatabase();
            c = db.rawQuery(" SELECT * FROM ADRES WHERE Sokak="+dosya8+ " AND Mahalle="+dosya7,null);
            while(c.moveToNext()){
                adresno=c.getString(0);
            }
        }catch(Exception e){
            ContentValues cv1 = new ContentValues();
            Integer son_adres= adres_bul();
            son_adres++;
            cv1.put("Adres_No",son_adres);
            cv1.put("Sokak",dosya8);
            cv1.put("Mahalle",dosya7);
            cv1.put("Sehir","Canakkale");
            cv1.put("Semt","Merkez");
            cv1.put("Durum","X");
            db.insert("ADRES", null, cv1);
            adresno=Integer.toString(son_adres);
        }
        return Integer.parseInt(adresno);
    }
    public int adres_bul(){

        try{
            dbHelper = new DatabaseHelper(getApplicationContext(),"DATABASE.db");
            dbHelper.createDatabase("DATABASE.db");
            db = dbHelper.getReadableDatabase();
            c = db.rawQuery(" SELECT * FROM ADRES ",null);
            while(c.moveToNext()){
                Log.i("DB_LOG"," "+c.getString(0));
                elemanno=c.getString(0);

            }
        }catch(Exception e){
            Log.e("DB_LOG",e.getMessage());
            Log.e("DB_LOG","Veritabanı oluşturulamadı veya kopyalanamadı !");
        }
        return Integer.parseInt(elemanno);

    }

}
