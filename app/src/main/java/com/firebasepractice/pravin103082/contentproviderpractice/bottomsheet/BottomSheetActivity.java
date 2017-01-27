package com.firebasepractice.pravin103082.contentproviderpractice.bottomsheet;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebasepractice.pravin103082.contentproviderpractice.R;

public class BottomSheetActivity extends AppCompatActivity {
    Button mButtonShowBottomSheet;
    Button mCollapseBottomSheet;
    Button mHideBottomSheet;
    Button mShowBottomSheetDialog;
    View mLayoutBottomSheet;
    TextView mTextViewBottomSheet;

    private BottomSheetBehavior mBottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

        mButtonShowBottomSheet = (Button) findViewById(R.id.button_show_bottom_sheet);
        mCollapseBottomSheet = (Button) findViewById(R.id.button_collapse_bottom_sheet);
        mHideBottomSheet = (Button) findViewById(R.id.button_hide_bottom_sheet);
        mShowBottomSheetDialog = (Button) findViewById(R.id.button_show_bottom_sheet_dialog);
        mLayoutBottomSheet=(View)findViewById(R.id.layout_bottom_sheet);
        mTextViewBottomSheet=(TextView)findViewById(R.id.text_view_sheet_title);


        mBottomSheetBehavior = BottomSheetBehavior.from(mLayoutBottomSheet);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        mTextViewBottomSheet.setText(getString(R.string.text_showing_sheet_content));
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Toast.makeText(BottomSheetActivity.this,"STATE_HIDDEN",Toast.LENGTH_SHORT).show();
                    default:
                        mTextViewBottomSheet.setText(getString(R.string.text_pull_to_show_more));
                        break;

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });


        mButtonShowBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        mCollapseBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        mHideBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });


        mShowBottomSheetDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomBottomSheetDialog bottomSheetDialog = CustomBottomSheetDialog.getInstance();
                bottomSheetDialog.show(getSupportFragmentManager(), "Custom Bottom Sheet");
            }
        });
    }
}
