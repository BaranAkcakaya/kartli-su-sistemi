package com.example.KartliSuSistemi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context myContext;
    private static String DB_PATH = "";
    private static int DATABASE_VERSION = 1;
    public SQLiteDatabase myDatabase;
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public DatabaseHelper(Context context,String db) throws IOException {
        super(context,db,null,DATABASE_VERSION);
        this.myContext = context;
        boolean dbExists = checkDatabase(db);
        DB_PATH = "/data/data/"+context.getPackageName()+"/databases/";

        if(dbExists){
            Log.d("DB_LOG","Database bulundu !");
        }else{
            try{
                if(createDatabase(db)==true){
                    Log.d("DB_LOG","Database oluşturuldu !");
                }else{

                    Log.d("DB_LOG","Database oluşturulamadı !");
                    Log.d("DB_LOG",db);
                }
            }catch (Exception e){
                Log.d("DB_LOG","Database oluşturulamadı !");
            }
        }

    }
    public boolean createDatabase(String db) throws IOException{
        boolean dbExists = checkDatabase(db);
        if(dbExists){
            return true;
        }else{
            this.getReadableDatabase();
            try {
                this.close();
                copyDatabase(db);
            }catch (IOException e){
                throw  new Error("Database kopyalanma hatası");
            }
            return false;
        }
    }

    public boolean checkDatabase(String db){
        boolean checkdb = false;

        try{
            String dosyaKonumu = DB_PATH + db;
            File dbFile = new File(dosyaKonumu);
            checkdb = dbFile.exists();
        }catch (SQLiteException e){
            Log.d("DB_LOG","Database bulunamadı");
        }

        return checkdb;
    }

    private void copyDatabase(String db) throws IOException{
        String outFileName = DB_PATH + db;
        OutputStream myOutput = new FileOutputStream(outFileName);
        InputStream myInput = myContext.getAssets().open(db);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0)
        {
            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    public boolean openDatabase(String db){
        String myPath = DB_PATH + db;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        if(myDatabase!=null) return true;
        else return false;
    }
    public synchronized void close(){
        if (myDatabase != null){
            myDatabase.close();
        }
        super.close();
    }



}
