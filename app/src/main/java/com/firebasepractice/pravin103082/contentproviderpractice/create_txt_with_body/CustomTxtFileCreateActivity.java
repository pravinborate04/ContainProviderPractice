package com.firebasepractice.pravin103082.contentproviderpractice.create_txt_with_body;

import android.content.Context;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebasepractice.pravin103082.contentproviderpractice.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CustomTxtFileCreateActivity extends AppCompatActivity {

    Button btnCreateTxtFile;
    EditText edtBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_txt_file_create);
        edtBody=(EditText)findViewById(R.id.edtBody);
        btnCreateTxtFile=(Button)findViewById(R.id.btnCreateTxtFile);
        btnCreateTxtFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                generateNoteOnSD(CustomTxtFileCreateActivity.this,"myFile.txt",edtBody.getText().toString());
            }
        });

    }



    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Log.e("directory",gpxfile.getPath());
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
