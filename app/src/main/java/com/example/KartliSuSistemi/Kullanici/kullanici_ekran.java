package com.example.KartliSuSistemi.Kullanici;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.KartliSuSistemi.DatabaseHelper;
import com.example.KartliSuSistemi.R;
import com.example.KartliSuSistemi.giris_main;

public class kullanici_ekran extends FragmentActivity {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Button button1;
    Button button2 ;
    Button button3;
    TextView textView;
    Bundle mybundle;
    String abone_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=(Button)findViewById(R.id.fatura);
        button2=(Button)findViewById(R.id.ariza);
        button3=(Button)findViewById(R.id.abone);
        textView=(TextView)findViewById(R.id.textView);
        mybundle=getIntent().getExtras();

        if(mybundle !=null) {
            abone_no=mybundle.getString("key");
            System.out.println("sıkıntı yok+++"+abone_no);
        }else
            System.out.println("sıkıntı");
    }
     public void faturaclick(View view){
         Intent intent = new Intent(this, giris_main.class);

         intent.putExtra("keytwo",abone_no);
         startActivity(intent);
     }
     public void arizaclick(View v){
         Intent intent = new Intent(this, Arizakayit.class);
         intent.putExtra("keyariza",abone_no);

         startActivity(intent);
     }
     public void bilgilerclick(View view){
        Intent intent = new Intent(this, abone_bilgiler.class);
        intent.putExtra("keyabone",abone_no);
        startActivity(intent);

     }
}
