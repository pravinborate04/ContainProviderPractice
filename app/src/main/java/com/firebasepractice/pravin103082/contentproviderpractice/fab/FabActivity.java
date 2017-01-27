package com.firebasepractice.pravin103082.contentproviderpractice.fab;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;

import com.firebasepractice.pravin103082.contentproviderpractice.R;

public class FabActivity extends AppCompatActivity {

    Button btnShow;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);

        btnShow=(Button)findViewById(R.id.btnShow);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingActionButton);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(floatingActionButton.getVisibility()==View.VISIBLE){
                    btnShow.setText("Show");
                    int cx = floatingActionButton.getWidth()- dpToPx(30);
                    int cy = floatingActionButton.getHeight()- dpToPx(30);

                    Log.e("X "+cx," Y "+cy);
// get the initial radius for the clipping circle
                    float initialRadius = (float) Math.hypot(cx, cy);
                    Log.e("radius",""+initialRadius);
// create the animation (the final radius is zero)
                    Animator anim =
                            null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        anim = ViewAnimationUtils.createCircularReveal(floatingActionButton, cx, cy, initialRadius, 0);
                    }

// make the view invisible when the animation is done
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            floatingActionButton.setVisibility(View.INVISIBLE);
                        }
                    });

                    anim.start();

                }else {
                    btnShow.setText("InVisible");
                    int cx = floatingActionButton.getWidth() - dpToPx(30);
                    int cy = floatingActionButton.getHeight() - dpToPx(30);
                    float finalRadius = (float) Math.hypot(cx, cy);
                    Animator anim = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        anim = ViewAnimationUtils.createCircularReveal(floatingActionButton, cx, cy, 0, finalRadius);
                    }
                    floatingActionButton.setVisibility(View.VISIBLE);
                    anim.start();
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

}
