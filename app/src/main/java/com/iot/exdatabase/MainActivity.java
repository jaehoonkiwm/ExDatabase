package com.iot.exdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    TextView txMsg;
    EditText edName;

    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txMsg = (TextView)findViewById(R.id.textView);
        edName = (EditText)findViewById(R.id.editText);

        db = new DatabaseHelper(getApplicationContext(), "db", null, 1);
        SQLiteDatabase mydb = db.getWritableDatabase();
        db.setDB(mydb);
    }

    public void onButtonCreateDBclicked(View v){
        //String databaseName = "db";
       // db.createDB(databaseName);
    }

    public void onButtonCreateTableclicked(View v){
        //String tableName = "db";
        //db.createTable(tableName);
    }

    public void onButtonCreateInsertclicked(View v){
        String name = edName.getText().toString();

        db.insertData(name, 20);
        db.insertData("아이유", 23);
        db.insertData("조준연", 29);
        db.updateData("table", "아이유");
    }

    public void println(String msg){
        txMsg.append(msg + "\n");
        //Log.i(TAG,MSG);
    }

    class DatabaseHelper extends SQLiteOpenHelper {

        private String databaseName = "mydb1";
        private String tableName = "myTable1";

        private SQLiteDatabase db;

        public void setDB(SQLiteDatabase mydb) {
            this.db = mydb;
        }

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String queryStr = "drop table if exists "+ tableName;
            db.execSQL(queryStr);
            println(queryStr);

            queryStr = "create table " + tableName + "("
                    + "_id integer PRIMARY KEY autoincrement"
                    + " name text, "
                    + " age integer, "
                    + ");" ;
            db.execSQL(queryStr);
            println(queryStr);

            queryStr = "insert into" + tableName + "(name,age) values ( '',20)";
            db.execSQL(queryStr);
            println(queryStr);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if(newVersion > 1){
                db.execSQL("drop table if exists"+tableName);
            }
        }

        public void openDatabase(){

        }

        public void createDB(String name){
            db = openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
            println("create db " + name);
        }

        public void createTable(String name){
            db.execSQL("create table " + name + "("
                    + "_id integer PRIMARY KEY autoincrement"
                    + " name text, "
                    + " age integer, "
                    + ");");
            println("create table : "+name);
        }

        public void insertData(String name, int age){
            db.execSQL("insert into " + tableName + "(name,age) values ( '"
                            + name
                            + "' , '"
                            + ((Integer) age).toString()
                            + "' );"
            );
            println("create table : " + name);
        }

        public void insertData(String tableName, String name, int age){
            ContentValues values = new ContentValues();
            values.put("name",name);
            values.put("age",age);

            db.insert(tableName, null, values);

            println("insert data : " + tableName + " , " + name);
        }

        public void updateData(String tableName, String name){
            ContentValues values = new ContentValues();
            values.put("name",name);
            String[] whereStr = {"2"};
            db.update(tableName, values, "_id = ? ", whereStr);
        }


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
