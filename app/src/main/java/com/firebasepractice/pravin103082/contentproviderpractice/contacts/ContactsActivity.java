package com.firebasepractice.pravin103082.contentproviderpractice.contacts;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebasepractice.pravin103082.contentproviderpractice.R;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.WRITE_CONTACTS;

public class ContactsActivity extends AppCompatActivity {

    Button btnAddContact, btnShowContacts;
    LinearLayout linRootContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        linRootContacts = (LinearLayout) findViewById(R.id.linRootContacts);
        btnAddContact = (Button) findViewById(R.id.btnAddContact);
        btnShowContacts = (Button) findViewById(R.id.btnShowContacts);

        btnShowContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {

                    getAllContacts();
                } else {
                    requestPersmission();
                }
            }
        });
    }


    public boolean checkPermission() {
        int readContacts = ActivityCompat.checkSelfPermission(this, READ_CONTACTS);
        int writeContacts = ActivityCompat.checkSelfPermission(this, WRITE_CONTACTS);
        if (readContacts == PackageManager.PERMISSION_GRANTED && writeContacts == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private static final int PERMISSION_REQUEST_CODE = 1;

    public void requestPersmission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_CONTACTS, WRITE_CONTACTS}, PERMISSION_REQUEST_CODE);
    }


    public void getAllContacts() {
        /*ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        *//*Toast.makeText(ContactsActivity.this, "Name: " + name
                                + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();*//*
                        Log.e("Name >"+name,"Phone"+phoneNo);
                    }
                    pCur.close();
                }
            }
        }*/

        new GetAllContacts().execute();
    }


    class GetAllContacts extends AsyncTask<Void, ContactModel, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            ContentResolver cr = getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);

            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String photo=cur.getString(cur.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
                    if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                        /*Toast.makeText(ContactsActivity.this, "Name: " + name
                                + ", Phone No: " + phoneNo, Toast.LENGTH_SHORT).show();*/
                            ContactModel contactModel = new ContactModel();
                            contactModel.setName(name);
                            contactModel.setPhoneNo(phoneNo);
                            contactModel.setPhtoUri(photo);
                            publishProgress(contactModel);
                            break;
                        }
                        pCur.close();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(ContactModel... values) {
            super.onProgressUpdate(values);
            View contactView = getLayoutInflater().inflate(R.layout.single_row_contacts, null);
            TextView txtName = (TextView) contactView.findViewById(R.id.txtName);
            TextView txtPhoneNo = (TextView) contactView.findViewById(R.id.txtPhoneNo);
            ImageView imageView = (ImageView)contactView.findViewById(R.id.imgContact);

            if(values[0].getPhtoUri()!=null){
                Uri uri=Uri.parse(values[0].getPhtoUri());
                imageView.setImageURI(uri);
            }else {
                imageView.setImageResource(R.mipmap.ic_launcher);
            }

            txtName.setText(values[0].getName());
            txtPhoneNo.setText(values[0].getPhoneNo());
            linRootContacts.addView(contactView);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(ContactsActivity.this, "All Contacts updated", Toast.LENGTH_SHORT).show();
        }
    }
}
