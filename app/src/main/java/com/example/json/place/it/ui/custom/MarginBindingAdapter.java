package com.example.json.place.it.ui.custom;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.BindingAdapter;

public class MarginBindingAdapter {
    @BindingAdapter("android:layout_marginStart")
    public static void setLayoutMarginRight(View view, float margin) {
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (lp != null) {
            lp.setMargins((int) margin, lp.topMargin, lp.rightMargin, lp.bottomMargin);
            view.setLayoutParams(lp);
        }
    }

}