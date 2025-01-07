package lk.javainstitute.app18note_store_project;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

import lk.javainstitute.app18note_store_project.model.SQLiteHelper;

public class CreateNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_note);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button button2  =findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editeText1 = findViewById(R.id.editTextText1);
                EditText editText2 = findViewById(R.id.editText2);

                if(editeText1.getText().toString().isEmpty()){
                    Toast.makeText(CreateNoteActivity.this, "Please Fill Title", Toast.LENGTH_SHORT).show();

                }else if(editText2.getText().toString().isEmpty()){
                    Toast.makeText(CreateNoteActivity.this, "Please Fill Content", Toast.LENGTH_SHORT).show();

                }else{


                    SQLiteHelper sqLiteHelper = new SQLiteHelper(
                            CreateNoteActivity.this,
                            "noteapp.db",
                            null,
                            1
                    );


                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            SQLiteDatabase sqLiteDatabase = sqLiteHelper.getWritableDatabase();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("title",editeText1.getText().toString());
                            contentValues.put("content",editText2.getText().toString());

                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:ss");
                            contentValues.put("dateCreated",format.format(new Date()));

                            long insertedId = sqLiteDatabase.insert("note",null,contentValues);
                            Log.i("MyNoteBookLog",String.valueOf(insertedId));

                            sqLiteDatabase.close();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    editeText1.setText("");
                                    editText2.setText("");

                                    editeText1.requestFocus();
                                }
                            });

                        }
                    }).start();



                }

            }
        });

    }
}