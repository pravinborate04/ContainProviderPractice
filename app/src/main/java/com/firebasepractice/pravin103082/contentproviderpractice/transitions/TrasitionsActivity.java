package com.firebasepractice.pravin103082.contentproviderpractice.transitions;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;

import com.firebasepractice.pravin103082.contentproviderpractice.R;

public class TrasitionsActivity extends AppCompatActivity {


    Button txtStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasitions);
        txtStart=(Button)findViewById(R.id.txtStart);


        txtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setExitTransition(new Explode());
                }
            }
        });
    }


}
