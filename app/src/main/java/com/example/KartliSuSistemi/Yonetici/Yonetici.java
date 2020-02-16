package com.example.KartliSuSistemi.Yonetici;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.KartliSuSistemi.R;

public class Yonetici extends AppCompatActivity {
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yonetici);

        button1=(Button)findViewById(R.id.admin);
        button2=(Button)findViewById(R.id.yabone);
        button3=(Button)findViewById(R.id.eleman);
        button4=(Button)findViewById(R.id.sube);
        textView=(TextView)findViewById(R.id.ytextView);

    }

    public void yaboneclick(View view){
        Intent intent = new Intent(this, yonetici_arama.class);
        startActivity(intent);

    }
    public void yelemanclick(View view){
        Intent intent = new Intent(this, yonetici_eleman.class);
        startActivity(intent);

    }public void ysubeclick(View view){
        Intent intent = new Intent(this, yonetici_sube.class);
        startActivity(intent);
    }



}
