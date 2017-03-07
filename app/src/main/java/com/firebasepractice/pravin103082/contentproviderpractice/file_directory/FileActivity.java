package com.firebasepractice.pravin103082.contentproviderpractice.file_directory;

import android.app.Activity;
import android.content.Intent;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebasepractice.pravin103082.contentproviderpractice.R;

import java.io.File;

import static android.R.attr.port;

public class FileActivity extends AppCompatActivity {

    private String path;
    private File downloadTarget;
    public final int fileRequestID = 55;
    public final int port = 7950;

    private Intent serverServiceIntent;

    private boolean serverThreadActive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        path = "/";
        downloadTarget = new File(path);
        serverServiceIntent = null;
        serverThreadActive = false;
    }

    public void startFileBrowseActivity(View view) {

        Intent clientStartIntent = new Intent(this, FileDirectoryActivity.class);
        startActivityForResult(clientStartIntent, fileRequestID);
        //Path returned to onActivityResult

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == fileRequestID) {
            //Fetch result
            File targetDir = (File) data.getExtras().get("file");

            if(targetDir.isDirectory())
            {
                if(targetDir.canWrite())
                {
                    downloadTarget = targetDir;
                    TextView filePath = (TextView) findViewById(R.id.server_file_path);
                    filePath.setText(targetDir.getPath());
                    setServerFileTransferStatus("Download directory set to " + targetDir.getName());

                }
                else
                {
                    setServerFileTransferStatus("You do not have permission to write to " + targetDir.getName());
                }

            }
            else
            {  downloadTarget = targetDir;
                TextView filePath = (TextView) findViewById(R.id.server_file_path);
                filePath.setText(targetDir.getPath());
                //setServerFileTransferStatus("The selected file is not a directory. Please select a valid download directory.");
            }

        }
    }

    public void startServer(View view) {

        //If server is already listening on port or transfering data, do not attempt to start server service
        if(!serverThreadActive)
        {
            //Create new thread, open socket, wait for connection, and transfer file

            serverServiceIntent = new Intent(this, ServerService.class);
            serverServiceIntent.putExtra("saveLocation", downloadTarget);
            serverServiceIntent.putExtra("port", new Integer(port));
            serverServiceIntent.putExtra("serverResult", new ResultReceiver(null) {
                @Override
                protected void onReceiveResult(int resultCode, final Bundle resultData) {

                    if(resultCode == port )
                    {
                        if (resultData == null) {
                            //Server service has shut down. Download may or may not have completed properly.
                            serverThreadActive = false;


                            final TextView server_status_text = (TextView) findViewById(R.id.server_status_text);
                            server_status_text.post(new Runnable() {
                                public void run() {
                                    server_status_text.setText(R.string.server_stopped);
                                }
                            });


                        }
                        else
                        {
                            final TextView server_file_status_text = (TextView) findViewById(R.id.server_file_transfer_status);

                            server_file_status_text.post(new Runnable() {
                                public void run() {
                                    server_file_status_text.setText((String)resultData.get("message"));
                                }
                            });
                        }
                    }

                }
            });

            serverThreadActive = true;
            startService(serverServiceIntent);

            //Set status to running
            TextView serverServiceStatus = (TextView) findViewById(R.id.server_status_text);
            serverServiceStatus.setText(R.string.server_running);

        }
        else
        {
            //Set status to already running
            TextView serverServiceStatus = (TextView) findViewById(R.id.server_status_text);
            serverServiceStatus.setText("The server is already running");

        }
    }

    public void stopServer(View view) {


        //stop download thread
        if(serverServiceIntent != null)
        {
            stopService(serverServiceIntent);

        }

    }

    public void setServerWifiStatus(String message)
    {
        TextView server_wifi_status_text = (TextView) findViewById(R.id.server_wifi_status_text);
        server_wifi_status_text.setText(message);
    }

    public void setServerStatus(String message)
    {
        TextView server_status_text = (TextView) findViewById(R.id.server_status_text_2);
        server_status_text.setText(message);
    }


    public void setServerFileTransferStatus(String message)
    {
        TextView server_status_text = (TextView) findViewById(R.id.server_file_transfer_status);
        server_status_text.setText(message);
    }
}
