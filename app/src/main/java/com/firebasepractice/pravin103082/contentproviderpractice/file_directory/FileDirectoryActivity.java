package com.firebasepractice.pravin103082.contentproviderpractice.file_directory;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebasepractice.pravin103082.contentproviderpractice.R;

import java.io.File;
import java.util.ArrayList;

public class FileDirectoryActivity extends AppCompatActivity {
    private String root;
    private String currentPath;

    private ArrayList<String> targets;
    private ArrayList<String> paths;


    private File targetFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_directory);

       // getActionBar().setDisplayHomeAsUpEnabled(true);

        root = "/";
        currentPath = root;

        targets = null;
        paths = null;

        targetFile = null;

        showDir(currentPath);
    }

    public void selectDirectory(View view) {

        File f = new File(currentPath);
        targetFile = f;

        //Return target File to activity
        returnTarget();


    }


    public void setCurrentPathText(String message)
    {
        TextView fileTransferStatusText = (TextView) findViewById(R.id.current_path);
        fileTransferStatusText.setText(message);
    }


    private void showDir(String targetDirectory){

        setCurrentPathText("Current Directory: " + currentPath);

        targets = new ArrayList<String>();
        paths = new ArrayList<String>();

        File f = new File(targetDirectory);
        File[] directoryContents = f.listFiles();


        if (!targetDirectory.equals(root))

        {
            targets.add(root);
            paths.add(root);
            targets.add("../");
            paths.add(f.getParent());
        }

        for(File target: directoryContents)
        {
            if(target.canRead())
            paths.add(target.getPath());

            if(target.isDirectory())
            {
                if(target.canRead())
                targets.add(target.getName() + "/");
            }
            else
            {
                if(target.canRead())
                targets.add(target.getName());

            }

        }

        ListView fileBrowserListView = (ListView) findViewById(R.id.file_browser_listview);

        ArrayAdapter<String> directoryData = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, targets);
        fileBrowserListView.setAdapter(directoryData);




        fileBrowserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View view, int pos,long id) {

                File f = new File(paths.get(pos));

                if(f.isFile())
                {
                    targetFile = f;
                    returnTarget();
                    //Return target File to activity
                }
                else
                {
                    //f must be a dir
                    if(f.canRead())
                    {
                        currentPath = paths.get(pos);
                        showDir(paths.get(pos));
                    }

                }


            }
            // TODO Auto-generated method stub
        });

	    /*
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("WiFi Direct File Transfer");
		*/


    }
public static final String TAG=FileDirectoryActivity.class.getSimpleName();
    public void returnTarget()
    {
        Log.e(TAG, "returnTarget: "+targetFile);
        Toast.makeText(FileDirectoryActivity.this,""+targetFile,Toast.LENGTH_SHORT).show();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("file", targetFile);
        setResult(RESULT_OK, returnIntent);
        finish();

    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FileDirectoryActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Close Application...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want Close Application?");

        // Setting Icon to Dialog

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                // Write your code here to invoke YES event
                finish();
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();

    }
}
