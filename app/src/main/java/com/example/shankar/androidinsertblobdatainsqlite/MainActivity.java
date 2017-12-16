package com.example.shankar.androidinsertblobdatainsqlite;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


//Created by Shankar on 16-12-2017.
//Class  which is extending Activity class.
public class MainActivity extends Activity
{

    public EditText nameET,ageET;    //Creating references of EditTexts.
    public Button showData;       //Creating reference of Button.

    @Override
    //onCreate method.
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);   //Setting ContentView.

        //Setting references to its IDs.
        nameET=(EditText)findViewById(R.id.name_et);
        ageET=(EditText)findViewById(R.id.age_et);
        showData=(Button) findViewById(R.id.show_btn);

        //Creating anonymous class declaration for the onClick event for button.
        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Creating reference of DBHelper.
                DBHelper db=new DBHelper(getApplicationContext());

                //Creating object of Student.
                Student student =new Student();

                //Initializing values of object.
                student.nameOfStudent=nameET.getText().toString();
                student.ageOfStudent=ageET.getText().toString();
                student.employeePhoto= BitmapFactory.decodeResource(getResources(),R.drawable.acadgild);

                //Adding the Object data into database.
                int id=db.addData(student);

                //Creating intent to switch to another Activity.
                Intent intent=new Intent(MainActivity.this,ShowStudent.class);
                //Putting student ID in the intent.
                intent.putExtra("stud_id",id);
                //Starting Activity.
                startActivity(intent);
            }
        });

    }

}
