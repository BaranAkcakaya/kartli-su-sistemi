package com.example.KartliSuSistemi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.KartliSuSistemi.Kullanici.kullanici_ekran;
import com.example.KartliSuSistemi.Yonetici.Yonetici;
import com.example.KartliSuSistemi.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class girisActivity extends AppCompatActivity {
    EditText editText2;
    EditText editText ;

    EditText yeditText ;
    EditText yeditText2 ;

    Button button ;
    Button button2 ;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        button=(Button) findViewById(R.id.kSignUp);
        button2=(Button) findViewById(R.id.yoneticiSignUp);
        setContentView(R.layout.activity_giris);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


    }
    public void k_girisyap(View view){
        editText=(EditText)findViewById(R.id.kullaniciid);
        editText2=(EditText)findViewById(R.id.kullaniciparola);

        String a= (editText.getText().toString());
        String b=(editText2.getText().toString());

        try{
            dbHelper = new DatabaseHelper(getApplicationContext(),"GIRIS.db");
            dbHelper.createDatabase("GIRIS.db");
            db = dbHelper.getReadableDatabase();
            c = db.rawQuery(" SELECT * FROM GIRIS WHERE YNTC!='X' AND ID="+a,null);


            c.moveToNext();
            if(b.equals(c.getString(1))){
                System.out.println("---oldu---");
                Toast.makeText(getApplicationContext(), "GİRİŞ BAŞARILI", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(this, kullanici_ekran.class);

                intent.putExtra("key",a);

                startActivity(intent);
            }else{
                System.out.println("---sıkıntı---");
                Toast.makeText(getApplicationContext(), "GİRİŞ BAŞARISIZ.TEKRAR DENEYİNİZ", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            Log.e("DB_LOG",e.getMessage());
            System.out.println("----"+a +"---" + b);
            Log.e("DB_LOG","Veritabanı oluşturulamadı veya kopyalanamadı !");
            Toast.makeText(getApplicationContext(), "KULLANICI BULUNAMADI!", Toast.LENGTH_LONG).show();

        }
    }
    public void yonetici_giris_yap(View view){
        yeditText=(EditText)findViewById(R.id.yoneticiid);
        yeditText2=(EditText)findViewById(R.id.yoneticiparola);

        String a= yeditText.getText().toString();
        String b=yeditText2.getText().toString();

        try{
            dbHelper = new DatabaseHelper(getApplicationContext(),"GIRIS.db");
            dbHelper.createDatabase("GIRIS.db");
            db = dbHelper.getReadableDatabase();
            c = db.rawQuery(" SELECT * FROM GIRIS WHERE YNTC='X' AND ID="+a,null);
            c.moveToNext();
            if(b.equals(c.getString(1))){
                Toast.makeText(getApplicationContext(), "GİRİŞ BAŞARILI", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(), Yonetici.class);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "GİRİŞ BAŞARISIZ.TEKRAR DENEYİNİZ", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            Log.e("DB_LOG",e.getMessage());
            System.out.println("----"+a +"---" + b);
            Log.e("DB_LOG","Veritabanı oluşturulamadı veya kopyalanamadı !");
            Toast.makeText(getApplicationContext(), "YONETİCİ BULUNAMADI!", Toast.LENGTH_LONG).show();
        }
    }
}