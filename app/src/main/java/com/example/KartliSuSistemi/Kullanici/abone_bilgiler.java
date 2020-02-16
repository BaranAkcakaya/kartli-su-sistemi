package com.example.KartliSuSistemi.Kullanici;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.KartliSuSistemi.DatabaseHelper;
import com.example.KartliSuSistemi.R;

public class abone_bilgiler extends AppCompatActivity {
    Bundle bundle;
    TextView textView2;
    TextView textView4;
    TextView textView6;
    TextView textView9;
    TextView textView11;
    TextView textView13;
    Button button;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor c;
    String email;
    String telno;
    String bilgiler ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abone_bilgiler);
        bundle=getIntent().getExtras();
        bilgiler= bundle.getString("keyabone");
        System.out.println("abone"+bilgiler);
        textView11=(TextView)findViewById(R.id.textView11);
        textView9=(TextView)findViewById(R.id.textView9);
        textView2=(TextView)findViewById(R.id.textView2);
        textView6=(TextView)findViewById(R.id.textView6);
        textView13=(TextView)findViewById(R.id.textView13);
        textView4=(TextView)findViewById(R.id.textView4);
        button=(Button)findViewById(R.id.button3);
        fillArrayList();
    }
    private void fillArrayList(){
        if(bundle !=null) {
            bilgiler=bundle.getString("keyabone");
            System.out.println("+++++"+bilgiler);
        }else
            System.out.println("sıkıntı-------");
        try{
            dbHelper = new DatabaseHelper(getApplicationContext(),"DATABASE.db");
            dbHelper.createDatabase("DATABASE.db");
            db = dbHelper.getWritableDatabase();
            c = db.rawQuery(" SELECT * FROM KULLANICI WHERE  ABONE_NO="+bilgiler ,null);
        }catch(Exception e){
            Log.e("DB_LOG",e.getMessage());
            Log.e("DB_LOG","Veritabanı oluşturulamadı veya kopyalanamadı !");
            Toast.makeText(getApplicationContext(), "KULLANICI BULUNAMADI!", Toast.LENGTH_LONG).show();
        }
        EditText editText=(EditText)findViewById(R.id.editText);
        EditText editText2=(EditText)findViewById(R.id.editText2);

        while(c.moveToNext()){
            if (c.getInt(0) != 0){
                textView11.setText(c.getString(1));
                textView9.setText(c.getString(4));
                textView2.setText(c.getString(2));
                textView6.setText(c.getString(3));
                textView13.setText(c.getString(5));
                textView4.setText(c.getString(9));
                editText.setText(c.getString(6));
                editText2.setText(c.getString(8));
                email=c.getString(6);
                telno=c.getString(8);
            }
        }
    }
    public void guncellebtn(View view){
        EditText editText=(EditText)findViewById(R.id.editText);
        EditText editText2=(EditText)findViewById(R.id.editText2);

        String email1 = editText.getText().toString();
        String telno1 = editText2.getText().toString();

        if(email1.equals(email) && telno1.equals(telno) ){
            Toast.makeText(getApplicationContext(), "DEĞİŞİKLİK BULUNAMADI!", Toast.LENGTH_LONG).show();
        }else{
            ContentValues cv = new ContentValues();
            cv.put("Mail",email1);
            cv.put("Telefon",telno1);
            db.update("KULLANICI",cv,"Abone_No="+bilgiler,null);
            Toast.makeText(getApplicationContext(), "BİLGİLER GUNCELLENDİ", Toast.LENGTH_LONG).show();
            db.close();
        }
    }

}
