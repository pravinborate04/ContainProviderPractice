package com.firebasepractice.pravin103082.contentproviderpractice.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebasepractice.pravin103082.contentproviderpractice.R;
import com.firebasepractice.pravin103082.contentproviderpractice.search.whatsapp_like_search.WhatsAppActivity;

public class SearchMainActivity extends AppCompatActivity {

    Button btnNormalSearch,btnVoiceSeach,btnWhatsapplikeSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_main);

        btnWhatsapplikeSearch=(Button)findViewById(R.id.btnWhatsapplikeSearch);
        btnNormalSearch=(Button)findViewById(R.id.btnNormalSearch);
        btnVoiceSeach=(Button)findViewById(R.id.btnVoiceSeach);


        btnNormalSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchMainActivity.this,SearchActivity.class);
                //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        btnVoiceSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchMainActivity.this,VoiceSearchActivity.class);
                //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        btnWhatsapplikeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchMainActivity.this,WhatsAppActivity.class);
                //              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}
