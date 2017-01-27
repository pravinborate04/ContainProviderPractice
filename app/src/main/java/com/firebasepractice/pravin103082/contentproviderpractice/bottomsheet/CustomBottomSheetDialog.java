package com.firebasepractice.pravin103082.contentproviderpractice.bottomsheet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebasepractice.pravin103082.contentproviderpractice.R;


public class CustomBottomSheetDialog extends BottomSheetDialogFragment {
    public static CustomBottomSheetDialog getInstance() {
        return new CustomBottomSheetDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_custom_bottom_sheet, container, false);
    }
}
