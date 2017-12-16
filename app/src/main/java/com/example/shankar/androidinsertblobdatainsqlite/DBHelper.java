package com.example.shankar.androidinsertblobdatainsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;



//Class declaration which is extending the SQLiteOpenHelper class.
public class DBHelper extends SQLiteOpenHelper
{


    //Constructor of class which is taking Context as a parameter.
    public DBHelper(Context context)
    {
        super(context, "blobShower", null, 1);
    }

    @Override
    //onCreate method.
    public void onCreate(SQLiteDatabase db)
    {
        //Creating String query.
        String CREATE_TABLE="create table personaldetails (id integer primary key autoincrement,pname text,age integer,img blob)";

        //Creating table to store data and executing it in db.
        db.execSQL(CREATE_TABLE);
    }

    @Override
    //onUpgrade method is used when database is changed. But here we left empty because we don't need it.
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

    //Method to add the data in the database.
    public int addData(Student student)
    {
        //Creating reference of writable database.
        SQLiteDatabase db=this.getWritableDatabase();

        //Creating object of ContentValues.
        ContentValues contentValues=new ContentValues();

        //putting the values in the ContentValues object using key-value.
        contentValues.put("pname",student.nameOfStudent);
        contentValues.put("age",student.ageOfStudent);

        //Converting Bitmap image to byte array.
        Bitmap bitmap=student.employeePhoto;
        ByteArrayOutputStream  byteArrayOutputStream= new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,byteArrayOutputStream);
        byte[] img=byteArrayOutputStream.toByteArray();

        //putting image.
        contentValues.put("img",img);

        //Fetching the id.
        int student_ID=(int)db.insert("personaldetails",null,contentValues);
        return student_ID;   //retruning id.
    }


    //Method to get the data by ID.
    public Student getDataByID(int student_ID)
    {
        //Creating reference of Readable Database.
        SQLiteDatabase db=this.getReadableDatabase();

        //Creating selectQuery String to select db element based on its ID.
        String selectQuery =  "SELECT  " +
                            "id" + "," +
                "pname" + "," +
                "age" +","+"img"+
                     " FROM " + "personaldetails"
                         + " WHERE " +
                          "id" + "=?";

        //Creating Student object.
        Student student=new Student();

        //Creating the cursor by using the rawQuery method of db.
        Cursor cursor=db.rawQuery(selectQuery,new String[]{String.valueOf(student_ID)});

        //Fetching the data into the Student object.
        if(cursor.moveToFirst())
        {
            student.nameOfStudent=cursor.getString(cursor.getColumnIndex("pname"));
            student.ageOfStudent=cursor.getString(cursor.getColumnIndex("age"));

            //Converting byte array into Bitmap Image.
            byte[] img=cursor.getBlob(cursor.getColumnIndex("img"));
            student.employeePhoto= BitmapFactory.decodeByteArray(img,0,img.length);
        }
        cursor.close();
        db.close();
        return student;    //returning object of student.
    }
}    //End of DBHelper.
