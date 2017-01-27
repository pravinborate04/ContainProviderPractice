package com.firebasepractice.pravin103082.contentproviderpractice;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebasepractice.pravin103082.contentproviderpractice.bluetooth.BluetoothActivity;
import com.firebasepractice.pravin103082.contentproviderpractice.bottomsheet.BottomSheetActivity;
import com.firebasepractice.pravin103082.contentproviderpractice.calender.CalenderActivity;
import com.firebasepractice.pravin103082.contentproviderpractice.contacts.ContactsActivity;
import com.firebasepractice.pravin103082.contentproviderpractice.fab.FabActivity;
import com.firebasepractice.pravin103082.contentproviderpractice.file_directory.FileActivity;
import com.firebasepractice.pravin103082.contentproviderpractice.hotspot.HotSpotActivity;
import com.firebasepractice.pravin103082.contentproviderpractice.search.SearchMainActivity;
import com.firebasepractice.pravin103082.contentproviderpractice.transitions.TrasitionsActivity;
import com.firebasepractice.pravin103082.contentproviderpractice.wifi.WifiActivity;
import com.firebasepractice.pravin103082.contentproviderpractice.wifidirect.WifiDirectActivity;

public class MainActivity extends AppCompatActivity {

    private static final int CONTACT_REQ_CODE = 111;

    private static final int CALENDER_REQ_CODE = 222;

    private static final int LOCATION_REQ_CODE = 333;

    private static final int GET_ACCOUNTS=444;

    private static final String TAG = "MAin";
    private static final int REQ_STORAGE = 555;
    Button btnCalender, btnContacts, btnSearch, btnTransitions, btnWifiDirect, btnWifiOnly,btnHotspot,btnBlueTooth
            ,btnBottomSheet,btnFileDirectory,btnFab;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCalender = (Button) findViewById(R.id.btnCalender);
        btnContacts = (Button) findViewById(R.id.btnContacts);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnTransitions = (Button) findViewById(R.id.btnTransitions);
        btnWifiDirect = (Button) findViewById(R.id.btnWifiDirect);
        btnWifiOnly = (Button) findViewById(R.id.btnWifiOnly);
        btnHotspot=(Button)findViewById(R.id.btnHotspot);
        btnBlueTooth=(Button)findViewById(R.id.btnBlueTooth);
        btnBottomSheet=(Button)findViewById(R.id.btnBottomSheet);
        btnFileDirectory=(Button)findViewById(R.id.btnFileDirectory);
        btnFab=(Button)findViewById(R.id.btnFab);
        btnFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FabActivity.class);
                //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!checkGetAccountsPermission()){
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.GET_ACCOUNTS}, GET_ACCOUNTS);
            }
        }

        btnFileDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkStoragePermission()){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_STORAGE);

                }else {
                    Intent intent = new Intent(MainActivity.this, FileActivity.class);
                    //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

            }
        });
        btnBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BottomSheetActivity.class);
                //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        btnBlueTooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    // Device does not support Bluetooth
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        // Bluetooth is not enable :)
                        Toast.makeText(MainActivity.this,"Please Enable Bluetooh", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(MainActivity.this, BluetoothActivity.class);
                        //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }


            }
        });
        btnHotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HotSpotActivity.class);
                //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnWifiOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkLocationPermission()) {
                    Intent intent = new Intent(MainActivity.this, WifiActivity.class);
                    //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_REQ_CODE);
                    }
                    return;
                }
            }
        });
        btnWifiDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WifiDirectActivity.class);
                //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        btnTransitions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TrasitionsActivity.class);
                //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchMainActivity.class);
                //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        btnCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkCalenderPersmission()) {
                    Intent intent = new Intent(MainActivity.this, CalenderActivity.class);
                    //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR}, CALENDER_REQ_CODE);
                    }
                    return;
                }


            }
        });

        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkContactPersmission()) {
                    Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, CONTACT_REQ_CODE);
                    }
                    return;
                }

            }
        });
    }


    public boolean checkContactPersmission() {
        int readContacts = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS);
        int writeContacts = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_CONTACTS);

        if (readContacts == PackageManager.PERMISSION_GRANTED && writeContacts == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }

    }


    public boolean checkGetAccountsPermission(){
        int getAccountsPermission=ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.GET_ACCOUNTS);
        if(getAccountsPermission==PackageManager.PERMISSION_GRANTED){
            return true;
        }else {
            return false;
        }
    }

    public boolean checkCalenderPersmission() {
        int readContacts = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALENDAR);
        int writeContacts = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALENDAR);

        if (readContacts == PackageManager.PERMISSION_GRANTED && writeContacts == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkLocationPermission() {
        int fineLocation = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocation = ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);

        if (fineLocation == PackageManager.PERMISSION_GRANTED && coarseLocation == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    public boolean checkStoragePermission(){
        int readStorage= ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writeStorage=ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(readStorage==PackageManager.PERMISSION_GRANTED && writeStorage==PackageManager.PERMISSION_GRANTED){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CONTACT_REQ_CODE) {
            if (grantResults.length > 0) {

                boolean checkReadPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean checkWritePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (checkReadPermission && checkWritePermission) {
                    Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
                    //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Log.e(TAG, "onRequestPermissionsResult: " + ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS));

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
                        try {
                            showMessageOKCancel("Go to Settings to Grant the permission", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String packageName = getPackageName();
                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.parse("package:" + packageName));
                                    startActivity(intent);
                                }
                            });
                            //Open the specific App Info page:
                        } catch (ActivityNotFoundException e) {
                            //e.printStackTrace();
                            Log.e("inside", "catch");
                            //Open the generic Apps page:
                            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                            startActivity(intent);

                        }
                    }
                }
            }
        }
        if (requestCode == CALENDER_REQ_CODE) {
            if (grantResults.length > 0) {

                boolean checkReadPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean checkWritePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (checkReadPermission && checkWritePermission) {
                    Intent intent = new Intent(MainActivity.this, CalenderActivity.class);
                    //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Log.e(TAG, "onRequestPermissionsResult: " + ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CALENDAR));

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
                        try {
                            showMessageOKCancel("Go to Settings to Grant the permission", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String packageName = getPackageName();
                                    Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    intent.setData(Uri.parse("package:" + packageName));
                                    startActivity(intent);
                                }
                            });
                            //Open the specific App Info page:
                        } catch (ActivityNotFoundException e) {
                            //e.printStackTrace();
                            Log.e("inside", "catch");
                            //Open the generic Apps page:
                            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                            startActivity(intent);

                        }
                    }
                }
            }

        }


        if(requestCode == LOCATION_REQ_CODE){
            boolean checkReadPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean checkWritePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (checkReadPermission && checkWritePermission) {
                Intent intent = new Intent(MainActivity.this, WifiActivity.class);
                //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else {
                Log.e(TAG, "onRequestPermissionsResult: " + ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION));

                if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    try {
                        showMessageOKCancel("Go to Settings to Grant the permission", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String packageName = getPackageName();
                                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + packageName));
                                startActivity(intent);
                            }
                        });
                        //Open the specific App Info page:
                    } catch (ActivityNotFoundException e) {
                        //e.printStackTrace();
                        Log.e("inside", "catch");
                        //Open the generic Apps page:
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                        startActivity(intent);

                    }
                }
            }
        }



        if(requestCode == GET_ACCOUNTS){
            boolean checkReadPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;

            if (checkReadPermission) {

            }else {
                Log.e(TAG, "onRequestPermissionsResult: " + ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION));

                if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.GET_ACCOUNTS)) {
                    try {
                        showMessageOKCancel("Go to Settings to Grant the permission", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String packageName = getPackageName();
                                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + packageName));
                                startActivity(intent);
                            }
                        });
                        //Open the specific App Info page:
                    } catch (ActivityNotFoundException e) {
                        //e.printStackTrace();
                        Log.e("inside", "catch");
                        //Open the generic Apps page:
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                        startActivity(intent);

                    }
                }
            }
        }

        if(requestCode == REQ_STORAGE){
            boolean checkReadPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean checkWritePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (checkReadPermission && checkWritePermission) {
                Intent intent = new Intent(MainActivity.this, FileActivity.class);
                //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }else {

                if (!ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    try {
                        showMessageOKCancel("Go to Settings to Grant the permission", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String packageName = getPackageName();
                                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + packageName));
                                startActivity(intent);
                            }
                        });
                        //Open the specific App Info page:
                    } catch (ActivityNotFoundException e) {
                        //e.printStackTrace();
                        Log.e("inside", "catch");
                        //Open the generic Apps page:
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                        startActivity(intent);

                    }
                }
            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("Settings", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
}
